package pe.gob.osce.redcompradores.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * Clase entidad que mapea la informacion de los reportes
 * de participaciones por mes
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
@Table(name = "VW_PARTICIPACION_MES")
public class VwParticipacionMes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VwParticipacionMesId id;
		
	@Column(name = "MES")
	private String mes;
	
	@Column(name = "PARTICIPACIONES")
	private BigDecimal participaciones;

	public VwParticipacionMesId getId() {
		return id;
	}

	public void setId(VwParticipacionMesId id) {
		this.id = id;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
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
