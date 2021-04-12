package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bancoexterior.parametros.monedas.response.Resultado;

import lombok.Data;


@Data
public class MonedaDtoResponse implements Serializable{
	
	
	private Resultado resultado;
	
	private List<MonedaDto> listMonedasDto;
	
	public MonedaDtoResponse(){
		super();
		this.resultado = new Resultado();
		this.listMonedasDto = new ArrayList<MonedaDto>();
	}
	
	public void addListMonedasDto(MonedaDto monedaDto) {
		listMonedasDto.add(monedaDto);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
