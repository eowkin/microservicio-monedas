package com.bancoexterior.parametros.monedas.exception;

public class MalFormedHeaderException extends BadRequestException{

	private static final String DESCRIPCION = "Cuerpo mal formado";
	
	public MalFormedHeaderException(String detail) {
		super(DESCRIPCION+ ". "+   detail);
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
