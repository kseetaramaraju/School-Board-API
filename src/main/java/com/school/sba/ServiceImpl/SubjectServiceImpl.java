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
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.utility.ResponseEntityProxy;
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

	@Autowired
	private ResponseStructure<List<SubjectResponse>> listOfSubjectsStructure;


	public List<SubjectResponse> mapTOListOfSubjectResponse(List<Subject> listOfSubjects) {
		List<SubjectResponse> listOfSubjectResponse = new ArrayList<>();

		listOfSubjects.forEach(subject -> {
			SubjectResponse sr = new SubjectResponse();
			sr.setSubjectId(subject.getSubjectId());
			sr.setSubjectNames(subject.getSubjectName());
			listOfSubjectResponse.add(sr);
		});

		return listOfSubjectResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(SubjectRequest subjectRequest, int programId) {

		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {
					List<Subject> listOfSubjects = (academicProgram.getSubjects()!=null)? academicProgram.getSubjects():new ArrayList<Subject>();

					//To Add New Subject To Specified By Client To Academic Program
					subjectRequest.getSubjectNames().forEach(name -> {
						boolean isPresent=false;

						for(Subject subject:listOfSubjects)
						{
							isPresent= (name.equalsIgnoreCase(subject.getSubjectName()))?true:false;
							if(isPresent)
							{
								break;
							}
						}
						if(!isPresent)
						{
							listOfSubjects.add( subjectRepository.findBySubjectName(name)
									.orElseGet( ()-> subjectRepository.save(Subject.builder().subjectName(name).build())) );
						}	
					});


					//to remove the subject that are not specified by the client

					List<Subject> toBeRemoved = new ArrayList<Subject>();
					listOfSubjects.forEach(subject -> {
						boolean isPresent = false;
						for(String name : subjectRequest.getSubjectNames()) {
							isPresent = (subject.getSubjectName().equalsIgnoreCase(name)) ? true : false;
							if(isPresent) break;
						}
						if(!isPresent) toBeRemoved.add(subject);
					});

					listOfSubjects.removeAll(toBeRemoved);

					academicProgram.setSubjects(listOfSubjects);

					academicProgramRepository.save(academicProgram);


					return ResponseEntityProxy.setResponseStructure(HttpStatus.CREATED,
							"Subjects Added Sccessfully!!",
							academicProgramServiceImpl.mapToAcademicProgramResponse(academicProgram));

				})
				.orElseThrow(() -> new AcademicProgramNotFoundException("academic program not found"));

	}

	
	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects() {


		List<Subject> listOfSubjects = subjectRepository.findAll();

		if(listOfSubjects.isEmpty()) {

			return ResponseEntityProxy.setResponseStructure(HttpStatus.NOT_FOUND,
					"No Subjects Found!!",
					mapTOListOfSubjectResponse(listOfSubjects));
			
		}
		else {
			
			return ResponseEntityProxy.setResponseStructure(HttpStatus.FOUND,
					"Subjects Found!!",
					mapTOListOfSubjectResponse(listOfSubjects));
		}

	}



}
