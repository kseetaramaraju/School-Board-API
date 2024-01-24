package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {
	
	public ResponseEntity<ResponseStructure<UserResponse>> saveUserRegister(UserRequest userRequest);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@Valid int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> addUserToAcademicProgram(int programId, int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(int subjectId, int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userRequest);

}
