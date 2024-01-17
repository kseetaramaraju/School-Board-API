package com.school.sba.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Component
@NoArgsConstructor
public class SchoolExistException extends RuntimeException {
	
	private String message;

}
