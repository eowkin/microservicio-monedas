package com.bancoexterior.parametros.monedas.exception;



public class FieldAlreadyExistException extends BadRequestException{
	private static final String DESCRIPCION = "Ya existe, esta en uso";

	public FieldAlreadyExistException(String detail) {
		super(DESCRIPCION+". "+detail);
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
