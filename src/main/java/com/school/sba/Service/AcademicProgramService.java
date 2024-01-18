package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

public interface AcademicProgramService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			AcademicProgramRequest academicProgramRequest, int schoolId);

}
