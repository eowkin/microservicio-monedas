package com.bancoexterior.parametros.monedas.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.exception.CodMonedaExistException;
import com.bancoexterior.parametros.monedas.exception.CodMonedaNoExistException;
import com.bancoexterior.parametros.monedas.exception.FieldErrorValidationException;
import com.bancoexterior.parametros.monedas.service.IMonedaService;

@Component
public class MonedaValidatorImpl implements IMonedaValidator{

	@Autowired
	IMonedaService monedaService;
	
	
	/**
	 * Nombre: validarCrear 
	 * Descripcion: Invocar metodo para realizar validacion
	 * de los parametros de entrada y demas validaciones 
	 * antes de procesar al endPoint crearMoneda.
	 *
	 * @param request     Objeto tipo MonedasRequest
	 * @param result     Objeto tipo BindingResult
	 * @return void
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
	@Override
	public void validarCrear(MonedasRequest monedasRequest, BindingResult result) {
		//Validando los valores de entrada
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new FieldErrorValidationException(errors.get(0));			
		}
		
		if (monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			throw new CodMonedaExistException(CodRespuesta.CME2001);
		}
		
	}

	/**
	 * Nombre: validarActualizar 
	 * Descripcion: Invocar metodo para realizar validacion
	 * de los parametros de entrada y demas validaciones 
	 * antes de procesar el endPoint actualizarMoneda.
	 *
	 * @param request     Objeto tipo MonedasRequest
	 * @param result     Objeto tipo BindingResult
	 * @return void
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	@Override
	public void validarActualizar(MonedasRequest monedasRequest, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new FieldErrorValidationException(errors.get(0));			

		}

		if (!monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			throw new CodMonedaNoExistException(CodRespuesta.CME2000);
		}
		
	}

}
