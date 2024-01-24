package com.school.sba.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdminCanNotBeAssignedToAcademicProgram extends RuntimeException{
	
	private String message;

}
