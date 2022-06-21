package pe.gob.osce.redcompradores.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.ParametroClaveEnum;
import pe.gob.osce.redcompradores.security.dto.UsuarioDto;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.service.UsuarioService;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.CorreoService;
import pe.gob.osce.redcompradores.service.EntidadService;
import pe.gob.osce.redcompradores.service.FileStorageService;
import pe.gob.osce.redcompradores.service.ParametroService;
import pe.gob.osce.redcompradores.util.BeanUtils;
import pe.gob.osce.redcompradores.util.PasswordGenerator;

/**
 * Controllador Usuario, aqui se realizan todas las funcionalidades del
 * mantenimiento de usuarios por parte del adminsitrador del sistema.
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
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UsuarioController {

	private static final AuditoriaService logger = new AuditoriaService(UsuarioController.class);

	@Value("${rc.package.usuario}")
	private String PACKAGE_USUARIO;

	@Autowired
	private EntidadService entidadService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CorreoService correoService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileStorageService archivoService;

	/**
	 * Metodo encargado de listar usuarios
	 * 
	 * @return List<UsuarioDto>
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listarUsuario")
	public ResponseEntity<List<UsuarioDto>> listarUsuario() {
		logger.debug("UsuarioController::listarUsuario::inicio");
		List<UsuarioDto> listaUsuarios = usuarioService.listarUsuarios();
		logger.debug(
				"UsuarioController::listarUsuario::Se listaron exitosamente " + listaUsuarios.size() + " usuarios");
		logger.debug("UsuarioController::listarUsuario::fin");
		return new ResponseEntity<List<UsuarioDto>>(listaUsuarios, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de listar los usuarios por entidad
	 * 
	 * @param usuarioDto: nIdEntidad = 1
	 * @return List<UsuarioDto>
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_ENTIDAD')")
	@PostMapping("/listarUsuarioPorEntidad")
	public ResponseEntity<List<UsuarioDto>> listarUsuarioPorEntidad(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::listarUsuarioPorEntidad::inicio");
		String trace;

		if (!entidadService.existePorcIdEntidad(usuarioDto.getnIdEntidad())) {
			trace = "Entidad con id: " + usuarioDto.getnIdEntidad() + " no encontrada";
			logger.error("UsuarioController::listarUsuarioPorEntidad::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		MprEntidad mprEntidad = entidadService.buscarPorId(usuarioDto.getnIdEntidad());
		List<UsuarioDto> listaUsuarios = usuarioService.listarUsuariosPorEntidad(mprEntidad);
		logger.debug("UsuarioController::listarUsuarioPorEntidad::Se listaron exitosamente " + listaUsuarios.size()
				+ " usuarios de la entidad :" + mprEntidad.getCDesEntidad());
		logger.debug("UsuarioController::listarUsuarioPorEntidad::fin");

		return new ResponseEntity<List<UsuarioDto>>(listaUsuarios, HttpStatus.OK);
	}

	/**
	 * Metodo encargado para crear usuario
	 * 
	 * @param usuarioDto
	 * @return String Se creo exitosamente el usuario : usuario@correo.com
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_ENTIDAD')")
	@PostMapping("/crearUsuario")
	public ResponseEntity<String> crearUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::crearUsuario::inicio");
		String trace;

		if (usuarioService.existePorCorreo(usuarioDto.getcValCorreo().toLowerCase())) {
			trace = "Ya existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::crearUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		if (!entidadService.existePorcIdEntidad(usuarioDto.getnIdEntidad())) {
			trace = "Entidad no encontrada";
			logger.error("UsuarioController::crearUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorNumDocumento(usuarioDto.getnNumDocumento());
		if (usuarioOptional.isPresent()) {
			MprUsuario mprUsuario = usuarioOptional.get();
			if (mprUsuario.getNValEstado().equals(new BigDecimal(EstadosEnum.ACTIVO.getValue()))
					|| mprUsuario.getNValEstado().equals(new BigDecimal(EstadosEnum.AMONESTACION.getValue()))) {

				mprUsuario.setNValEstado(new BigDecimal(EstadosEnum.BAJA.getValue()));
				mprUsuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
				usuarioService.guardarUsuario(mprUsuario);
				trace = "Se dio de baja a el usuario con documento " + usuarioDto.getnNumDocumento()
						+ " para un nuevo registro";
				logger.error("UsuarioController::crearUsuario::" + trace);
			}
		}

		MprEntidad entidad = entidadService.buscarPorId(usuarioDto.getnIdEntidad());
		ParametroDto parametroDto = parametroService
				.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_REGISTRO_NUEVO.name());

		MprUsuario usuario = new MprUsuario();
		usuario.setCCodTipodoc(usuarioDto.getcCodTipodoc());
		usuario.setNNumDocumento(usuarioDto.getnNumDocumento());
		usuario.setCApeUsuario(usuarioDto.getcApeUsuario());
		usuario.setCNomUsuario(usuarioDto.getcNomUsuario());
		usuario.setCValSexo(usuarioDto.getcValSexo());
		usuario.setCCodDep(usuarioDto.getcCodDep());
		usuario.setCCodProv(usuarioDto.getcCodProv());
		usuario.setCCodDist(usuarioDto.getcCodDist());
		usuario.setCCodRol(usuarioDto.getcCodRol());
		usuario.setCValCelular(usuarioDto.getcValCelular());

		usuario.setCValCorreo(usuarioDto.getcValCorreo().toLowerCase());
		String nuevaClave = PasswordGenerator.getPassword(10);
		usuario.setCValClave(passwordEncoder.encode(nuevaClave));

		usuario.setCValSeace(usuarioDto.getcValSeace());
		usuario.setCNumSeace(usuarioDto.getcNumSeace());

		usuario.setCValReniec(usuarioDto.getcValReniec());

		usuario.setNValCalificacion(usuarioDto.getnValCalificacion());
		usuario.setNValEstado(usuarioDto.getnValEstado());
		usuario.setCValUsuarioCrea(usuarioDto.getcValUsuarioCrea());
		usuario.setDFecFechaCrea(new Date());
		usuario.setCDirFoto("usuario-file-0.png");
		usuario.setMprEntidad(entidad);

		usuarioService.guardarUsuario(usuario);

		ParametroDto parametroLnkWeb = parametroService
				.buscarPorcValClaveParam(ParametroClaveEnum.LINK_RED_COMPRADORES.name());

		String nombresCompletos = usuarioDto.getcNomUsuario() + " " + usuarioDto.getcApeUsuario();
		String tenorCorreo = String.format(parametroDto.getcValValorParam(), nombresCompletos,
				usuarioDto.getcValCorreo(), nuevaClave, parametroLnkWeb.getcValValorParam());
		try {
			correoService.enviarCorreoHTML(usuarioDto.getcValCorreo(), "Registro de Usuario - Red de Compradores OSCE",
					tenorCorreo);
		} catch (MessagingException e) {
			trace = "Error al enviar correo de registro de usuario";
			logger.error("UsuarioController::crearUsuario::" + trace + e.getMessage(), e);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		logger.debug(
				"UsuarioController::crearUsuario::Se creo exitosamente el usuario : " + usuarioDto.getcValCorreo());
		logger.debug("UsuarioController::crearUsuario::fin");

		return new ResponseEntity(new MensajeDto("Usuario creado desde acceso publico con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de editar usuario
	 * 
	 * @param usuarioDto
	 * @return Se actualizo exitosamente el usuario : usuario@correo.com
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_ENTIDAD')")
	@PostMapping("/editarUsuario")
	public ResponseEntity<String> editarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::editarUsuario::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());
		if (usuarioOptional.isPresent()) {
			MprUsuario usuario = usuarioOptional.get();
			usuario.setCCodTipodoc(usuarioDto.getcCodTipodoc());
			usuario.setNNumDocumento(usuarioDto.getnNumDocumento());
			usuario.setCApeUsuario(usuarioDto.getcApeUsuario());
			usuario.setCNomUsuario(usuarioDto.getcNomUsuario());
			usuario.setCValSexo(usuarioDto.getcValSexo());
			usuario.setCCodDep(usuarioDto.getcCodDep());
			usuario.setCCodProv(usuarioDto.getcCodProv());
			usuario.setCCodDist(usuarioDto.getcCodDist());
			usuario.setCCodRol(usuarioDto.getcCodRol());
			usuario.setCValCelular(usuarioDto.getcValCelular());
			usuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
			usuario.setDFecFechaActu(new Date());

			usuario.setNValEstado(usuarioDto.getnValEstado());
			usuario.setCObsUsuario(usuarioDto.getcObsUsuario());

			usuarioService.guardarUsuario(usuario);

			if (usuarioDto.getnValEstado().intValue() == EstadosEnum.AMONESTACION.getValue()
					|| usuarioDto.getnValEstado().intValue() == EstadosEnum.BAJA.getValue()) {

				ParametroDto parametroDto = parametroService
						.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_AMONESTACION_BAJA.name());
				String nombresCompletos = usuarioDto.getcNomUsuario() + " " + usuarioDto.getcApeUsuario();
				String tenorCorreo = String.format(parametroDto.getcValValorParam(), nombresCompletos,
						usuarioDto.getcObsUsuario(), usuarioDto.getcValUsuarioActu());

				try {
					correoService.enviarCorreoHTML(usuarioDto.getcValCorreo(),
							nombresCompletos.concat(" tienes una notificación del Administrador."), tenorCorreo);
				} catch (MessagingException e) {
					trace = "Error al enviar correo de amonestación";
					logger.error("UsuarioController::editarUsuario::" + trace + e.getMessage(), e);
					return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
				}
			}

			trace = "Se actualizo exitosamente el usuario : " + usuarioDto.getcValCorreo();
			logger.debug("UsuarioController::editarUsuario::" + trace);
			logger.debug("UsuarioController::editarUsuario::fin");

			return new ResponseEntity(new MensajeDto(trace), HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::editarUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de editar el estado de un usuario
	 * 
	 * @param usuarioDto
	 * @return String Se actualizo exitosamente el usuario : usuario@correo.com
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_ENTIDAD')")
	@PostMapping("/editarEstadoUsuario")
	public ResponseEntity<String> editarEstadoUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::editarEstadoUsuario::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());

		if (usuarioOptional.isPresent()) {
			MprUsuario usuario = usuarioOptional.get();
			ParametroDto parametroDto = parametroService
					.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_AMONESTACION_BAJA.name());
			usuario.setNValEstado(usuarioDto.getnValEstado());
			usuario.setCObsUsuario(usuarioDto.getcObsUsuario());

			usuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
			usuario.setDFecFechaActu(new Date());
			usuarioService.guardarUsuario(usuario);

			if (usuarioDto.getnValEstado().intValue() == EstadosEnum.AMONESTACION.getValue()
					|| usuarioDto.getnValEstado().intValue() == EstadosEnum.BAJA.getValue()) {

				String nombresCompletos = usuarioDto.getcNomUsuario() + " " + usuarioDto.getcApeUsuario();
				String tenorCorreo = String.format(parametroDto.getcValValorParam(), nombresCompletos,
						usuarioDto.getcObsUsuario(), usuarioDto.getcValUsuarioActu());

				try {
					correoService.enviarCorreoHTML(usuarioDto.getcValCorreo(),
							nombresCompletos.concat(" tienes una notificación del Administrador."), tenorCorreo);
				} catch (MessagingException e) {
					trace = "Error al enviar correo de amonestación";
					logger.error("UsuarioController::editarEstadoUsuario::" + trace + e.getMessage(), e);
					return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
				}
			}

			trace = "Se actualizo exitosamente el usuario : " + usuarioDto.getcValCorreo();
			logger.debug("UsuarioController::editarEstadoUsuario::" + trace);
			logger.debug("UsuarioController::editarEstadoUsuario::fin");

			return new ResponseEntity(new MensajeDto(trace), HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::editarEstadoUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de editar informacion de perfil
	 * 
	 * @param usuarioDto
	 * @return String Se actualizo exitosamente el perfil del usuario :
	 *         usuario@correo.com
	 * @throws Exception
	 */
	@PostMapping("/editarPerfil")
	public ResponseEntity<String> editarPerfil(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::editarPerfil::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());

		if (usuarioOptional.isPresent()) {
			MprUsuario usuario = usuarioOptional.get();
			usuario.setCCodDep(usuarioDto.getcCodDep());
			usuario.setCCodProv(usuarioDto.getcCodProv());
			usuario.setCCodDist(usuarioDto.getcCodDist());
			usuario.setCValCelular(usuarioDto.getcValCelular());

			usuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
			usuario.setDFecFechaActu(new Date());

			usuarioService.guardarUsuario(usuario);

			logger.debug("UsuarioController::editarPerfil::Se actualizo exitosamente el perfil del usuario : "
					+ usuarioDto.getcValCorreo());
			logger.debug("UsuarioController::editarPerfil::fin");

			return new ResponseEntity(new MensajeDto("Usuario editado con exito"), HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::editarPerfil::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de editar foto de perfil de un usuario
	 * 
	 * @param file
	 * @param idUsuario
	 * @return String Se actualizo exitosamente la foto de perfil del usuario id :
	 *         usuario@correo.com
	 * @throws Exception
	 */
	@PostMapping("/editarFotoPerfil")
	public ResponseEntity<UsuarioDto> editarFotoPerfil(@RequestParam("file") MultipartFile file,
			@RequestParam("idUsuario") String idUsuario) {
		logger.debug("UsuarioController::editarFotoPerfil::inicio");
		String trace;

		if (usuarioService.existePorIdUsuario(new BigDecimal(idUsuario))) {
			MprUsuario mprUsuario = usuarioService.buscarPorIdUsuarioEntity(new BigDecimal(idUsuario));
			UsuarioDto usuarioDto = new UsuarioDto();
			try {
				archivoService.subirArchivoUsuario(file, mprUsuario);
			} catch (IOException e) {
				trace = "Error al subir la foto de perfil: " + e.getMessage();
				logger.error("UsuarioController::editarFotoPerfil::" + trace, e);
				return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
			}
			usuarioService.guardarUsuario(mprUsuario);

			BeanUtils.copyPropertiesUsuario(usuarioDto, mprUsuario);
			logger.debug(
					"UsuarioController::editarFotoPerfil::Se actualizo exitosamente la foto de perfil del usuario id : "
							+ mprUsuario.getCValCorreo());
			logger.debug("UsuarioController::editarFotoPerfil::fin");

			return new ResponseEntity(usuarioDto, HttpStatus.OK);

		} else {
			trace = "No existe un usuario identificado con id: " + idUsuario + " en el sistema";
			logger.error("UsuarioController::editarFotoPerfil::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de actualizar informacion complementaria
	 * 
	 * @param usuarioDto
	 * @return String Se actualizo la informacion complementaria del usuario :
	 *         usuario@correo.com
	 * @throws Exception
	 */
	@PostMapping("/editarInfoComplementaria")
	public ResponseEntity<String> editarInfoComplementaria(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::editarInfoComplementaria::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());
		if (usuarioOptional.isPresent()) {
			MprUsuario usuario = usuarioOptional.get();
			usuario.setCDesExperiencia(usuarioDto.getcDesExperiencia());
			usuario.setCDesProfesion(usuarioDto.getcDesProfesion());
			usuario.setCValFacebook(usuarioDto.getcValFacebook());
			usuario.setCValInstagram(usuarioDto.getcValInstagram());
			usuario.setCValLinkedin(usuarioDto.getcValLinkedin());
			usuario.setCValTwitter(usuarioDto.getcValTwitter());
			usuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
			usuario.setDFecFechaActu(new Date());
			usuarioService.guardarUsuario(usuario);
			logger.debug(
					"UsuarioController::editarInfoComplementaria::Se actualizo la informacion complementaria del usuario : "
							+ usuarioDto.getcValCorreo());
			logger.debug("UsuarioController::editarInfoComplementaria::fin");

			return new ResponseEntity(new MensajeDto("Usuario editado con exito"), HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::editarInfoComplementaria::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado para actualizar el password de usuario
	 * 
	 * @param usuarioDto
	 * @return Se actualizo exitosamente la contraseña del usuario :
	 *         usuario@correo.com
	 * @throws Exception
	 */
	@PostMapping("/editarUsuarioPassword")
	public ResponseEntity<String> editarUsuarioPassword(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::editarUsuarioPassword::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());
		if (usuarioOptional.isPresent()) {
			MprUsuario usuario = usuarioOptional.get();
			if (passwordEncoder.matches(usuarioDto.getcValClaveActual(), usuario.getCValClave())) {
				usuario.setCValClave(passwordEncoder.encode(usuarioDto.getcValClave()));
				usuario.setCValUsuarioActu(usuarioDto.getcValUsuarioActu());
				usuario.setDFecFechaActu(new Date());
				usuarioService.guardarUsuario(usuario);

				logger.debug(
						"UsuarioController::editarUsuarioPassword::Se actualizo exitosamente la contraseña del usuario : "
								+ usuarioDto.getcValCorreo());
				logger.debug("UsuarioController::editarUsuarioPassword::fin");

				return new ResponseEntity(new MensajeDto("Usuario editado con exito"), HttpStatus.OK);
			} else {
				trace = "La clave actual ingresada no es correcta";
				logger.error("UsuarioController::editarUsuarioPassword::" + trace);
				return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
			}

		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("UsuarioController::editarUsuarioPassword::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Metodo encargado de buscar usuario por id
	 * 
	 * @param usuarioDto
	 * @return UsuarioDto
	 * @throws Exception
	 */
	@PostMapping("/buscarUsuario")
	public ResponseEntity<UsuarioDto> buscarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("UsuarioController::buscarUsuario::inicio");
		String trace;

		if (usuarioService.existePorIdUsuario(usuarioDto.getnIdUsuario())) {
			UsuarioDto dtoReturn = usuarioService.buscarPorIdUsuario(usuarioDto.getnIdUsuario());
			logger.debug(
					"UsuarioController::buscarUsuario::Se encontro el usuario con id: " + usuarioDto.getnIdUsuario());
			logger.debug("UsuarioController::buscarUsuario::fin");

			return new ResponseEntity<UsuarioDto>(dtoReturn, HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con id:" + usuarioDto.getnIdUsuario() + " en el sistema";
			logger.error("UsuarioController::buscarUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de retornar la foto de perfil del usuario
	 * 
	 * @param cDirFoto
	 * @return Resource
	 * @throws Exception
	 */
	@GetMapping("/verFoto")
	public ResponseEntity<Resource> verFoto(String cDirFoto) {
		logger.debug("UsuarioController::verFoto::inicio");

		String pathFile = PACKAGE_USUARIO + File.separator + cDirFoto;
		Resource file = loadFile(pathFile);
		logger.debug("UsuarioController::verFoto::fin");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	/**
	 * Metodo encargado de obtener Resource por el nombre de archivo
	 * 
	 * @param pathFile
	 * @return Resource
	 */
	public Resource loadFile(String pathFile) {
		try {
			Path file = Paths.get(pathFile);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				String pathfileDefault = PACKAGE_USUARIO + File.separator + "usuario-file-0.png";
				Path fileDefault = Paths.get(pathfileDefault);
				Resource resourceDefault = new UrlResource(fileDefault.toUri());
				return resourceDefault;
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(
					"UsuarioController::loadFile::URL mal formado para la busqueda de foto de usuario");
		}
	}
}
