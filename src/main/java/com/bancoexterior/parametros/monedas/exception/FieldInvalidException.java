package com.bancoexterior.parametros.monedas.exception;

public class FieldInvalidException extends BadRequestException{
	private static final String DESCRIPCION = "Campo invalido";
	
	public FieldInvalidException(String detail) {
		super(DESCRIPCION+". "+detail);
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
