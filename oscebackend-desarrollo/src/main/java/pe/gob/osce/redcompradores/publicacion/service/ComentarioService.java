package pe.gob.osce.redcompradores.publicacion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.publicacion.dto.ComentarioDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblComentario;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.publicacion.repository.ComentarioRepository;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.FileStorageService;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de los comenntarios de publicaciones
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
public class ComentarioService {

	private static final AuditoriaService logger = new AuditoriaService(ComentarioService.class);
	
	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private FileStorageService fileService;
	
	/**
	 * Metodo encargado de guardar un comentario
	 * @param tblComentario
	 */
	public void guardarComentario(TblComentario tblComentario) {
		comentarioRepository.save(tblComentario);
	}
	
	/**
	 * Metodo encargado de listar un comentario de una
	 * publicacion
	 * @param tblPublicacion
	 * @return List<ComentarioDto>
	 */
	public List<ComentarioDto> listarComentario(TblPublicacion tblPublicacion) {
		List<TblComentario> listaComentario = comentarioRepository.findByTblPublicacion(tblPublicacion);
		List<ComentarioDto> listaFinal = new ArrayList<ComentarioDto>();
		ComentarioDto comentarioDto;
		
		for (TblComentario tblComentario : listaComentario) {
			comentarioDto = new ComentarioDto();
			BeanUtils.copyPropertiesComentario(comentarioDto, tblComentario);
			try {
				comentarioDto.setbFotoPerfil(fileService.contentOfFotoPerfil(tblComentario.getMprUsuario().getCDirFoto()));
			} catch (Exception e) {
				logger.error("ComentarioService::listarComentario::Se produjo el error:: " + e.getMessage(), e);
			}
			listaFinal.add(comentarioDto);
		}
		return listaFinal;		
	}
}
