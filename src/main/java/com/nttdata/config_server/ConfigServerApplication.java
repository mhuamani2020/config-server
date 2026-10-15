package com.nttdata.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Clase principal del servidor de configuración centralizada
 * (Spring Cloud Config Server).
 * <p>
 * Sirve a los demás microservicios del banco la configuración versionada y
 * externa (por ejemplo, rutas del API Gateway o propiedades de conexión a
 * MongoDB), evitando duplicarla en cada servicio.
 *
 * @author NTT Data
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	/**
	 * Punto de entrada de la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos proporcionados al arrancar
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
