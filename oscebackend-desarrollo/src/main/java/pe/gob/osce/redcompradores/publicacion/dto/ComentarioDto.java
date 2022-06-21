package pe.gob.osce.redcompradores.publicacion.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ComentarioDto {

	private BigDecimal nIdComentario;	
	private BigDecimal nIdUsuario;
	private BigDecimal nIdPublicacion;
	private String cValComentario;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private BigDecimal nValEstado;
	private String cNomUsuarioComentario;
	private String cDirFoto;
	private String bFotoPerfil;
	private String cCodRol;
	
	public BigDecimal getnIdComentario() {
		return nIdComentario;
	}
	public void setnIdComentario(BigDecimal nIdComentario) {
		this.nIdComentario = nIdComentario;
	}
	public String getcValComentario() {
		return cValComentario;
	}
	public void setcValComentario(String cValComentario) {
		this.cValComentario = cValComentario;
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
	public String getcNomUsuarioComentario() {
		return cNomUsuarioComentario;
	}
	public void setcNomUsuarioComentario(String cNomUsuarioComentario) {
		this.cNomUsuarioComentario = cNomUsuarioComentario;
	}
	public BigDecimal getnIdUsuario() {
		return nIdUsuario;
	}
	public void setnIdUsuario(BigDecimal nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}
	public BigDecimal getnIdPublicacion() {
		return nIdPublicacion;
	}
	public void setnIdPublicacion(BigDecimal nIdPublicacion) {
		this.nIdPublicacion = nIdPublicacion;
	}
	public String getcDirFoto() {
		return cDirFoto;
	}
	public void setcDirFoto(String cDirFoto) {
		this.cDirFoto = cDirFoto;
	}
	public String getbFotoPerfil() {
		return bFotoPerfil;
	}
	public void setbFotoPerfil(String bFotoPerfil) {
		this.bFotoPerfil = bFotoPerfil;
	}
	public String getcCodRol() {
		return cCodRol;
	}
	public void setcCodRol(String cCodRol) {
		this.cCodRol = cCodRol;
	}
		
}
