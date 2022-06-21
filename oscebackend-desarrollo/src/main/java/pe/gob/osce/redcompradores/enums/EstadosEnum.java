package pe.gob.osce.redcompradores.enums;

public enum EstadosEnum {

	ACTIVO(1), INACTIVO(0), AMONESTACION(2), BAJA(9);
	
	private final int value;
	
	private EstadosEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
