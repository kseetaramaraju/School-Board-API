package com.school.sba.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.AcademicProgramRepository;
import com.school.sba.Repository.SchoolRepository;
import com.school.sba.Service.AcademicProgramService;
import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.School;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

@Service
public class AcademicProgramServiceImpl implements AcademicProgramService{


	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	private ResponseStructure<List<AcademicProgramResponse>> liststructure;
	

	//mapping methods
	private AcademicProgram mapToAcademicProgram(AcademicProgramRequest academicProgramRequest)
	{
		return	AcademicProgram.builder()
				.programName(academicProgramRequest.getProgramName())
				.programType(academicProgramRequest.getProgramType())
				.beginsAt(academicProgramRequest.getBeginsAt())
				.endsAt(academicProgramRequest.getEndsAt())
				.build();
	}

	public AcademicProgramResponse mapToAcademicProgramResponse(AcademicProgram academicProgram)
	{
		return	AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programName(academicProgram.getProgramName())
				.programType(academicProgram.getProgramType())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
				.subjects(academicProgram.getSubjects())
				.build();
	}



	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			AcademicProgramRequest academicProgramRequest, int schoolId) {

		School school=schoolRepository.findById(schoolId).orElseThrow( ()-> new SchoolNotFoundException("School Not Found!"));

		AcademicProgram academicProgram = academicProgramRepository.save(mapToAcademicProgram(academicProgramRequest));
		academicProgram.setSchool(school);
	   academicProgram = academicProgramRepository.save(academicProgram);

		List<AcademicProgram> acadamicprograms=new ArrayList();
		acadamicprograms.add(academicProgram);

		school.setAcademicPrograms(acadamicprograms);

		school = schoolRepository.save(school);

		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Academic Program is Added Successfully!!");
		structure.setData(mapToAcademicProgramResponse(academicProgram));


		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> FindAllAcademicProgram(int schoolId) {
		
		return schoolRepository.findById(schoolId).map( school ->{
			
			List<AcademicProgram> academicPrograms = school.getAcademicPrograms();
			
			List<AcademicProgramResponse> listOfAcademicResponse = academicPrograms.stream()
			.map(this::mapToAcademicProgramResponse)
			.collect(Collectors.toList());
			
			if(academicPrograms.isEmpty())
			{
				liststructure.setStatus(HttpStatus.NO_CONTENT.value());
				liststructure.setMessage("No Academic Programs Not Found!!");
				liststructure.setData(listOfAcademicResponse);
				
				return new ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>>(liststructure,HttpStatus.NO_CONTENT);
			}
			else
			{
				liststructure.setStatus(HttpStatus.FOUND.value());
				liststructure.setMessage("Academic Programs Found Successfully!!");
				liststructure.setData(listOfAcademicResponse);
				
				return new ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>>(liststructure,HttpStatus.FOUND);
			}
			
		})
		
		
		.orElseThrow(()-> new SchoolNotFoundException("School Not Found!")  );
		
		
	}

}
