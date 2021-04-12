package com.bancoexterior.parametros.monedas.annotation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.util.Utils;



public class FechaValidate implements 
ConstraintValidator<AFechaValidate, String> {

	private String formato;
	
	
	/**
     * Nombre:                  FechaValidate
     * Descripcion:             Validar formato de la fecha ingresada

     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
     */
	
  @Override
  public void initialize(AFechaValidate fecha) {
		  this.formato = fecha.formato();
  }

  @Override
  public boolean isValid(String fechaValidar,
    ConstraintValidatorContext cxt) {
	 
	  fechaValidar = fechaValidar == null ? Constantes.BLANK:fechaValidar;
	  return Utils.validaFormatoFecha(fechaValidar,formato);
  

  }

}
