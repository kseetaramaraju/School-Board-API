package com.school.sba.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{

       //Helper method
		private ResponseEntity<Object> helperStructureMethod(HttpStatus httpStatus,String message,Object rootCause)
		{
			
			return new ResponseEntity<Object>( Map.of(
				                          "Status" , httpStatus.value(),
				                          "Message" , message,
				                          "RootCause" , rootCause),httpStatus );
		}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String,String> errormap=new HashMap<>();

		allErrors.forEach( error->
		{
			FieldError fieldError=(FieldError)error;
			errormap.put(fieldError.getField(),fieldError.getDefaultMessage());
		});

		return helperStructureMethod(HttpStatus.BAD_REQUEST,"Failed to Register The User", errormap);

	}

	
	@ExceptionHandler(UserNotFoundById.class)
	public ResponseEntity<Object> userNotFoundById(UserNotFoundById userNotFoundById)
	{		
		return helperStructureMethod(HttpStatus.NOT_FOUND,userNotFoundById.getMessage(),"User not found with given Id!") ;

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException constraintViolationException)
	{		
		return helperStructureMethod(HttpStatus.BAD_REQUEST,constraintViolationException.getMessage(),"User Data Not Saving Due To ConstraintVoilation!") ;

	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> sQLDataIntegretyViolationException(DataIntegrityViolationException dataIntegrityViolationException)
	{		
		return helperStructureMethod(HttpStatus.BAD_REQUEST,dataIntegrityViolationException.getMessage(),"User Data Not Saving Due To DataIntegrity Voilation!") ;

	}
	
	
	@ExceptionHandler(AdminAllReadyExistsException.class)
	public ResponseEntity<Object> adminAllReadyExistsException(AdminAllReadyExistsException adminAllReadyExistsException)
	{		
		return helperStructureMethod(HttpStatus.BAD_REQUEST,adminAllReadyExistsException.getMessage(),"There Should Be Only One Admin He Is ALready Present!") ;

	}


}
