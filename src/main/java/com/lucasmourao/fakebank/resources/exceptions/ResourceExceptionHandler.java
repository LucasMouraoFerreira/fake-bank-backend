package com.lucasmourao.fakebank.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucasmourao.fakebank.services.exceptions.DatabaseException;
import com.lucasmourao.fakebank.services.exceptions.FieldRequiredException;
import com.lucasmourao.fakebank.services.exceptions.InsufficientBalanceException;
import com.lucasmourao.fakebank.services.exceptions.InvalidFormatException;
import com.lucasmourao.fakebank.services.exceptions.LimitExceededException;
import com.lucasmourao.fakebank.services.exceptions.NegativeValueException;
import com.lucasmourao.fakebank.services.exceptions.ObjectNotFoundException;
import com.lucasmourao.fakebank.services.exceptions.UsernameNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){
		String error = "Object not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request){
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(FieldRequiredException.class)
	public ResponseEntity<StandardError> fielRequiredException(FieldRequiredException e, HttpServletRequest request){
		String error = "Field required error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<StandardError> invalidFormatException(InvalidFormatException e, HttpServletRequest request){
		String error = "Invalid format error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(NegativeValueException.class)
	public ResponseEntity<StandardError> negativeValueException(NegativeValueException e, HttpServletRequest request){
		String error = "Nagative value error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<StandardError> insufficientBalanceException(InsufficientBalanceException e, HttpServletRequest request){
		String error = "Insufficient balance error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(LimitExceededException.class)
	public ResponseEntity<StandardError> limitExceededException(LimitExceededException e, HttpServletRequest request){
		String error = "Limit exceeded error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request){
		String error = "Account Not Found";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(),status.value(),error,e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}