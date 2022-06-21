package pe.gob.osce.redcompradores.chatroom.enums;

public enum TipoMensajeEnum {

	CONNECTION("C"), TEXT("T"), LINK("L");
	
	private final String value;
	
	private TipoMensajeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
