package com.bancoexterior.parametros.monedas.annotation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidate implements 
ConstraintValidator<ANotEmptyValidate, Object> {

	/**
     * Nombre:                  NotEmptyValidate
     * Descripcion:             Validar que el objeto no sea nulo

     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
     */
	
  @Override
  public void initialize(ANotEmptyValidate objeto) {
	//Do Nothing
  }

  @Override
  public boolean isValid(final Object object, final ConstraintValidatorContext cvc) {
      boolean isValid = true;

      if (object == null) { 
          isValid = false;
      } 

      return isValid;
  }

}
