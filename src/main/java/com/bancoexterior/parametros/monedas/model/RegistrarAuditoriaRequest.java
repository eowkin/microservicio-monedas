package com.bancoexterior.parametros.monedas.model;

import javax.servlet.http.HttpServletRequest;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;



public class RegistrarAuditoriaRequest {
		
	private String fecha;
	private String idCliente;
	private String ipOrigen;
	private String usuario;
	private String terminal;
	private String microservicio;
	private String referencia;
	private String codigoResultado;
	private String descripcionResultado;
	private String detalleError;
	private String idCanal;
	private String idSesion;
	private String telefono;
	private String cedula;
	private String medio;

	
	public RegistrarAuditoriaRequest() {
		super();
	}
	
	public RegistrarAuditoriaRequest(MonedasRequest request, String microservicio,HttpServletRequest requestHTTP) {
		
		this.fecha                = Constantes.BLANK;
		this.idCliente            = Constantes.BLANK;
		this.ipOrigen             = requestHTTP.getHeader(Constantes.XCLIENTIP) != null ? requestHTTP.getHeader(Constantes.XCLIENTIP) : requestHTTP.getRemoteAddr();
		this.usuario              = request.getCodUsuarioMR();
		this.terminal             = Constantes.TERMINAL;
		this.microservicio        = microservicio;
		this.referencia           = Constantes.N_A;
		this.codigoResultado      = Constantes.BLANK;
		this.descripcionResultado = Constantes.BLANK;
		this.detalleError         = Constantes.BLANK;
		this.idCanal              = "8";
		this.idSesion             = request.getIdSesionMR();
		this.telefono             = Constantes.BLANK;
		this.medio                = Constantes.TERMINAL;
		
	}
	
	
	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getIpOrigen() {
		return ipOrigen;
	}
	public void setIpOrigen(String ipOrigen) {
		this.ipOrigen = ipOrigen;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getMicroservicio() {
		return microservicio;
	}
	public void setMicroservicio(String microservicio) {
		this.microservicio = microservicio;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getCodigoResultado() {
		return codigoResultado;
	}
	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}
	public String getDescripcionResultado() {
		return descripcionResultado;
	}
	public void setDescripcionResultado(String descripcionResultado) {
		this.descripcionResultado = descripcionResultado;
	}
	public String getDetalleError() {
		return detalleError;
	}
	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}
	public String getIdCanal() {
		return idCanal;
	}
	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}
	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistrarAuditoriaRequest [fecha=");
		builder.append(fecha);
		builder.append(", idCliente=");
		builder.append(idCliente);
		builder.append(", ipOrigen=");
		builder.append(ipOrigen);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", terminal=");
		builder.append(terminal);
		builder.append(", microservicio=");
		builder.append(microservicio);
		builder.append(", referencia=");
		builder.append(referencia);
		builder.append(", codigoResultado=");
		builder.append(codigoResultado);
		builder.append(", descripcionResultado=");
		builder.append(descripcionResultado);
		builder.append(", detalleError=");
		builder.append(detalleError);
		builder.append(", idCanal=");
		builder.append(idCanal);
		builder.append(", idSesion=");
		builder.append(idSesion);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", cedula=");
		builder.append(cedula);
		builder.append("]");
		return builder.toString();
	}

	
}
