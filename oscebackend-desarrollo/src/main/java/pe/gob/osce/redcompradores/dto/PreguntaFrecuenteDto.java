package pe.gob.osce.redcompradores.dto;

public class PreguntaFrecuenteDto {

	private String idPregunta;
	private String idRespuesta;
	private String pregunta;
	private String respuesta;
	
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(String idPregunta) {
		this.idPregunta = idPregunta;
	}
	public String getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(String idRespuesta) {
		this.idRespuesta = idRespuesta;
	}
	
	
}
