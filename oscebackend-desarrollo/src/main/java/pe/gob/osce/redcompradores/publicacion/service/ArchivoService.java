package pe.gob.osce.redcompradores.publicacion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.enums.ParametroGrupoEnum;
import pe.gob.osce.redcompradores.publicacion.dto.ArchivoDto;
import pe.gob.osce.redcompradores.publicacion.emtity.TblArchivo;
import pe.gob.osce.redcompradores.publicacion.repository.ArchivoRepository;
import pe.gob.osce.redcompradores.respository.ParametroRepository;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de los archivos de publicaciones
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
public class ArchivoService {

	@Autowired
	private ArchivoRepository archivoRepository;

	@Autowired
	private ParametroRepository parametroRespository;

	/**
	 * Metodo encargado de guardar la informacion de los
	 * archivos de las publicaciones
	 * @param tblArchivo
	 */
	public void guardarArchivo(TblArchivo tblArchivo) {
		archivoRepository.save(tblArchivo);
	}

	/**
	 * Metodo encargado de verificar la existencia de un
	 * registro de archivo por id
	 * @param nIdArchivo
	 * @return boolean
	 */
	public boolean existePornIdArchivo(BigDecimal nIdArchivo) {
		return archivoRepository.existsBynIdArchivo(nIdArchivo);
	}

	/**
	 * Metodo encargado de buscar un registro de archivo por id
	 * @param nIdArchivo
	 * @return TblArchivo
	 */
	public TblArchivo buscarPornIdArchivo(BigDecimal nIdArchivo) {
		Optional<TblArchivo> archivoOptional = archivoRepository.findById(nIdArchivo);
		
		if (archivoOptional.isPresent()) {
			return archivoOptional.get();
		}
		else {
			return null;
		}
	}

	/**
	 * Metodo encargado de listar los archivos de las publicaciones 
	 * activas
	 * @return List<ArchivoDto> 
	 */
	public List<ArchivoDto> listarArchivos() {
		List<TblArchivo> listaArchivo = archivoRepository.findByPublicacionActiva();
		List<ArchivoDto> listaFinal = new ArrayList<ArchivoDto>();
		ArchivoDto archivoDto;

		for (TblArchivo tblArchivo : listaArchivo) {
			archivoDto = new ArchivoDto();
			BeanUtils.copyPropertiesArchivo(archivoDto, tblArchivo);
			this.buildTemaArchivo(archivoDto);
			listaFinal.add(archivoDto);
		}
		return listaFinal;
	}

	/**
	 * Metodo encargado de setear la descripcion del tema
	 * al registro de archivo
	 * @param archivoDto
	 */
	public void buildTemaArchivo(ArchivoDto archivoDto) {
		List<ParParametro> listaParametros = parametroRespository
				.findBycValGrupoParam(ParametroGrupoEnum.TEMA_SISTEMA.name());

		for (ParParametro parParametro : listaParametros) {
			if (archivoDto.getcCodTema().trim().equalsIgnoreCase(parParametro.getCValValorParam())) {
				archivoDto.setcDesTema(parParametro.getCValEtiquetaParam());
				break;
			}
		}
	}
}
