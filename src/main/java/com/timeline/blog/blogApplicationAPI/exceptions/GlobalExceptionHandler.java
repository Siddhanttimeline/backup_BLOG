package com.timeline.blog.blogApplicationAPI.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.timeline.blog.blogApplicationAPI.payloads.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		APIResponse response = new APIResponse(message,false);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(response);
//        return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
    }
    
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> handleAPIException(APIException ex){
		String message = ex.getMessage();
		APIResponse response = new APIResponse(message,false);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
    
	
}
