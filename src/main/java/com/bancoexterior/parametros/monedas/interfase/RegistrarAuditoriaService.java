package com.bancoexterior.parametros.monedas.interfase;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.ExceptionMessages;
import com.bancoexterior.parametros.monedas.config.Codigos.InfoMessages;
import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.model.RegistrarAuditoriaRequest;
import com.bancoexterior.parametros.monedas.model.WSRequest;
import com.bancoexterior.parametros.monedas.model.WSResponse;
import com.bancoexterior.parametros.monedas.util.Utils;


@Service
@Async
public class RegistrarAuditoriaService implements IRegistrarAuditoriaService{
	
	private static final Logger LOGGER = LogManager.getLogger(RegistrarAuditoriaService.class);
	
	@Value("${${microservicio.ambiente}"+".registrarAudit.url}")
    private String url;
	@Value("${registrarAudit.ReadTimeout}")
    private String readTimeout;
	@Value("${registrarAudit.ConnectTimeout}")
    private String connectTimeout;
	@Value("${registrarAudit.ConnectionRequestTimeout}")
    private String connectionRequestTimeout;
	@Autowired
	private IWSService wsService ;
	
	/**
     * Nombre:                  registrarAuditoria
     * Descripcion:             Web Service registrar auditoria
     *
     * @param  auditoria  Objeto RegistrarAuditoriaRequest
     * @param codigo Codigo respuesta para el momento
     * @param  mensaje mensaje a registrar
     * @return RegistrarAuditoriaResponse
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 01/07/20
     */
	
	@Override
	public void registrarAuditoria(RegistrarAuditoriaRequest auditoria, String codigo, String mensaje, String errorAdicional) {
		
		LOGGER.info(Servicios.ASERVICEI);
		WSRequest wsrequest = new WSRequest();
		JSONObject registrarJSON = new JSONObject();
		WSResponse  retorno;
		String print;
        auditoria.setCodigoResultado(codigo);
        auditoria.setFecha(Utils.fechaOut(Constantes.FECHA_HORA));
        auditoria.setDescripcionResultado(mensaje);			
	
		if(!codigo.substring(0,1).equalsIgnoreCase(Constantes.SUBSTRING_COD_OK)){					
			auditoria.setDetalleError(mensaje+Constantes.PLECA+errorAdicional);			
		}
		
		        
		try {			 		
			registrarJSON.put("fecha", auditoria.getFecha()); //2019-08-27 13:23:2
			registrarJSON.put("id_cliente", StringUtils.left(auditoria.getIdCliente(),50));
			registrarJSON.put("ip_origen",  StringUtils.left(auditoria.getIpOrigen(),40));
			registrarJSON.put("usuario", StringUtils.left(auditoria.getUsuario(),50));
			registrarJSON.put("terminal", StringUtils.left(auditoria.getTerminal(),50));
			registrarJSON.put("microservicio",  StringUtils.left(auditoria.getMicroservicio(),255));
			registrarJSON.put("referencia", StringUtils.left(auditoria.getReferencia(),30));
			registrarJSON.put("codigo_resultado", auditoria.getCodigoResultado());
            registrarJSON.put("descripcion_resultado",  StringUtils.left(auditoria.getDescripcionResultado(),255));
			registrarJSON.put("detalle_error", StringUtils.left(auditoria.getDetalleError(), 255));	
			registrarJSON.put("telefono", StringUtils.right(auditoria.getTelefono(),20));
			registrarJSON.put("cedula", StringUtils.left(auditoria.getCedula(),20));
			registrarJSON.put("id_canal", auditoria.getIdCanal());
			registrarJSON.put("id_sesion", StringUtils.left(auditoria.getIdSesion(),30));
			
			LOGGER.info(InfoMessages.AUREQUEST,registrarJSON);
			LOGGER.info(url);
			
			wsrequest.setBody(registrarJSON.toString());
			wsrequest.setConnectTimeout(Integer.parseInt(connectTimeout));
			wsrequest.setContenType(Constantes.APP_JSON);
			wsrequest.setSocketTimeout(Integer.parseInt(connectTimeout));
			wsrequest.setUrl(url);
			retorno = wsService.post(wsrequest);
			
			print = InfoMessages.AUPRINTINFO + retorno.getBody();
			
			if (retorno.getStatus()==200) {
				LOGGER.info(print);
			}else {
				LOGGER.error(ExceptionMessages.AUPRINTERROR,print);
			}
			
		} catch (Exception e) {
			LOGGER.error(ExceptionMessages.AUPRINTERRORMENSA , e.getMessage());
		}
		
		LOGGER.info(Servicios.ASERVICEF);

	}

}
