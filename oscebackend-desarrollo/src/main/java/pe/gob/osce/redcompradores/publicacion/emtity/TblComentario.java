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

import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Clase entidad que almacena la informacion de los
 * comentarios de las publicaciones
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
@Table(name = "TBL_COMENTARIO")
public class TblComentario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_COMENTARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_COMENTARIO")
	@Column(name = "N_ID_COMENTARIO")
	private BigDecimal nIdComentario;

	@Column(name = "C_VAL_COMENTARIO")
	private String cValComentario;

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
	@JoinColumn(name = "N_ID_USUARIO")
	private MprUsuario mprUsuario;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "N_ID_PUBLICACION")
	private TblPublicacion tblPublicacion;

	public BigDecimal getNIdComentario() {
		return this.nIdComentario;
	}

	public void setNIdComentario(BigDecimal nIdComentario) {
		this.nIdComentario = nIdComentario;
	}

	public String getCValComentario() {
		return this.cValComentario;
	}

	public void setCValComentario(String cValComentario) {
		this.cValComentario = cValComentario;
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