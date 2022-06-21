package pe.gob.osce.redcompradores.dto;

import java.math.BigDecimal;

public class ReporteDto {

	private BigDecimal nAnioPub;
	private BigDecimal nMesPub;
	private BigDecimal nDiaPub;
	public BigDecimal getnAnioPub() {
		return nAnioPub;
	}
	public void setnAnioPub(BigDecimal nAnioPub) {
		this.nAnioPub = nAnioPub;
	}
	public BigDecimal getnMesPub() {
		return nMesPub;
	}
	public void setnMesPub(BigDecimal nMesPub) {
		this.nMesPub = nMesPub;
	}
	public BigDecimal getnDiaPub() {
		return nDiaPub;
	}
	public void setnDiaPub(BigDecimal nDiaPub) {
		this.nDiaPub = nDiaPub;
	}
	
	
}
