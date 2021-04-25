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
		
		
		String queryNativo = "SELECT cod_moneda, descripcion, cod_alterno, flag_activo, cod_usuario, fecha_modificacion "
				+ "FROM \"Convenio1\".\"Monedas\" "
				+ "where cod_moneda = (case when ?1 = '' then cod_moneda else ?1 end) \r\n"
				+ "and \r\n"
				+ "(case when  ?2 = 'si' then \r\n"
				+ "	flag_activo= ?3 \r\n"
				+ "else \r\n"
				+ "	flag_activo = flag_activo\r\n"
				+ "\r\n"
				+ "end) ";
		
		
		@Query(value = queryNativo, nativeQuery = true)
		public List<Moneda> getMonedaByidNuevo(String codMoneda, String flag, boolean flagActivo);
		
		
//		String queryNuevo = "select new com.bancoexterior.parametros.monedas.dto.MonedaDto(t.codMoneda, t.descripcion, t.codAlterno, t.flagActivo, t.codUsuario, t.fechaModificacion) "
//				+ " from Moneda t"
//				+ " where t.codMoneda = (case when ?1 = '' then t.codMoneda else ?1 end) "
//				+ " and "
//				+ " (case when  ?2 = '' then "
//				+ "    t.flagActivo = ?3 "
//				+ " else "
//				+ "   t.flagActivo = t.flagActivo "
//				+ " end)";
//	
//
//		@Query(value = queryNuevo)
//		public List<MonedaDto> getMonedaByidNuevo(String codMoneda, String flag, boolean flagActivo);
		
		
		@Query(value = queryAll)
		public List<MonedaDto> getAll();
		
		@Query(value = queryAll + " and t.codMoneda = ?1")
		public List<MonedaDto> getMonedaByid(String codMoneda);
		
		@Query(value = queryAll + " and t.flagActivo = ?1")
		public List<MonedaDto> getMonedaByFlagActivo(boolean flagActivo);
		
		@Query(value = queryAll + " and t.codMoneda = ?1 and t.flagActivo = ?2")
		public List<MonedaDto> getMonedaByidAndFlag(String codMoneda, boolean flagActivo);
		
		
		public List<Moneda> findByCodMonedaAndFlagActivo(String codMoneda, boolean flagActivo);
		
	
}
