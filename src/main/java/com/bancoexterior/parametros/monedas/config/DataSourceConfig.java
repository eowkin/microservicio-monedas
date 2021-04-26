package com.bancoexterior.parametros.monedas.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.bancoexterior.parametros.monedas.seguridad.MiCipher;



@Configuration
@PropertySource( value = "file://"+"${${microservicio.ambiente}"+".seed.ruta}"+"application.properties", ignoreResourceNotFound = false)
public class DataSourceConfig {
    
	private static final Logger LOGGER = LogManager.getLogger(DataSourceConfig.class);
	
	@Value("${microservicio.ambiente}")
	private String ambiente;
	
	@Value("${spring.datasource.username}")
	private String usuario;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${${microservicio.ambiente}"+".seed.moneda}")
    private String seedMoneda;
	
	@Value("${sconfig.deskey}")
    private String sconfigDesKey;
	
	@Value("${sconfig.prokey}")
    private String sconfigProKey;
	
    @Bean
    public DataSource getDataSource() {
    	
    	
    	
    	
    	
    	LOGGER.info("ambiente: "+ambiente);
    	LOGGER.info("usuario: "+usuario);
    	LOGGER.info("password: "+password);
    	LOGGER.info("sconfigDesKey: "+sconfigDesKey);
    	LOGGER.info("sconfigProKey: "+sconfigProKey);
    	LOGGER.info("seedMoneda: "+seedMoneda);
    	//String usuarioEncrypt = MiCipher.encrypt(usuario, SConfig.getkEY(ambiente)[0]);
    	//String passwordEncrypt = MiCipher.encrypt(password, SConfig.getkEY(ambiente)[0]);
    	
    	
    	
    	String usuarioDecrypt = MiCipher.decrypt(usuario, sconfigDesKey);
    	String passwordDecrypt = MiCipher.decrypt(password, sconfigDesKey);
    	
    	LOGGER.info("usuarioDecrypt: "+usuarioDecrypt);
    	LOGGER.info("passwordDecrypt: "+passwordDecrypt);
    	
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/Convenio1");
        dataSourceBuilder.username(usuarioDecrypt);
        dataSourceBuilder.password(passwordDecrypt);
        return dataSourceBuilder.build();
    }
}