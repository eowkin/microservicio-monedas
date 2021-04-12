package com.bancoexterior.parametros.monedas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.monedas.dto.MonedaDto;
import com.bancoexterior.parametros.monedas.entities.Moneda;

@Repository
public interface IMonedaRepository extends JpaRepository<Moneda, String>{
	String queryAll = "select new com.bancoexterior.parametros.monedas.dto.MonedaDto(t.codMoneda, t.descripcion, t.codAlterno, t.flagActivo, t.codUsuario, t.fechaModificacion) "
			+ " from Moneda t"
			+ " where 1=1";

		@Query(value = queryAll)
		public List<MonedaDto> getAll();
		
		@Query(value = queryAll + " and t.codMoneda = ?1")
		public List<MonedaDto> getMonedaByid(String codMoneda);
		
		@Query(value = queryAll + " and t.flagActivo = ?1")
		public List<MonedaDto> getMonedaByFlagActivo(boolean flagActivo);
		
		@Query(value = queryAll + " and t.codMoneda = ?1 and t.flagActivo = ?2")
		public List<MonedaDto> getMonedaByidAndFlag(String codMoneda, boolean flagActivo);
	
}
