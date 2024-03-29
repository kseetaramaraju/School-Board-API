package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(@RequestBody AcademicProgramRequest academicProgramRequest,@PathVariable int schoolId )
	{
		
		return academicProgramService.saveAcademicProgram(academicProgramRequest,schoolId);
	}
	
	@GetMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> FindAllAcademicProgram( @PathVariable int schoolId )
	{
		
		return academicProgramService.FindAllAcademicProgram(schoolId);
	}
	
	
	
}
