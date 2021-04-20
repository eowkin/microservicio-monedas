package com.bancoexterior.parametros.monedas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.bancoexterior.parametros.monedas.dto.MonedaDto;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedaRequestActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasDtoRequest;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.entities.Moneda;
import com.bancoexterior.parametros.monedas.response.Resultado;

public interface IMonedaService {

	public MonedaDtoResponse consultaMonedas(MonedasRequest request);
	public Resultado gestionCrearMoneda(MonedasRequest request,HttpServletRequest requestHTTP);
	public Resultado gestionActualizarMoneda(MonedasRequest request,HttpServletRequest requestHTTP);
	public boolean existsById(String codMoneda); 
	public MonedaDto findById(String codMoneda);
	public MonedaDtoResponse save(MonedasRequest monedasRequest);
	public Resultado crear(MonedasRequest monedasRequest);
	public Resultado actualizar(MonedaRequestActualizar monedaRequestActualizar);
	public List<Moneda> findAll();
	public List<MonedaDto> findAllMonedasDto(MonedaDto monedaDto);
	public List<MonedaDto> findAllGlobalMapper();
	public MonedaDtoResponse getAll();
	public MonedaDtoResponse findAllDtoResponse();
	public MonedaDtoResponse get(String codMoneda);
	public MonedaDtoResponse getMonedasByParameter(String codMoneda, boolean flagActivo);
	public MonedaDtoResponse getMonedasByFlagActivo(boolean flagActivo);
}
