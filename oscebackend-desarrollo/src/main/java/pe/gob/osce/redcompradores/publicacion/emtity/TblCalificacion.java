package pe.gob.osce.redcompradores.publicacion.emtity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Clase entidad que almacena la informacion de las 
 * calificaciones de las publicaciones
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
@Table(name = "TBL_CALIFICACION")
public class TblCalificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_CALIFICACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_CALIFICACION")
	@Column(name = "N_ID_CALIFICACION")
	private BigDecimal nIdCalificacion;

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

	@Column(name = "N_VAL_CALIFICACION")
	private BigDecimal nValCalificacion;

	@Column(name = "N_VAL_ESTADO")
	private BigDecimal nValEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_USUARIO")
	private MprUsuario mprUsuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "N_ID_PUBLICACION")
	private TblPublicacion tblPublicacion;

	public BigDecimal getNIdCalificacion() {
		return this.nIdCalificacion;
	}

	public void setNIdCalificacion(BigDecimal nIdCalificacion) {
		this.nIdCalificacion = nIdCalificacion;
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

	public MprUsuario getMprUsuario() {
		return this.mprUsuario;
	}

	public void setMprUsuario(MprUsuario mprUsuario) {
		this.mprUsuario = mprUsuario;
	}

	public TblPublicacion getTblPublicacion() {
		return this.tblPublicacion;
	}

	public void setTblPublicacion(TblPublicacion tblPublicacion) {
		this.tblPublicacion = tblPublicacion;
	}

}