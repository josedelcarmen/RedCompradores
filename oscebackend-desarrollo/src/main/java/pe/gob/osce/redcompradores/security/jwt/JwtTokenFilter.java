package pe.gob.osce.redcompradores.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import pe.gob.osce.redcompradores.security.service.UserDetailsServiceImpl;
import pe.gob.osce.redcompradores.service.AuditoriaService;

/**
 * Filtro de la aplicacion que valida qus cada peticion se realice con un
 * token valido
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
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final AuditoriaService loggger = new AuditoriaService(JwtTokenFilter.class);

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(req);
			if (token != null && jwtProvider.validateToken(token)) {
				String nombreUsuario = jwtProvider.getUsernameFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			loggger.error("JwtTokenFilter::doFilterInternal::fail en el método doFilter " + e.getMessage());
		}
		filterChain.doFilter(req, res);
	}

	/**
	 * Metodo encargado de obtener el token enviada desde el frontend
	 * en el header de la peticion
	 * @param autorizacion
	 * @return String eyJhbGciOiJIUzUxMiJ9....
	 */
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer"))
			return header.replace("Bearer ", "");
		return null;
	}
}
