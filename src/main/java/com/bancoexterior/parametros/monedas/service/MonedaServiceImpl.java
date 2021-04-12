package com.bancoexterior.parametros.monedas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.MonedaDto;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedasDtoRequest;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.response.Resultado;
import com.bancoexterior.parametros.monedas.entities.Moneda;
import com.bancoexterior.parametros.monedas.model.RegistrarAuditoriaRequest;
//import com.bancoexterior.tesoreria.ve.model.RegistrarAuditoriaRequest;
import com.bancoexterior.parametros.monedas.repository.IMonedaRepository;
import com.bancoexterior.parametros.monedas.util.Mapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MonedaServiceImpl implements IMonedaService {

	@Autowired
	private IMonedaRepository repo;

	@Autowired
	private Environment env;

	@Autowired
	private Mapper mapper;

	
	@Override
	public MonedaDtoResponse save(MonedasRequest monedasRequest) {
		Moneda obj = new Moneda();
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		//monedasDtoRequest.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		try {
			
			log.info("monedasDtoRequest: "+monedasRequest);
			
			MonedasDtoRequest monedasDtoRequest =  monedasRequest.getMonedasDtoRequest();
			log.info("monedasDtoRequest: "+monedasRequest);
			obj = mapper.map(monedasDtoRequest, Moneda.class);
			log.info("monedasDtoRequest: "+monedasRequest);
			obj.setCodUsuario(monedasRequest.getCodUsuarioMR());
			
			log.info("obj: "+obj);
			obj = repo.save(obj);
			response.setResultado(resultado);
			response.setListMonedasDto(repo.getMonedaByid(obj.getCodMoneda()));
			return response;
		} catch (Exception e) {
			log.error("no se pudo crear el usuario");
			response.getResultado().setCodigo(CodRespuesta.CME2001);
			resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME2001,CodRespuesta.CME2001).replace(Constantes.ERROR, Constantes.BLANK));
			return response;
		}

		
	}
	
	@Override
	public Resultado crear(MonedasRequest monedasRequest) {
		Moneda obj = new Moneda();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			log.info("monedasDtoRequest: "+monedasRequest);
			
			MonedasDtoRequest monedasDtoRequest =  monedasRequest.getMonedasDtoRequest();
			log.info("monedasDtoRequest: "+monedasRequest);
			obj = mapper.map(monedasDtoRequest, Moneda.class);
			log.info("moneda: "+obj);
			obj.setCodUsuario(monedasRequest.getCodUsuarioMR());
			
			log.info("obj: "+obj);
			obj = repo.save(obj);

			return resultado;
		} catch (Exception e) {
			resultado.setCodigo(CodRespuesta.CME2001);
			return resultado;
		}
		
	}
	
	@Override
	public MonedaDtoResponse getAll() {
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		List<MonedaDto> listMonedasDto = repo.getAll();
		
		if (listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		} else {
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		}
		
		response.setResultado(resultado);
		response.setListMonedasDto(repo.getAll());

		return response;
	}

	@Override
	public MonedaDtoResponse findAllDtoResponse() {
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		List<MonedaDto> listMonedasDto = repo.getAll();
		
		if (listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		} else {
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		}
		
		response.setResultado(resultado);
		response.setListMonedasDto(repo.getAll());

		return response;
	}
	
	@Override
	public MonedaDtoResponse getMonedasByParameter(String codMoneda, boolean flagActivo) {
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		log.info("getMonedasByParameter");
		log.info("codMoneda: "+codMoneda);
		log.info("flagActivo: "+flagActivo);
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		List<MonedaDto> listMonedasDto = repo.getMonedaByidAndFlag(codMoneda, flagActivo);
		
		if (listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		} else {
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		}
		
		response.setResultado(resultado);
		response.setListMonedasDto(listMonedasDto);

		return response;
	}
	
	@Override
	public MonedaDtoResponse getMonedasByFlagActivo(boolean flagActivo) {
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		List<MonedaDto> listMonedasDto = repo.getMonedaByFlagActivo(flagActivo);
		
		if (listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		} else {
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		}
		
		response.setResultado(resultado);
		response.setListMonedasDto(repo.getAll());

		return response;
	}
	
	
	@Override
	public MonedaDtoResponse get(String codMoneda) {
		MonedaDtoResponse response = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);

		List<MonedaDto> listMonedasDto = repo.getMonedaByid(codMoneda);
		if (listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		} else {
			resultado.setDescripcion(env.getProperty(Constantes.RES + resultado.getCodigo(), resultado.getCodigo()));
		}
		response.setResultado(resultado);
		response.setListMonedasDto(repo.getMonedaByid(codMoneda));

		return response;
	}
	
	@Override
	public List<MonedaDto> findAllMonedasDto(MonedaDto monedaDto) {

		List<MonedaDto> listMonedasDto = null;
		
		if(monedaDto.getCodMoneda() == null && monedaDto.getFlagActivo() == null) {
			log.info("hizo este query 1");
			listMonedasDto = repo.getAll();
		}
		
		if(monedaDto.getCodMoneda() != null && monedaDto.getFlagActivo() == null) {
			log.info("hizo este query 2");
			listMonedasDto = repo.getMonedaByid(monedaDto.getCodMoneda());
		}
		
		if(monedaDto.getCodMoneda() == null && monedaDto.getFlagActivo() != null) {
			log.info("hizo este query 3");
			listMonedasDto = repo.getMonedaByFlagActivo(monedaDto.getFlagActivo());
		}
		
		if(monedaDto.getCodMoneda() != null && monedaDto.getFlagActivo() != null) {
			log.info("hizo este query 4");
			listMonedasDto = repo.getMonedaByidAndFlag(monedaDto.getCodMoneda(),monedaDto.getFlagActivo());
		}
		return listMonedasDto;
	}

	@Override
	public List<Moneda> findAll() {
		return repo.findAll();
	}

	@Override
	public List<MonedaDto> findAllGlobalMapper() {

		List<Moneda> listMonedas = repo.findAll();
		List<MonedaDto> listMonedasDto = new ArrayList<MonedaDto>();

		for (Moneda moneda : listMonedas) {

			MonedaDto monedaDto = mapper.map(moneda, MonedaDto.class);
			listMonedasDto.add(monedaDto);
		}

		return listMonedasDto;
	}

	@Override
	public MonedaDto findById(String codMoneda) {
		
		
		Moneda moneda = repo.findById(codMoneda).orElse(null);
		
		if (moneda == null) {
			return null;
		}else {
			
			return mapper.map(moneda, MonedaDto.class);
			//return monedaDto;
		}
		
		
	}

	@Override
	public boolean existsById(String codMoneda) {
		return repo.existsById(codMoneda);
	}
	
	
	@Override
	public MonedaDtoResponse consultaMonedas(MonedasRequest request) {
		log.info(Servicios.MONEDASCONSULTASERVICEI);
		MonedaDtoResponse monedaDtoResponse = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		String codigo = CodRespuesta.C0000;
		String errorCM = Constantes.BLANK;
		List<MonedaDto> listMonedasDto;
		MonedaDto monedaDto =  new MonedaDto(request);
		MonedasDtoRequest monedasDtoRequest  = request.getMonedasDtoRequest();
		log.info("codMoneda: "+monedasDtoRequest.getCodMoneda());
		log.info("flagActivo: "+monedasDtoRequest.getFlagActivo());
		try {
			
			codigo = validaDatosConsulta(request);
			log.info("codigo: "+codigo);
			if(codigo.equalsIgnoreCase(CodRespuesta.C0000)) {
				log.info("monedaDto: "+monedaDto);	
				log.info("codMeneda: "+monedaDto.getCodMoneda());
				log.info("flagActivo: "+monedaDto.getFlagActivo());
				//consulta BD
				listMonedasDto = this.findAllMonedasDto(monedaDto);
				monedaDtoResponse.setListMonedasDto(listMonedasDto);
				
				//Validar Respuesta
				resultado = validaConsulta(listMonedasDto);
				codigo = resultado.getCodigo();
				errorCM = resultado.getDescripcion();
			}
		} catch (Exception e) {
			log.error(""+e);
			codigo = CodRespuesta.CME6000;
			errorCM = Constantes.EXC+e;
		}
		
		monedaDtoResponse.getResultado().setCodigo(codigo);
		monedaDtoResponse.getResultado().setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorCM));
		
		log.info("monedaDtoResponse: "+monedaDtoResponse);
		log.info(Servicios.MONEDASCONSULTASERVICEF);
		return monedaDtoResponse;
	}
	/*
	@Override
	public Resultado gestionMoneda(MonedasRequest request, HttpServletRequest requestHTTP) {
		log.info(Servicios.MONEDASSERVICEI);
		
		Resultado response = new Resultado();
		Resultado resultadoV ;
		String codigo ;
		String errorM ;
		//String microservicio = request.getFlagActualiza() == 0 ? Servicios.MONEDAS:Servicios.MONEDASACTUALIZAR;
		String microservicio = Servicios.MONEDAS;
		//MonedasBD monedas = new MonedasBD();
		//List<MonedaDto> monedasBDg;
		RegistrarAuditoriaRequest reAU = null;
		//monedas.setCodMonedaBD(request.getMonedaDatosMR().getCodMonedaMD());
		//monedas.setCodMonedaBD(request.getMonedasDtoRequest().getCodMoneda());
		try {
			
			reAU = new RegistrarAuditoriaRequest(request, microservicio,requestHTTP);
			
			
			
			codigo = resultadoV.getCodigo();
			errorM = resultadoV.getDescripcion();
			
		} catch (Exception e) {
			log.error(e);
			codigo = CodRespuesta.CME6000;
			errorM = Constantes.EXC+e;
		}
		
		response.setCodigo(codigo);
		response.setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorM));
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal("8");
			registrarAuditoriaBD(reAU, response, errorM);
		}
		
		LOGGER.info(Servicios.MONEDASSERVICEF);
		return response;
	}*/

	

	/**
     * Nombre:                  validaDatosConsulta
     * Descripcion:             Valida datos de entrada del metodo de consulta.
     *
     * @param  Objeto MonedasRequest
     * @return String  Codigo resultado de la evaluacion.
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
     */
	private String validaDatosConsulta(MonedasRequest request) {
		
		log.info(""+request);

		String codigo = CodRespuesta.C0000;
		String codMoneda;
		Boolean flagActivo;
		
		codMoneda = request.getMonedasDtoRequest().getCodMoneda() == null ? Constantes.MONEDADEFAULT:request.getMonedasDtoRequest().getCodMoneda();
		flagActivo = request.getMonedasDtoRequest().getFlagActivo() == null ? Boolean.parseBoolean(Constantes.TRUE) : request.getMonedasDtoRequest().getFlagActivo();
		
		request.getMonedasDtoRequest().setCodMoneda(codMoneda);
		request.getMonedasDtoRequest().setFlagActivo(flagActivo);
		request.getMonedasDtoRequest().setCodAlterno(Constantes.CODALTERNODEFAULT);
		request.getMonedasDtoRequest().setDescripcion(Constantes.CODALTERNODEFAULT);
	

		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<MonedasRequest>> errores = validator.validate(request);
		
	
			for (ConstraintViolation<MonedasRequest> cv : errores) {
				
				if ( !cv.getMessage().equalsIgnoreCase(Constantes.BLANK)) {
					codigo = cv.getMessage();
					 break;
				}

			}

		
		return codigo;
	}

	/**
     * Nombre:                  validaConsulta
     * Descripcion:             Metodo para evaluar el resultado de la consulta de las monedas
     *
     * @param  Objeto List<MonedasBD>
     * @return Resultado  Objeto con la información de la evaluacion.
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
    */ 
	
	private Resultado validaConsulta(List<MonedaDto> listMonedasDto) {
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(Constantes.BLANK);
		
		if(listMonedasDto.isEmpty()) {
			resultado.setCodigo(CodRespuesta.C0001);
			return resultado;
		}

		/*
	    if(monedasBD.get(0).getCodMonedaBD().equalsIgnoreCase(Constantes.SERROR)) {
	    	resultado.setCodigo(CodRespuesta.CME6002);
	    	resultado.setDescripcion(monedasBD.get(0).getDescripcionBD());
	    	 LOGGER.error(resultado);
	    	return resultado;
	    }*/

	    
	    log.info(""+resultado);
		return resultado;
		
	}

	@Override
	public Resultado gestionCrearMoneda(MonedasRequest request, HttpServletRequest requestHTTP) {
		
		log.info(Servicios.MONEDASSERVICEI+"gestionCrearMoneda");
		Moneda moneda = new Moneda();
		Resultado response = new Resultado();
		String codigo ;
		String errorM ;
		String microservicio =  Servicios.MONEDAS;
		RegistrarAuditoriaRequest reAU = null;
		
		
		reAU = new RegistrarAuditoriaRequest(request, microservicio,requestHTTP);

		try {
			log.info("MonedasRequest: " +request);
			
			response = this.crear(request);
			log.info("response: "+response);
		} catch (Exception e) {
			log.error("e: "+e);
			codigo = CodRespuesta.CME6000;
			errorM = Constantes.EXC+e;
		}
		
		
		return null;
	}

	

	

	

	
	
	
	

}
