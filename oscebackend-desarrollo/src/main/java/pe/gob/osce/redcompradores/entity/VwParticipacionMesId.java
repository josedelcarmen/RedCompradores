package pe.gob.osce.redcompradores.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwParticipacionMesId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2360567410218550522L;

	@Column(name = "ANIOS")
	private BigDecimal anios;

	@Column(name = "IDMES")
	private BigDecimal idmes;

	public BigDecimal getAnios() {
		return anios;
	}

	public void setAnios(BigDecimal anios) {
		this.anios = anios;
	}

	public BigDecimal getIdmes() {
		return idmes;
	}

	public void setIdmes(BigDecimal idmes) {
		this.idmes = idmes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
