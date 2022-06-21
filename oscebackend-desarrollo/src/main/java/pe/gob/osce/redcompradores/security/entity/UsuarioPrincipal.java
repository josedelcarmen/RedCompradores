package pe.gob.osce.redcompradores.security.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pe.gob.osce.redcompradores.util.Utility;

/**
 * Clase que mapea al usuario autenticado junto a sus roles.
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
public class UsuarioPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal nIdUsuario;
	private String username;
	private String nombreUsuario;
	private String email;
	private String password;
	private String rol;
	private BigDecimal nIdEntidad;
	private String cDesEntidad;
	private BigDecimal nValCalificacion;
	private String cDirFoto;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(String username, String nombreUsuario, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String idrol, BigDecimal nIdUsuario,
			BigDecimal nIdEntidad, String cDesEntidad, BigDecimal nValCalificacion, String cDirFoto) {
		this.username = username;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.rol = Utility.obtenerRol(idrol);
		this.authorities = authorities;
		this.nIdUsuario = nIdUsuario;
		this.nIdEntidad = nIdEntidad;
		this.cDesEntidad = cDesEntidad;
		this.nValCalificacion = nValCalificacion;
		this.cDirFoto = cDirFoto;
	}

	public static UsuarioPrincipal build(MprUsuario usuario) {

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Utility.obtenerRol(usuario.getCCodRol()));
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(grantedAuthority);

		return new UsuarioPrincipal(usuario.getCValCorreo(),
				usuario.getCNomUsuario().concat(" ").concat(usuario.getCApeUsuario()), usuario.getCValCorreo(),
				usuario.getCValClave(), authorities, usuario.getCCodRol(), usuario.getNIdUsuario(),
				usuario.getMprEntidad().getNIdEntidad(), usuario.getMprEntidad().getCDesEntidad(),
				usuario.getNValCalificacion(), usuario.getCDirFoto());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public String getRol() {
		return rol;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public BigDecimal getnIdUsuario() {
		return nIdUsuario;
	}

	public BigDecimal getnIdEntidad() {
		return nIdEntidad;
	}

	public String getcDesEntidad() {
		return cDesEntidad;
	}

	public BigDecimal getnValCalificacion() {
		return nValCalificacion;
	}

	public String getcDirFoto() {
		return cDirFoto;
	}

	
}
