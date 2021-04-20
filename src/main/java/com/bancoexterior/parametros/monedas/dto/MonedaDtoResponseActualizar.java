package com.bancoexterior.parametros.monedas.dto;

import java.io.Serializable;


import com.bancoexterior.parametros.monedas.response.Resultado;

import lombok.Data;

@Data
public class MonedaDtoResponseActualizar implements Serializable{

	
	private Resultado resultado;

	public MonedaDtoResponseActualizar() {
		super();
		this.resultado = new Resultado();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
