package pe.gob.osce.redcompradores.publicacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PublicacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal nIdPublicacion;	
	private BigDecimal nIdUsuario;	
	private String cValNomUsuario;
	private String cValNomEntidad;
	private String cValNomArchivo;
	private String cValRutaArchivo;
	private BigDecimal nIdArchivo;
	private String cCodTema;
	private String cValClavePub;
	private String cValDetPub;
	private String cValEnviaCorreo;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private BigDecimal nIdCalificacion;
	private BigDecimal nValCalificacionUsuario;
	private BigDecimal nValEstado;
	private BigDecimal nCantLikes;
	private BigDecimal nCantDisslikes;
	private BigDecimal nCantComentarios;
	private String cTipoCalificacion;
	private String cTipoArchivo;
	private String cFilterTema;
	private String cFilterPalabrasClave;
	private String cDirFoto;
	private String bFotoPerfil;
	
	public BigDecimal getnIdPublicacion() {
		return nIdPublicacion;
	}
	public void setnIdPublicacion(BigDecimal nIdPublicacion) {
		this.nIdPublicacion = nIdPublicacion;
	}
	public String getcCodTema() {
		return cCodTema;
	}
	public void setcCodTema(String cCodTema) {
		this.cCodTema = cCodTema;
	}
	public String getcValClavePub() {
		return cValClavePub;
	}
	public void setcValClavePub(String cValClavePub) {
		this.cValClavePub = cValClavePub;
	}
	public String getcValDetPub() {
		return cValDetPub;
	}
	public void setcValDetPub(String cValDetPub) {
		this.cValDetPub = cValDetPub;
	}
	public String getcValEnviaCorreo() {
		return cValEnviaCorreo;
	}
	public void setcValEnviaCorreo(String cValEnviaCorreo) {
		this.cValEnviaCorreo = cValEnviaCorreo;
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
	public BigDecimal getnIdArchivo() {
		return nIdArchivo;
	}
	public void setnIdArchivo(BigDecimal nIdArchivo) {
		this.nIdArchivo = nIdArchivo;
	}
	public BigDecimal getnIdUsuario() {
		return nIdUsuario;
	}
	public void setnIdUsuario(BigDecimal nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
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
	public String getcValNomArchivo() {
		return cValNomArchivo;
	}
	public void setcValNomArchivo(String cValNomArchivo) {
		this.cValNomArchivo = cValNomArchivo;
	}
	public String getcValRutaArchivo() {
		return cValRutaArchivo;
	}
	public void setcValRutaArchivo(String cValRutaArchivo) {
		this.cValRutaArchivo = cValRutaArchivo;
	}
	public BigDecimal getnCantLikes() {
		return nCantLikes;
	}
	public void setnCantLikes(BigDecimal nCantLikes) {
		this.nCantLikes = nCantLikes;
	}
	public BigDecimal getnCantDisslikes() {
		return nCantDisslikes;
	}
	public void setnCantDisslikes(BigDecimal nCantDisslikes) {
		this.nCantDisslikes = nCantDisslikes;
	}
	public BigDecimal getnCantComentarios() {
		return nCantComentarios;
	}
	public void setnCantComentarios(BigDecimal nCantComentarios) {
		this.nCantComentarios = nCantComentarios;
	}
	public String getcTipoCalificacion() {
		return cTipoCalificacion;
	}
	public void setcTipoCalificacion(String cTipoCalificacion) {
		this.cTipoCalificacion = cTipoCalificacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getnIdCalificacion() {
		return nIdCalificacion;
	}
	public void setnIdCalificacion(BigDecimal nIdCalificacion) {
		this.nIdCalificacion = nIdCalificacion;
	}
	public BigDecimal getnValCalificacionUsuario() {
		return nValCalificacionUsuario;
	}
	public void setnValCalificacionUsuario(BigDecimal nValCalificacionUsuario) {
		this.nValCalificacionUsuario = nValCalificacionUsuario;
	}
	public String getcTipoArchivo() {
		return cTipoArchivo;
	}
	public void setcTipoArchivo(String cTipoArchivo) {
		this.cTipoArchivo = cTipoArchivo;
	}
	public String getcFilterTema() {
		return cFilterTema;
	}
	public void setcFilterTema(String cFilterTema) {
		this.cFilterTema = cFilterTema;
	}
	public String getcFilterPalabrasClave() {
		return cFilterPalabrasClave;
	}
	public void setcFilterPalabrasClave(String cFilterPalabrasClave) {
		this.cFilterPalabrasClave = cFilterPalabrasClave;
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
	
}
