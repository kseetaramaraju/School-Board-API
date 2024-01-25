package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.UserService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users/register") 
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.registerAdmin(userRequest);
	}
	
	@PostMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.saveUser(userRequest);
	}
	
	@DeleteMapping("/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable @Valid int userId )
	{
		return userService.deleteUser(userId);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@PathVariable @Valid int userId )
	{
		return userService.findUser(userId);
	}
	
	@PutMapping("/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable  int userId,@RequestBody UserRequest userRequest){
		return userService.updateUser(userId, userRequest);
	}
	
	@PutMapping("/academic-programs/{programId}/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> addUserToAcademicProgram(@PathVariable int programId ,@PathVariable int userId )
	{
		return userService.addUserToAcademicProgram(programId,userId);
	}
	
	@PutMapping("/subjects/{subjectId}/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(@PathVariable int subjectId,@PathVariable int userId )
	{
		return userService.addSubjectToTeacher(subjectId,userId);
	}
	
	

}
