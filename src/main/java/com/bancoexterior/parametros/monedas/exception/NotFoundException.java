package com.bancoexterior.parametros.monedas.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String codigo) {
        super(codigo);
    }
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
