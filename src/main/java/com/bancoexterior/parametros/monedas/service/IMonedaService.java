package com.bancoexterior.parametros.monedas.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.bancoexterior.parametros.monedas.dto.MonedaDto;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponse;
import com.bancoexterior.parametros.monedas.dto.MonedaDtoResponseActualizar;
import com.bancoexterior.parametros.monedas.dto.MonedasRequest;
import com.bancoexterior.parametros.monedas.response.Resultado;

public interface IMonedaService {

	public MonedaDtoResponse consultaMonedas(MonedasRequest request);
	public boolean existsById(String codMoneda); 
	public MonedaDto findById(String codMoneda);
	public MonedaDtoResponseActualizar save(MonedasRequest monedasRequest, HttpServletRequest requestHTTP);
	public Resultado crear(MonedasRequest monedasRequest);
	public MonedaDtoResponseActualizar actualizar(MonedasRequest monedasRequest, HttpServletRequest requestHTTP);
	public List<MonedaDto> findAllMonedasDto(MonedaDto monedaDto);
	public List<MonedaDto> findAllMonedasDtoNuevo(MonedaDto monedaDto);
	
}
