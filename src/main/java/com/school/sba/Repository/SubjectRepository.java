package com.school.sba.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
	
	public Optional<Subject> findBySubjectName(String name);

}
