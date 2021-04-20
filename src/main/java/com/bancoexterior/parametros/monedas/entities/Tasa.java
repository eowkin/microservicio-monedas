package com.bancoexterior.parametros.monedas.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name = "\"Tasas\"", schema = "\"Convenio1\"")
public class Tasa {
	
	@EmbeddedId
	private TasaPk id;
	
	
	//@NotEmpty(message = "no puede ser vacio")
	@Column(name="monto_tasa", nullable = false)
	private BigDecimal montoTasa; 
	
	//@NotEmpty(message = "no puede ser vacio")
	@Column(name = "cod_usuario", nullable = false)
	//@Size(max = 10)
	private String codUsuario;
	
	@Column(name = "fecha_modificacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	
	@PrePersist
	public void prePersist() {
		setFechaModificacion(new Date());
	}

}
