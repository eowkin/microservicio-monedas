package com.bancoexterior.parametros.monedas.seguridad;

import com.bancoexterior.parametros.monedas.config.Codigos.Ambientes;
import com.bancoexterior.parametros.monedas.config.Codigos.Constantes;

public class SConfig {
	
	private SConfig() {
	    super();
	  }
	
	public static final String ALGORITMO = "AES/GCM/NoPadding";
	public static final String CSET = "AES";
	protected static final byte[] IV = new byte[16];
	public static final int TAG_LENGTH_BIT = 128;
	public static final String SHA256 = "SHA-256";
	public static final String SHA512 = "SHA-512";
	
	private static final String DKEY  = "X#!g00aN430=?$|:.dAs";
	private static final String DKEY2 = "A&a032HVWnd||_jk?oBN";

	private static final String PKEY  = "OA#$%&.=?(Ta00fgb$sd";
	private static final String PKEY2 = "FA;:La00%$TopPbn;d|@";
	
	
	public static String[] getkEY(String ambiente) {
		
		ambiente = ambiente == null ? Constantes.BLANK:ambiente;
		
		switch (ambiente.toLowerCase()) {
		case Ambientes.DESARROLLO: case Ambientes.CALIDAD:
			return new String[] {DKEY,DKEY2};		
		case Ambientes.PRODUCCION : default:
			return new String[] {PKEY,PKEY2};


	}
				
		
	}
	

}
