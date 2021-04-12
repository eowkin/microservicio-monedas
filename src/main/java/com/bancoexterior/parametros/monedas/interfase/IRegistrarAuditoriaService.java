package com.bancoexterior.parametros.monedas.interfase;

import com.bancoexterior.parametros.monedas.model.RegistrarAuditoriaRequest;

public interface IRegistrarAuditoriaService {
	
	void registrarAuditoria(RegistrarAuditoriaRequest auditoria,  String codigo, String mensaje, String errorAdicional);

}
