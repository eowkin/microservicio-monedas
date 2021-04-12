package com.bancoexterior.parametros.monedas.exception;


import com.bancoexterior.parametros.monedas.response.Resultado;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class ErrorMessage {

	
	@JsonProperty("resultado")
	private Resultado resultadoBAD;
	
	
    private final String error;
    private final String message;
    //private final String path;
    private final Integer code;

    ErrorMessage(Exception exception, Integer code) {
        
    	this.resultadoBAD = new Resultado();
    	this.resultadoBAD.setCodigo(String.valueOf(code)); 
    	this.resultadoBAD.setDescripcion(exception.getClass().getSimpleName() +" " +exception.getMessage());
    	this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
    }

}
