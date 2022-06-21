package pe.gob.osce.redcompradores.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EntidadDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1965934126224130047L;

	private BigDecimal nIdEntidad;
	private String cDesEntidad;
	private String cNomDominio;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private BigDecimal nValEstado;
	private String nValValidaUsuario;
	private String cValEstadoDesc;
	private String cValValidaUsuarioDesc;

	public EntidadDto() {
	}

	public String getcDesEntidad() {
		return cDesEntidad;
	}

	public void setcDesEntidad(String cDesEntidad) {
		this.cDesEntidad = cDesEntidad;
	}

	public String getcNomDominio() {
		return cNomDominio;
	}

	public void setcNomDominio(String cNomDominio) {
		this.cNomDominio = cNomDominio;
	}

	public String getnValValidaUsuario() {
		return nValValidaUsuario;
	}

	public void setnValValidaUsuario(String nValValidaUsuario) {
		this.nValValidaUsuario = nValValidaUsuario;
	}

	public BigDecimal getnIdEntidad() {
		return nIdEntidad;
	}

	public void setnIdEntidad(BigDecimal nIdEntidad) {
		this.nIdEntidad = nIdEntidad;
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

	public String getcValEstadoDesc() {
		return cValEstadoDesc;
	}

	public void setcValEstadoDesc(String cValEstadoDesc) {
		this.cValEstadoDesc = cValEstadoDesc;
	}

	public String getcValValidaUsuarioDesc() {
		return cValValidaUsuarioDesc;
	}

	public void setcValValidaUsuarioDesc(String cValValidaUsuarioDesc) {
		this.cValValidaUsuarioDesc = cValValidaUsuarioDesc;
	}
}