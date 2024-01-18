package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.AcademicProgramService;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

@RestController
public class AcademicProgramController {

	@Autowired
	private AcademicProgramService academicProgramService;
	
	@PostMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSchedule(@RequestBody AcademicProgramRequest academicProgramRequest,@PathVariable int schoolId )
	{
		
		return academicProgramService.saveAcademicProgram(academicProgramRequest,schoolId);
	}
	
	
	
}
