package com.bancoexterior.parametros.monedas.exception;

public class FieldErrorValidationException extends BadRequestUnprocessableException{
	
	public FieldErrorValidationException(String codigo) {
		super(codigo);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
