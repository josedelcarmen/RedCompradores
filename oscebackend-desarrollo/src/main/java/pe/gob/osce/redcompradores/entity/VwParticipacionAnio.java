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
 * de participaciones por año
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
@Table(name = "VW_PARTICIPACION_ANIO")
public class VwParticipacionAnio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ANIOS")
	private BigDecimal anios;

	@Column(name = "PARTICIPACIONES")
	private BigDecimal participaciones;

	public BigDecimal getAnios() {
		return anios;
	}

	public void setAnios(BigDecimal anios) {
		this.anios = anios;
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
