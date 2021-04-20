package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class MonedaRequestActualizar implements Serializable{

	
	@JsonProperty("idSesion")
	private String idSesionMR;
	
	@JsonProperty("idUsuario")
	private String idUsuarioMR;
	
	@JsonProperty("codUsuario")
	private String codUsuarioMR;
	
	@JsonProperty("canal")
	private String canalCM;
	
	@JsonProperty("moneda")
	private MonedaDto monedaDto;
	
	
	public MonedaRequestActualizar() {
		super();
		this.monedaDto = new MonedaDto();
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
