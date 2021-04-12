package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bancoexterior.parametros.monedas.config.Codigos.CodRespuesta;
import com.bancoexterior.parametros.monedas.config.Codigos.ParamConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MonedasDtoRequest implements Serializable{
	
	
	@JsonProperty("codMoneda")
	@NotEmpty(message=CodRespuesta.CDE1004)
	@Pattern(regexp=ParamConfig.CODMONEDA, message=CodRespuesta.CDE1004)
	private String codMoneda;
	
	@JsonProperty("descripcion")
	@NotEmpty(message=CodRespuesta.CDE1005)
	@Pattern(regexp=ParamConfig.DESCRIPCION, message=CodRespuesta.CDE1005)
	private String descripcion;
	
	@JsonProperty("codAlterno")
	@NotEmpty(message=CodRespuesta.CDE1006)
	@Pattern(regexp=ParamConfig.CODALTERNO, message=CodRespuesta.CDE1006)
	private String codAlterno;
	
	@JsonProperty("flagActivo")
	@NotNull(message=CodRespuesta.CDE1007)
	private Boolean flagActivo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
