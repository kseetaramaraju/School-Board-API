package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.AcademicProgram;

@Repository
public interface AcademicProgramRepository extends JpaRepository<AcademicProgram,Integer> {

	
	
}
