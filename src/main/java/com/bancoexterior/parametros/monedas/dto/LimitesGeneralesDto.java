package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LimitesGeneralesDto implements Serializable{

	@JsonProperty("codMoneda")
	private String codMoneda;
	
	@JsonProperty("tipoTransaccion")
	private String tipoTransaccion;
	
	@JsonProperty("tipoCliente")
	private String tipoCliente;
	
	@JsonProperty("montoMin")
	private BigDecimal montoMin;
	
	@JsonProperty("montoMax")
	private BigDecimal montoMax;
	
	@JsonProperty("montoTope")
	private BigDecimal montoTope;
	
	@JsonProperty("montoMensual")
	private BigDecimal montoMensual;
	
	@JsonProperty("montoDiario")
	private BigDecimal montoDiario;
	
	@JsonProperty("montoBanco")
	private BigDecimal montoBanco;
	
	@JsonProperty("codUsuario")
	private String codUsuario;
	
	@JsonProperty("flagActivo")
	private Boolean flagActivo;
	
	@JsonProperty("fechaModificacion")
	private Date fechaModificacion;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
