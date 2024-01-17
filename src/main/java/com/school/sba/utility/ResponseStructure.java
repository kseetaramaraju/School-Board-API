package com.school.sba.utility;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructure<T>{

	private int status;
	private String message;
	private T data;
	
	
}
