package pe.gob.osce.redcompradores.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import pe.gob.osce.redcompradores.service.AuditoriaService;

/**
 * Clase validadora de autenticacion dentro del sistema que 
 * valide el token y el uso de esto dentro del sistema
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
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private static final AuditoriaService logger = new AuditoriaService(JwtEntryPoint.class);

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
			throws IOException, ServletException {
		logger.error("JwtEntryPoint::commence::Error en el metodo commence: " + e.getMessage());
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
	}
}
