package com.school.sba.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SubjectNotFoundByIdException extends RuntimeException {

	private String message;
}
