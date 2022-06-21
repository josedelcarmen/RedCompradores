package pe.gob.osce.redcompradores.publicacion.emtity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Clase entidad que almacena la informacion de las publicaciones
 * 
 * <ul>
 * <li>[2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
 * @author [Fernando Cuadros] o [DELTA]
 * @version [1.0]
 * @since [2021]
 * 
 */
@Entity
@Table(name = "TBL_PUBLICACION")
public class TblPublicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_PUBLICACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_PUBLICACION")
	@Column(name = "N_ID_PUBLICACION")
	private BigDecimal nIdPublicacion;

	@Column(name = "C_COD_TEMA")
	private String cCodTema;

	@Column(name = "C_VAL_CLAVE_PUB")
	private String cValClavePub;

	@Column(name = "C_VAL_DET_PUB")
	private String cValDetPub;

	@Column(name = "C_VAL_ENVIA_CORREO")
	private String cValEnviaCorreo;

	@Column(name = "C_VAL_USUARIO_ACTU")
	private String cValUsuarioActu;

	@Column(name = "C_VAL_USUARIO_CREA")
	private String cValUsuarioCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "D_FEC_FECHA_ACTU")
	private Date dFecFechaActu;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "D_FEC_FECHA_CREA")
	private Date dFecFechaCrea;

	@Column(name = "N_VAL_ESTADO")
	private BigDecimal nValEstado;

	@JsonIgnore
	@OneToMany(mappedBy = "tblPublicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TblArchivo> tblArchivos;

	@JsonIgnore
	@OneToMany(mappedBy = "tblPublicacion", fetch = FetchType.LAZY)
	private List<TblCalificacion> tblCalificacions;

	@JsonIgnore
	@OneToMany(mappedBy = "tblPublicacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblComentario> tblComentarios;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_USUARIO")
	private MprUsuario mprUsuario;

	public BigDecimal getNIdPublicacion() {
		return this.nIdPublicacion;
	}

	public void setNIdPublicacion(BigDecimal nIdPublicacion) {
		this.nIdPublicacion = nIdPublicacion;
	}

	public String getCCodTema() {
		return this.cCodTema;
	}

	public void setCCodTema(String cCodTema) {
		this.cCodTema = cCodTema;
	}

	public String getCValClavePub() {
		return this.cValClavePub;
	}

	public void setCValClavePub(String cValClavePub) {
		this.cValClavePub = cValClavePub;
	}

	public String getCValDetPub() {
		return this.cValDetPub;
	}

	public void setCValDetPub(String cValDetPub) {
		this.cValDetPub = cValDetPub;
	}

	public String getCValEnviaCorreo() {
		return this.cValEnviaCorreo;
	}

	public void setCValEnviaCorreo(String cValEnviaCorreo) {
		this.cValEnviaCorreo = cValEnviaCorreo;
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

	public BigDecimal getNValEstado() {
		return this.nValEstado;
	}

	public void setNValEstado(BigDecimal nValEstado) {
		this.nValEstado = nValEstado;
	}

	public List<TblArchivo> getTblArchivos() {
		return this.tblArchivos;
	}

	public void setTblArchivos(List<TblArchivo> tblArchivos) {
		this.tblArchivos = tblArchivos;
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

	public MprUsuario getMprUsuario() {
		return this.mprUsuario;
	}

	public void setMprUsuario(MprUsuario mprUsuario) {
		this.mprUsuario = mprUsuario;
	}

}