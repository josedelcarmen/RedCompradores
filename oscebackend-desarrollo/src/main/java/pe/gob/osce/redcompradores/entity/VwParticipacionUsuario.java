package pe.gob.osce.redcompradores.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * Clase entidad que mapea la informacion de los reportes
 * de participaciones por usuario
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

@Entity
@Immutable
@Table(name = "VW_PARTICIPACION_USUARIOS")
public class VwParticipacionUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDUSUARIO")
	private BigDecimal idusuario;
	
	@Column(name = "USUARIO")
	private String usuario;

	@Column(name = "PUBLICACIONES")
	private BigDecimal publicaciones;

	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(BigDecimal publicaciones) {
		this.publicaciones = publicaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
