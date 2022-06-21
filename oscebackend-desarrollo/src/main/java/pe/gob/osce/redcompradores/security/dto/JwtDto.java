package pe.gob.osce.redcompradores.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.util.Collection;

public class JwtDto extends LoginUsuario{
    private String token;
    private String bearer = "Bearer";
    private String rol;
    private String nombreUsuario;
    private String message;
    private BigDecimal nIdUsuario;
    private BigDecimal nIdEntidad;
    private String cDesEntidad;
    private String type;
    private BigDecimal nValCalificacion;
    private String cDirFoto;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities, 
    		String rol, String nombreUsuario, BigDecimal nIdUsuario, BigDecimal nIdEntidad, String cDesEntidad,
    		BigDecimal nValCalificacion, String cDirFoto) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.rol = rol;
        this.nombreUsuario = nombreUsuario;
        this.nIdUsuario = nIdUsuario;
        this.nIdEntidad = nIdEntidad;
        this.cDesEntidad = cDesEntidad;
        this.nValCalificacion = nValCalificacion;
        this.cDirFoto = cDirFoto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(BigDecimal nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public BigDecimal getnIdEntidad() {
		return nIdEntidad;
	}

	public void setnIdEntidad(BigDecimal nIdEntidad) {
		this.nIdEntidad = nIdEntidad;
	}

	public String getcDesEntidad() {
		return cDesEntidad;
	}

	public void setcDesEntidad(String cDesEntidad) {
		this.cDesEntidad = cDesEntidad;
	}

	public BigDecimal getnValCalificacion() {
		return nValCalificacion;
	}

	public void setnValCalificacion(BigDecimal nValCalificacion) {
		this.nValCalificacion = nValCalificacion;
	}

	public String getcDirFoto() {
		return cDirFoto;
	}

	public void setcDirFoto(String cDirFoto) {
		this.cDirFoto = cDirFoto;
	}

    
}
