package pe.gob.osce.redcompradores.enums;

public enum TipoArchivoEnum {

	IMAGE("I"), VIDEO("V"), DOCUMENT("D");
	
	private final String value;
	
	private TipoArchivoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
