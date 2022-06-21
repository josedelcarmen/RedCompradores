package pe.gob.osce.redcompradores.chatroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacionArchivo;
import pe.gob.osce.redcompradores.chatroom.repository.ComunicacionArchivoRepository;

/**
 * Servicio transacional de los archivos del chatroom
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
public class ComunicacionArchivoService {

	@Autowired
	private ComunicacionArchivoRepository archivoRepository;
	
	/**
	 * Metodo encargado de guardar la informacion de un archivo 
	 * adjunto del chatroom
	 * @param tblComunicacionArchivo
	 */
	public void guardarComunicacionArchivo(TblComunicacionArchivo tblComunicacionArchivo) {
		archivoRepository.save(tblComunicacionArchivo);
	}
}
