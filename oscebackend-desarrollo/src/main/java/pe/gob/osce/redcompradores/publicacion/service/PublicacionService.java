package pe.gob.osce.redcompradores.publicacion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.ParametroClaveEnum;
import pe.gob.osce.redcompradores.publicacion.dto.PublicacionDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.publicacion.repository.CalificacionRepository;
import pe.gob.osce.redcompradores.publicacion.repository.PublicacionRepository;
import pe.gob.osce.redcompradores.respository.ParametroRepository;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.service.AuditoriaService;
import pe.gob.osce.redcompradores.service.FileStorageService;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de las publicaciones
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
public class PublicacionService {

	private static final AuditoriaService logger = new AuditoriaService(PublicacionService.class);

	@Autowired
	private PublicacionRepository publicacionRepository;

	@Autowired
	private CalificacionRepository calificacionRepository;

	@Autowired
	private ParametroRepository parametroRespository;

	@Autowired
	private FileStorageService fileService;

	/**
	 * Metodo encargado de guardar un registro de una publicacion
	 * 
	 * @param tblPublicacion
	 */
	public void guardarPublicacion(TblPublicacion tblPublicacion) {
		this.publicacionRepository.save(tblPublicacion);
	}

	/**
	 * Metodo encargado de retornar una publicacion buscada por id
	 * 
	 * @param nIdPublicacion
	 * @return TblPublicacion
	 */
	public TblPublicacion buscarPornIdPublicacion(BigDecimal nIdPublicacion) {
		Optional<TblPublicacion> publicacionOptional = publicacionRepository.findById(nIdPublicacion);
		if (publicacionOptional.isPresent()) {
			return publicacionOptional.get();
		} else {
			return null;
		}
	}

	/**
	 * Metodo encargado de listar publicaciones activas
	 * 
	 * @return List<PublicacionDto>
	 */
	public List<PublicacionDto> listarPublicaciones() {
		Optional<ParParametro> parametroOptional = parametroRespository
				.findBycValClaveParam(ParametroClaveEnum.LIMIT_MESSAGE_PUBLICACION.name());
		Pageable filterTop;
		List<TblPublicacion> listaPublicacion;

		if (parametroOptional.isPresent()) {
			ParParametro parParametro = parametroOptional.get();
			BigDecimal limitList = new BigDecimal(parParametro.getCValValorParam());
			filterTop = PageRequest.of(0, limitList.intValue(), Sort.by("nIdPublicacion").descending());

			Page<TblPublicacion> page = publicacionRepository
					.findBynValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()), filterTop);
			listaPublicacion = page.getContent();
		} else {
			listaPublicacion = publicacionRepository.findBynValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()));
		}

		List<PublicacionDto> listaFinal = new ArrayList<PublicacionDto>();
		PublicacionDto publicacionDto;

		for (TblPublicacion tblPublicacion : listaPublicacion) {
			publicacionDto = new PublicacionDto();
			BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
			try {
				publicacionDto
						.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
			} catch (Exception e) {
				logger.error("PublicacionService::listarPublicaciones::Se produjo el error:: " + e.getMessage(), e);
			}
			this.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
			listaFinal.add(publicacionDto);
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de listar publicaciones activas por usuario
	 * 
	 * @param mprUsuario
	 * @return List<PublicacionDto>
	 */
	public List<PublicacionDto> listarPublicacionesByUsuario(MprUsuario mprUsuario) {

		Optional<ParParametro> parametroOptional = parametroRespository
				.findBycValClaveParam(ParametroClaveEnum.LIMIT_MESSAGE_PUBLICACION.name());
		Pageable filterTop;
		List<TblPublicacion> listaPublicacion;
		if (parametroOptional.isPresent()) {
			ParParametro parParametro = parametroOptional.get();

			BigDecimal limitList = new BigDecimal(parParametro.getCValValorParam());
			filterTop = PageRequest.of(0, limitList.intValue(), Sort.by("nIdPublicacion").descending());
			Page<TblPublicacion> page = publicacionRepository.findBynValEstadoAndMprUsuario(
					new BigDecimal(EstadosEnum.ACTIVO.getValue()), mprUsuario, filterTop);

			listaPublicacion = page.getContent();
		} else {
			listaPublicacion = publicacionRepository
					.findBynValEstadoAndMprUsuario(new BigDecimal(EstadosEnum.ACTIVO.getValue()), mprUsuario);
		}

		List<PublicacionDto> listaFinal = new ArrayList<PublicacionDto>();
		PublicacionDto publicacionDto;

		for (TblPublicacion tblPublicacion : listaPublicacion) {
			publicacionDto = new PublicacionDto();
			BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
			try {
				publicacionDto
						.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
			} catch (Exception e) {
				logger.error(
						"PublicacionService::listarPublicacionesByUsuario::Se produjo el error:: " + e.getMessage(), e);
			}
			this.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
			listaFinal.add(publicacionDto);
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de listar publicaciones activas filtradas por temas y/o
	 * palabras claves
	 * 
	 * @param temas
	 * @param palabraClave
	 * @return List<PublicacionDto>
	 */
	public List<PublicacionDto> listarPublicacionesFiltro(List<String> temas, String palabraClave) {

		Optional<ParParametro> parametroOptional = parametroRespository
				.findBycValClaveParam(ParametroClaveEnum.LIMIT_MESSAGE_PUBLICACION.name());
		Pageable filterTop;
		List<TblPublicacion> listaPublicacion;
		Page<TblPublicacion> page;

		if (parametroOptional.isPresent()) {
			ParParametro parParametro = parametroOptional.get();
			BigDecimal limitList = new BigDecimal(parParametro.getCValValorParam());
			filterTop = PageRequest.of(0, limitList.intValue(), Sort.by("nIdPublicacion").descending());

			listaPublicacion = new ArrayList<TblPublicacion>();

		} else {
			filterTop = PageRequest.of(0, 100, Sort.by("nIdPublicacion").descending());
		}

		if (temas != null && palabraClave == null) {
			page = publicacionRepository.findBynValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()), filterTop);
			List<TblPublicacion> lista = page.getContent();
			listaPublicacion = this.filtrarTemas(temas, lista);
		} else if (temas == null && palabraClave != null) {
			page = publicacionRepository.findBynValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()), filterTop);
			List<TblPublicacion> lista = page.getContent();
			listaPublicacion = this.filtrarPalabrasClaves(palabraClave, lista);
		} else if (temas != null && palabraClave != null) {
			page = publicacionRepository.findByFilterTema(temas, new BigDecimal(EstadosEnum.ACTIVO.getValue()),
					filterTop);
			List<TblPublicacion> lista = page.getContent();
			List<TblPublicacion> listaTemp = this.filtrarTemas(temas, lista);
			listaPublicacion = this.filtrarPalabrasClaves(palabraClave, listaTemp);
		} else {
			page = publicacionRepository.findBynValEstado(new BigDecimal(EstadosEnum.ACTIVO.getValue()), filterTop);
			listaPublicacion = page.getContent();
		}

		List<PublicacionDto> listaFinal = new ArrayList<PublicacionDto>();
		PublicacionDto publicacionDto;

		for (TblPublicacion tblPublicacion : listaPublicacion) {
			publicacionDto = new PublicacionDto();
			BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
			try {
				publicacionDto
						.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
			} catch (Exception e) {
				logger.error("PublicacionService::listarPublicacionesFiltro::Se produjo el error:: " + e.getMessage(),
						e);
			}
			this.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
			listaFinal.add(publicacionDto);
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de listar publicaciones activas de un usuario filtradas por
	 * temas y/o palabras claves
	 * 
	 * @param mprUsuario
	 * @param temas
	 * @param palabraClave
	 * @return List<PublicacionDto>
	 */
	public List<PublicacionDto> listarPublicacionesFiltroPorUsuario(MprUsuario mprUsuario, List<String> temas,
			String palabraClave) {

		Optional<ParParametro> parametroOptional = parametroRespository
				.findBycValClaveParam(ParametroClaveEnum.LIMIT_MESSAGE_PUBLICACION.name());
		Pageable filterTop;
		List<TblPublicacion> listaPublicacion;
		Page<TblPublicacion> page;

		if (parametroOptional.isPresent()) {
			ParParametro parParametro = parametroOptional.get();
			BigDecimal limitList = new BigDecimal(parParametro.getCValValorParam());
			filterTop = PageRequest.of(0, limitList.intValue(), Sort.by("nIdPublicacion").descending());
			listaPublicacion = new ArrayList<TblPublicacion>();

		} else {
			filterTop = PageRequest.of(0, 100, Sort.by("nIdPublicacion").descending());
		}

		if (temas != null && palabraClave == null) {
			page = publicacionRepository.findBynValEstadoAndMprUsuario(new BigDecimal(EstadosEnum.ACTIVO.getValue()),
					mprUsuario, filterTop);
			List<TblPublicacion> lista = page.getContent();
			listaPublicacion = this.filtrarTemas(temas, lista);
		} else if (temas == null && palabraClave != null) {
			page = publicacionRepository.findBynValEstadoAndMprUsuario(new BigDecimal(EstadosEnum.ACTIVO.getValue()),
					mprUsuario, filterTop);
			List<TblPublicacion> lista = page.getContent();
			listaPublicacion = this.filtrarPalabrasClaves(palabraClave, lista);
		} else if (temas != null && palabraClave != null) {
			page = publicacionRepository.findBynValEstadoAndMprUsuario(new BigDecimal(EstadosEnum.ACTIVO.getValue()),
					mprUsuario, filterTop);
			List<TblPublicacion> lista = page.getContent();
			List<TblPublicacion> listaTemp = this.filtrarTemas(temas, lista);
			listaPublicacion = this.filtrarPalabrasClaves(palabraClave, listaTemp);
		} else {
			page = publicacionRepository.findBynValEstadoAndMprUsuario(new BigDecimal(EstadosEnum.ACTIVO.getValue()),
					mprUsuario, filterTop);
			listaPublicacion = page.getContent();
		}

		List<PublicacionDto> listaFinal = new ArrayList<PublicacionDto>();
		PublicacionDto publicacionDto;

		for (TblPublicacion tblPublicacion : listaPublicacion) {
			publicacionDto = new PublicacionDto();
			BeanUtils.copyPropertiesPublicacion(publicacionDto, tblPublicacion);
			try {
				publicacionDto
						.setbFotoPerfil(fileService.contentOfFotoPerfil(tblPublicacion.getMprUsuario().getCDirFoto()));
			} catch (Exception e) {
				logger.error("PublicacionService::listarPublicacionesFiltroPorUsuario::Se produjo el error:: "
						+ e.getMessage(), e);
			}
			this.buildCalificacionPublicacion(publicacionDto, tblPublicacion);
			listaFinal.add(publicacionDto);
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de setear las calificaciones likes o disslikes de una
	 * publicacion
	 * 
	 * @param publicacionDto
	 * @param tblPublicacion
	 */
	public void buildCalificacionPublicacion(PublicacionDto publicacionDto, TblPublicacion tblPublicacion) {
		Integer cantLikes = calificacionRepository
				.countLikesBynIdPublicacion(tblPublicacion.getNIdPublicacion()) != null
						? calificacionRepository.countLikesBynIdPublicacion(tblPublicacion.getNIdPublicacion())
						: 0;

		Integer cantDisslikes = calificacionRepository
				.countDisslikesBynIdPublicacion(tblPublicacion.getNIdPublicacion()) != null
						? calificacionRepository.countDisslikesBynIdPublicacion(tblPublicacion.getNIdPublicacion())
						: 0;
		publicacionDto.setnCantLikes(new BigDecimal(cantLikes));
		publicacionDto.setnCantDisslikes(new BigDecimal(cantDisslikes));
	}

	/**
	 * Metodo encargado de filtrar las listas de publicaciones mediante palabras
	 * claves
	 * 
	 * @param palabrasClaves
	 * @param listaPublicacion
	 * @return List<TblPublicacion>
	 */
	public List<TblPublicacion> filtrarPalabrasClaves(String palabrasClaves, List<TblPublicacion> listaPublicacion) {
		String[] listaSplit = palabrasClaves.split(",");
		List<TblPublicacion> listaFinal = new ArrayList<TblPublicacion>();

		for (TblPublicacion tblPublicacion : listaPublicacion) {
			List<String> listaTemp = Arrays.asList(
					tblPublicacion.getCValClavePub().trim().toLowerCase().replaceAll("[\\[\\](){}]", "").split(","));
			for (String palabraFiltro : listaSplit) {
				if (listaTemp.contains(palabraFiltro.trim().toLowerCase())) {
					listaFinal.add(tblPublicacion);
					break;
				}
			}
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de filtrar las listas de publicaciones mediante temas
	 * 
	 * @param temas
	 * @param listaPublicacion
	 * @return List<TblPublicacion>
	 */
	public List<TblPublicacion> filtrarTemas(List<String> temas, List<TblPublicacion> listaPublicacion) {
		List<TblPublicacion> listaFinal = new ArrayList<TblPublicacion>();
		for (TblPublicacion tblPublicacion : listaPublicacion) {
			for (String temaFiltro : temas) {
				if (tblPublicacion.getCCodTema().trim().equalsIgnoreCase(temaFiltro)) {
					listaFinal.add(tblPublicacion);
					break;
				}
			}
		}
		return listaFinal;
	}
}
