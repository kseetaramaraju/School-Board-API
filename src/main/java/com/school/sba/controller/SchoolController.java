package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.SchoolService;
import com.school.sba.entity.School;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class SchoolController {
	
	@Autowired
	public SchoolService schoolService;
	
	@PostMapping("/users/schools")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@RequestBody @Valid SchoolRequest schoolRequest)
	{
		return schoolService.saveSchool(schoolRequest);
	}
	
	@PutMapping("/users/schools/{schoolId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(@RequestBody @Valid SchoolRequest schoolRequest, @PathVariable int schoolId)
	{
		return schoolService.updateSchool(schoolRequest,schoolId);
	}
	
	@GetMapping("/users/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool( @PathVariable int schoolId)
	{
		return schoolService.findSchool(schoolId);
	}
	
	@DeleteMapping("/users/schools/{schoolId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(@PathVariable int schoolId)
	{
		return schoolService.deleteSchool(schoolId);
	}
	
	
	

}
