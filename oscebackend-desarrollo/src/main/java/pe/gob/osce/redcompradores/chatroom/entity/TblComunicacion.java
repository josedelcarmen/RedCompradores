package pe.gob.osce.redcompradores.chatroom.entity;

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
 * Clase entidad que almacena los mensajes del chatroom
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
@Table(name = "TBL_COMUNICACION")
public class TblComunicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_COMUNICACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_COMUNICACION")
	@Column(name = "N_ID_COMUNICACION")
	private BigDecimal nIdComunicacion;

	@Column(name = "C_DES_COMUNICACION")
	private String cDesComunicacion;

	@Column(name = "C_VAL_TIPO_COMUNICACION")
	private String cValTipoComunicacion;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "N_ID_USUARIO")
	private MprUsuario mprUsuario;

	public BigDecimal getNIdComunicacion() {
		return this.nIdComunicacion;
	}

	public void setNIdComunicacion(BigDecimal nIdComunicacion) {
		this.nIdComunicacion = nIdComunicacion;
	}

	public String getCDesComunicacion() {
		return this.cDesComunicacion;
	}

	public void setCDesComunicacion(String cDesComunicacion) {
		this.cDesComunicacion = cDesComunicacion;
	}

	public String getCValTipoComunicacion() {
		return this.cValTipoComunicacion;
	}

	public void setCValTipoComunicacion(String cValTipoComunicacion) {
		this.cValTipoComunicacion = cValTipoComunicacion;
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

}