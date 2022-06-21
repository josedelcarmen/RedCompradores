package pe.gob.osce.redcompradores.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwParticipacionDiaId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4257982911096287602L;

	@Column(name = "ANIOS")
	private BigDecimal anios;

	@Column(name = "IDMES")
	private BigDecimal idmes;
	
	@Column(name = "IDDIA")
	private BigDecimal iddia;

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

	public BigDecimal getIddia() {
		return iddia;
	}

	public void setIddia(BigDecimal iddia) {
		this.iddia = iddia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
