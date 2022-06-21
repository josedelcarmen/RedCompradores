package pe.gob.osce.redcompradores.publicacion.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.publicacion.dto.PublicacionDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.publicacion.service.PublicacionService;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.service.UsuarioService;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.FileStorageService;

/**
 * Controlador Websockets para el manejo de las publicaciones
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
@Controller
public class PublicacionSocketController {

	private static final AuditoriaService logger = new AuditoriaService(PublicacionSocketController.class);

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private PublicacionService publicacionService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FileStorageService fileService;

	/**
	 * Metodo encargado de setear una nueva publicacion en el topico de
	 * publicaciones y guardarlo en la base de datos
	 * 
	 * @param publicacionDto
	 * @return
	 */
	@MessageMapping("/sendPublish")
	@SendTo("/topic/publish")
	public PublicacionDto sendPublish(@Payload PublicacionDto publicacionDto) {
		logger.debug("PublicacionSocketController::sendPublish::inicio");

		if (usuarioService.existePorIdUsuario(publicacionDto.getnIdUsuario())) {
			TblPublicacion tblPublicacion = new TblPublicacion();
			MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(publicacionDto.getnIdUsuario());
			tblPublicacion.setMprUsuario(mprUsuario);
			tblPublicacion.setCValDetPub(publicacionDto.getcValDetPub());
			tblPublicacion.setCCodTema(publicacionDto.getcCodTema());
			tblPublicacion.setCValClavePub(publicacionDto.getcValClavePub());
			tblPublicacion.setCValEnviaCorreo(publicacionDto.getcValEnviaCorreo());
			tblPublicacion.setCValUsuarioCrea(publicacionDto.getcValUsuarioCrea());
			tblPublicacion.setDFecFechaCrea(new Date());
			tblPublicacion.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
			publicacionService.guardarPublicacion(tblPublicacion);

			publicacionDto.setnIdPublicacion(tblPublicacion.getNIdPublicacion());
			publicacionDto.setdFecFechaCrea(new Date());
			publicacionDto.setnCantComentarios(new BigDecimal(0));
			publicacionDto.setnCantDisslikes(new BigDecimal(0));
			publicacionDto.setnCantLikes(new BigDecimal(0));
			publicacionDto.setnValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
			publicacionDto.setnIdUsuario(mprUsuario.getNIdUsuario());
			publicacionDto
					.setcValNomUsuario(mprUsuario.getCNomUsuario().concat(" ").concat(mprUsuario.getCApeUsuario()));
			publicacionDto.setcValNomEntidad(mprUsuario.getMprEntidad().getCDesEntidad());
			publicacionDto.setcDirFoto(mprUsuario.getCDirFoto());
			publicacionDto.setnValCalificacionUsuario(mprUsuario.getNValCalificacion());
			try {
				publicacionDto.setbFotoPerfil(fileService.contentOfFotoPerfil(publicacionDto.getcDirFoto()));
			} catch (Exception e) {
				logger.error("PublicacionSocketController::sendPublish::Se produjo el error:: " + e.getMessage(), e);
			}

			logger.debug("PublicacionSocketController::sendPublish::Publicacion creada con exito");
		}

		logger.debug("PublicacionSocketController::sendPublish::fin");
		return publicacionDto;
	}

	/**
	 * Metodo encargado de listar el historial de publicaciones
	 * 
	 * @param topicSend
	 */
	@MessageMapping("/historial/publish")
	public void historial(@Payload String topicSend) {
		logger.debug("PublicacionSocketController::historial/publish::inicio");
		logger.debug("PublicacionSocketController::topicSend: " + topicSend);
		List<PublicacionDto> listDto = publicacionService.listarPublicaciones();
		logger.debug("PublicacionSocketController::Se listaron : " + listDto.size() + " publicaciones");
		logger.debug("PublicacionSocketController::historial/publish::fin");

		simpMessagingTemplate.convertAndSend(topicSend, listDto);
	}

	/**
	 * Metodo encargado de listar el historial de publicaciones por usuario
	 * 
	 * @param topicSend
	 */
	@MessageMapping("/historial/publish/usuario")
	public void historialPorUsuario(@Payload String topicSend) {
		logger.debug("PublicacionSocketController::historial/publish/usuario::inicio");

		String[] paramsSplit = topicSend.split("idUser");
		logger.debug("PublicacionSocketController::historial/publish/usuario::idUsuario: " + paramsSplit[1]);
		logger.debug("PublicacionSocketController::historial/publish/usuario::topicSend: " + paramsSplit[0]);
		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(new BigDecimal(paramsSplit[1]));
		List<PublicacionDto> listDto = publicacionService.listarPublicacionesByUsuario(mprUsuario);
		logger.debug("PublicacionSocketController::historial/publish/usuario::Se listaron : " + listDto.size()
				+ " publicaciones");
		logger.debug("PublicacionSocketController::historial/publish/usuario::fin");

		simpMessagingTemplate.convertAndSend(topicSend, listDto);
	}

}
