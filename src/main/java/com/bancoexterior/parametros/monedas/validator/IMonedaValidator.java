package com.bancoexterior.parametros.monedas.validator;

import org.springframework.validation.BindingResult;

import com.bancoexterior.parametros.monedas.dto.MonedasRequest;

public interface IMonedaValidator {
	
	public void validarCrear(MonedasRequest monedasRequest, BindingResult result);
	
	public void validarActualizar(MonedasRequest monedasRequest, BindingResult result);

}
