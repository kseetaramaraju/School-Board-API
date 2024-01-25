package com.school.sba.Service;


import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.utility.ResponseStructure;



public interface SchoolService {

	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(SchoolRequest schoolRequest);

	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(SchoolRequest schoolRequest,int schoolId);

	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(int schoolId);

	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId);




}
