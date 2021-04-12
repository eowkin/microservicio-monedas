package com.bancoexterior.parametros.monedas.exception;

public class ParametroNoValid extends BadRequestException {
    private static final String DESCRIPTION = "Not Found Exception";

    public ParametroNoValid(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}