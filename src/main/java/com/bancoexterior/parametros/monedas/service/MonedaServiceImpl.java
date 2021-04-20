package com.bancoexterior.parametros.monedas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.monedas.dto.MonedaDto;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedaRequestActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasDtoRequest;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.dto.TasaDto;
import com.bancoexterior.parametros.monedas.response.Resultado;
import com.bancoexterior.parametros.monedas.entities.Moneda;
import com.bancoexterior.parametros.monedas.interfase.IRegistrarAuditoriaService;
import com.bancoexterior.parametros.monedas.model.RegistrarAuditoriaRequest;
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
	private IRegistrarAuditoriaService registrarA ;

	@Autowired
	private Mapper mapper;
	
	@Value("${microservicio.codmonedabs}")
	private String codmonedabs;

	@Autowired
	private ITasaService tasaService;
	
	@Autowired
	private ILimitesGeneralesService limitesService;
	
	@Override
	public MonedaDtoResponse save(MonedasRequest monedasRequest) {
		log.info("Inicio del Guardar");
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
			log.error("no se pudo crear la moneda");
			response.getResultado().setCodigo(CodRespuesta.CME6001);
			log.info("error: "+env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			response.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			return response;
		}

		
	}
	
	@Override
	//@Transactional
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
			log.info("error en las trasacciones");
			resultado.setCodigo(CodRespuesta.CME2001);
			return resultado;
		}
		
	}
	
	@Override
	public Resultado actualizar(MonedaRequestActualizar monedaRequestActualizar) {
		Moneda obj = new Moneda();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			log.info("monedaRequestActualizar: "+monedaRequestActualizar);
			
			MonedaDto monedaDto =  monedaRequestActualizar.getMonedaDto();
			log.info("monedaDto: "+monedaDto);
			obj = mapper.map(monedaDto, Moneda.class);
			log.info("moneda: "+obj);
			obj.setCodUsuario(monedaRequestActualizar.getCodUsuarioMR());
			
			
			log.info("obj: "+obj);
			obj = repo.save(obj);
			
			return resultado;
		} catch (Exception e) {
			log.info("error en la actualizacion");
			resultado.setCodigo(CodRespuesta.CME6001);
			resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			return resultado;
		}
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
	public MonedaDto findById(String codMoneda) {
		
		
		Moneda moneda = repo.findById(codMoneda).orElse(null);
		
		if (moneda == null) {
			return null;
		}else {
			return mapper.map(moneda, MonedaDto.class);
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
	    
	    log.info(""+resultado);
		return resultado;
		
	}

	/**
     * Nombre:                 registrarAuditoriaBD
     * Descripcion:            Registrar Auditoria en Web Service
     *
     * @param  req  Objeto RegistrarAuditoriaRequest
     * @param  codigo   Codigo de respuesta
     * @param descripcion Descripcion del resultado
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 02/03/21
     */
	private void registrarAuditoriaBD(RegistrarAuditoriaRequest registrarAu,Resultado response, String errorAdicional) {
			
		        registrarA.registrarAuditoria(registrarAu, response.getCodigo(),response.getDescripcion(),errorAdicional);	
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
			codigo = response.getCodigo();
			errorM = response.getDescripcion();
			
			if(response.getCodigo().equals(CodRespuesta.C0000)) {
				if(!request.getMonedasDtoRequest().getCodMoneda().equals(codmonedabs)) {
					
					
					log.info("No es moneda nacional, se crea y se inicializa tasa y limites");
					//llamar inicializar Tasa
					TasaDto tasaDto = new TasaDto();
					tasaDto.setCodMonedaOrigen(request.getMonedasDtoRequest().getCodMoneda());
					tasaDto.setCodMonedaDestino(codmonedabs);
					tasaDto.setCodUsuario(request.getCodUsuarioMR());
					tasaDto.setMontoTasa(BigDecimal.ZERO);
					tasaService.inicializarTasaMoneda(tasaDto);
					
					//llamar inicializar limites generales
					LimitesGeneralesDto limitesGeneralesDto = new LimitesGeneralesDto();
					limitesGeneralesDto.setCodMoneda(request.getMonedasDtoRequest().getCodMoneda());
					limitesGeneralesDto.setCodUsuario(request.getCodUsuarioMR());
					limitesService.inicializarLimitesGeneralesMoneda(limitesGeneralesDto);
				}

			}
			
						
		} catch (Exception e) {
			log.error("e: "+e);
			codigo = CodRespuesta.CME6000;
			errorM = Constantes.EXC+e;
			
		}
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal("8");
			registrarAuditoriaBD(reAU, response, errorM);
		}
		
		return response;
	}

	@Override
	public Resultado gestionActualizarMoneda(MonedasRequest request, HttpServletRequest requestHTTP) {
		log.info(Servicios.MONEDASSERVICEIACTUALIZAR);
		Moneda moneda = new Moneda();
		Resultado response = new Resultado();
		String codigo ;
		String errorM ;
		String microservicio =  Servicios.MONEDASACTUALIZAR;
		RegistrarAuditoriaRequest reAU = null;
		
		
		reAU = new RegistrarAuditoriaRequest(request, microservicio,requestHTTP);

		try {
			log.info("MonedasRequest: " +request);
			log.info(request.getMonedasDtoRequest().getCodMoneda());
			MonedaDto monedaDto = this.findById(request.getMonedasDtoRequest().getCodMoneda());
			log.info("monedaDto: " +monedaDto);
			
			MonedasDtoRequest monedasDtoRequest = request.getMonedasDtoRequest();
			
			monedaDto.setCodAlterno(monedasDtoRequest.getCodAlterno());
			monedaDto.setDescripcion(monedasDtoRequest.getDescripcion());
			monedaDto.setFlagActivo(monedasDtoRequest.getFlagActivo());
			
			MonedaRequestActualizar monedaRequestActualizar = new MonedaRequestActualizar();
			monedaRequestActualizar.setIdSesionMR(request.getIdSesionMR());
			monedaRequestActualizar.setIdUsuarioMR(request.getIdUsuarioMR());
			monedaRequestActualizar.setCodUsuarioMR(request.getCodUsuarioMR());
			monedaRequestActualizar.setCanalCM(request.getCanalCM());
			
			monedaRequestActualizar.setMonedaDto(monedaDto);
			log.info("monedaRequestActualizar: " +monedaRequestActualizar);
			response = this.actualizar(monedaRequestActualizar);
			log.info("response: "+response);
			
			codigo = response.getCodigo();
			errorM = response.getDescripcion();
			
						
		} catch (Exception e) {
			log.error("e: "+e);
			codigo = CodRespuesta.CME6000;
			errorM = Constantes.EXC+e;
			
		}
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal("8");
			registrarAuditoriaBD(reAU, response, errorM);
		}
		return response;

	}
}
