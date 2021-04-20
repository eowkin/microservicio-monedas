package com.bancoexterior.parametros.monedas.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor @NoArgsConstructor
public class TasaPk implements Serializable{
	

	@NotEmpty(message = "no puede ser vacio")
	@Column(name= "cod_moneda_origen",nullable = false)
	@Size(min = 3, max = 3)
	private String codMonedaOrigen;

	@NotEmpty(message = "no puede ser vacio")
	@Column(name= "cod_moneda_destino", nullable = false)
	@Size(min = 3, max = 3)
	private String codMonedaDestino;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
