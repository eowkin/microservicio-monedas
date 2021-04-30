package com.bancoexterior.parametros.monedas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.monedas.config.Codigos.SQLUtils;
import com.bancoexterior.parametros.monedas.entities.Moneda;

@Repository
public interface IMonedaRepository extends JpaRepository<Moneda, String>{
		
		
		@Query(value = SQLUtils.SELECTMONEDA, nativeQuery = true)
		public List<Moneda> getMonedaByidNuevo(String codMoneda, String flag, boolean flagActivo);
		
	
		
		
		
	
}
