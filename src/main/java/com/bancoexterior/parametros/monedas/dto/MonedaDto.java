package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class MonedaDto implements Serializable{

	@JsonProperty("codMoneda")
	private String codMoneda;
	
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonProperty("codAlterno")
	private String codAlterno;
	
	@JsonProperty("flagActivo")
	private Boolean flagActivo;
	
	@JsonProperty("codUsuario")
	private String codUsuario;
	
	@JsonProperty("fechaModificacion")
	private Date fechaModificacion;
	
	
	
	public MonedaDto(MonedasRequest request ) {
		super();
		this.codMoneda         = request.getMonedasDtoRequest().getCodMoneda();
		this.flagActivo        = request.getMonedasDtoRequest().getFlagActivo();
		this.descripcion       = request.getMonedasDtoRequest().getDescripcion();
		this.codAlterno        = request.getMonedasDtoRequest().getCodAlterno();
		this.codUsuario        = request.getCodUsuarioMR();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
