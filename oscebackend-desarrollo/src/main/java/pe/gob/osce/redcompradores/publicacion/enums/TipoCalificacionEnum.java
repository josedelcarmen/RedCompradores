package pe.gob.osce.redcompradores.publicacion.enums;

public enum TipoCalificacionEnum {

	LIKE("L"), DISSLIKE("D");
	
	private final String value;
	
	private TipoCalificacionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
