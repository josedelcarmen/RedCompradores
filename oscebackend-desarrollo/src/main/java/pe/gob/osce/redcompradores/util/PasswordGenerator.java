package pe.gob.osce.redcompradores.util;

/**
 * Clase generadora de password segun estructura rigida 1 mayuscula, 1
 * minuscula, 1 caracter especial, 1 numero y tamaño minimo de 8 caracteres
 * 
 * <ul>
 * <li>[2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
 * @author [Fernando Cuadros] o [DELTA]
 * @version [1.0]
 * @since [2021]
 * 
 */
public class PasswordGenerator {

	private static final String NUMEROS = "0123456789";

	private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	private static final String ESPECIALES = "ñÑ@_/*+-";

	/**
	 * Metodo que genera la contraseña con la estructura rigida
	 * 
	 * @param key
	 * @param length
	 * @return String Rg@3CZfÑDA
	 */
	public static String getPassword(int length) {
		String pswd = "";
		String key = MINUSCULAS + MAYUSCULAS + ESPECIALES;
		pswd += getCharacterForKey(MAYUSCULAS);
		pswd += getCharacterForKey(MINUSCULAS);
		pswd += getCharacterForKey(ESPECIALES);
		pswd += getCharacterForKey(NUMEROS);
		for (int i = pswd.length(); i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}
		return pswd;
	}
	
	public static String getCharacterForKey(String key) {
		char caracter = (key.charAt((int) (Math.random() * key.length())));
		return caracter + "";
	}
}