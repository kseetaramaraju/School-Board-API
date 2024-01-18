package com.school.sba.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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

	private AcademicProgramResponse mapToAcademicProgramResponse(AcademicProgram academicProgram)
	{
		return	AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programName(academicProgram.getProgramName())
				.programType(academicProgram.getProgramType())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
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

}
