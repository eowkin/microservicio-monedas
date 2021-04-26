package com.bancoexterior.parametros.monedas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponseActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasDtoRequest;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.dto.TasaDto;
import com.bancoexterior.parametros.monedas.response.Resultado;
import com.bancoexterior.parametros.monedas.entities.Moneda;
import com.bancoexterior.parametros.monedas.exception.CodMonedaExistException;
import com.bancoexterior.parametros.monedas.exception.CodMonedaNoExistException;
import com.bancoexterior.parametros.monedas.interfase.IRegistrarAuditoriaService;
import com.bancoexterior.parametros.monedas.model.RegistrarAuditoriaRequest;
import com.bancoexterior.parametros.monedas.repository.IMonedaRepository;
import com.bancoexterior.parametros.monedas.util.Mapper;




@Service
public class MonedaServiceImpl implements IMonedaService {

	private static final Logger LOGGER = LogManager.getLogger(MonedaServiceImpl.class);
	
	@Autowired
	private IMonedaRepository repo;

	@Autowired
	private Environment env;
	
	@Autowired
	private IRegistrarAuditoriaService registrarA;

	@Autowired
	private Mapper mapper;
	
	@Value("${microservicio.codmonedabs}")
	private String codmonedabs;

	@Autowired
	private ITasaService tasaService;
	
	@Autowired
	private ILimitesGeneralesService limitesService;
	
	@Override
	public MonedaDtoResponseActualizar save(MonedasRequest monedasRequest, HttpServletRequest requestHTTP) {
		LOGGER.info(Servicios.MONEDASSERVICEICREAR);
		LOGGER.info(monedasRequest);
		String microservicio = Servicios.MONEDAS;
		
		
		if (this.existsById(monedasRequest.getMonedasDtoRequest().getCodMoneda())) {
			throw new CodMonedaExistException(CodRespuesta.CME2001);
		}
		
		RegistrarAuditoriaRequest reAU = null;
		
		reAU = new RegistrarAuditoriaRequest(monedasRequest, microservicio, requestHTTP);
		String errorM = Constantes.BLANK;
		String codigo =  CodRespuesta.C0000;
		
		Moneda obj = new Moneda();
		MonedaDtoResponseActualizar response = new MonedaDtoResponseActualizar();
		Resultado resultado = new Resultado();
		
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		//monedasDtoRequest.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		try {
			
			
			
			MonedasDtoRequest monedasDtoRequest =  monedasRequest.getMonedasDtoRequest();
			LOGGER.info(monedasRequest);
			obj = mapper.map(monedasDtoRequest, Moneda.class);
			LOGGER.info(monedasRequest);
			obj.setCodUsuario(monedasRequest.getCodUsuarioMR());
			//obj.setCodUsuario("E66666666666666666666666666666");
			LOGGER.info(obj);
			
			if(monedasDtoRequest.equals(codmonedabs)) {
				obj = repo.save(obj);
				
			}else {
				obj = repo.save(obj);
				
				
				LOGGER.info("No es moneda nacional, se crea y se inicializa tasa y limites");
				//llamar inicializar Tasa
				TasaDto tasaDto = new TasaDto();
				tasaDto.setCodMonedaOrigen(monedasRequest.getMonedasDtoRequest().getCodMoneda());
				tasaDto.setCodMonedaDestino(codmonedabs);
				tasaDto.setCodUsuario(monedasRequest.getCodUsuarioMR());
				tasaDto.setMontoTasa(BigDecimal.ZERO);
				tasaService.inicializarTasaMoneda(tasaDto);
				
				//llamar inicializar limites generales
				LimitesGeneralesDto limitesGeneralesDto = new LimitesGeneralesDto();
				limitesGeneralesDto.setCodMoneda(monedasRequest.getMonedasDtoRequest().getCodMoneda());
				limitesGeneralesDto.setCodUsuario(monedasRequest.getCodUsuarioMR());
				limitesService.inicializarLimitesGeneralesMoneda(limitesGeneralesDto);
				
			}
			
			response.setResultado(resultado);
		
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6001;
			errorM = Constantes.EXC+e;
			response.getResultado().setCodigo(CodRespuesta.CME6001);
			response.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
		}
		
		resultado.setCodigo(codigo);
		resultado.setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorM));
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal(monedasRequest.getCanalCM());
			registrarAuditoriaBD(reAU, resultado, errorM);
		}
		
		LOGGER.info(Servicios.MONEDASSERVICEFCREAR);
		return response;
		
		

		
	}
	
	@Override
	//@Transactional
	public Resultado crear(MonedasRequest monedasRequest) {
		LOGGER.info(Servicios.MONEDASSERVICEICREAR);
		Moneda obj = new Moneda();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			LOGGER.info(monedasRequest);
			
			MonedasDtoRequest monedasDtoRequest =  monedasRequest.getMonedasDtoRequest();
			LOGGER.info(monedasRequest);
			obj = mapper.map(monedasDtoRequest, Moneda.class);
			LOGGER.info(obj);
			obj.setCodUsuario(monedasRequest.getCodUsuarioMR());
			
			LOGGER.info(obj);
			obj = repo.save(obj);
			
			LOGGER.info(Servicios.MONEDASSERVICEFCREAR);
			return resultado;
		} catch (Exception e) {
			LOGGER.error(e);
			resultado.setCodigo(CodRespuesta.CME2001);
			return resultado;
		}
		
	}
	
	@Override
	public MonedaDtoResponseActualizar actualizar(MonedasRequest monedasRequest, HttpServletRequest requestHTTP) {
		LOGGER.info(Servicios.MONEDASSERVICEIACTUALIZAR);
		LOGGER.info(monedasRequest);
		String microservicio = Servicios.MONEDASACTUALIZAR;
		
		RegistrarAuditoriaRequest reAU = null;
		
		reAU = new RegistrarAuditoriaRequest(monedasRequest, microservicio, requestHTTP);
		String errorM = Constantes.BLANK;
		String codigo =  CodRespuesta.C0000;
		
		Moneda obj = new Moneda();
		MonedaDtoResponseActualizar response = new MonedaDtoResponseActualizar();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			
			MonedaDto monedaDto = this.findById(monedasRequest.getMonedasDtoRequest().getCodMoneda());
			LOGGER.info(monedaDto);
			
			MonedasDtoRequest monedasDtoRequest = monedasRequest.getMonedasDtoRequest();
			
			monedaDto.setCodAlterno(monedasDtoRequest.getCodAlterno());
			monedaDto.setDescripcion(monedasDtoRequest.getDescripcion());
			monedaDto.setFlagActivo(monedasDtoRequest.getFlagActivo());
			
			
			
			//MonedaDto monedaDto =  monedaRequestActualizar.getMonedaDto();
			LOGGER.info(monedaDto);
			obj = mapper.map(monedaDto, Moneda.class);
			LOGGER.info(obj);
			obj.setCodUsuario(monedasRequest.getCodUsuarioMR());
			//obj.setCodUsuario("E66666666666666666666666666666");
			
			LOGGER.info(obj);
			obj = repo.save(obj);
			
			response.setResultado(resultado);
			
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6001;
			errorM = Constantes.EXC+e;
			response.getResultado().setCodigo(CodRespuesta.CME6001);
			response.getResultado().setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
		}
		
		resultado.setCodigo(codigo);
		resultado.setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorM));
		
		if(reAU != null) {
			reAU.setIdCliente(Constantes.RIF);
			reAU.setCedula(Constantes.CEDULA);
			reAU.setTelefono(Constantes.TELEFONO);
			reAU.setIdCanal(monedasRequest.getCanalCM());
			registrarAuditoriaBD(reAU, resultado, errorM);
		}
		LOGGER.info(Servicios.MONEDASSERVICEFACTUALIZAR);
		
		return response;
	}
	
	/*
	@Override
	public Resultado actualizar(MonedaRequestActualizar monedaRequestActualizar) {
		
		LOGGER.info(Servicios.MONEDASSERVICEIACTUALIZAR);
		Moneda obj = new Moneda();
		Resultado resultado = new Resultado();
		resultado.setCodigo(CodRespuesta.C0000);
		resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.C0000,CodRespuesta.C0000).replace(Constantes.ERROR, Constantes.BLANK));
		
		try {
			LOGGER.info(monedaRequestActualizar);
			
			MonedaDto monedaDto =  monedaRequestActualizar.getMonedaDto();
			LOGGER.info(monedaDto);
			obj = mapper.map(monedaDto, Moneda.class);
			LOGGER.info(obj);
			obj.setCodUsuario(monedaRequestActualizar.getCodUsuarioMR());
			
			
			LOGGER.info(obj);
			obj = repo.save(obj);
			LOGGER.info(Servicios.MONEDASSERVICEFACTUALIZAR);
			return resultado;
		} catch (Exception e) {
			LOGGER.error(e);
			resultado.setCodigo(CodRespuesta.CME6001);
			resultado.setDescripcion(env.getProperty(Constantes.RES+CodRespuesta.CME6001,CodRespuesta.CME6001));
			return resultado;
		}
	}*/
	
	
	@Override
	public List<MonedaDto> findAllMonedasDto(MonedaDto monedaDto) {
		LOGGER.info(Servicios.MONEDASSERVICEICONSULTA);
		
		
		List<MonedaDto> listMonedasDto = null;
		
		if(monedaDto.getCodMoneda() == null && monedaDto.getFlagActivo() == null) {
			listMonedasDto = repo.getAll();
		}
		
		if(monedaDto.getCodMoneda() != null && monedaDto.getFlagActivo() == null) {
			listMonedasDto = repo.getMonedaByid(monedaDto.getCodMoneda());
		}
		
		if(monedaDto.getCodMoneda() == null && monedaDto.getFlagActivo() != null) {
			listMonedasDto = repo.getMonedaByFlagActivo(monedaDto.getFlagActivo());
		}
		
		if(monedaDto.getCodMoneda() != null && monedaDto.getFlagActivo() != null) {
			listMonedasDto = repo.getMonedaByidAndFlag(monedaDto.getCodMoneda(),monedaDto.getFlagActivo());
		}
		
		LOGGER.info(Servicios.MONEDASSERVICEFCONSULTA);
		return listMonedasDto;
	}
	
	@Override
	public List<MonedaDto> findAllMonedasDtoNuevo(MonedaDto monedaDto) {
		LOGGER.info(Servicios.MONEDASSERVICEICONSULTA+"NUEVA");
		
		String codMoneda = Constantes.BLANK;
		String flag = Constantes.BLANK;
		boolean flagActivo = false;
		
		if(monedaDto.getCodMoneda()!= null) {
			codMoneda = monedaDto.getCodMoneda();
		}
		
		if(monedaDto.getFlagActivo()!= null) {
			flag = "si";
			flagActivo = monedaDto.getFlagActivo();
		}
		
		List<Moneda> listMonedas = repo.getMonedaByidNuevo(codMoneda, flag, flagActivo);
		
		List<MonedaDto> listMonedasDto = new ArrayList<MonedaDto>();

		for (Moneda moneda : listMonedas) {

			MonedaDto monedaDto1 = mapper.map(moneda, MonedaDto.class);
			listMonedasDto.add(monedaDto1);
		}
		LOGGER.info(Servicios.MONEDASSERVICEFCONSULTA);
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
		LOGGER.info(Servicios.MONEDASCONSULTASERVICEI);
		MonedaDtoResponse monedaDtoResponse = new MonedaDtoResponse();
		Resultado resultado = new Resultado();
		String codigo = CodRespuesta.C0000;
		String errorCM = Constantes.BLANK;
		List<MonedaDto> listMonedasDto;
		MonedaDto monedaDto =  new MonedaDto(request);
		MonedasDtoRequest monedasDtoRequest  = request.getMonedasDtoRequest();
		try {
			
			codigo = validaDatosConsulta(request);
			LOGGER.info(codigo);
			if(codigo.equalsIgnoreCase(CodRespuesta.C0000)) {
				//consulta BD
				//listMonedasDto = this.findAllMonedasDto(monedaDto);
				listMonedasDto = this.findAllMonedasDtoNuevo(monedaDto);
				monedaDtoResponse.setListMonedasDto(listMonedasDto);
				
				//Validar Respuesta
				resultado = validaConsulta(listMonedasDto);
				codigo = resultado.getCodigo();
				errorCM = resultado.getDescripcion();
			}
		} catch (Exception e) {
			LOGGER.error(e);
			codigo = CodRespuesta.CME6000;
			errorCM = Constantes.EXC+e;
		}
		
		monedaDtoResponse.getResultado().setCodigo(codigo);
		monedaDtoResponse.getResultado().setDescripcion(env.getProperty(Constantes.RES+codigo,codigo).replace(Constantes.ERROR, errorCM));
		
		LOGGER.info(monedaDtoResponse);
		LOGGER.info(Servicios.MONEDASCONSULTASERVICEF);
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
		
		LOGGER.info(""+request);

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
	    
		LOGGER.info(resultado);
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

	
	
	
	
}
