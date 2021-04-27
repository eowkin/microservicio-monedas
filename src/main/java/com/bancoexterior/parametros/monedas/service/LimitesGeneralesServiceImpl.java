package com.bancoexterior.parametros.monedas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancoexterior.parametros.monedas.config.Codigos.Servicios;
import com.bancoexterior.parametros.monedas.dto.LimitesGeneralesDto;
import com.bancoexterior.parametros.monedas.entities.LimitesGenerales;
import com.bancoexterior.parametros.monedas.entities.LimitesGeneralesPk;
import com.bancoexterior.parametros.monedas.repository.ILimitesGeneralesRepository;




@Service
public class LimitesGeneralesServiceImpl implements ILimitesGeneralesService{

	private static final Logger LOGGER = LogManager.getLogger(LimitesGeneralesServiceImpl.class);
	
	@Autowired
	private ILimitesGeneralesRepository repo;
	
	
	
	/**
	 * Nombre: inicializarLimitesGeneralesMoneda 
	 * Descripcion: Invocar metodo para inicializar los
	 * limites a la moneda a crear.
	 *
	 * @param request     Objeto tipo LimitesGeneralesDto
	 * @return void
	 * @version 1.0
	 * @author Eugenio Owkin
	 * @since 12/04/21
	 */
	
	
	@Override
	public void inicializarLimitesGeneralesMoneda(LimitesGeneralesDto limitesGeneralesDto) {
		LOGGER.info(Servicios.MONEDASSERVICEILIMITES);
		
		try {
			List<LimitesGenerales> listLimites = new ArrayList<LimitesGenerales>();
			String codMoneda = limitesGeneralesDto.getCodMoneda();
			LimitesGeneralesPk id = new LimitesGeneralesPk();
			id.setCodMoneda(codMoneda);
			id.setTipoTransaccion("C");
			id.setTipoCliente("J");
			
			LimitesGenerales limites = new LimitesGenerales();
			limites.setId(id);
			limites.setMontoMin(BigDecimal.ZERO);
			limites.setMontoMax(BigDecimal.ZERO);
			limites.setMontoTope(BigDecimal.ZERO);
			limites.setMontoMensual(BigDecimal.ZERO);
			limites.setMontoDiario(BigDecimal.ZERO);
			limites.setMontoBanco(BigDecimal.ZERO);
			limites.setCodUsuario(limitesGeneralesDto.getCodUsuario());
			limites.setFlagActivo(false);
			listLimites.add(limites);
			
		
			LimitesGeneralesPk id1 = new LimitesGeneralesPk();
			id1.setCodMoneda(codMoneda);
			id1.setTipoTransaccion("V");
			id1.setTipoCliente("J");
			
			LimitesGenerales limites1 = new LimitesGenerales();
			
			limites1.setId(id1);
			limites1.setMontoBanco(BigDecimal.ZERO);
			limites1.setMontoDiario(BigDecimal.ZERO);
			limites1.setMontoMax(BigDecimal.ZERO);
			limites1.setMontoMensual(BigDecimal.ZERO);
			limites1.setMontoMin(BigDecimal.ZERO);
			limites1.setMontoTope(BigDecimal.ZERO);
			limites1.setCodUsuario(limitesGeneralesDto.getCodUsuario());
			limites1.setFlagActivo(false);
			
			listLimites.add(limites1);
			
			LimitesGeneralesPk id2 = new LimitesGeneralesPk();
			id2.setCodMoneda(codMoneda);
			id2.setTipoTransaccion("C");
			id2.setTipoCliente("N");
			
			LimitesGenerales limites2 = new LimitesGenerales();
			limites2.setId(id2);
			limites2.setMontoBanco(BigDecimal.ZERO);
			limites2.setMontoDiario(BigDecimal.ZERO);
			limites2.setMontoMax(BigDecimal.ZERO);
			limites2.setMontoMensual(BigDecimal.ZERO);
			limites2.setMontoMin(BigDecimal.ZERO);
			limites2.setMontoTope(BigDecimal.ZERO);
			limites2.setCodUsuario(limitesGeneralesDto.getCodUsuario());
			limites2.setFlagActivo(false);
			
			listLimites.add(limites2);
			
			LimitesGeneralesPk id3 = new LimitesGeneralesPk();
			id3.setCodMoneda(codMoneda);
			id3.setTipoTransaccion("V");
			id3.setTipoCliente("N");
			
			LimitesGenerales limites3 = new LimitesGenerales();
			limites3.setId(id3);
			limites3.setMontoBanco(BigDecimal.ZERO);
			limites3.setMontoDiario(BigDecimal.ZERO);
			limites3.setMontoMax(BigDecimal.ZERO);
			limites3.setMontoMensual(BigDecimal.ZERO);
			limites3.setMontoMin(BigDecimal.ZERO);
			limites3.setMontoTope(BigDecimal.ZERO);
			limites3.setCodUsuario(limitesGeneralesDto.getCodUsuario());
			limites3.setFlagActivo(false);
			
			listLimites.add(limites3);
		
			repo.saveAll(listLimites);
			LOGGER.info(Servicios.MONEDASSERVICEFLIMITES);
		} catch (Exception e) {
			LOGGER.error("Error inicializando-Limites");
			LOGGER.error(e);
			
		}
		
		
	}

}
