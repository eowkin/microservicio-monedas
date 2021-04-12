package com.bancoexterior.parametros.monedas.exception;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Not Found Exception";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
