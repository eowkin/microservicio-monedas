package com.bancoexterior.parametros.monedas.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
//@Table(name = "Monedas",  schema = "Convenio1") "\"AIRPORTS\""
@Table(name = "\"Monedas\"", schema = "\"Convenio1\"")
public class Moneda {
	
	@Id
	@Column(name = "cod_moneda")
	@Size(max = 3)
	private String codMoneda;
	
	@NotEmpty(message = "no puede ser vacio")
	@Column(name="descripcion", nullable = false)
	@Size(max = 500)
	private String descripcion;
	
	@NotEmpty(message = "no puede ser vacio")
	@Column(name = "cod_alterno", nullable = false)
	@Size(max = 10)
	private String codAlterno;
	
	
	@Column(name = "flag_activo", nullable = false)
	private Boolean flagActivo;
	
	@NotEmpty(message = "no puede ser vacio")
	@Column(name = "cod_usuario", nullable = false)
	@Size(max = 10)
	private String codUsuario;
		
	@Column(name = "fecha_modificacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	
	
	@PrePersist
	public void prePersist() {
		setFechaModificacion(new Date());
	}
	
}
