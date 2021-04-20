package com.bancoexterior.parametros.monedas.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.bancoexterior.parametros.monedas.response.Resultado;
import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponseActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.service.IMonedaService;
import com.bancoexterior.parametros.monedas.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequestMapping("/api/monedas")
@RequestMapping("${microservicio.path.pre}" + "${microservicio.ambiente}")
public class MonedaController {

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

		log.info(Servicios.MONEDASCONTROLLERI);
		log.info("MonedasRequest: " + monedasRequest);
		MonedaDtoResponse monedaDtoResponse;
		HttpStatus estatusCM;

		monedaDtoResponse = monedaService.consultaMonedas(monedasRequest);
		estatusCM = Utils.getHttpStatus(monedaDtoResponse.getResultado().getCodigo().trim());

		log.info("estatusCM: " + estatusCM);
		log.info("monedaDtoResponse: " + monedaDtoResponse);
		log.info(Servicios.MONEDASCONTROLLERF);
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
	 * @since 16/03/21
	 */

	@PostMapping(path = Servicios.MONEDASURLV1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> crearMoneda(@Valid @RequestBody MonedasRequest monedasRequest, BindingResult result,
			HttpServletRequest requestHTTP) {

		log.info(Servicios.MONEDASSERVICEI);
		log.info("monedasRequest: " + monedasRequest);
		if (result.hasErrors()) {

			ResponseBad responseBad = new ResponseBad();
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			log.info("errors: " + errors);

			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
			responseBad.getResultadoBAD()
					.setDescripcion(env.getProperty(Constantes.RES + errors.get(0), errors.get(0)));
			return new ResponseEntity<>(responseBad, httpStatusError);

		}

		MonedaDtoResponseActualizar response = new MonedaDtoResponseActualizar();
		Resultado resultado;
		HttpStatus estatusCM;

		if (monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			log.info("existe");
			ResponseBad responseBad = new ResponseBad();
			HttpStatus httpStatusError = Utils.getHttpStatus(CodRespuesta.CME2001);
			responseBad.getResultadoBAD().setCodigo(CodRespuesta.CME2001);
			responseBad.getResultadoBAD()
					.setDescripcion(env.getProperty(Constantes.RES + CodRespuesta.CME2001, CodRespuesta.CME2001));
			return new ResponseEntity<>(responseBad, httpStatusError);
		} else {
			resultado = monedaService.gestionCrearMoneda(monedasRequest, requestHTTP);
			log.info("resultado de regrso: " + resultado);
			estatusCM = Utils.getHttpStatus(resultado.getCodigo().trim());
			log.info("[==== FIN Convenio n° 1 Monedas - Controller ====]");
			log.info("estatusCM: " + estatusCM);
			log.info("response: " + response);
			log.info("[==== FIN Convenio n° 1 Monedas - Controller ====]");
			response.setResultado(resultado);
			if (resultado.getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
				log.info("se fue por aqui, buena respuesta");
				return new ResponseEntity<>(response, estatusCM);
			} else {
				log.info("se fue por aqui");
				return new ResponseEntity<>(response.getResultado(), estatusCM);
			}
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

		log.info(Servicios.MONEDASSERVICEI);
		log.info("monedasRequest: " + monedasRequest);
		if (result.hasErrors()) {

			ResponseBad responseBad = new ResponseBad();
			List<String> errors = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.toList());
			log.info("errors: " + errors);

			HttpStatus httpStatusError = Utils.getHttpStatus(errors.get(0));
			log.info("httpStatusError: " + httpStatusError);
			responseBad.getResultadoBAD().setCodigo(errors.get(0));
			responseBad.getResultadoBAD()
					.setDescripcion(env.getProperty(Constantes.RES + errors.get(0), errors.get(0)));
			return new ResponseEntity<>(responseBad, httpStatusError);

		}

		MonedaDtoResponseActualizar response = new MonedaDtoResponseActualizar();
		Resultado resultado;
		HttpStatus estatusCM;

		if (!monedaService.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			log.info("no existe");
			ResponseBad responseBad = new ResponseBad();
			HttpStatus httpStatusError = Utils.getHttpStatus("2000");
			log.info("httpStatusError: " + httpStatusError);
			responseBad.getResultadoBAD().setCodigo("2000");
			responseBad.getResultadoBAD().setDescripcion(env.getProperty(Constantes.RES + "2000", "2000"));
			return new ResponseEntity<>(responseBad, httpStatusError);
		} else {
			resultado = monedaService.gestionActualizarMoneda(monedasRequest, requestHTTP);
			log.info("resultado de regrso: " + resultado);
			estatusCM = Utils.getHttpStatus(resultado.getCodigo().trim());
			log.info("[==== FIN Convenio n° 1 Monedas - Controller ====]");
			log.info("estatusCM: " + estatusCM);
			log.info("response: " + response);
			log.info("[==== FIN Convenio n° 1 Monedas - Controller ====]");
			response.setResultado(resultado);
			if (resultado.getCodigo().trim().substring(0, 1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)) {
				log.info("se fue por aqui, buena respuesta");
				return new ResponseEntity<>(response, estatusCM);
			} else {
				log.info("se fue por aqui");
				return new ResponseEntity<>(response.getResultado(), estatusCM);
			}
		}

	}

}
