package pe.gob.osce.redcompradores.publicacion.emtity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
/**
 * Clase entidad que almacena la informacion de los
 * archivos de las publicaciones
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
@Table(name = "TBL_ARCHIVO")
public class TblArchivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_ARCHIVO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_ARCHIVO")
	@Column(name = "N_ID_ARCHIVO")
	private BigDecimal nIdArchivo;

	@Column(name = "C_VAL_CLAVE_ARCHIVO")
	private String cValClaveArchivo;

	@Column(name = "C_VAL_NOM_ARCHIVO")
	private String cValNomArchivo;

	@Column(name = "C_VAL_NOM_ORI_ARCHIVO")
	private String cValNomOriArchivo;

	@Column(name = "C_VAL_RUTA_ARCHIVO")
	private String cValRutaArchivo;

	@Column(name = "C_VAL_TIPO_ARCHIVO")
	private String cValTipoArchivo;

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

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "N_ID_PUBLICACION")
	private TblPublicacion tblPublicacion;

	public BigDecimal getNIdArchivo() {
		return this.nIdArchivo;
	}

	public void setNIdArchivo(BigDecimal nIdArchivo) {
		this.nIdArchivo = nIdArchivo;
	}

	public String getCValClaveArchivo() {
		return this.cValClaveArchivo;
	}

	public void setCValClaveArchivo(String cValClaveArchivo) {
		this.cValClaveArchivo = cValClaveArchivo;
	}

	public String getCValNomArchivo() {
		return this.cValNomArchivo;
	}

	public void setCValNomArchivo(String cValNomArchivo) {
		this.cValNomArchivo = cValNomArchivo;
	}

	public String getCValNomOriArchivo() {
		return this.cValNomOriArchivo;
	}

	public void setCValNomOriArchivo(String cValNomOriArchivo) {
		this.cValNomOriArchivo = cValNomOriArchivo;
	}

	public String getCValRutaArchivo() {
		return this.cValRutaArchivo;
	}

	public void setCValRutaArchivo(String cValRutaArchivo) {
		this.cValRutaArchivo = cValRutaArchivo;
	}

	public String getCValTipoArchivo() {
		return this.cValTipoArchivo;
	}

	public void setCValTipoArchivo(String cValTipoArchivo) {
		this.cValTipoArchivo = cValTipoArchivo;
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

	public TblPublicacion getTblPublicacion() {
		return this.tblPublicacion;
	}

	public void setTblPublicacion(TblPublicacion tblPublicacion) {
		this.tblPublicacion = tblPublicacion;
	}

}