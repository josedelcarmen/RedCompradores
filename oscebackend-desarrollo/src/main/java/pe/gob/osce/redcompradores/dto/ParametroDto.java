package pe.gob.osce.redcompradores.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParametroDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5771885375561541718L;
	private BigDecimal nIdParametro;
	private String cValClaveParam;
	private String cValCortaParam;
	private String cValEtiquetaParam;
	private String cValGrupoParam;
	private String cValLargaParam;
	private BigDecimal cValTipoParam;
	private String cValUsuarioActu;
	private String cValUsuarioCrea;
	private String cValValorParam;
	private Date dFecFechaActu;
	private Date dFecFechaCrea;
	private String cCodPadreParam;
	private BigDecimal nValEstado;
	private BigDecimal nValOrdenParam;
	private String cValEstadoDesc;
	private String cValTipoParamDesc;
	
	public BigDecimal getnIdParametro() {
		return nIdParametro;
	}
	public void setnIdParametro(BigDecimal nIdParametro) {
		this.nIdParametro = nIdParametro;
	}
	public String getcValClaveParam() {
		return cValClaveParam;
	}
	public void setcValClaveParam(String cValClaveParam) {
		this.cValClaveParam = cValClaveParam;
	}
	public String getcValCortaParam() {
		return cValCortaParam;
	}
	public void setcValCortaParam(String cValCortaParam) {
		this.cValCortaParam = cValCortaParam;
	}
	public String getcValEtiquetaParam() {
		return cValEtiquetaParam;
	}
	public void setcValEtiquetaParam(String cValEtiquetaParam) {
		this.cValEtiquetaParam = cValEtiquetaParam;
	}
	public String getcValGrupoParam() {
		return cValGrupoParam;
	}
	public void setcValGrupoParam(String cValGrupoParam) {
		this.cValGrupoParam = cValGrupoParam;
	}
	public String getcValLargaParam() {
		return cValLargaParam;
	}
	public void setcValLargaParam(String cValLargaParam) {
		this.cValLargaParam = cValLargaParam;
	}
	public BigDecimal getcValTipoParam() {
		return cValTipoParam;
	}
	public void setcValTipoParam(BigDecimal cValTipoParam) {
		this.cValTipoParam = cValTipoParam;
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
	public String getcValValorParam() {
		return cValValorParam;
	}
	public void setcValValorParam(String cValValorParam) {
		this.cValValorParam = cValValorParam;
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
	public BigDecimal getnValOrdenParam() {
		return nValOrdenParam;
	}
	public void setnValOrdenParam(BigDecimal nValOrdenParam) {
		this.nValOrdenParam = nValOrdenParam;
	}
	public String getcValEstadoDesc() {
		return cValEstadoDesc;
	}
	public void setcValEstadoDesc(String cValEstadoDesc) {
		this.cValEstadoDesc = cValEstadoDesc;
	}
	public String getcValTipoParamDesc() {
		return cValTipoParamDesc;
	}
	public void setcValTipoParamDesc(String cValTipoParamDesc) {
		this.cValTipoParamDesc = cValTipoParamDesc;
	}
	public String getcCodPadreParam() {
		return cCodPadreParam;
	}
	public void setcCodPadreParam(String cCodPadreParam) {
		this.cCodPadreParam = cCodPadreParam;
	}
	
}
