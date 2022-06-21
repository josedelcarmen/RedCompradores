package pe.gob.osce.redcompradores.chatroom.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osce.redcompradores.chatroom.dto.ComunicacionDto;
import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacionArchivo;
import pe.gob.osce.redcompradores.chatroom.enums.TipoMensajeEnum;
import pe.gob.osce.redcompradores.chatroom.service.ComunicacionArchivoService;
import pe.gob.osce.redcompradores.dto.MensajeDto;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.TipoArchivoEnum;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.service.UsuarioService;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.FileStorageService;

/**
 * Controlador Rest, encargado del manejo del manejo del archivos del chatroom
* 
 * <ul>
 * <li> [2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
* @author [Fernando Cuadros] o [DELTA]
* @version [1.0]
* @since [2021]
* 
*/
@RestController
@RequestMapping("/comunicacion")
@CrossOrigin(value = "*", exposedHeaders = { "Content-Disposition" })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ComunicacionController {

	private static final AuditoriaService logger = new AuditoriaService(ComunicacionController.class);

	@Autowired
	private ComunicacionArchivoService comunicacionArchivoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FileStorageService archivoService;

	/**
	 * Metodo encargado de cargar archivos al chatroom
	 * @param file
	 * @param idUsuario
	 * @return ComunicacionDto
	 * @throws Exception
	 */
	@PostMapping(value = "/cargarArchivoChatroom", consumes = "multipart/form-data")
	public ResponseEntity<ComunicacionDto> cargarArchivoChatroom(@RequestParam("file") MultipartFile file,
			@RequestParam("idUsuario") String idUsuario){

		logger.debug("ComunicacionController::cargarArchivoChatroom::iniciando cargarArchivoChatroom");
		String trace;
		
		TblComunicacionArchivo tblComunicacionArchivo = new TblComunicacionArchivo();
		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(new BigDecimal(idUsuario));
		tblComunicacionArchivo.setMprUsuario(mprUsuario);
		tblComunicacionArchivo.setCValTipoArchivo(TipoArchivoEnum.IMAGE.getValue());
		tblComunicacionArchivo.setDFecFechaCrea(new Date());
		tblComunicacionArchivo.setCValUsuarioCrea(mprUsuario.getCValCorreo());
		tblComunicacionArchivo.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
		try {
			archivoService.subirArchivoChatRoom(file, tblComunicacionArchivo);
		} catch (IOException e) {
			trace = "Error al cargar archivo en chatroom";
			logger.error("ComunicacionController::cargarArchivoChatroom::" + trace  + e.getMessage(), e);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		comunicacionArchivoService.guardarComunicacionArchivo(tblComunicacionArchivo);

		ComunicacionDto comunicacionDto = new ComunicacionDto();
		comunicacionDto.setnIdUsuario(new BigDecimal(idUsuario));
		comunicacionDto.setcValCorreo(mprUsuario.getCValCorreo());
		comunicacionDto.setcDesComunicacion(tblComunicacionArchivo.getCValNomArchivo());
		comunicacionDto.setcValTipoComunicacion(TipoMensajeEnum.LINK.getValue());
		comunicacionDto.setcValUsuarioCrea(mprUsuario.getCValCorreo());
		comunicacionDto.setdFecFechaCrea(new Date());
		comunicacionDto.setnValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));

		logger.debug("ComunicacionController::cargarArchivoChatroom::fin cargarArchivoChatroom");
		return new ResponseEntity<ComunicacionDto>(comunicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de descargar archivos del chatroom
	 * @param fileName
	 * @param res
	 * @throws Exception
	 */
	@GetMapping("/descargarArchivoChatroom")
	public void descargarArchivoChatroom(String fileName, HttpServletResponse res){
		logger.debug("descargarArchivoChatroom::inicio descargarArchivoChatroom");
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");
		try {
			res.getOutputStream().write(archivoService.contentOfChatroom(fileName));
		} catch (IOException e) {
			logger.error("ComunicacionController::descargarArchivoChatroom::Se producjo el error : " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("ComunicacionController::descargarArchivoChatroom::Se producjo el error : " + e.getMessage(), e);
		}
		logger.debug("ComunicacionController::descargarArchivoChatroom::fin");
	}
	
}
