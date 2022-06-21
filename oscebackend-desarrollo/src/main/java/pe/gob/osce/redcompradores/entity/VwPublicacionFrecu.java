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
 * de publicaciones por temas
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
@Table(name = "VW_PUBLICACION_FRECU")
public class VwPublicacionFrecu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTEMA")
	private String idTema;

	@Column(name = "TEMA")
	private String tema;

	@Column(name = "PARTICIPACIONES")
	private BigDecimal participaciones;

	public String getIdTema() {
		return idTema;
	}

	public void setIdTema(String idTema) {
		this.idTema = idTema;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public BigDecimal getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(BigDecimal participaciones) {
		this.participaciones = participaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
