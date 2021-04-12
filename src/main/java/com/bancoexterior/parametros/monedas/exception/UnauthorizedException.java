package com.bancoexterior.parametros.monedas.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String DESCRIPTION = "Unauthorized Exception - 401";

    public UnauthorizedException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
