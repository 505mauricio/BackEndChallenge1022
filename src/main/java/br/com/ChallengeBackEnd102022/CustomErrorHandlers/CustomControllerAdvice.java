package br.com.ChallengeBackEnd102022.CustomErrorHandlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@ControllerAdvice
class CustomControllerAdvice {
	
	@Autowired
	MessageSource messageSource;
	
	
    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(
        Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(
            new ErrorResponse(
              status, 
              e.getMessage()
            ),
            status
        );
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
        Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

    // converting the stack trace to String
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    e.printStackTrace(printWriter);
    String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
            new ErrorResponse(
              status, 
              e.getMessage(), 
              stackTrace // specifying the stack trace in case of 500s
            ),
            status
        );
    }
    
    
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
     HttpStatus status = HttpStatus.UNAUTHORIZED;
     ErrorResponse exceptionMessage = new ErrorResponse(status,ex.getMessage());
    
     return new ResponseEntity<>(exceptionMessage, status);
    }
    
    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request) {
     HttpStatus status = HttpStatus.UNAUTHORIZED;
     return new ResponseEntity<>("Credenciais inválidas", status);
    }
    
    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>("Usuário e senha inválido", status);
       }
    
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(err-> messageSource.getMessage(err, locale))
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
     
       }
    
    @ExceptionHandler(value= {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> result = ex.getConstraintViolations();
        List<String> errorMessages = result.stream()
                .map(err->err.getMessage())
                .collect(Collectors.toList());
        		
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
     
       }
    
    @ExceptionHandler(value= {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest httpRequest) {
    	String error = ex.getMessage();  
    	String uri = httpRequest.getRequestURI();
    	if (uri.equals("/signup")) {
    		return new ResponseEntity<>("Login unavailable", HttpStatus.BAD_REQUEST);
    	}
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
     
       }
}