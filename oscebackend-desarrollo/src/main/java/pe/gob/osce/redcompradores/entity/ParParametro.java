package pe.gob.osce.redcompradores.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase entidad que almacena la informacion de los parametros
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
@Table(name = "PAR_PARAMETROS")
public class ParParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_N_ID_PARAMETROS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_N_ID_PARAMETROS")
	@Column(name = "N_ID_PARAMETRO")
	private BigDecimal nIdParametro;

	@Column(name = "C_VAL_CLAVE_PARAM")
	private String cValClaveParam;

	@Column(name = "C_VAL_CORTA_PARAM")
	private String cValCortaParam;

	@Column(name = "C_VAL_ETIQUETA_PARAM")
	private String cValEtiquetaParam;

	@Column(name = "C_VAL_GRUPO_PARAM")
	private String cValGrupoParam;

	@Column(name = "C_VAL_LARGA_PARAM")
	private String cValLargaParam;

	@Column(name = "C_VAL_TIPO_PARAM")
	private BigDecimal cValTipoParam;

	@Column(name = "C_VAL_USUARIO_ACTU")
	private String cValUsuarioActu;

	@Column(name = "C_VAL_USUARIO_CREA")
	private String cValUsuarioCrea;

	@Column(name = "C_VAL_VALOR_PARAM")
	private String cValValorParam;

	@Temporal(TemporalType.DATE)
	@Column(name = "D_FEC_FECHA_ACTU")
	private Date dFecFechaActu;

	@Temporal(TemporalType.DATE)
	@Column(name = "D_FEC_FECHA_CREA")
	private Date dFecFechaCrea;

	@Column(name = "C_COD_PADRE_PARAM")
	private String cCodPadreParam;

	@Column(name = "N_VAL_ESTADO")
	private BigDecimal nValEstado;

	@Column(name = "N_VAL_ORDEN_PARAM")
	private BigDecimal nValOrdenParam;

	public BigDecimal getNIdParametro() {
		return this.nIdParametro;
	}

	public void setNIdParametro(BigDecimal nIdParametro) {
		this.nIdParametro = nIdParametro;
	}

	public String getCValClaveParam() {
		return this.cValClaveParam;
	}

	public void setCValClaveParam(String cValClaveParam) {
		this.cValClaveParam = cValClaveParam;
	}

	public String getCValCortaParam() {
		return this.cValCortaParam;
	}

	public void setCValCortaParam(String cValCortaParam) {
		this.cValCortaParam = cValCortaParam;
	}

	public String getCValEtiquetaParam() {
		return this.cValEtiquetaParam;
	}

	public void setCValEtiquetaParam(String cValEtiquetaParam) {
		this.cValEtiquetaParam = cValEtiquetaParam;
	}

	public String getCValGrupoParam() {
		return this.cValGrupoParam;
	}

	public void setCValGrupoParam(String cValGrupoParam) {
		this.cValGrupoParam = cValGrupoParam;
	}

	public String getCValLargaParam() {
		return this.cValLargaParam;
	}

	public void setCValLargaParam(String cValLargaParam) {
		this.cValLargaParam = cValLargaParam;
	}

	public BigDecimal getCValTipoParam() {
		return this.cValTipoParam;
	}

	public void setCValTipoParam(BigDecimal cValTipoParam) {
		this.cValTipoParam = cValTipoParam;
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

	public String getCValValorParam() {
		return this.cValValorParam;
	}

	public void setCValValorParam(String cValValorParam) {
		this.cValValorParam = cValValorParam;
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

	public String getCCodPadreParam() {
		return this.cCodPadreParam;
	}

	public void setCCodPadreParam(String cCodPadreParam) {
		this.cCodPadreParam = cCodPadreParam;
	}

	public BigDecimal getNValEstado() {
		return this.nValEstado;
	}

	public void setNValEstado(BigDecimal nValEstado) {
		this.nValEstado = nValEstado;
	}

	public BigDecimal getNValOrdenParam() {
		return this.nValOrdenParam;
	}

	public void setNValOrdenParam(BigDecimal nValOrdenParam) {
		this.nValOrdenParam = nValOrdenParam;
	}

}