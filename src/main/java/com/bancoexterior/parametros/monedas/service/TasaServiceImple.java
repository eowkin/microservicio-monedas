package com.bancoexterior.parametros.monedas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.monedas.dto.TasaDto;
import com.bancoexterior.parametros.monedas.entities.Tasa;
import com.bancoexterior.parametros.monedas.entities.TasaPk;
import com.bancoexterior.parametros.monedas.repository.ITasaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TasaServiceImple implements ITasaService{

	@Autowired
	private ITasaRepository repo;
	
	@Override
	public void inicializarTasaMoneda(TasaDto tasaDto) {
		
		try {
			TasaPk id = new TasaPk();
			id.setCodMonedaOrigen(tasaDto.getCodMonedaOrigen());
			id.setCodMonedaDestino(tasaDto.getCodMonedaDestino());
			
			Tasa obj = new Tasa();
			obj.setId(id);
			obj.setCodUsuario(tasaDto.getCodUsuario());
			obj.setMontoTasa(tasaDto.getMontoTasa());
			repo.save(obj);
			
			
		} catch (Exception e) {
			log.info("NO se puedo inicializar la Tasa de la moneda");
		}
		
		
		
		
	}

}
