package pe.gob.osce.redcompradores.publicacion.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ArchivoDto {

	private BigDecimal nIdArchivo;
	private String cValClaveArchivo;
	private String cValNomArchivo;
	private String cValNomOriArchivo;
	private String cValRutaArchivo;
	private String cValTipoArchivo;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private BigDecimal nValEstado;
	
	private String cCodTema;
	private String cDesTema;
	
	public BigDecimal getnIdArchivo() {
		return nIdArchivo;
	}
	public void setnIdArchivo(BigDecimal nIdArchivo) {
		this.nIdArchivo = nIdArchivo;
	}
	public String getcValClaveArchivo() {
		return cValClaveArchivo;
	}
	public void setcValClaveArchivo(String cValClaveArchivo) {
		this.cValClaveArchivo = cValClaveArchivo;
	}
	public String getcValNomArchivo() {
		return cValNomArchivo;
	}
	public void setcValNomArchivo(String cValNomArchivo) {
		this.cValNomArchivo = cValNomArchivo;
	}
	public String getcValNomOriArchivo() {
		return cValNomOriArchivo;
	}
	public void setcValNomOriArchivo(String cValNomOriArchivo) {
		this.cValNomOriArchivo = cValNomOriArchivo;
	}
	public String getcValRutaArchivo() {
		return cValRutaArchivo;
	}
	public void setcValRutaArchivo(String cValRutaArchivo) {
		this.cValRutaArchivo = cValRutaArchivo;
	}
	public String getcValTipoArchivo() {
		return cValTipoArchivo;
	}
	public void setcValTipoArchivo(String cValTipoArchivo) {
		this.cValTipoArchivo = cValTipoArchivo;
	}
	public String getcValUsuarioActu() {
		return cValUsuarioActu;
	}
	public void setcValUsuarioActu(String cValUsuarioActu) {
		this.cValUsuarioActu = cValUsuarioActu;
	}
	public String getcValUsuarioCrea() {
		return cValUsuarioCrea;
	}
	public void setcValUsuarioCrea(String cValUsuarioCrea) {
		this.cValUsuarioCrea = cValUsuarioCrea;
	}
	public Date getdFecFechaActu() {
		return dFecFechaActu;
	}
	public void setdFecFechaActu(Date dFecFechaActu) {
		this.dFecFechaActu = dFecFechaActu;
	}
	public Date getdFecFechaCrea() {
		return dFecFechaCrea;
	}
	public void setdFecFechaCrea(Date dFecFechaCrea) {
		this.dFecFechaCrea = dFecFechaCrea;
	}
	public BigDecimal getnValEstado() {
		return nValEstado;
	}
	public void setnValEstado(BigDecimal nValEstado) {
		this.nValEstado = nValEstado;
	}
	public String getcCodTema() {
		return cCodTema;
	}
	public void setcCodTema(String cCodTema) {
		this.cCodTema = cCodTema;
	}
	public String getcDesTema() {
		return cDesTema;
	}
	public void setcDesTema(String cDesTema) {
		this.cDesTema = cDesTema;
	}
	
	
}
