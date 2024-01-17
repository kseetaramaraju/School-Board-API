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

	@ExceptionHandler(IsNotAdminException.class)
	public ResponseEntity<Object> isNotAdminException(IsNotAdminException isNotAdminException)
	{		
		return helperStructureMethod(HttpStatus.BAD_REQUEST,isNotAdminException.getMessage(),"The User is Not Admin So he Cannot Create School!") ;

	}

	@ExceptionHandler(SchoolExistException.class)
	public ResponseEntity<Object> schoolExistException(SchoolExistException schoolExistException)
	{		
		return helperStructureMethod(HttpStatus.BAD_REQUEST,schoolExistException.getMessage(),"School Is Already Present So Can Not Create Another One!") ;

	}
	
	@ExceptionHandler(SchoolNotFoundException.class)
	public ResponseEntity<Object> schoolNotFoundException(SchoolNotFoundException schoolNotFoundException)
	{		
		return helperStructureMethod(HttpStatus.NOT_FOUND,schoolNotFoundException.getMessage(),"School Not Found with given Id!") ;

	}
	
	


}
