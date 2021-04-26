package com.bancoexterior.parametros.monedas.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.response.ResponseBad;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;




@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(ControllerExceptionHandler.class);

	@Autowired
	private Environment env;
	
	
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBad ExceptionHandler(Exception e) {
    	LOGGER.info("ExceptionHandler");
        
    	ResponseBad responseBad = new ResponseBad();
    	responseBad.getResultadoBAD().setCodigo(CodRespuesta.CME6000);
    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES + CodRespuesta.CME6000, CodRespuesta.CME6000)+" "+e.getMessage());
    	/*
    	Resultado response = new Resultado();
        response.setCodigo(CodRespuesta.CME6000);
    	response.setDescripcion(env.getProperty(Constantes.RES + CodRespuesta.CME6000, CodRespuesta.CME6000)+" "+e.getMessage());*/
    	LOGGER.fatal(e.getMessage(), e);
    	return responseBad;
    }
    
   
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class
            //org.springframework.dao.DuplicateKeyException.class,
            //org.springframework.web.HttpRequestMethodNotSupportedException.class,
            //org.springframework.web.bind.MethodArgumentNotValidException.class,
            //org.springframework.web.bind.MissingRequestHeaderException.class,
            //org.springframework.web.bind.MissingServletRequestParameterException.class,
            //org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            //org.springframework.http.converter.HttpMessageNotReadableException.class
    })
    @ResponseBody
    public ResponseBad badRequest(Exception e) {
    	LOGGER.info("badRequest");
    	LOGGER.info("message: "+e.getMessage());
    	
    	ResponseBad responseBad = new ResponseBad();
    	responseBad.getResultadoBAD().setCodigo(e.getMessage());
    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES + e.getMessage(), e.getMessage()));
    	/*
    	Resultado response = new Resultado();
        response.setCodigo(e.getMessage());
    	response.setDescripcion(env.getProperty(Constantes.RES + e.getMessage(), e.getMessage()));*/
    	return responseBad;
    }
   
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
    	BadRequestUnprocessableException.class
    })
    @ResponseBody
    public ResponseBad badRequestUnprocessable(Exception e) {
    	LOGGER.info("badRequestUnprocessable");
    	LOGGER.info("message: "+e.getMessage());
    	
    	ResponseBad responseBad = new ResponseBad();
    	responseBad.getResultadoBAD().setCodigo(e.getMessage());
    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES + e.getMessage(), e.getMessage()));
    	/*
    	Resultado response = new Resultado();
        response.setCodigo(e.getMessage());
    	response.setDescripcion(env.getProperty(Constantes.RES + e.getMessage(), e.getMessage()));*/
    	return responseBad;
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
    	NotFoundException.class
    })
    @ResponseBody
    public ResponseBad notFound(Exception e) {
    	LOGGER.info("notFound");
    	LOGGER.info("message: "+e.getMessage());
    	
    	ResponseBad responseBad = new ResponseBad();
    	responseBad.getResultadoBAD().setCodigo(CodRespuesta.CME2005);
    	responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES + CodRespuesta.CME2005, CodRespuesta.CME2005));
    	/*
    	Resultado response = new Resultado();
        response.setCodigo(e.getMessage());
    	response.setDescripcion(env.getProperty(Constantes.RES + e.getMessage(), e.getMessage()));*/
    	return responseBad;
    }
}
