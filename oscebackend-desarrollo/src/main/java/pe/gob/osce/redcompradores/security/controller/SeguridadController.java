package pe.gob.osce.redcompradores.security.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osce.redcompradores.dto.MensajeDto;
import pe.gob.osce.redcompradores.security.dto.JwtDto;
import pe.gob.osce.redcompradores.security.dto.LoginUsuario;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.entity.UsuarioPrincipal;
import pe.gob.osce.redcompradores.security.jwt.JwtProvider;
import pe.gob.osce.redcompradores.security.service.UsuarioService;

/**
 * Controlador de seguridad del sistema, aqui se valida y genera el token,
 * roles, permisos, etc.
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
@RequestMapping("/seguridad")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SeguridadController {

	private final static Logger logger = LoggerFactory.getLogger(SeguridadController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Metodo de autenticacion del sistema
	 * 
	 * @param loginUsuario
	 * @param bindingResult
	 * @return JwtDto
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String trace = "Trama de consulta mal estructurada";
			logger.error("SeguridadController::login::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(loginUsuario.getUsername());
		if (usuarioOptional.isPresent()) {
			MprUsuario mprUsuario = usuarioOptional.get();
			if (mprUsuario != null) {
				if (mprUsuario.getNValEstado().intValue() == 0) {
					String trace = "El usuario se encuentra inactivo. Por favor solicite su activación a su administrador.";
					logger.error("SeguridadController::login::" + trace);
					return new ResponseEntity(new MensajeDto(trace), HttpStatus.UNAUTHORIZED);
				} else if (mprUsuario.getNValEstado().intValue() == 2) {
					String trace = "El usuario se encuentra suspendido. Por favor comuniquese con su administrador.";
					logger.error("SeguridadController::login::" + trace);
					return new ResponseEntity(new MensajeDto(trace), HttpStatus.UNAUTHORIZED);
				} else if (mprUsuario.getNValEstado().intValue() == 9) {
					String trace = "El usuario se encuentra de baja. Por favor comuniquese con su administrador.";
					logger.error("SeguridadController::login::" + trace);
					return new ResponseEntity(new MensajeDto(trace), HttpStatus.UNAUTHORIZED);
				}
			}
		} else {
			String trace = "Bad credentials";
			logger.error("SeguridadController::login::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.UNAUTHORIZED);
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UsuarioPrincipal userDetails = (UsuarioPrincipal) authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(), userDetails.getRol(),
				userDetails.getNombreUsuario(), userDetails.getnIdUsuario(), userDetails.getnIdEntidad(),
				userDetails.getcDesEntidad(), userDetails.getnValCalificacion(), userDetails.getcDirFoto());

		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}
}
