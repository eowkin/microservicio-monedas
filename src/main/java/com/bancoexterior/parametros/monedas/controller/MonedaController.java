package com.bancoexterior.parametros.monedas.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bancoexterior.parametros.monedas.response.ResponseBad;
import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponseActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.exception.CodMonedaExistException;
import com.bancoexterior.parametros.monedas.exception.CodMonedaNoExistException;
import com.bancoexterior.parametros.monedas.exception.FieldErrorValidationException;
import com.bancoexterior.parametros.monedas.service.IMonedaService;
import com.bancoexterior.parametros.monedas.util.Utils;





@RestController
@RequestMapping("${microservicio.path.pre}" + "${microservicio.ambiente}")
public class MonedaController {
	
	private static final Logger LOGGER = LogManager.getLogger(MonedaController.class);
	
	@Autowired
	IMonedaService monedaService;

	@Autowired
	private Environment env;
	
	
	

	/**
	 * Nombre: listAllMonedas 
	 * Descripcion: Invocar metodo para consultar monedas parametros
	 *
	 * @param request     Objeto tipo MonedasRequest
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 16/03/21
	 */
	@PostMapping(path = Servicios.MONEDASURLV1
			+ "/consultas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> listAllMonedas(@RequestBody MonedasRequest monedasRequest,
			HttpServletRequest requestHTTP) {

		LOGGER.info(Servicios.MONEDASCONTROLLERI);
		LOGGER.info(monedasRequest);
		MonedaDtoResponse monedaDtoResponse;
		HttpStatus estatusCM;

		monedaDtoResponse = monedaService.consultaMonedas(monedasRequest);
		estatusCM = Utils.getHttpStatus(monedaDtoResponse.getResultado().getCodigo().trim());

		LOGGER.info(estatusCM);
		LOGGER.info(monedaDtoResponse);
		LOGGER.info(Servicios.MONEDASCONTROLLERF);
		if (monedaDtoResponse.getResultado().getCodigo().trim().substring(0, 1)
				.equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			return new ResponseEntity<>(monedaDtoResponse, estatusCM);
		} else {

			return new ResponseEntity<>(monedaDtoResponse.getResultado(), estatusCM);
		}
	}

	/**
	 * Nombre: crearMoneda 
	 * Descripcion: Invocar metodo para ingresar moneda nueva
	 * @param request     Objeto tipo MonedasRequest
	 * @param BindingResult result
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @throws ApiUnprocessableEntity 
	 * @since 16/03/21
	 */

	@PostMapping(path = Servicios.MONEDASURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> crearMoneda(@Valid @RequestBody MonedasRequest monedasRequest, BindingResult result,
			HttpServletRequest requestHTTP) {

		LOGGER.info(Servicios.MONEDASSERVICEI);
		LOGGER.info(monedasRequest);
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			LOGGER.error(errors);
			throw new FieldErrorValidationException(errors.get(0));			

		}
		
		if (monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			throw new CodMonedaExistException(CodRespuesta.CME2001);
		}
		
		

		MonedaDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = monedaService.save(monedasRequest, requestHTTP);
		LOGGER.info(response);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		LOGGER.info(estatusCM);
		LOGGER.info(Servicios.MONEDASCONTROLLERF);
		
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			
			return new ResponseEntity<>(response,estatusCM);
		}else {
			
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
		
	}
	
	/**
	 * Nombre: crearMoneda 
	 * Descripcion: Invocar metodo para ingresar moneda nueva
	 * @param request     Objeto tipo MonedasRequest
	 * @param BindingResult result
	 * @param requestHTTP Objeto tipo HttpServletRequest
	 * @return ResponseEntity<Object>
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 16/03/21
	 */

	@PutMapping(path = Servicios.MONEDASURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> actualizarMoneda(@Valid @RequestBody MonedasRequest monedasRequest,
			BindingResult result, HttpServletRequest requestHTTP) {

		LOGGER.info(Servicios.MONEDASSERVICEI);
		LOGGER.info(monedasRequest);
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			LOGGER.error(errors);
			throw new FieldErrorValidationException(errors.get(0));			

		}

		if (!monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			throw new CodMonedaNoExistException(CodRespuesta.CME2000);
		}

		MonedaDtoResponseActualizar response;
		HttpStatus estatusCM;
		
		response = monedaService.actualizar(monedasRequest, requestHTTP);
		LOGGER.info(response);
		estatusCM = Utils.getHttpStatus(response.getResultado().getCodigo().trim());
		LOGGER.info(estatusCM);
		LOGGER.info(Servicios.MONEDASCONTROLLERF);
		
		if(response.getResultado().getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
			
			return new ResponseEntity<>(response,estatusCM);
		}else {
			
			return new ResponseEntity<>(response.getResultado(),estatusCM);
		}
	}

}
