package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

public interface SubjectService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveSubject(SubjectRequest subjectRequest, int programId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubject(SubjectRequest subjectRequest,int programId);
	
	

}
