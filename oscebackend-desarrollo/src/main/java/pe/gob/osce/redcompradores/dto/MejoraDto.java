package pe.gob.osce.redcompradores.dto;

import java.io.Serializable;

public class MejoraDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3575405368966126952L;
	private String cVistaMejora;
	private String cProblemaMejora;
	private String cSugerenciaMejora;
	private String cCorreoMejora;
	private String cComunicacionMejora;

	public String getcVistaMejora() {
		return cVistaMejora;
	}

	public void setcVistaMejora(String cVistaMejora) {
		this.cVistaMejora = cVistaMejora;
	}

	public String getcProblemaMejora() {
		return cProblemaMejora;
	}

	public void setcProblemaMejora(String cProblemaMejora) {
		this.cProblemaMejora = cProblemaMejora;
	}

	public String getcSugerenciaMejora() {
		return cSugerenciaMejora;
	}

	public void setcSugerenciaMejora(String cSugerenciaMejora) {
		this.cSugerenciaMejora = cSugerenciaMejora;
	}

	public String getcCorreoMejora() {
		return cCorreoMejora;
	}

	public void setcCorreoMejora(String cCorreoMejora) {
		this.cCorreoMejora = cCorreoMejora;
	}

	public String getcComunicacionMejora() {
		return cComunicacionMejora;
	}

	public void setcComunicacionMejora(String cComunicacionMejora) {
		this.cComunicacionMejora = cComunicacionMejora;
	}

	
}
