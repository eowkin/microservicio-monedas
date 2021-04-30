package com.bancoexterior.parametros.monedas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;





@Component
public class LibreriaUtils {

	private static final Logger LOGGER = LogManager.getLogger(LibreriaUtils.class);
	
	
	private LibreriaUtils() {
		
	}
	
	
	
	/**
     * Nombre:                  getHttpStatus
     * Descripcion:             Obtener Status HTTP cuando la peticion dio resultado incorrecto
     * 
     * @param  codigo codigo de respuesta
     * @return  HttpStatus estatus Http
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 08/06/20
     */
	public static HttpStatus getHttpStatus(String codigo) {
		
	
		try {
		  String cod = codigo.substring(0, 1);
	
	
		switch (cod) {
		case Constantes.SUBSTRING_COD_OK:
			return HttpStatus.OK;
			
		case Constantes.SUBSTRING_COD_UNPROCESSABLE_ENTITY:
			return HttpStatus.UNPROCESSABLE_ENTITY;
			
		case Constantes.INTERNAL_SERVER_ERROR:
			return HttpStatus.INTERNAL_SERVER_ERROR;		

		default:
			return HttpStatus.BAD_REQUEST;
			
		}
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return HttpStatus.BAD_REQUEST;
		}
		
		
	}
	
	/**
     * Nombre:                  validaFormatoFecha
     * Descripcion:             Valida formato de la fecha
     *
     * @param  String Fecha a validar 
     * @param String Formato de la fecha
     * @return boolean  true  = Formato y fecha coinciden
     * 				    false = Formato y fecha NO coinciden
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 16/03/21
     */
		
	public static boolean validaFormatoFecha(String fechaValidar, String formato ) {
		
		try {
		      // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
		      LocalDate.parse(fechaValidar,DateTimeFormatter.ofPattern(formato)
		    		   .withResolverStyle(ResolverStyle.STRICT));
		      return true;
		      } catch (DateTimeParseException e) {
		    	  LOGGER.info(e);
		          return false;
		      }
	}
	
	/**
     * Nombre:                  fechaOut
     * Descripcion:             Obtener fecha/hora actual segun el formato deseado
     *
     * @param  format  formato deseado para la fecha
     * @return String  fecha actual con el formato deseado
     * @version 1.0
     * @author Wilmer Vieira
	 * @since 08/06/20
     */
	public static String fechaOut(String format) {
		String dateString = null;
		DateFormat formatof = new SimpleDateFormat(format);
		dateString = formatof.format(new Date());
		return dateString;
	}
}
