package pe.gob.osce.redcompradores.entity;

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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Clase entidad que almacena la informacion de las entidades
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
@Table(name = "MPR_ENTIDAD")
@NamedQuery(name = "MprEntidad.findAll", query = "SELECT m FROM MprEntidad m")
public class MprEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_ENTIDAD", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_ENTIDAD")
	@Column(name = "N_ID_ENTIDAD")
	private BigDecimal nIdEntidad;

	@Column(name = "C_DES_ENTIDAD")
	private String cDesEntidad;

	@Column(name = "C_NOM_DOMINIO", unique = true)
	private String cNomDominio;

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

	@Column(name = "N_VAL_ESTADO")
	private BigDecimal nValEstado;

	@Column(name = "N_VAL_VALIDA_USUARIO")
	private String nValValidaUsuario;

	@JsonIgnore
	@OneToMany(mappedBy = "mprEntidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MprUsuario> mprUsuarios;

	public BigDecimal getNIdEntidad() {
		return this.nIdEntidad;
	}

	public void setNIdEntidad(BigDecimal nIdEntidad) {
		this.nIdEntidad = nIdEntidad;
	}

	public String getCDesEntidad() {
		return this.cDesEntidad;
	}

	public void setCDesEntidad(String cDesEntidad) {
		this.cDesEntidad = cDesEntidad;
	}

	public String getCNomDominio() {
		return this.cNomDominio;
	}

	public void setCNomDominio(String cNomDominio) {
		this.cNomDominio = cNomDominio;
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

	public String getNValValidaUsuario() {
		return this.nValValidaUsuario;
	}

	public void setNValValidaUsuario(String nValValidaUsuario) {
		this.nValValidaUsuario = nValValidaUsuario;
	}

	public List<MprUsuario> getMprUsuarios() {
		return this.mprUsuarios;
	}

	public void setMprUsuarios(List<MprUsuario> mprUsuarios) {
		this.mprUsuarios = mprUsuarios;
	}

}