package pe.gob.osce.redcompradores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase main de la aplicacion Red de Compradores
* 
 * <ul>
 * <li> [2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
* @author [Fernando Cuadros] o [DELTA]
* @version [1.0]
* @since [2021]
* 
*/

@SpringBootApplication
public class RedCompradoresApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RedCompradoresApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedCompradoresApplication.class, args);
	}
}
