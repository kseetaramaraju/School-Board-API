package com.school.sba.ServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.AcademicProgramRepository;
import com.school.sba.Repository.SubjectRepository;
import com.school.sba.Service.SubjectService;
import com.school.sba.entity.Subject;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private SubjectRepository subjectRepository; 

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(SubjectRequest subjectRequest, int programId) {

		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {
					List<Subject> listOfSubjects = new ArrayList<Subject>();
					
					subjectRequest.getSubjectNames().forEach(name -> {
						 subjectRepository.findBySubjectName(name.toLowerCase()).map(subject -> {
							listOfSubjects.add(subject);
							return subject;
						}).orElseGet( () -> {
							Subject subject = new Subject();
							subject.setSubjectName(name.toLowerCase());
							subjectRepository.save(subject);
							listOfSubjects.add(subject);
							return subject;
						});
					});
					
					academicProgram.setSubjects(listOfSubjects);
					academicProgramRepository.save(academicProgram);
					
					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("subjects have been updated successfully");
					structure.setData(academicProgramServiceImpl.mapToAcademicProgramResponse(academicProgram));
					
					return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
					
				})
				.orElseThrow(() -> new AcademicProgramNotFoundException("academic program not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubject(SubjectRequest subjectRequest,int programId) {

		return  academicProgramRepository.findById(programId)

				.map( program -> {

					List<Subject> subjects=(program.getSubjects()!=null)? program.getSubjects():new ArrayList<Subject>(); 

					// to add new Subjects that are specified by Client

					subjectRequest.getSubjectNames().forEach(name ->{

						boolean isPresent=false;
						for(Subject subject:subjects)
						{
							isPresent=(name.equalsIgnoreCase(subject.getSubjectName()))?true:false;
							if(isPresent) break;
						}

						if(!isPresent)subjects.add(subjectRepository.findBySubjectName(name)
								.orElseGet( ()-> subjectRepository.save(Subject.builder().subjectName(name).build())));

					});

					// to remove Subjects that are not specified by the Client
					List<Subject> toBeRemoved=new ArrayList<Subject>();

					subjects.forEach(subject->{
						boolean isPresent=false;

						for(String name: subjectRequest.getSubjectNames())
						{
							isPresent=(subject.getSubjectName().equalsIgnoreCase(name))?true:false;
							if(!isPresent)break;
						}
						if(!isPresent) toBeRemoved.add(subject);

					});

					subjects.removeAll(toBeRemoved);

					program.setSubjects(subjects);
					academicProgramRepository.save(program);

					structure.setStatus(HttpStatus.OK.value());
					structure.setMessage("Updated the Subject List to AcademicProgram Successfully!!");
					structure.setData(academicProgramServiceImpl.mapToAcademicProgramResponse(program));

					return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure,HttpStatus.OK);
				})


				.orElseThrow( ()-> new AcademicProgramNotFoundException("Academic Program Not Found!") );

	}

}
