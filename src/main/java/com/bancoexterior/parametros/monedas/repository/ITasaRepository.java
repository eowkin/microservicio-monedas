package com.bancoexterior.parametros.monedas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancoexterior.parametros.monedas.entities.Tasa;
import com.bancoexterior.parametros.monedas.entities.TasaPk;

@Repository
public interface ITasaRepository extends JpaRepository<Tasa, TasaPk>{

}
