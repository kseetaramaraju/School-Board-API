package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.utility.ResponseStructure;

public interface AcademicProgramService {

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			AcademicProgramRequest academicProgramRequest, int schoolId);

	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> FindAllAcademicProgram(int schoolId);

}
