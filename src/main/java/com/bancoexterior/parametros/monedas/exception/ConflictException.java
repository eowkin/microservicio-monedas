package com.bancoexterior.parametros.monedas.exception;

public class ConflictException extends RuntimeException {
    
	private static final String DESCRIPTION = "Conflict Exception";

    public ConflictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
