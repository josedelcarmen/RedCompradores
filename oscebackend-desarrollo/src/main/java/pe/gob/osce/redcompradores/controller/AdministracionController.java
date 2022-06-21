package pe.gob.osce.redcompradores.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osce.redcompradores.dto.EntidadDto;
import pe.gob.osce.redcompradores.dto.MensajeDto;
import pe.gob.osce.redcompradores.dto.ParametroDto;
import pe.gob.osce.redcompradores.dto.PreguntaFrecuenteDto;
import pe.gob.osce.redcompradores.dto.ReporteDto;
import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.entity.VwParticipacionAnio;
import pe.gob.osce.redcompradores.entity.VwParticipacionDia;
import pe.gob.osce.redcompradores.entity.VwParticipacionMes;
import pe.gob.osce.redcompradores.entity.VwParticipacionUsuario;
import pe.gob.osce.redcompradores.entity.VwPublicacionFrecu;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.EntidadService;
import pe.gob.osce.redcompradores.service.ParametroService;
import pe.gob.osce.redcompradores.service.ReportesService;

/**
 * Controlador Rest Administracion, aqui se procesan las funcionalidades propias
 * de entidades, parametros, preguntas frecuentes y reportes
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
@RequestMapping("/administracion")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdministracionController {

	private static final AuditoriaService logger = new AuditoriaService(AdministracionController.class);

	@Autowired
	private EntidadService entidadService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private ReportesService reportesService;

	/**
	 * Metodo que lista las entidades activas del sistema
	 * 
	 * @return List<EntidadDto>
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listarEntidad")
	public ResponseEntity<List<EntidadDto>> listarEntidad() {

		logger.debug("AdministracionController::listarEntidad::Inicio");
		List<EntidadDto> listaEntidades = entidadService.listarEntidad();
		logger.debug("AdministracionController::listarEntidad::Se listaron exitosamente " + listaEntidades.size()
				+ " entidades");
		logger.debug("AdministracionController::listarEntidad::Fin");
		return new ResponseEntity<List<EntidadDto>>(listaEntidades, HttpStatus.OK);
	}

	/**
	 * Metodo que busca una entidad por su Id
	 * 
	 * @param entidadDto
	 * @return EntidadDto
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_ENTIDAD')")
	@PostMapping("/buscarEntidadPorId")
	public ResponseEntity<EntidadDto> buscarEntidadPorId(@Valid @RequestBody EntidadDto entidadDto) {
		logger.debug("AdministracionController::buscarEntidadPorId::Inicio");
		String trace;

		if (entidadService.existePorcIdEntidad(entidadDto.getnIdEntidad())) {
			logger.debug("AdministracionController::buscarEntidadPorId::Se encontro la entidad con id: "
					+ entidadDto.getnIdEntidad());
			EntidadDto dto = entidadService.buscarEntidadDtoPorId(entidadDto.getnIdEntidad());
			return new ResponseEntity<EntidadDto>(dto, HttpStatus.OK);
		} else {
			trace = "Entidad no encontrada con id: " + entidadDto.getnIdEntidad();
			logger.error("AdministracionController::buscarEntidadPorId::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo encargado de crear una nueva entidad
	 * 
	 * @param entidadDto
	 * @return Se creo exitosamente la entidad : OSCE
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crearEntidad")
	public ResponseEntity<String> crearEntidad(@Valid @RequestBody EntidadDto entidadDto) {
		logger.debug("AdministracionController::crearEntidad::inicio");
		String trace;

		if (entidadService.existePorcNomDominio(entidadDto.getcNomDominio())) {
			trace = "El dominio " + entidadDto.getcNomDominio() + " ya se encuentra registrado";
			logger.error("AdministracionController::crearEntidad::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		MprEntidad entidad = new MprEntidad();
		entidad.setCDesEntidad(entidadDto.getcDesEntidad());
		entidad.setCNomDominio(entidadDto.getcNomDominio());
		entidad.setNValValidaUsuario(entidadDto.getnValValidaUsuario());
		entidad.setNValEstado(entidadDto.getnValEstado());
		entidad.setCValUsuarioCrea(entidadDto.getcValUsuarioCrea());
		entidad.setDFecFechaCrea(new Date());

		entidadService.guardarEntidad(entidad);

		logger.debug("AdministracionController::crearEntidad::Se creo exitosamente la entidad : "
				+ entidadDto.getcDesEntidad());
		logger.debug("AdministracionController::crearEntidad::fin");
		return new ResponseEntity(new MensajeDto("Entidad creada con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de editar una entidad
	 * 
	 * @param entidadDto
	 * @return String Se actualizó exitosamente la entidad con id: 1
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/editarEntidad")
	public ResponseEntity<String> editarEntidad(@Valid @RequestBody EntidadDto entidadDto) {
		logger.debug("AdministracionController::actualizarEntidad::inicio");
		String trace;

		if (!entidadService.existePorcIdEntidad(entidadDto.getnIdEntidad())) {
			trace = "Entidad no encontrada";
			logger.error("AdministracionController::actualizarEntidad::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		MprEntidad entidad = entidadService.buscarPorId(entidadDto.getnIdEntidad());
		entidad.setCDesEntidad(entidadDto.getcDesEntidad());
		entidad.setCNomDominio(entidadDto.getcNomDominio());
		entidad.setNValValidaUsuario(entidadDto.getnValValidaUsuario());
		entidad.setNValEstado(entidadDto.getnValEstado());
		entidad.setCValUsuarioActu(entidadDto.getcValUsuarioActu());
		entidad.setDFecFechaActu(new Date());

		entidadService.guardarEntidad(entidad);

		logger.debug("AdministracionController::actualizarEntidad::Se actualizó exitosamente la entidad con id: "
				+ entidadDto.getnIdEntidad());
		logger.debug("AdministracionController::actualizarEntidad::fin");
		return new ResponseEntity(new MensajeDto("Entidad actualizada con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado eliminar una entidad
	 * 
	 * @param entidadDto
	 * @return Se elimino exitosamente la entidad con id: 1
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/eliminarEntidad")
	public ResponseEntity<String> eliminarEntidad(@Valid @RequestBody EntidadDto entidadDto) {
		logger.debug("AdministracionController::eliminarEntidad::inicio");
		String trace;

		if (!entidadService.existePorcIdEntidad(entidadDto.getnIdEntidad())) {
			trace = "Entidad no encontrada";
			logger.error("AdministracionController::eliminarEntidad::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		MprEntidad entidad = entidadService.buscarPorId(entidadDto.getnIdEntidad());
		entidad.setNValEstado(new BigDecimal(0));
		entidad.setCValUsuarioActu("ADMIN");
		entidad.setDFecFechaActu(new Date());
		entidadService.guardarEntidad(entidad);

		logger.debug("AdministracionController::eliminarEntidad::Se elimino exitosamente la entidad con id: "
				+ entidad.getNIdEntidad());
		logger.debug("AdministracionController::eliminarEntidad::fin");
		return new ResponseEntity(new MensajeDto("Entidad eliminada con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de listar los parametros activos
	 * 
	 * @return List<ParametroDto>
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listarParametros")
	public ResponseEntity<List<ParametroDto>> listarParametros() {
		logger.debug("AdministracionController::listarParametros::inicio");
		List<ParametroDto> parametros = parametroService.listarParametros();
		logger.debug("AdministracionController::listarParametros::Se listaron exitosamente " + parametros.size()
				+ " parametros");
		logger.debug("AdministracionController::listarParametros::fin");
		return new ResponseEntity<List<ParametroDto>>(parametros, HttpStatus.OK);
	}

	/**
	 * Metodo encargado de crear un nuevo parametro
	 * 
	 * @param parametroDto
	 * @return String Se creo exitosamente el Parametro : 1
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/crearParametro")
	public ResponseEntity<String> crearParametro(@Valid @RequestBody ParametroDto parametroDto) {
		logger.debug("AdministracionController::crearParametro::inicio");
		String trace;

		if (parametroService.existePorcValClaveParam(parametroDto.getcValClaveParam())) {
			trace = "Ya existe un Parametro con clave: " + parametroDto.getcValClaveParam();
			logger.error("AdministracionController::crearParametro::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		ParParametro parametro = new ParParametro();
		parametro.setCValLargaParam(parametroDto.getcValLargaParam());
		parametro.setCValCortaParam(parametroDto.getcValCortaParam());
		parametro.setCValTipoParam(parametroDto.getcValTipoParam());
		parametro.setCValEtiquetaParam(parametroDto.getcValEtiquetaParam());
		parametro.setCValValorParam(parametroDto.getcValValorParam());

		parametro.setCValClaveParam(parametroDto.getcValClaveParam());
		parametro.setCValGrupoParam(parametroDto.getcValGrupoParam());
		parametro.setNValOrdenParam(parametroDto.getnValOrdenParam());
		parametro.setCCodPadreParam(parametroDto.getcCodPadreParam());
		parametro.setNValEstado(parametroDto.getnValEstado());
		parametro.setCValUsuarioCrea(parametroDto.getcValUsuarioCrea());
		parametro.setDFecFechaCrea(new Date());

		parametroService.guardarParametro(parametro);

		logger.debug("AdministracionController::crearParametro::Se creo exitosamente el Parametro : "
				+ parametro.getCValCortaParam());
		logger.debug("AdministracionController::crearParametro::fin");
		return new ResponseEntity(new MensajeDto("Parametro creado con exito"), HttpStatus.OK);

	}

	/**
	 * Metodo encargado de editar un parametro
	 * 
	 * @param parametroDto
	 * @return String Se actualizó exitosamente el Parametro con id: 1
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/editarParametro")
	public ResponseEntity<String> editarParametro(@Valid @RequestBody ParametroDto parametroDto) {
		logger.debug("AdministracionController::editarParametro::inicio");
		String trace;

		if (!parametroService.existePornIdParametro(parametroDto.getnIdParametro())) {
			trace = "Parametro no encontrado con id: " + parametroDto.getnIdParametro();
			logger.error("AdministracionController::editarParametro::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		ParParametro parametro = parametroService.buscarPorId(parametroDto.getnIdParametro());

		parametro.setCValCortaParam(parametroDto.getcValCortaParam());
		parametro.setCValLargaParam(parametroDto.getcValLargaParam());
		parametro.setCValEtiquetaParam(parametroDto.getcValEtiquetaParam());
		parametro.setCValValorParam(parametroDto.getcValValorParam());
		parametro.setNValOrdenParam(parametroDto.getnValOrdenParam());
		parametro.setCCodPadreParam(parametroDto.getcCodPadreParam());

		parametro.setNValEstado(parametroDto.getnValEstado());
		parametro.setCValUsuarioActu(parametroDto.getcValUsuarioActu());
		parametro.setDFecFechaActu(new Date());
		parametroService.guardarParametro(parametro);

		logger.debug("AdministracionController::editarParametro::Se actualizó exitosamente el Parametro con id: "
				+ parametro.getNIdParametro());
		logger.debug("AdministracionController::editarParametro::fin");

		return new ResponseEntity(new MensajeDto("Parametro actualizado con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo encargado de eliminar parametros
	 * 
	 * @param parametrosDto
	 * @return String Se elimino exitosamente el Parametro con id: 1
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/eliminarParametro")
	public ResponseEntity<String> eliminarParametro(@Valid @RequestBody ParametroDto parametrosDto) {
		logger.debug("AdministracionController::eliminarParametro::inicio");
		String trace;

		if (!parametroService.existePornIdParametro(parametrosDto.getnIdParametro())) {
			trace = "Parametro no encontrado con id: " + parametrosDto.getnIdParametro();
			logger.error("AdministracionController::eliminarParametro::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		ParParametro parametro = parametroService.buscarPorId(parametrosDto.getnIdParametro());
		parametro.setNValEstado(new BigDecimal(0));
		parametro.setCValUsuarioActu("ADMIN");
		parametro.setDFecFechaActu(new Date());
		parametroService.guardarParametro(parametro);

		logger.debug("AdministracionController::eliminarParametro::Se elimino exitosamente el Parametro con id: "
				+ parametrosDto.getnIdParametro());
		logger.debug("AdministracionController::eliminarParametro::fin");

		return new ResponseEntity(new MensajeDto("Parametro eliminado con exito"), HttpStatus.OK);
	}

	/**
	 * Metodo para buscar parametros por el atributo clave
	 * 
	 * @param parametrosDto : cValClaveParam = TYPE_VIDEO
	 * @return ParametroDto : cValValorParam = mp4
	 * @throws Exception
	 */
	@PostMapping("/buscarParametroPorClave")
	public ResponseEntity<ParametroDto> buscarParametroPorClave(@Valid @RequestBody ParametroDto parametrosDto) {
		logger.debug("AdministracionController::buscarPorClave::inicio");
		String trace;
		ParametroDto parametro = parametroService.buscarPorcValClaveParam(parametrosDto.getcValClaveParam());

		if (parametro == null) {
			trace = "Parametro con clave: " + parametrosDto.getcValClaveParam() + " no existe";
			logger.error("AdministracionController::buscarPorClave::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		} else if (parametro.getnValEstado().intValue() == 0) {
			trace = "Parametro con clave: " + parametrosDto.getcValClaveParam() + " esta inactivo";
			logger.error("AdministracionController::buscarPorClave::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}

		logger.debug(
				"AdministracionController::buscarPorClave::Se listó el parámetro: " + parametro.getcValCortaParam());
		logger.debug("AdministracionController::buscarPorClave::fin");
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
		logger.debug("AdministracionController::buscarPorGrupo::inicio");
		String trace;
		List<ParametroDto> parametros = parametroService.buscarPorcValGrupoParam(parametrosDto.getcValGrupoParam());

		if (parametros == null || parametros.size() == 0) {
			trace = "Parametros con grupo: " + parametrosDto.getcValGrupoParam() + " no existen";
			logger.error("AdministracionController::buscarPorGrupo::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		List<ParametroDto> listParametros = new ArrayList<ParametroDto>();
		for (ParametroDto parParametro : parametros) {
			if (parParametro.getnValEstado().intValue() == 1) {
				listParametros.add(parParametro);
			}
		}
		logger.debug("AdministracionController::buscarPorGrupo::Se listaron exitosamente " + listParametros.size()
				+ " parámetros activos");
		logger.debug("AdministracionController::buscarPorGrupo::fin");

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
		logger.debug("AdministracionController::buscarParametroPorPadre::inicio");
		String trace;
		List<ParametroDto> parametros = parametroService.buscarPorcCodPadreParam(parametrosDto.getcCodPadreParam());

		if (parametros == null || parametros.size() == 0) {
			trace = "Parametros con grupo: " + parametrosDto.getcValGrupoParam() + " no existen";
			logger.error("AdministracionController::buscarParametroPorPadre::" + trace);
			return new ResponseEntity(new MensajeDto(trace), HttpStatus.BAD_REQUEST);
		}
		List<ParametroDto> listParametros = new ArrayList<ParametroDto>();
		for (ParametroDto parParametro : parametros) {
			if (parParametro.getnValEstado().intValue() == 1) {
				listParametros.add(parParametro);
			}
		}
		logger.debug("AdministracionController::buscarParametroPorPadre::Se listaron exitosamente "
				+ listParametros.size() + " parámetros activos");
		logger.debug("AdministracionController::buscarParametroPorPadre::fin");

		return new ResponseEntity<List<ParametroDto>>(listParametros, HttpStatus.OK);
	}

	/**
	 * Metodo que lista las preguntas frecuentes configuradas en el sistema.
	 * 
	 * @return List<PreguntaFrecuenteDto>
	 * @throws Exception
	 */
	@GetMapping("/listarPreguntasFrecuentes")
	public ResponseEntity<List<PreguntaFrecuenteDto>> listarPreguntasFrecuentes() {
		logger.debug("AdministracionController::listarPreguntasFrecuentes::inicio");
		List<PreguntaFrecuenteDto> lista = parametroService.listarPreguntasFrecuentes();
		logger.debug("AdministracionController::listarPreguntasFrecuentes::Se listaron exitosamente " + lista.size()
				+ " preguntas frecuentes");
		logger.debug("AdministracionController::listarPreguntasFrecuentes::fin");

		return new ResponseEntity<List<PreguntaFrecuenteDto>>(lista, HttpStatus.OK);
	}

	/**
	 * Metodo que lista la informacion del reporte publicaciones frecuentes
	 * 
	 * @return List<VwPublicacionFrecu>
	 * @throws Exception
	 */
	@GetMapping("/listarPublicacionesFrecuentes")
	public ResponseEntity<List<VwPublicacionFrecu>> listarPublicacionesFrecuentes() {
		logger.debug("AdministracionController::listarPublicacionesFrecuentes::inicio");
		List<VwPublicacionFrecu> lista = reportesService.listarPublicacionesFrecuentes();
		logger.debug("AdministracionController::listarPublicacionesFrecuentes::fin");
		return new ResponseEntity<List<VwPublicacionFrecu>>(lista, HttpStatus.OK);
	}

	/**
	 * Metodo que lista la informacion de listado de Participacion de Usuario
	 * 
	 * @return List<VwParticipacionUsuario>
	 * @throws Exception
	 */
	@GetMapping("/listarParticipacionUsuario")
	public ResponseEntity<List<VwParticipacionUsuario>> listarParticipacionUsuario() {

		logger.debug("AdministracionController::listarParticipacionUsuario::inicio");
		List<VwParticipacionUsuario> lista = reportesService.listarParticipacionUsuario();
		logger.debug("AdministracionController::listarParticipacionUsuario::fin");

		return new ResponseEntity<List<VwParticipacionUsuario>>(lista, HttpStatus.OK);
	}

	/**
	 * Metodo que lista la cantidad de publicaciones por año
	 * 
	 * @return List<VwParticipacionAnio>
	 * @throws Exception
	 */
	@GetMapping("/listarParticipacionAnio")
	public ResponseEntity<List<VwParticipacionAnio>> listarParticipacionAnio() {

		logger.debug("AdministracionController::listarParticipacionAnio::inicio");
		List<VwParticipacionAnio> lista = reportesService.listarParticipacionAnio();
		logger.debug("AdministracionController::listarParticipacionAnio::fin");

		return new ResponseEntity<List<VwParticipacionAnio>>(lista, HttpStatus.OK);
	}

	/**
	 * Metodo que lista la cantidad de publicaciones de 1 mes por año
	 * 
	 * @param reporteDto
	 * @return List<VwParticipacionMes>
	 * @throws Exception
	 */
	@PostMapping("/listarParticipacionMes")
	public ResponseEntity<List<VwParticipacionMes>> listarParticipacionMes(@Valid @RequestBody ReporteDto reporteDto) {

		logger.debug("AdministracionController::listarParticipacionMes::inicio");
		List<VwParticipacionMes> lista = reportesService.listarParticipacionMes(reporteDto.getnAnioPub());
		logger.debug("AdministracionController::listarParticipacionMes::fin");

		return new ResponseEntity<List<VwParticipacionMes>>(lista, HttpStatus.OK);
	}

	/**
	 * Metodo que lista la cantidad de publicaciones de 1 dia por un mes
	 * 
	 * @param reporteDto
	 * @return List<VwParticipacionDia>
	 * @throws Exception
	 */
	@PostMapping("/listarParticipacionDia")
	public ResponseEntity<List<VwParticipacionDia>> listarParticipacionDia(@Valid @RequestBody ReporteDto reporteDto) {

		logger.debug("AdministracionController::listarParticipacionDia::inicio");
		List<VwParticipacionDia> lista = reportesService.listarParticipacionDia(reporteDto.getnAnioPub(),
				reporteDto.getnMesPub());
		logger.debug("AdministracionController::listarParticipacionDia::fin");

		return new ResponseEntity<List<VwParticipacionDia>>(lista, HttpStatus.OK);
	}

}
