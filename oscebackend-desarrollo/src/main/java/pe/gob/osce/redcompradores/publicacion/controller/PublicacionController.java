package pe.gob.osce.redcompradores.publicacion.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osce.redcompradores.dto.MensajeDto;
import pe.gob.osce.redcompradores.dto.ParametroDto;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.ParametroClaveEnum;
import pe.gob.osce.redcompradores.publicacion.dto.ArchivoDto;
import pe.gob.osce.redcompradores.publicacion.dto.ComentarioDto;
import pe.gob.osce.redcompradores.publicacion.dto.PublicacionDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblArchivo;
import pe.gob.osce.redcompradores.publicacion.emtity.TblCalificacion;
import pe.gob.osce.redcompradores.publicacion.emtity.TblComentario;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.publicacion.service.ArchivoService;
import pe.gob.osce.redcompradores.publicacion.service.CalificacionService;
import pe.gob.osce.redcompradores.publicacion.service.ComentarioService;
import pe.gob.osce.redcompradores.publicacion.service.PublicacionService;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.service.UsuarioService;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.CorreoService;
import pe.gob.osce.redcompradores.service.FileStorageService;
import pe.gob.osce.redcompradores.service.ParametroService;
import pe.gob.osce.redcompradores.util.BeanUtils;
import pe.gob.osce.redcompradores.util.Utility;

/**
 * Controlador Rest, encargado del manejo del manejo calificacion, comentarios y
 * archivos adjuntos de las publicaciones
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
@RestController
@RequestMapping("/publicacion")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PublicacionController {

	private static final AuditoriaService logger = new AuditoriaService(PublicacionController.class);

	@Autowired
	private PublicacionService publicacionService;

	@Autowired
	private CalificacionService calificacionService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private FileStorageService fileService;

	@Autowired
	private CorreoService correoService;

	@Autowired
	private ParametroService parametroService;

	/**
	 * Metodo encargado de buscar publicaciones por temas y/o palabras claves
	 * 
	 * @param publicacionDto
	 * @return List<PublicacionDto>
	 * @throws Exception
	 */
	@PostMapping("/buscarPublicacion")
	public ResponseEntity<List<PublicacionDto>> buscarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::buscarPublicacion::inicio");

		List<String> cFilterTema = null;
		String cFilterPalabraClave = null;

		if (publicacionDto.getcFilterTema() != null && publicacionDto.getcFilterTema().length() > 0)
			cFilterTema = Arrays.asList(
					publicacionDto.getcFilterTema().replaceAll("[\\[\\](){}]", "").replaceAll("\\\"", "").split(","));

		if (publicacionDto.getcFilterPalabrasClave() != null && publicacionDto.getcFilterPalabrasClave().length() > 0)
			cFilterPalabraClave = publicacionDto.getcFilterPalabrasClave().toLowerCase().replaceAll("[\\[\\](){}]", "");

		List<PublicacionDto> listaRetorno = publicacionService.listarPublicacionesFiltro(cFilterTema,
				cFilterPalabraClave);

		logger.debug("PublicacionController::buscarPublicacion::Se filtraron las publicacion por los filtros :"
				+ cFilterTema + " " + cFilterPalabraClave);
		logger.debug("PublicacionController::buscarPublicacion::fin");

		return new ResponseEntity(listaRetorno, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de buscar publicaciones de un usuario por temas y/o palabras
	 * claves
	 * 
	 * @param publicacionDto
	 * @return List<PublicacionDto>
	 * @throws Exception
	 */
	@PostMapping("/buscarPublicacionPorUsuario")
	public ResponseEntity<List<PublicacionDto>> buscarPublicacionPorUsuario(
			@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::buscarPublicacionPorUsuario::inicio");

		List<String> cFilterTema = null;
		String cFilterPalabraClave = null;

		if (publicacionDto.getcFilterTema() != null && publicacionDto.getcFilterTema().length() > 0)
			cFilterTema = Arrays.asList(
					publicacionDto.getcFilterTema().replaceAll("[\\[\\](){}]", "").replaceAll("\\\"", "").split(","));

		if (publicacionDto.getcFilterPalabrasClave() != null && publicacionDto.getcFilterPalabrasClave().length() > 0)
			cFilterPalabraClave = publicacionDto.getcFilterPalabrasClave().toLowerCase().replaceAll("[\\[\\](){}]", "");

		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(publicacionDto.getnIdUsuario());
		List<PublicacionDto> listaRetorno = publicacionService.listarPublicacionesFiltroPorUsuario(mprUsuario,
				cFilterTema, cFilterPalabraClave);

		logger.debug(
				"PublicacionController::buscarPublicacionPorUsuario::Se filtraron las publicacion por los filtros :"
						+ cFilterTema + " " + cFilterPalabraClave);
		logger.debug("PublicacionController::buscarPublicacionPorUsuario::fin");

		return new ResponseEntity(listaRetorno, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de buscar publicaciones de su id
	 * 
	 * @param publicacionDto
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping("/buscarPublicacionPorId")
	public ResponseEntity<PublicacionDto> buscarPublicacionPorId(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::buscarPublicacionPorId::inicio");
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error(
					"PublicacionController::buscarPublicacionPorId::Error al cargar foto de perfil: " + e.getMessage(),
					e);
		}
		logger.debug("PublicacionController::buscarPublicacionPorId::Se actualizó exitosamente la publicacion con id: "
				+ publicacionDto.getnIdPublicacion());
		logger.debug("PublicacionController::buscarPublicacionPorId::fin");

		return new ResponseEntity(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de editar una publicacion
	 * 
	 * @param publicacionDto
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping("/editarPublicacion")
	public ResponseEntity<PublicacionDto> editarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::editarPublicacion::inicio");
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		tblPublicacion.setCValDetPub(publicacionDto.getcValDetPub());
		tblPublicacion.setCValEnviaCorreo(publicacionDto.getcValEnviaCorreo());
		tblPublicacion.setCValUsuarioActu(publicacionDto.getcValUsuarioActu());
		tblPublicacion.setDFecFechaActu(new Date());
		publicacionService.guardarPublicacion(tblPublicacion);
		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error("PublicacionController::editarPublicacion::Error al cargar foto de perfil: " + e.getMessage(),
					e);
		}
		logger.debug("PublicacionController::editarPublicacion::Se actualizó exitosamente la publicacion con id: "
				+ publicacionDto.getnIdPublicacion());
		logger.debug("PublicacionController::editarPublicacion::fin");

		return new ResponseEntity(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de editar una publicacion de un determinado usuario
	 * 
	 * @param publicacionDto
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping("/editarPublicacionUsuario")
	public ResponseEntity<PublicacionDto> editarPublicacionUsuario(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::editarPublicacionUsuario::inicio");
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		tblPublicacion.setCValDetPub(publicacionDto.getcValDetPub());
		tblPublicacion.setCValEnviaCorreo(publicacionDto.getcValEnviaCorreo());
		tblPublicacion.setCValUsuarioActu(publicacionDto.getcValUsuarioActu());
		tblPublicacion.setDFecFechaActu(new Date());
		publicacionService.guardarPublicacion(tblPublicacion);
		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error("PublicacionController::editarPublicacionUsuario::Error al cargar foto de perfil: "
					+ e.getMessage(), e);
		}
		logger.debug(
				"PublicacionController::editarPublicacionUsuario::Se actualizó exitosamente la publicacion con id: "
						+ publicacionDto.getnIdPublicacion());
		logger.debug("PublicacionController::editarPublicacionUsuario::fin");

		return new ResponseEntity(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de eliminar de manera logica una publicacion
	 * 
	 * @param publicacionDto
	 * @return String Se elimino exitosamente la publicacion con id: 1
	 * @throws Exception
	 */
	@PostMapping("/eliminarPublicacion")
	public ResponseEntity<String> eliminarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::eliminarPublicacion::inicio");

		String trace;
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		tblPublicacion.setNValEstado(new BigDecimal(EstadosEnum.INACTIVO.getValue()));
		tblPublicacion.setCValUsuarioActu(publicacionDto.getcValUsuarioActu());
		tblPublicacion.setDFecFechaActu(new Date());

		publicacionService.guardarPublicacion(tblPublicacion);

		trace = "Se elimino exitosamente la publicacion con id: "
				+ publicacionDto.getnIdPublicacion();
		logger.debug("PublicacionController::eliminarPublicacion::" + trace);
		logger.debug("PublicacionController::eliminarPublicacion::fin");

		return new ResponseEntity(new MensajeDto(trace), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de eliminar de manera logica una publicacion de un usuario
	 * 
	 * @param publicacionDto
	 * @return String Se elimino exitosamente la publicacion con id: 1
	 * @throws Exception
	 */
	@PostMapping("/eliminarPublicacionUsuario")
	public ResponseEntity<String> eliminarPublicacionUsuario(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::eliminarPublicacionUsuario::inicio");

		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		tblPublicacion.setNValEstado(new BigDecimal(EstadosEnum.INACTIVO.getValue()));
		tblPublicacion.setCValUsuarioActu(publicacionDto.getcValUsuarioActu());
		tblPublicacion.setDFecFechaActu(new Date());

		publicacionService.guardarPublicacion(tblPublicacion);
		String trace = "Se elimino exitosamente la publicacion con id: "
				+ publicacionDto.getnIdPublicacion();
		logger.debug("PublicacionController::eliminarPublicacionUsuario::" + trace);
		logger.debug("PublicacionController::eliminarPublicacionUsuario::fin");

		return new ResponseEntity(new MensajeDto(trace), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de calificar una publicacion, like o disslike
	 * 
	 * @param publicacionDto
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping("/calificarPublicacion")
	public ResponseEntity<PublicacionDto> calificarPublicacion(@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::calificarPublicacion::inicio");

		TblCalificacion tblCalificacion;
		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(publicacionDto.getnIdUsuario());
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());

		if (calificacionService.existePorUsuarioAndPublicacion(mprUsuario, tblPublicacion)) {
			tblCalificacion = calificacionService.buscarPorUsuarioAndPublicacion(mprUsuario, tblPublicacion);
			tblCalificacion.setCValUsuarioActu(mprUsuario.getCValCorreo());
			tblCalificacion.setDFecFechaActu(new Date());

		} else {
			tblCalificacion = new TblCalificacion();
			tblCalificacion.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
			tblCalificacion.setCValUsuarioCrea(mprUsuario.getCValCorreo());
			tblCalificacion.setDFecFechaCrea(new Date());
		}

		tblCalificacion.setMprUsuario(mprUsuario);
		tblCalificacion.setTblPublicacion(tblPublicacion);
		tblCalificacion.setNValCalificacion(Utility.obtenerValorCalificacion(publicacionDto.getcTipoCalificacion()));
		calificacionService.guardarCalificacion(tblCalificacion);

		logger.debug("PublicacionController::calificarPublicacion::Actualizar calificacion de usuario: "
				+ tblPublicacion.getMprUsuario().getNIdUsuario());
		Integer totalCalificacion = calificacionService
				.cantidadCalificacionPornIdUsuario(tblPublicacion.getMprUsuario().getNIdUsuario());
		Integer totalLikesCalificacion = calificacionService
				.cantidadLikesPornIdUsuario(tblPublicacion.getMprUsuario().getNIdUsuario());

		MprUsuario mprUsuarioUpdate = usuarioService
				.buscarPorIdUsuarioEntity(tblPublicacion.getMprUsuario().getNIdUsuario());
		mprUsuarioUpdate
				.setNValCalificacion(Utility.obtenerCalificacionUsuario(totalCalificacion, totalLikesCalificacion));
		usuarioService.guardarUsuario(mprUsuarioUpdate);
		logger.debug("PublicacionController::calificarPublicacion::Actualización calificacion exitosa");

		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error(
					"PublicacionController::calificarPublicacion::Error al cargar foto de perfil: " + e.getMessage(),
					e);
		}
		publicacionService.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
		logger.debug("PublicacionController::calificarPublicacion::Se califico exitosamente la publicacion con id: "
				+ publicacionDto.getnIdPublicacion());
		logger.debug("PublicacionController::calificarPublicacion::fin");

		return new ResponseEntity(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de calificar una publicacion de un usuario determinado, like
	 * o disslike
	 * 
	 * @param publicacionDto
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping("/calificarPublicacionUsuario")
	public ResponseEntity<PublicacionDto> calificarPublicacionUsuario(
			@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::calificarPublicacionUsuario::inicio");

		TblCalificacion tblCalificacion;
		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(publicacionDto.getnIdUsuario());
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());

		if (calificacionService.existePorUsuarioAndPublicacion(mprUsuario, tblPublicacion)) {
			tblCalificacion = calificacionService.buscarPorUsuarioAndPublicacion(mprUsuario, tblPublicacion);
			tblCalificacion.setCValUsuarioActu(mprUsuario.getCValCorreo());
			tblCalificacion.setDFecFechaActu(new Date());

		} else {
			tblCalificacion = new TblCalificacion();
			tblCalificacion.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
			tblCalificacion.setCValUsuarioCrea(mprUsuario.getCValCorreo());
			tblCalificacion.setDFecFechaCrea(new Date());
		}

		tblCalificacion.setMprUsuario(mprUsuario);
		tblCalificacion.setTblPublicacion(tblPublicacion);
		tblCalificacion.setNValCalificacion(Utility.obtenerValorCalificacion(publicacionDto.getcTipoCalificacion()));
		calificacionService.guardarCalificacion(tblCalificacion);

		logger.debug("PublicacionController::calificarPublicacionUsuario::Actualizar calificacion de usuario: "
				+ tblPublicacion.getMprUsuario().getNIdUsuario());
		Integer totalCalificacion = calificacionService
				.cantidadCalificacionPornIdUsuario(tblPublicacion.getMprUsuario().getNIdUsuario());
		Integer totalLikesCalificacion = calificacionService
				.cantidadLikesPornIdUsuario(tblPublicacion.getMprUsuario().getNIdUsuario());

		MprUsuario mprUsuarioUpdate = usuarioService
				.buscarPorIdUsuarioEntity(tblPublicacion.getMprUsuario().getNIdUsuario());
		mprUsuarioUpdate
				.setNValCalificacion(Utility.obtenerCalificacionUsuario(totalCalificacion, totalLikesCalificacion));
		usuarioService.guardarUsuario(mprUsuarioUpdate);
		logger.debug("PublicacionController::calificarPublicacionUsuario::Actualización calificacion exitosa");

		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error("PublicacionController::calificarPublicacionUsuario::Error al cargar foto de perfil: "
					+ e.getMessage(), e);
		}
		publicacionService.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
		logger.debug(
				"PublicacionController::calificarPublicacionUsuario::Se califico exitosamente la publicacion con id: "
						+ publicacionDto.getnIdPublicacion());
		logger.debug("PublicacionController::calificarPublicacionUsuario::fin");

		return new ResponseEntity(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de adjuntar un archivo a una publicacion y guardarla en la
	 * base de datos
	 * 
	 * @param file
	 * @param idPublicacion
	 * @param idTema
	 * @param tipoArchivo
	 * @return PublicacionDto
	 * @throws Exception
	 */
	@PostMapping(value = "/cargarArchivoPublicacion", consumes = "multipart/form-data")
	public ResponseEntity<PublicacionDto> cargarArchivoPublicacion(@RequestParam("file") MultipartFile file,
			@RequestParam("idPublicacion") String idPublicacion, @RequestParam("idTema") String idTema,
			@RequestParam("tipoArchivo") String tipoArchivo) {

		logger.debug("PublicacionController::cargarArchivoPublicacion::inicio");
		String trace;

		TblArchivo tblArchivo = new TblArchivo();
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(new BigDecimal(idPublicacion));
		tblArchivo.setTblPublicacion(tblPublicacion);
		tblArchivo.setCValClaveArchivo(tblPublicacion.getCValClavePub());
		tblArchivo.setCValTipoArchivo(Utility.obtenerTipoArchivo(tipoArchivo));
		tblArchivo.setDFecFechaCrea(new Date());
		tblArchivo.setCValUsuarioCrea(tblPublicacion.getMprUsuario().getCValCorreo());
		tblArchivo.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
		try {
			fileService.subirArchivo(file, idTema, idPublicacion, tblArchivo);
		} catch (IOException e) {
			trace = "Error al cargar archivo en la publicación";
			logger.error("PublicacionController::cargarArchivoPublicacion::" + trace + e.getMessage(), e);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		archivoService.guardarArchivo(tblArchivo);

		PublicacionDto publicacionDto = new PublicacionDto();
		BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
		try {
			publicacionDto
					.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
		} catch (Exception e) {
			logger.error("PublicacionController::cargarArchivoPublicacion::Error al cargar foto de perfil: "
					+ e.getMessage(), e);
		}
		publicacionDto.setcValNomArchivo(tblArchivo.getCValNomArchivo());
		publicacionDto.setcValRutaArchivo(tblArchivo.getCValRutaArchivo());
		publicacionDto.setcTipoArchivo(tblArchivo.getCValTipoArchivo());

		logger.debug("PublicacionController::cargarArchivoPublicacion::fin");
		return new ResponseEntity<PublicacionDto>(publicacionDto, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de descarga un archivo de una publicacion
	 * 
	 * @param fileName
	 * @param res
	 * @throws Exception
	 */
	@GetMapping("/descargarArchivoPublicacion")
	public void descargarArchivoPublicacion(String fileName, HttpServletResponse res) {
		logger.debug("PublicacionController::descargarArchivoPublicacion::inicio");
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");
		try {
			res.getOutputStream().write(fileService.contentOf(fileName));
		} catch (IOException e) {
			logger.error("PublicacionController::descargarArchivoPublicacion::Se produjo el error:: " + e.getMessage(),
					e);
		} catch (Exception e) {
			logger.error("PublicacionController::descargarArchivoPublicacion::Se produjo el error:: " + e.getMessage(),
					e);
		}
		logger.debug("PublicacionController::descargarArchivoPublicacion::fin");
	}

	/**
	 * Metodo encargado de listar los archivos para el modulo de consulta
	 * 
	 * @param publicacionDto
	 * @return List<ArchivoDto>
	 * @throws Exception
	 */
	@PostMapping("/listarArchivosPublicacion")
	public ResponseEntity<List<ArchivoDto>> listarArchivosPublicacion(
			@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::listarArchivosPublicacion::inicio");
		List<ArchivoDto> listaRetorno = archivoService.listarArchivos();
		logger.debug(
				"PublicacionController::listarArchivosPublicacion::Se listaron " + listaRetorno.size() + " archivos");
		logger.debug("PublicacionController::listarArchivosPublicacion::fin");

		return new ResponseEntity(listaRetorno, HttpStatus.OK);
	}

	/**
	 * Metodo que lista los comentarios de una publicacion
	 * 
	 * @param publicacionDto
	 * @return List<ComentarioDto>
	 * @throws Exception
	 */
	@PostMapping("/listarComentariosPublicacion")
	public ResponseEntity<List<ComentarioDto>> listarComentariosPublicacion(
			@Valid @RequestBody PublicacionDto publicacionDto) {
		logger.debug("PublicacionController::listarComentariosPublicacion::inicio");
		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(publicacionDto.getnIdPublicacion());
		List<ComentarioDto> listaRetorno = comentarioService.listarComentario(tblPublicacion);

		logger.debug("PublicacionController::listarComentariosPublicacion::Se listaron " + listaRetorno.size()
				+ " comentarios de la publicacion id: " + tblPublicacion.getNIdPublicacion());
		logger.debug("PublicacionController::listarComentariosPublicacion::fin");

		return new ResponseEntity(listaRetorno, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de guardar una publicacion determinada y se envia un
	 * notificacion al usuari si es que tiene activa esta opcion
	 * 
	 * @param comentarioDto
	 * @return List<ComentarioDto>
	 * @throws Exception
	 */
	@PostMapping("/guardarComentarioPublicacion")
	public ResponseEntity<List<ComentarioDto>> guardarComentarioPublicacion(
			@Valid @RequestBody ComentarioDto comentarioDto) {
		logger.debug("PublicacionController::guardarComentarioPublicacion::inicio");
		String trace;

		TblPublicacion tblPublicacion = publicacionService.buscarPornIdPublicacion(comentarioDto.getnIdPublicacion());
		MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(comentarioDto.getnIdUsuario());
		logger.debug("PublicacionController::guardarComentarioPublicacion::Usuario que comenta id :"
				+ mprUsuario.getNIdUsuario());

		TblComentario tblComentario = new TblComentario();
		tblComentario.setCValComentario(comentarioDto.getcValComentario());
		tblComentario.setCValUsuarioCrea(comentarioDto.getcValUsuarioCrea());
		tblComentario.setDFecFechaCrea(new Date());
		tblComentario.setNValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
		tblComentario.setTblPublicacion(tblPublicacion);
		tblComentario.setMprUsuario(mprUsuario);
		comentarioService.guardarComentario(tblComentario);
		List<ComentarioDto> listaRetorno = comentarioService.listarComentario(tblPublicacion);
		logger.debug("PublicacionController::guardarComentarioPublicacion::Se listaron " + listaRetorno.size()
				+ " comentarios de la publicacion id: " + tblPublicacion.getNIdPublicacion());

		if (tblPublicacion.getCValEnviaCorreo().equalsIgnoreCase("1")) {
			logger.debug(
					"PublicacionController::guardarComentarioPublicacion::Se envia notificacion por correo por nuevo comentario");
			ParametroDto parametroDto = parametroService
					.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_NOTIFICACION_COMENTARIO.name());
			String nombresCompletos = tblPublicacion.getMprUsuario().getCNomUsuario() + " "
					+ tblPublicacion.getMprUsuario().getCApeUsuario();
			String tenorCorreo = String.format(parametroDto.getcValValorParam(), nombresCompletos);
			try {
				correoService.enviarCorreoHTML(tblPublicacion.getMprUsuario().getCValCorreo(),
						nombresCompletos.concat(" tienes una notificación."), tenorCorreo);
			} catch (MessagingException e) {
				trace = "Error al enviar correo de notificación de comentario";
				logger.error("PublicacionController::guardarComentarioPublicacion::" + trace + e.getMessage(), e);
				return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
			}
		}

		logger.debug("PublicacionController::guardarComentarioPublicacion::fin");

		return new ResponseEntity(listaRetorno, HttpStatus.OK);
	}
}
