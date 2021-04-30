package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bancoexterior.parametros.monedas.response.Resultado;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class MonedaDtoResponse implements Serializable{
	
	
	private Resultado resultado;
	
	@JsonProperty("monedas")
	private List<MonedaDto> listMonedasDto;
	
	public MonedaDtoResponse(){
		super();
		this.resultado = new Resultado();
		this.listMonedasDto = new ArrayList<>();
	}
	
	public void addListMonedasDto(MonedaDto monedaDto) {
		listMonedasDto.add(monedaDto);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
