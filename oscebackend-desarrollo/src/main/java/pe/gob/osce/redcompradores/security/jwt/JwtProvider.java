package pe.gob.osce.redcompradores.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import pe.gob.osce.redcompradores.security.entity.UsuarioPrincipal;
import pe.gob.osce.redcompradores.service.AuditoriaService;

/**
 * Clase proveedora del generar el token y/o validarlo
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
public class JwtProvider {

	private static final AuditoriaService logger = new AuditoriaService(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	/**
	 * Metodo encargado de generar el token en el sistema
	 * @param authentication
	 * @return String eyJhbGciOiJIUzUxMiJ9.... 
	 */
	public String generateToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		return Jwts.builder().setSubject(usuarioPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Metodo encargado de validar el token
	 * @param token
	 * @return true
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			logger.error("token no soportado");
		} catch (ExpiredJwtException e) {
			logger.error("token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("token vacío");
		} catch (SignatureException e) {
			logger.error("fail en la firma");
		}
		return false;
	}
}
