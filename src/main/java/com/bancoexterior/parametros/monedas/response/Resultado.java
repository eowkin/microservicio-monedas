package com.bancoexterior.parametros.monedas.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Resultado {
	@JsonProperty("codigo")
	private String codigo;
	@JsonProperty("descripcion")
	private String descripcion;

	
}
