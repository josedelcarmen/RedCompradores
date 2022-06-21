/**
 * 
 */
package pe.gob.osce.redcompradores.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase encargada de la generacion del archivo log de la aplicacion
 * 
 * <ul>
 * <li>[2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
 * @author [Fernando Cuadros] o [DELTA]
 * @version [1.0]
 * @since [2021]
 * 
 */

public class AuditoriaService {

	private Logger logger;

	public AuditoriaService(Class<?> clazz) {
		logger = LogManager.getLogger(clazz);
	}

	public void info(String trace) {
		logger.info(trace);
	}

	public void error(String trace) {
		logger.error(trace);
	}
	
	public void error(String trace, Throwable t) {
		logger.error(trace, t);
	}

	public void warn(String trace) {
		logger.warn(trace);
	}

	public void fatal(String trace) {
		logger.fatal(trace);
	}

	public void debug(String trace) {
		logger.debug(trace);
	}
	
}
