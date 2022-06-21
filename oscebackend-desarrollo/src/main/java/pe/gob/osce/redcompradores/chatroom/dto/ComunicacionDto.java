package pe.gob.osce.redcompradores.chatroom.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ComunicacionDto {

	private BigDecimal nIdComunicacion;
	private BigDecimal nIdUsuario;
	private String cValCorreo;
	private String cDesComunicacion;
	private String cValTipoComunicacion;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private BigDecimal nValEstado;
	private String cValColor;
	private String cValNomUsuario;
	private String cValNomEntidad;
	public BigDecimal getnIdComunicacion() {
		return nIdComunicacion;
	}
	public void setnIdComunicacion(BigDecimal nIdComunicacion) {
		this.nIdComunicacion = nIdComunicacion;
	}
	public BigDecimal getnIdUsuario() {
		return nIdUsuario;
	}
	public void setnIdUsuario(BigDecimal nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}
	public String getcValCorreo() {
		return cValCorreo;
	}
	public void setcValCorreo(String cValCorreo) {
		this.cValCorreo = cValCorreo;
	}
	public String getcDesComunicacion() {
		return cDesComunicacion;
	}
	public void setcDesComunicacion(String cDesComunicacion) {
		this.cDesComunicacion = cDesComunicacion;
	}
	public String getcValTipoComunicacion() {
		return cValTipoComunicacion;
	}
	public void setcValTipoComunicacion(String cValTipoComunicacion) {
		this.cValTipoComunicacion = cValTipoComunicacion;
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
	public String getcValColor() {
		return cValColor;
	}
	public void setcValColor(String cValColor) {
		this.cValColor = cValColor;
	}
	public String getcValNomUsuario() {
		return cValNomUsuario;
	}
	public void setcValNomUsuario(String cValNomUsuario) {
		this.cValNomUsuario = cValNomUsuario;
	}
	public String getcValNomEntidad() {
		return cValNomEntidad;
	}
	public void setcValNomEntidad(String cValNomEntidad) {
		this.cValNomEntidad = cValNomEntidad;
	}
}

