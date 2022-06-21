package pe.gob.osce.redcompradores.chatroom.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import pe.gob.osce.redcompradores.chatroom.dto.ComunicacionDto;
import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacion;
import pe.gob.osce.redcompradores.chatroom.enums.TipoMensajeEnum;
import pe.gob.osce.redcompradores.chatroom.service.ComunicacionService;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.service.UsuarioService;
import pe.gob.osce.redcompradores.service.AuditoriaService;

/**
 * Controlador WebSocket para el manejo de las conversaciones del chatroom
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
public class ComunicacionSocketController {

	private static final AuditoriaService logger = new AuditoriaService(ComunicacionSocketController.class);

	@Autowired
	private ComunicacionService comunicacionService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * Metodo encargado de setear una nueva conversacion en el topico de mensajes y guardarlo
	 * en la base de datos
	 * 
	 * @param comunicacionDto
	 * @return ComunicacionDto
	 */
	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public ComunicacionDto sendMessage(@Payload ComunicacionDto comunicacionDto) {

		logger.debug("ComunicacionSocketController::sendMessage::iniciando sendMessage");
		comunicacionDto.setdFecFechaCrea(new Date());
		comunicacionDto.setnValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));

		if (!comunicacionDto.getcValTipoComunicacion().equals(TipoMensajeEnum.CONNECTION.getValue())) {
			MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(comunicacionDto.getnIdUsuario());
			TblComunicacion tblComunicacion = new TblComunicacion();
			tblComunicacion.setMprUsuario(mprUsuario);
			tblComunicacion.setCDesComunicacion(comunicacionDto.getcDesComunicacion());
			tblComunicacion.setCValTipoComunicacion(comunicacionDto.getcValTipoComunicacion());
			tblComunicacion.setCValUsuarioCrea(comunicacionDto.getcValUsuarioCrea());
			tblComunicacion.setDFecFechaCrea(new Date());
			tblComunicacion.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
			comunicacionService.guardarComunicacion(tblComunicacion);

			comunicacionDto
					.setcValNomUsuario(mprUsuario.getCNomUsuario().concat(" ").concat(mprUsuario.getCApeUsuario()));
			comunicacionDto.setcValNomEntidad(mprUsuario.getMprEntidad().getCDesEntidad());
		}
		return comunicacionDto;
	}

	/**
	 * Metodo encargado de listar el historial de conversaciones del chatroom
	 * 
	 * @param topicSend
	 */
	@MessageMapping("/historial")
	public void historial(@Payload String topicSend) {
		logger.debug("ComunicacionSocketController::historial::iniciando historial");
		logger.debug("ComunicacionSocketController::historial::iniciando clienteGenerate: " + topicSend);
		List<ComunicacionDto> listDto = comunicacionService.listarMensajesLimit();
		logger.debug("ComunicacionSocketController::historial::Se listaron : " + listDto.size() + " conversaciones");
		simpMessagingTemplate.convertAndSend(topicSend, listDto);
	}

}
