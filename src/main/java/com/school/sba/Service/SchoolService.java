package com.school.sba.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.sba.Repository.SchoolRepository;
import com.school.sba.entity.School;

@Service
public class SchoolService {
	
	@Autowired
	public SchoolRepository schoolRepository;
	
	
	public void insertByElement(School school)
	{
		schoolRepository.save(school);
	}
	
	public void insertAll(List<School> schools)
	{
		schoolRepository.saveAll(schools);
	}
	
	

}
