package pe.gob.osce.redcompradores.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.TipoArchivoEnum;
import pe.gob.osce.redcompradores.publicacion.enums.TipoCalificacionEnum;
import pe.gob.osce.redcompradores.security.enums.RolEnum;

/**
 * [Clase utilitaria de la aplicacion aqui se formatean valores como por ejemplo
 * estados, se calculan la calificacion de usuarios ]
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

@Component
public class Utility {
	
	private Utility() {
	}

	/**
	 * Generador de cadena con nombre de fecha en formato yyyyMMdd para la creacion
	 * de carpertas de archivos de las publicaciones
	 * 
	 * @return String "20210101"
	 */
	public static String generaFechaDirectorio() {
		String result = "";
		Date objDate = new Date();
		String strDateFormat = "yyyyMMdd";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		result = objSDF.format(objDate);
		return result;
	}

	/**
	 * Metodo para obtener la descripcion del rol en funcion del codigo de este
	 * 
	 * @param idRol
	 * @return String "ROLE_ADMIN"
	 */
	public static String obtenerRol(String idRol) {
		if (idRol.equalsIgnoreCase("0"))
			return RolEnum.ROLE_ADMIN.name();

		else if (idRol.equalsIgnoreCase("1"))
			return RolEnum.ROLE_ADMIN_ENTIDAD.name();

		else if (idRol.equalsIgnoreCase("2"))
			return RolEnum.ROLE_USER.name();

		else
			return RolEnum.NO_ROLE.name();
	}

	/**
	 * Metodo para obtener la descripcion del estado en funcion del codigo de este
	 * 
	 * @param idEstado
	 * @return String ACTIVO
	 */
	public static String obtenerEstado(int idEstado) {
		if (idEstado == 1)
			return EstadosEnum.ACTIVO.name();
		else if (idEstado == 0)
			return EstadosEnum.INACTIVO.name();
		else if (idEstado == 2)
			return EstadosEnum.AMONESTACION.name();
		else
			return EstadosEnum.BAJA.name();
	}

	/**
	 * Metodo para obtener la descripcion de autorizado o no en funcion del codigo
	 * de esta, este metodo se usa por ejemplo para el listado de entidades
	 * 
	 * @param idAutorizado
	 * @return String "AUTORIZADO"
	 */
	public static String obtenerAutorizado(String idAutorizado) {
		if (idAutorizado.equals("1"))
			return "AUTORIZADO";
		else
			return "NO AUTORIZADO";
	}

	/**
	 * Metodo para obtener la descripcion de un tipo de parametro
	 * 
	 * @param idTipoParametro
	 * @return String "SIMPLE"
	 */
	public static String obtenerTipoParametro(int idTipoParametro) {
		if (idTipoParametro == 1)
			return "SIMPLE";
		else
			return "AGRUPADO";
	}

	/**
	 * Metodo para obtener el valor de la calificacion de una publicacion, LIKE o
	 * DISSLIKE
	 * 
	 * @param tipoCalificacion
	 * @return BigDecimal(1)
	 */
	public static BigDecimal obtenerValorCalificacion(String tipoCalificacion) {
		if (tipoCalificacion.equalsIgnoreCase(TipoCalificacionEnum.LIKE.getValue()))
			return new BigDecimal(1);
		else
			return new BigDecimal(9);
	}

	/**
	 * Metodo para obtener el valor de la calificacion de un usuario en el sistema
	 * 
	 * @param total
	 * @param likes
	 * @return BigDecimal(1)
	 */
	public static BigDecimal obtenerCalificacionUsuario(Integer total, Integer likes) {
		Double porcentajeEntero = (new Double(likes) / new Double(total)) * 100;
		if (porcentajeEntero <= 20) {
			return new BigDecimal(1);
		} else if (porcentajeEntero <= 40) {
			return new BigDecimal(2);
		} else if (porcentajeEntero <= 60) {
			return new BigDecimal(3);
		} else if (porcentajeEntero <= 80) {
			return new BigDecimal(4);
		} else
			return new BigDecimal(5);
	}

	/**
	 * Metodo para obtener la descripcion del un tipo de archivo determinado
	 * 
	 * @param tipoArchivo
	 * @return String IMAGE
	 */
	public static String obtenerTipoArchivo(String tipoArchivo) {
		if (tipoArchivo.equalsIgnoreCase(TipoArchivoEnum.IMAGE.getValue()))
			return TipoArchivoEnum.IMAGE.getValue();
		else if (tipoArchivo.equalsIgnoreCase(TipoArchivoEnum.VIDEO.getValue()))
			return TipoArchivoEnum.VIDEO.getValue();
		else
			return TipoArchivoEnum.DOCUMENT.getValue();
	}

	/**
	 * Metodo encargado de obtener el token enviada desde el frontend
	 * 
	 * @param autorizacion
	 * @return String eyJhbGciOiJIUzUxMiJ9....
	 */
	public static String getToken(String autorizacion) {
		if (autorizacion != null && autorizacion.startsWith("Bearer"))
			return autorizacion.replace("Bearer ", "");
		return null;
	}
}
