package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.AdminResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {
	
	public ResponseEntity<ResponseStructure<AdminResponse>> registerAdmin( UserRequest userRequest);
	
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> findUser( int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> assignUserToAcademicProgram(int programId, int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(int subjectId, int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userRequest);

	

}
