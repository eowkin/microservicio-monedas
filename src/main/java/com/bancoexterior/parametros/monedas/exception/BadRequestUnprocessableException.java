package com.bancoexterior.parametros.monedas.exception;

public class BadRequestUnprocessableException extends RuntimeException {
	
	public BadRequestUnprocessableException(String codigo) {
		super(codigo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
