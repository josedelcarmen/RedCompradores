package pe.gob.osce.redcompradores.publicacion.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.publicacion.emtity.TblCalificacion;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.publicacion.repository.CalificacionRepository;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;

/**
 * Servicio transacional de las calificaciones de publicaciones
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
@Service
@Transactional
public class CalificacionService {

	@Autowired
	private CalificacionRepository calificacionRepository;

	/**
	 * Metodo encargado de retorna la cantidad de likes de 
	 * una publicacion
	 * @param nIdPublicacion
	 * @return Integer
	 */
	public Integer cantidadLikesPornIdPublicacion(BigDecimal nIdPublicacion) {
		return calificacionRepository.countLikesBynIdPublicacion(nIdPublicacion);
	}

	/**
	 * Metodo encargado de retorna la cantidad total de calificaciones
	 * de una publicacion
	 * @param nIdPublicacion
	 * @return Integer
	 */
	public Integer cantidadCalificacionPornIdPublicacion(BigDecimal nIdPublicacion) {
		return calificacionRepository.countBynIdPublicacion(nIdPublicacion);
	}

	/**
	 * Metodo encargado de retorna la cantidad de likes de 
	 * una publicacion de un usuario determinado
	 * @param nIdUsuario
	 * @return Integer
	 */
	public Integer cantidadLikesPornIdUsuario(BigDecimal nIdUsuario) {
		return calificacionRepository.countLikesBynIdUsuario(nIdUsuario);
	}

	/**
	 * Metodo encargado de retorna la cantidad total de calificaciones
	 * de una publicacion de un usuario determiando
	 * @param nIdUsuario
	 * @return Integer
	 */
	public Integer cantidadCalificacionPornIdUsuario(BigDecimal nIdUsuario) {
		return calificacionRepository.countBynIdUsuario(nIdUsuario);
	}

	/**
	 * Metodo encargdo de verificar la existencia de una calificacion
	 * por publicacion y usuario
	 * @param mprUsuario
	 * @param tblPublicacion
	 * @return boolean
	 */
	public boolean existePorUsuarioAndPublicacion(MprUsuario mprUsuario, TblPublicacion tblPublicacion) {
		return calificacionRepository.existsByMprUsuarioAndTblPublicacion(mprUsuario, tblPublicacion);
	}

	/**
	 * Metodo encargdo de buscar una calificacion
	 * por publicacion y usuario
	 * @param mprUsuario
	 * @param tblPublicacion
	 * @return TblCalificacion
	 */
	public TblCalificacion buscarPorUsuarioAndPublicacion(MprUsuario mprUsuario, TblPublicacion tblPublicacion) {
		return calificacionRepository.findByMprUsuarioAndTblPublicacion(mprUsuario, tblPublicacion);
	}

	/**
	 * Metodo encargdo de buscar una calificacion
	 * por id de calificacion
	 * @param nIdCalificacion
	 * @return TblCalificacion
	 */
	public TblCalificacion buscarPornIdCalificacion(BigDecimal nIdCalificacion) {
		Optional<TblCalificacion> calificacionOptional = calificacionRepository.findById(nIdCalificacion);
		if (calificacionOptional.isPresent()) {
			return calificacionOptional.get();
		}
		else {
			return null;
		}
	}

	/**
	 * Metodo encargado de guardar una calificacion
	 * @param tblCalificacion
	 */
	public void guardarCalificacion(TblCalificacion tblCalificacion) {
		calificacionRepository.save(tblCalificacion);
	}
}
