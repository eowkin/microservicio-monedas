package com.bancoexterior.parametros.monedas.exception;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.response.CustromErrorResponse;
import com.bancoexterior.parametros.monedas.response.ResponseBad;
import com.bancoexterior.parametros.monedas.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	
	@Autowired
	private Environment env;	
	private CustromErrorResponse response = new CustromErrorResponse();
	private String error;
	
	
    // error handle for @Valid
	
	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
    	log.info("hablame manito 2");
    	log.info("handleMethodArgumentNotValid");
    	log.info("ex: "+ex);
    	log.info("headers: "+headers);
    	log.info("status: "+status);
    	log.info("request: "+request);
    	
    	ResponseBad responseBad = new ResponseBad();
   
        //Get all errors
  
		List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
	 
        responseBad.getResultadoBAD().setCodigo(errors.get(0));
    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES+errors.get(0),errors.get(0)));
    	
    	error = responseBad+((ServletWebRequest)request).getRequest().getRequestURI();
       log.error(error);
       return handleExceptionInternal(ex, responseBad, headers, Utils.getHttpStatus(errors.get(0)), request);

    }
    
    /**
	 * Customize the response for HttpMessageNotReadableException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
    @Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
    	log.info("hablame manito");
    	log.info("handleHttpMessageNotReadable");
    	log.info("ex: "+ex);
    	log.info("headers: "+headers);
    	log.info("status: "+status);
    	log.info("request: "+request);
		response.setStatus(status.value());
		response.setError(ex.getClass().getSimpleName());
		response.setTimestamp(new Date().toString());
		response.setMessage(ex.getMessage());
		response.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		error = response+((ServletWebRequest)request).getRequest().getRequestURI();
		log.error(error);
	    return handleExceptionInternal(ex, response, headers, status, request);
	}

	
    
    
    
    

}
