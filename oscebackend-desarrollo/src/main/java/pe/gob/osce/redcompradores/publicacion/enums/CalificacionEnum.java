package pe.gob.osce.redcompradores.publicacion.enums;

public enum CalificacionEnum {

	LIKE(1), DISSLIKE(9);
	
	private final int value;
	
	private CalificacionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
