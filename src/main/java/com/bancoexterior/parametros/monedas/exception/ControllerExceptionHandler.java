package com.bancoexterior.parametros.monedas.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.response.Resultado;

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
    Resultado ExceptionHandler(Exception e) {
       
        Resultado response = new Resultado();
        
    	response.setCodigo(CodRespuesta.CME6000);
    	response.setDescripcion(env.getProperty(Constantes.RES + CodRespuesta.CME6000, CodRespuesta.CME6000)+" "+e.getMessage());
    	LOGGER.fatal(e.getMessage(), e);
    	return response;
    }

}
