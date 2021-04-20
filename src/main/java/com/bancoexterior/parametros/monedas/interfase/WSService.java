package com.bancoexterior.parametros.monedas.interfase;

import javax.net.ssl.HostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;
import com.bancoexterior.parametros.monedas.config.Codigos.ExceptionMessages;
import com.bancoexterior.parametros.monedas.model.WSRequest;
import com.bancoexterior.parametros.monedas.model.WSResponse;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Service
public class WSService implements IWSService{
	
	 private static final Logger LOGGER = LogManager.getLogger(WSService.class);
	 private static final boolean VERIFYSSL = false;
	 private static final HostnameVerifier VERIFIER = NoopHostnameVerifier.INSTANCE;
	 
	 @Override
	 public WSResponse post(WSRequest request) {
		
		 HttpResponse<String> retorno = null;
		 WSResponse response;
		 
		 try {
			 initUniRest (request.getSocketTimeout(),request.getConnectTimeout() );
			 retorno = Unirest.post(request.getUrl()) 
			  .header(Constantes.CONTENT_TYPE, request.getContenType())
			  .header(Constantes.ACCEPT_CHARSET, Constantes.UTF8)
			  .body(request.getBody()).asString();
			 response = new WSResponse(retorno);
			 
	
		} catch (HttpStatusCodeException e) {
			LOGGER.error(String.format(ExceptionMessages.UNIRESTHTTPE ,e));
			LOGGER.error(String.format(ExceptionMessages.UNIRESTBODYE ,e.getResponseBodyAsString()));
			response = new WSResponse(e);
		}catch (Exception e) {
			LOGGER.error(String.format(ExceptionMessages.UNIRESTE ,e));
			response = new WSResponse(e);
		}	

		 return response;
	 }
	 
	 
	 /**
		 * Nombre:                  initUniRest
		 * Descripcion:             Inicializar Objeto Unirest
		 *
		 * @version 1.0
		 * @author Wilmer Vieira
		 * @since 13/10/20
		 */
     
		private void initUniRest (int socketTimeout, int connectTimeout ) {
			Unirest.config().reset();
			Unirest.config()
		    .socketTimeout(socketTimeout)
		    .connectTimeout(connectTimeout)
			.verifySsl(VERIFYSSL)
			.hostnameVerifier(VERIFIER);
			
		}

}
