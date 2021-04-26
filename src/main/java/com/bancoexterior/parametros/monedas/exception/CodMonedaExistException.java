package com.bancoexterior.parametros.monedas.exception;

public class CodMonedaExistException extends BadRequestException{
	
	public CodMonedaExistException(String codigo) {
		super(codigo);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
