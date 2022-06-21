package pe.gob.osce.redcompradores.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import pe.gob.osce.redcompradores.chatroom.dto.ComunicacionDto;
import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacion;
import pe.gob.osce.redcompradores.dto.EntidadDto;
import pe.gob.osce.redcompradores.dto.ParametroDto;
import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.publicacion.dto.ArchivoDto;
import pe.gob.osce.redcompradores.publicacion.dto.ComentarioDto;
import pe.gob.osce.redcompradores.publicacion.dto.PublicacionDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblArchivo;
import pe.gob.osce.redcompradores.publicacion.emtity.TblComentario;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.security.dto.UsuarioDto;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Clase utilitaria que copia los valores de las clases Entity a los Dto
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
public class BeanUtils {

	private BeanUtils() {}
	/**
	 * Metodo que copias los valores del entity MprEntidad
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesEntidad(EntidadDto dto, MprEntidad entity) {
		dto.setnIdEntidad(entity.getNIdEntidad());
		dto.setcNomDominio(entity.getCNomDominio());
		dto.setcDesEntidad(entity.getCDesEntidad());
		dto.setnValValidaUsuario(entity.getNValValidaUsuario());
		dto.setcValValidaUsuarioDesc(Utility.obtenerAutorizado(entity.getNValValidaUsuario()));
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValEstadoDesc(Utility.obtenerEstado(entity.getNValEstado().intValue()));
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
	}

	/**
	 * Metodo que copias los valores del entity ParParametro
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesParametros(ParametroDto dto, ParParametro entity) {
		dto.setnIdParametro(entity.getNIdParametro());
		dto.setcValLargaParam(entity.getCValLargaParam());
		dto.setcValCortaParam(entity.getCValCortaParam());
		dto.setcValClaveParam(entity.getCValClaveParam());
		dto.setcValGrupoParam(entity.getCValGrupoParam());
		dto.setcValEtiquetaParam(entity.getCValEtiquetaParam());
		dto.setcValValorParam(entity.getCValValorParam());
		dto.setcCodPadreParam(entity.getCCodPadreParam());
		dto.setnValOrdenParam(entity.getNValOrdenParam());
		dto.setcValTipoParam(entity.getCValTipoParam());
		dto.setcValTipoParamDesc(Utility.obtenerTipoParametro(entity.getCValTipoParam().intValue()));
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValEstadoDesc(Utility.obtenerEstado(entity.getNValEstado().intValue()));
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());

	}

	/**
	 * Metodo que copias los valores del entity MprUsuario
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesUsuario(UsuarioDto dto, MprUsuario entity) {
		dto.setnIdUsuario(entity.getNIdUsuario());
		dto.setnIdEntidad(entity.getMprEntidad().getNIdEntidad());
		dto.setcDesEntidad(entity.getMprEntidad().getCDesEntidad());
		dto.setcCodTipodoc(entity.getCCodTipodoc());
		dto.setnNumDocumento(entity.getNNumDocumento());
		dto.setcApeUsuario(entity.getCApeUsuario());
		dto.setcNomUsuario(entity.getCNomUsuario());
		dto.setcValSexo(entity.getCValSexo());
		dto.setcCodDep(entity.getCCodDep());
		dto.setcCodProv(entity.getCCodProv());
		dto.setcCodDist(entity.getCCodDist());
		dto.setcCodRol(entity.getCCodRol());
		dto.setcCodRolDesc(Utility.obtenerRol(entity.getCCodRol()));
		dto.setcValCelular(entity.getCValCelular());
		dto.setcValCorreo(entity.getCValCorreo());
		dto.setcValClave(entity.getCValClave());
		dto.setcValSeace(entity.getCValSeace());
		dto.setcNumSeace(entity.getCNumSeace());
		dto.setcDirFoto(entity.getCDirFoto());
		dto.setcValReniec(entity.getCValReniec());
		dto.setnValCalificacion(entity.getNValCalificacion());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcDesExperiencia(entity.getCDesExperiencia());
		dto.setcDesProfesion(entity.getCDesProfesion());
		dto.setcDirUsuario(entity.getCDirUsuario());
		dto.setcValFacebook(entity.getCValFacebook());
		dto.setcValInstagram(entity.getCValInstagram());
		dto.setcValLinkedin(entity.getCValLinkedin());
		dto.setcValTwitter(entity.getCValTwitter());
		dto.setcObsUsuario(entity.getCObsUsuario());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValEstadoDesc(Utility.obtenerEstado(entity.getNValEstado().intValue()));
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
	}

	/**
	 * Metodo que copias los valores del entity TblComunicacion
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesComunicacion(ComunicacionDto dto, TblComunicacion entity) {
		dto.setnIdComunicacion(entity.getNIdComunicacion());
		dto.setcDesComunicacion(entity.getCDesComunicacion());
		dto.setnIdUsuario(entity.getMprUsuario().getNIdUsuario());
		dto.setcValCorreo(entity.getMprUsuario().getCValCorreo());
		dto.setcValTipoComunicacion(entity.getCValTipoComunicacion());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
		dto.setcValNomUsuario(
				entity.getMprUsuario().getCNomUsuario().concat(" ").concat(entity.getMprUsuario().getCApeUsuario()));
		dto.setcValNomEntidad(entity.getMprUsuario().getMprEntidad().getCDesEntidad());
	}

	/**
	 * Metodo que copias los valores del entity TblPublicacion
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesPublicacion(PublicacionDto dto, TblPublicacion entity) {
		dto.setnIdPublicacion(entity.getNIdPublicacion());
		dto.setcCodTema(entity.getCCodTema());
		dto.setcValClavePub(entity.getCValClavePub());
		dto.setcValDetPub(entity.getCValDetPub());
		dto.setcValEnviaCorreo(entity.getCValEnviaCorreo());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
		dto.setnIdUsuario(entity.getMprUsuario().getNIdUsuario());
		dto.setnValCalificacionUsuario(entity.getMprUsuario().getNValCalificacion());
		dto.setcValNomUsuario(
				entity.getMprUsuario().getCNomUsuario().concat(" ").concat(entity.getMprUsuario().getCApeUsuario()));
		dto.setcValNomEntidad(entity.getMprUsuario().getMprEntidad().getCDesEntidad());
		dto.setcDirFoto(entity.getMprUsuario().getCDirFoto());
	
		if (entity.getTblArchivos() != null && entity.getTblArchivos().size() > 0) {
			dto.setcValNomArchivo(entity.getTblArchivos().get(0).getCValNomArchivo());
			dto.setcValRutaArchivo(entity.getTblArchivos().get(0).getCValRutaArchivo());
		}

		if (entity.getTblComentarios() != null && entity.getTblComentarios().size() > 0)
			dto.setnCantComentarios(new BigDecimal(entity.getTblComentarios().size()));
		else
			dto.setnCantComentarios(new BigDecimal(0));		
	}

	/**
	 * Metodo que copias los valores del entity TblArchivo
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesArchivo(ArchivoDto dto, TblArchivo entity) {
		dto.setnIdArchivo(entity.getNIdArchivo());
		dto.setcValClaveArchivo(entity.getCValClaveArchivo().replaceAll("[\\[\\](){}]", "").replaceAll("\\\"", ""));
		dto.setcValNomArchivo(entity.getCValNomArchivo());
		dto.setcValNomOriArchivo(entity.getCValNomOriArchivo());
		dto.setcValRutaArchivo(entity.getCValRutaArchivo());
		dto.setcValTipoArchivo(entity.getCValTipoArchivo());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
		dto.setcCodTema(entity.getTblPublicacion().getCCodTema().trim());
	}

	/**
	 * Metodo que copias los valores del entity TblComentario
	 * 
	 * @param dto
	 * @param entity
	 */
	public static void copyPropertiesComentario(ComentarioDto dto, TblComentario entity) {
		dto.setnIdComentario(entity.getNIdComentario());
		dto.setnIdPublicacion(entity.getTblPublicacion().getNIdPublicacion());
		dto.setnIdUsuario(entity.getMprUsuario().getNIdUsuario());
		dto.setcNomUsuarioComentario(
				entity.getMprUsuario().getCNomUsuario().concat(" ").concat(entity.getMprUsuario().getCApeUsuario()));
		dto.setcValComentario(entity.getCValComentario());
		dto.setnValEstado(entity.getNValEstado());
		dto.setcValUsuarioCrea(entity.getCValUsuarioCrea());
		dto.setdFecFechaCrea(entity.getDFecFechaCrea());
		dto.setcValUsuarioActu(entity.getCValUsuarioActu());
		dto.setdFecFechaActu(entity.getDFecFechaActu());
		dto.setcDirFoto(entity.getMprUsuario().getCDirFoto());
		dto.setcCodRol(entity.getMprUsuario().getCCodRol());
	}
}
