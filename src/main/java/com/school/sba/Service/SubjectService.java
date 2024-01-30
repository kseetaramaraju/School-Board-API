package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.utility.ResponseStructure;

public interface SubjectService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(SubjectRequest subjectRequest, int programId);


	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects();
	
	

}
