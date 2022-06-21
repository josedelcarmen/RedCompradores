package pe.gob.osce.redcompradores.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osce.redcompradores.dto.EntidadDto;
import pe.gob.osce.redcompradores.dto.MejoraDto;
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
import pe.gob.osce.redcompradores.service.ParametroService;
import pe.gob.osce.redcompradores.util.PasswordGenerator;

/**
 * Controlador Rest Publico, aqui se realizan los registros de usuario de manera
 * publica y la recuperacion de contraseñas, y el listado de otros recursos como
 * entidades y parametros para que un registro de usuario se complete.
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
@RequestMapping("/publico")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PublicoController {

	private static final AuditoriaService logger = new AuditoriaService(PublicoController.class);

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

	/**
	 * Metodo encargado de listar entidades
	 * 
	 * @return List<EntidadDto>
	 * @throws Exception
	 */
	@GetMapping("/listarEntidad")
	public ResponseEntity<List<EntidadDto>> listarEntidad() {
		logger.debug("PublicoController::listarEntidad::inicio");
		List<EntidadDto> listaEntidades = entidadService.listarEntidad();
		logger.debug(
				"PublicoController::listarEntidad::Se listaron exitosamente " + listaEntidades.size() + " entidades");
		logger.debug("PublicoController::listarEntidad::fin");

		return new ResponseEntity<List<EntidadDto>>(listaEntidades, HttpStatus.OK);
	}

	/**
	 * Metodo para buscar parametros por el atributo clave
	 * 
	 * @param parametrosDto : cValClaveParam = VALID_RENIEC
	 * @return ParametroDto : cValValorParam = S
	 * @throws Exception
	 */
	@PostMapping("/buscarParametroPorClave")
	public ResponseEntity<ParametroDto> buscarParametroPorClave(@Valid @RequestBody ParametroDto parametrosDto) {
		logger.debug("PublicoController::buscarPorClave::inicio");
		String trace;
		ParametroDto parametro = parametroService.buscarPorcValClaveParam(parametrosDto.getcValClaveParam());
		if (parametro == null) {
			trace = "Parametro con clave: " + parametrosDto.getcValClaveParam() + " no existe";
			logger.error("PublicoController::buscarPorClave::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		} else if (parametro.getnValEstado().intValue() == 0) {
			trace = "Parametro con clave: " + parametrosDto.getcValClaveParam() + " esta inactivo";
			logger.error("PublicoController::buscarPorClave::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		logger.debug("PublicoController::buscarPorClave::Se listó el parámetro: " + parametro.getcValCortaParam());
		logger.debug("PublicoController::buscarPorClave::fin");

		return new ResponseEntity<ParametroDto>(parametro, HttpStatus.OK);
	}

	/**
	 * Metodo para buscar parametros por grupo
	 * 
	 * @param parametrosDto: cValGrupoParam = TIPO_DOCUMENTO
	 * @return List<ParametroDto>
	 * @throws Exception
	 */
	@PostMapping("/buscarParametroPorGrupo")
	public ResponseEntity<List<ParametroDto>> buscarParametroPorGrupo(@Valid @RequestBody ParametroDto parametrosDto) {

		logger.debug("PublicoController::buscarPorGrupo::inicio");
		String trace;
		List<ParametroDto> parametros = parametroService.buscarPorcValGrupoParam(parametrosDto.getcValGrupoParam());

		if (parametros == null || parametros.size() == 0) {
			trace = "Parametros con grupo: " + parametrosDto.getcValGrupoParam() + " no existen";
			logger.error("PublicoController::buscarPorGrupo::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		List<ParametroDto> listParametros = new ArrayList<ParametroDto>();
		for (ParametroDto parParametro : parametros) {
			if (parParametro.getnValEstado().intValue() == 1) {
				listParametros.add(parParametro);
			}
		}

		logger.debug("PublicoController::buscarPorGrupo::Se listaron exitosamente " + listParametros.size()
				+ " parámetros activos");
		logger.debug("PublicoController::buscarPorGrupo::fin");

		return new ResponseEntity<List<ParametroDto>>(listParametros, HttpStatus.OK);
	}

	/**
	 * Metodo que busca parametros por el campo padre
	 * 
	 * @param parametrosDto: cCodPadreParam = 222
	 * @return List<ParametroDto>
	 * @throws Exception
	 */
	@PostMapping("/buscarParametroPorPadre")
	public ResponseEntity<List<ParametroDto>> buscarParametroPorPadre(@Valid @RequestBody ParametroDto parametrosDto) {

		logger.debug("PublicoController::buscarParametroPorPadre::inicio");
		String trace;
		List<ParametroDto> parametros = parametroService.buscarPorcCodPadreParam(parametrosDto.getcCodPadreParam());

		if (parametros == null || parametros.size() == 0) {
			trace = "Parametros con grupo: " + parametrosDto.getcValGrupoParam() + " no existen";
			logger.error("PublicoController::buscarParametroPorPadre::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		List<ParametroDto> listParametros = new ArrayList<ParametroDto>();
		for (ParametroDto parParametro : parametros) {
			if (parParametro.getnValEstado().intValue() == 1) {
				listParametros.add(parParametro);
			}
		}

		logger.debug("PublicoController::buscarParametroPorPadre::Se listaron exitosamente " + listParametros.size()
				+ " parámetros activos");
		logger.debug("PublicoController::buscarParametroPorPadre::fin");

		return new ResponseEntity<List<ParametroDto>>(listParametros, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de la creacion de un nuevo usuario
	 * 
	 * @param usuarioDto
	 * @return Se creo exitosamente el usuario : usuario@correo.com
	 * @throws Exception
	 */
	@PostMapping("/crearUsuario")
	public ResponseEntity<String> crearUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("PublicoController::crearUsuario::inicio");
		String trace;

		if (usuarioService.existePorCorreo(usuarioDto.getcValCorreo().toLowerCase())) {
			trace = "Ya existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("PublicoController::crearUsuario::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		if (!entidadService.existePorcIdEntidad(usuarioDto.getnIdEntidad())) {
			trace = "Entidad con id: " + usuarioDto.getnIdEntidad() + " no encontrada";
			logger.error("PublicoController::crearUsuario::" + trace);
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
				trace = "PublicoController::crearUsuario::Se dio de baja a el usuario con documento "
						+ usuarioDto.getnNumDocumento() + " para un nuevo registro";
				logger.error(trace);
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
		usuario.setCValClave(passwordEncoder.encode(usuarioDto.getcValClave()));
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
				usuarioDto.getcValCorreo(), usuarioDto.getcValClave(), parametroLnkWeb.getcValValorParam());

		try {
			correoService.enviarCorreoHTML(usuarioDto.getcValCorreo(), "Registro de Usuario - Red de Compradores OSCE",
					tenorCorreo);
		} catch (MessagingException e) {
			trace = "Error al enviar correo de registro de usuario";
			logger.error("PublicoController::crearUsuario::" + trace + e.getMessage(), e);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		logger.debug(
				"PublicoController::crearUsuario::Se creo exitosamente el usuario : " + usuarioDto.getcValCorreo());
		logger.debug("PublicoController::crearUsuario::fin");

		return new ResponseEntity(new MensajeDto("Usuario creado desde acceso publico con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de enviar una nueva contraseña creada por correo
	 * 
	 * @param usuarioDto
	 * @return Rg@3CZfÑDA
	 * @throws Exception
	 */
	@PostMapping("/resetearPassword")
	public ResponseEntity<String> resetearPassword(@Valid @RequestBody UsuarioDto usuarioDto) {
		logger.debug("PublicoController::resetearPassword::inicio");
		String trace;

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(usuarioDto.getcValCorreo());
		if (usuarioOptional.isPresent()) {
			ParametroDto parametroDto = parametroService
					.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_RESET_PASSWORD.name());
			MprUsuario usuario = usuarioOptional.get();

			String nombresCompletos = usuario.getCNomUsuario() + " " + usuario.getCApeUsuario();
			String nuevaClave = PasswordGenerator.getPassword(10);
			String tenorCorreo = String.format(parametroDto.getcValValorParam(), nombresCompletos, nuevaClave);
			usuario.setCValClave(passwordEncoder.encode(nuevaClave));
			usuarioService.guardarUsuario(usuario);

			try {
				correoService.enviarCorreoHTML(usuarioDto.getcValCorreo(), nombresCompletos + " recupera tu contraseña",
						tenorCorreo);
			} catch (MessagingException e) {
				trace = "Error al enviar correo de reseteo de clave";
				logger.error("PublicoController::resetearPassword::" + trace + e.getMessage(), e);
				return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
			}
			trace = "Se reseteo correctamente la contraseña del usuario " + usuarioDto.getcValCorreo();
			logger.debug("PublicoController::resetearPassword::" + trace);
			logger.debug("PublicoController::resetearPassword::fin");

			return new ResponseEntity(new MensajeDto("Su contraseña ha sido enviada a su correo electrónico"),
					HttpStatus.OK);
		} else {
			trace = "No existe un usuario identificado con " + usuarioDto.getcValCorreo() + " en el sistema";
			logger.error("PublicoController::resetearPassword::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado para el envio de correos de mejora
	 * 
	 * @param mejoraDto
	 * @return Correo de notificación de mejora enviado con exito
	 */
	@PostMapping("/enviarCorreoMejora")
	public ResponseEntity<String> enviarCorreoMejora(@Valid @RequestBody MejoraDto mejoraDto) {
		logger.debug("PublicoController::enviarCorreoMejora:inicio");
		String trace;
		ParametroDto parametroTenor = parametroService
				.buscarPorcValClaveParam(ParametroClaveEnum.TENOR_NOTIFICACION_MEJORA.name());

		ParametroDto parametroCorreo = parametroService
				.buscarPorcValClaveParam(ParametroClaveEnum.SEND_MAIL_MEJORA.name());

		String tenorCorreo = String.format(parametroTenor.getcValValorParam(), mejoraDto.getcVistaMejora(),
				mejoraDto.getcProblemaMejora(), mejoraDto.getcSugerenciaMejora(), mejoraDto.getcComunicacionMejora(),
				mejoraDto.getcCorreoMejora());

		try {
			correoService.enviarCorreoHTML(parametroCorreo.getcValValorParam(),
					"Notificación de Mejora - Red de Compradores OSCE", tenorCorreo);
		} catch (MessagingException e) {
			trace = "Error al enviar correo de notificación de mejora";
			logger.error("PublicoController::enviarCorreoMejora:" + trace + e.getMessage(), e);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		logger.debug("PublicoController::enviarCorreoMejora:fin");
		return new ResponseEntity(new MensajeDto("Correo de notificación de mejora enviado con exito"), HttpStatus.OK);
	}
}
