package com.bancoexterior.parametros.monedas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.monedas.entities.LimitesGenerales;
import com.bancoexterior.parametros.monedas.entities.LimitesGeneralesPk;

@Repository
public interface ILimitesGeneralesRepository extends JpaRepository<LimitesGenerales, LimitesGeneralesPk>{

}
