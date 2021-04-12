package com.bancoexterior.parametros.monedas.interfase;

import com.bancoexterior.parametros.monedas.model.WSRequest;
import com.bancoexterior.parametros.monedas.model.WSResponse;

public interface  IWSService {
	WSResponse post(WSRequest request) ;
}
