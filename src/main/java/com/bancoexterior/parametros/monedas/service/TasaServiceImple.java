package com.bancoexterior.parametros.monedas.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.TasaDto;
import com.bancoexterior.parametros.monedas.entities.Tasa;
import com.bancoexterior.parametros.monedas.entities.TasaPk;
import com.bancoexterior.parametros.monedas.repository.ITasaRepository;




@Service
public class TasaServiceImple implements ITasaService{

	private static final Logger LOGGER = LogManager.getLogger(TasaServiceImple.class);
	
	@Autowired
	private ITasaRepository repo;
	
	@Override
	public void inicializarTasaMoneda(TasaDto tasaDto) {
		LOGGER.info(Servicios.MONEDASSERVICEITASAS);
		try {
			TasaPk id = new TasaPk();
			id.setCodMonedaOrigen(tasaDto.getCodMonedaOrigen());
			id.setCodMonedaDestino(tasaDto.getCodMonedaDestino());
			
			Tasa obj = new Tasa();
			obj.setId(id);
			obj.setCodUsuario(tasaDto.getCodUsuario());
			obj.setMontoTasa(tasaDto.getMontoTasa());
			repo.save(obj);
			
			LOGGER.info(Servicios.MONEDASSERVICEFTASAS);
		} catch (Exception e) {
			LOGGER.error("Error inicializando-Limites");
			LOGGER.error(e);
		}
		
		
		
		
	}

}
