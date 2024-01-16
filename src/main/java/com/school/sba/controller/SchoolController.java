package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.SchoolService;
import com.school.sba.entity.School;

@RestController
public class SchoolController {
	
	@Autowired
	public SchoolService schoolService;
	
	@PostMapping("/insertoneschoolobject")
	public void insertByElement(@RequestBody School school)
	{
		schoolService.insertByElement(school);
	}
	
	@PostMapping("/insertallschoolobjects")
	public void insertAll(@RequestBody List<School> schools)
	{
		schoolService.insertAll(schools);
	}
	
	

}
