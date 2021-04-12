package com.bancoexterior.parametros.monedas.exception;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Forbidden Exception";

    public ForbiddenException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
