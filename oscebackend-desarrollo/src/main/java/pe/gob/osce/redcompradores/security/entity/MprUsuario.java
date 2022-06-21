package pe.gob.osce.redcompradores.security.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacion;
import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacionArchivo;
import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.publicacion.emtity.TblCalificacion;
import pe.gob.osce.redcompradores.publicacion.emtity.TblComentario;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;

/**
 * Clase entidad que almacena la informacion de los usuarios
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
@Table(name = "MPR_USUARIO")
public class MprUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_USUARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_USUARIO")
	@Column(name = "N_ID_USUARIO")
	private BigDecimal nIdUsuario;

	@Column(name = "C_APE_USUARIO")
	private String cApeUsuario;

	@Column(name = "C_COD_DEP")
	private String cCodDep;

	@Column(name = "C_COD_DIST")
	private String cCodDist;

	@Column(name = "C_COD_PROV")
	private String cCodProv;

	@Column(name = "C_COD_ROL")
	private String cCodRol;

	@Column(name = "C_COD_TIPODOC")
	private String cCodTipodoc;

	@Column(name = "C_DES_EXPERIENCIA")
	private String cDesExperiencia;

	@Column(name = "C_DES_PROFESION")
	private String cDesProfesion;

	@Column(name = "C_DIR_FOTO")
	private String cDirFoto;

	@Column(name = "C_DIR_USUARIO")
	private String cDirUsuario;

	@Column(name = "C_NOM_USUARIO")
	private String cNomUsuario;

	@Column(name = "C_NUM_SEACE")
	private String cNumSeace;

	@Column(name = "C_OBS_USUARIO")
	private String cObsUsuario;

	@Column(name = "C_VAL_CELULAR")
	private String cValCelular;

	@Column(name = "C_VAL_CLAVE")
	private String cValClave;

	@Column(name = "C_VAL_CORREO")
	private String cValCorreo;

	@Column(name = "C_VAL_FACEBOOK")
	private String cValFacebook;

	@Column(name = "C_VAL_INSTAGRAM")
	private String cValInstagram;

	@Column(name = "C_VAL_LINKEDIN")
	private String cValLinkedin;

	@Column(name = "C_VAL_RENIEC")
	private String cValReniec;

	@Column(name = "C_VAL_SEACE")
	private String cValSeace;

	@Column(name = "C_VAL_SEXO")
	private String cValSexo;

	@Column(name = "C_VAL_TWITTER")
	private String cValTwitter;

	@Column(name = "C_VAL_USUARIO_ACTU")
	private String cValUsuarioActu;

	@Column(name = "C_VAL_USUARIO_CREA")
	private String cValUsuarioCrea;

	@Temporal(TemporalType.DATE)
	@Column(name = "D_FEC_FECHA_ACTU")
	private Date dFecFechaActu;

	@Temporal(TemporalType.DATE)
	@Column(name = "D_FEC_FECHA_CREA")
	private Date dFecFechaCrea;

	@Temporal(TemporalType.DATE)
	@Column(name = "D_FEC_NAC")
	private Date dFecNac;

	@Column(name = "N_NUM_DOCUMENTO")
	private String nNumDocumento;

	@Column(name = "N_VAL_CALIFICACION")
	private BigDecimal nValCalificacion;

	@Column(name = "N_VAL_ESTADO")
	private BigDecimal nValEstado;

	// @JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "N_ID_ENTIDAD", nullable = false, updatable = false)
	private MprEntidad mprEntidad;

	@JsonIgnore
	@OneToMany(mappedBy = "mprUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblCalificacion> tblCalificacions;

	@JsonIgnore
	@OneToMany(mappedBy = "mprUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblComentario> tblComentarios;

	@JsonIgnore
	@OneToMany(mappedBy = "mprUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblComunicacion> tblComunicacions;

	@JsonIgnore
	@OneToMany(mappedBy = "mprUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblComunicacionArchivo> tblComunicacionArchivos;

	@JsonManagedReference
	@OneToMany(mappedBy = "mprUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblPublicacion> tblPublicacions;

	public MprUsuario() {
	}

	public BigDecimal getNIdUsuario() {
		return this.nIdUsuario;
	}

	public void setNIdUsuario(BigDecimal nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public String getCApeUsuario() {
		return this.cApeUsuario;
	}

	public void setCApeUsuario(String cApeUsuario) {
		this.cApeUsuario = cApeUsuario;
	}

	public String getCCodDep() {
		return this.cCodDep;
	}

	public void setCCodDep(String cCodDep) {
		this.cCodDep = cCodDep;
	}

	public String getCCodDist() {
		return this.cCodDist;
	}

	public void setCCodDist(String cCodDist) {
		this.cCodDist = cCodDist;
	}

	public String getCCodProv() {
		return this.cCodProv;
	}

	public void setCCodProv(String cCodProv) {
		this.cCodProv = cCodProv;
	}

	public String getCCodRol() {
		return this.cCodRol;
	}

	public void setCCodRol(String cCodRol) {
		this.cCodRol = cCodRol;
	}

	public String getCCodTipodoc() {
		return this.cCodTipodoc;
	}

	public void setCCodTipodoc(String cCodTipodoc) {
		this.cCodTipodoc = cCodTipodoc;
	}

	public String getCDesExperiencia() {
		return this.cDesExperiencia;
	}

	public void setCDesExperiencia(String cDesExperiencia) {
		this.cDesExperiencia = cDesExperiencia;
	}

	public String getCDesProfesion() {
		return this.cDesProfesion;
	}

	public void setCDesProfesion(String cDesProfesion) {
		this.cDesProfesion = cDesProfesion;
	}

	public String getCDirFoto() {
		return this.cDirFoto;
	}

	public void setCDirFoto(String cDirFoto) {
		this.cDirFoto = cDirFoto;
	}

	public String getCDirUsuario() {
		return this.cDirUsuario;
	}

	public void setCDirUsuario(String cDirUsuario) {
		this.cDirUsuario = cDirUsuario;
	}

	public String getCNomUsuario() {
		return this.cNomUsuario;
	}

	public void setCNomUsuario(String cNomUsuario) {
		this.cNomUsuario = cNomUsuario;
	}

	public String getCNumSeace() {
		return this.cNumSeace;
	}

	public void setCNumSeace(String cNumSeace) {
		this.cNumSeace = cNumSeace;
	}

	public String getCObsUsuario() {
		return this.cObsUsuario;
	}

	public void setCObsUsuario(String cObsUsuario) {
		this.cObsUsuario = cObsUsuario;
	}

	public String getCValCelular() {
		return this.cValCelular;
	}

	public void setCValCelular(String cValCelular) {
		this.cValCelular = cValCelular;
	}

	public String getCValClave() {
		return this.cValClave;
	}

	public void setCValClave(String cValClave) {
		this.cValClave = cValClave;
	}

	public String getCValCorreo() {
		return this.cValCorreo;
	}

	public void setCValCorreo(String cValCorreo) {
		this.cValCorreo = cValCorreo;
	}

	public String getCValFacebook() {
		return this.cValFacebook;
	}

	public void setCValFacebook(String cValFacebook) {
		this.cValFacebook = cValFacebook;
	}

	public String getCValInstagram() {
		return this.cValInstagram;
	}

	public void setCValInstagram(String cValInstagram) {
		this.cValInstagram = cValInstagram;
	}

	public String getCValLinkedin() {
		return this.cValLinkedin;
	}

	public void setCValLinkedin(String cValLinkedin) {
		this.cValLinkedin = cValLinkedin;
	}

	public String getCValReniec() {
		return this.cValReniec;
	}

	public void setCValReniec(String cValReniec) {
		this.cValReniec = cValReniec;
	}

	public String getCValSeace() {
		return this.cValSeace;
	}

	public void setCValSeace(String cValSeace) {
		this.cValSeace = cValSeace;
	}

	public String getCValSexo() {
		return this.cValSexo;
	}

	public void setCValSexo(String cValSexo) {
		this.cValSexo = cValSexo;
	}

	public String getCValTwitter() {
		return this.cValTwitter;
	}

	public void setCValTwitter(String cValTwitter) {
		this.cValTwitter = cValTwitter;
	}

	public String getCValUsuarioActu() {
		return this.cValUsuarioActu;
	}

	public void setCValUsuarioActu(String cValUsuarioActu) {
		this.cValUsuarioActu = cValUsuarioActu;
	}

	public String getCValUsuarioCrea() {
		return this.cValUsuarioCrea;
	}

	public void setCValUsuarioCrea(String cValUsuarioCrea) {
		this.cValUsuarioCrea = cValUsuarioCrea;
	}

	public Date getDFecFechaActu() {
		return this.dFecFechaActu;
	}

	public void setDFecFechaActu(Date dFecFechaActu) {
		this.dFecFechaActu = dFecFechaActu;
	}

	public Date getDFecFechaCrea() {
		return this.dFecFechaCrea;
	}

	public void setDFecFechaCrea(Date dFecFechaCrea) {
		this.dFecFechaCrea = dFecFechaCrea;
	}

	public Date getDFecNac() {
		return this.dFecNac;
	}

	public void setDFecNac(Date dFecNac) {
		this.dFecNac = dFecNac;
	}

	public String getNNumDocumento() {
		return this.nNumDocumento;
	}

	public void setNNumDocumento(String nNumDocumento) {
		this.nNumDocumento = nNumDocumento;
	}

	public BigDecimal getNValCalificacion() {
		return this.nValCalificacion;
	}

	public void setNValCalificacion(BigDecimal nValCalificacion) {
		this.nValCalificacion = nValCalificacion;
	}

	public BigDecimal getNValEstado() {
		return this.nValEstado;
	}

	public void setNValEstado(BigDecimal nValEstado) {
		this.nValEstado = nValEstado;
	}

	public MprEntidad getMprEntidad() {
		return this.mprEntidad;
	}

	public void setMprEntidad(MprEntidad mprEntidad) {
		this.mprEntidad = mprEntidad;
	}

	public List<TblCalificacion> getTblCalificacions() {
		return this.tblCalificacions;
	}

	public void setTblCalificacions(List<TblCalificacion> tblCalificacions) {
		this.tblCalificacions = tblCalificacions;
	}

	public List<TblComentario> getTblComentarios() {
		return this.tblComentarios;
	}

	public void setTblComentarios(List<TblComentario> tblComentarios) {
		this.tblComentarios = tblComentarios;
	}

	public List<TblComunicacion> getTblComunicacions() {
		return this.tblComunicacions;
	}

	public void setTblComunicacions(List<TblComunicacion> tblComunicacions) {
		this.tblComunicacions = tblComunicacions;
	}

	public List<TblComunicacionArchivo> getTblComunicacionArchivos() {
		return this.tblComunicacionArchivos;
	}

	public void setTblComunicacionArchivos(List<TblComunicacionArchivo> tblComunicacionArchivos) {
		this.tblComunicacionArchivos = tblComunicacionArchivos;
	}

	public List<TblPublicacion> getTblPublicacions() {
		return this.tblPublicacions;
	}

	public void setTblPublicacions(List<TblPublicacion> tblPublicacions) {
		this.tblPublicacions = tblPublicacions;
	}

}