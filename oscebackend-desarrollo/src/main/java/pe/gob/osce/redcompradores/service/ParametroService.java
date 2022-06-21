package pe.gob.osce.redcompradores.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.dto.ParametroDto;
import pe.gob.osce.redcompradores.dto.PreguntaFrecuenteDto;
import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.enums.EstadosEnum;
import pe.gob.osce.redcompradores.enums.ParametroGrupoEnum;
import pe.gob.osce.redcompradores.respository.ParametroRepository;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de los parametros
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
public class ParametroService {

	@Autowired
	private ParametroRepository parametroRespository;

	/**
	 * Metodo encargado de listar los parametros activos
	 * @return List<ParametroDto>
	 */
	public List<ParametroDto> listarParametros() {
		List<ParParametro> list = parametroRespository.findAll();
		List<ParametroDto> listDto = new ArrayList<>();
		ParametroDto parametroDto = null;
		for (ParParametro parParametro : list) {
			parametroDto = new ParametroDto();
			BeanUtils.copyPropertiesParametros(parametroDto, parParametro);
			listDto.add(parametroDto);
		}
		return listDto;
	}

	/**
	 * Metodo encargado de guardar un nuevo parametro
	 * @param parametro
	 */
	public void guardarParametro(ParParametro parametro) {
		parametroRespository.save(parametro);
	}

	/**
	 * Metodo encargado de eliminar un parametro
	 * @param id
	 */
	public void eliminarParametro(BigDecimal id) {
		parametroRespository.deleteById(id);
	}

	/**
	 * Metodo encargado de buscar un parametro por id
	 * @param id
	 * @return ParParametro
	 */
	public ParParametro buscarPorId(BigDecimal id) {
		Optional<ParParametro> parametroOptional = parametroRespository.findById(id);
		if (parametroOptional.isPresent()) {
			return parametroOptional.get();
		}
		else {
			return null;
		}
	}

	/**
	 * Metodo encargado de buscar un parametro por clave
	 * @param cValClaveParam
	 * @return ParParametro
	 */
	public ParametroDto buscarPorcValClaveParam(String cValClaveParam) {
		Optional<ParParametro> parametroOptional = parametroRespository.findBycValClaveParam(cValClaveParam);
		if (parametroOptional.isPresent()) {
			ParParametro parParametro = parametroOptional.get();
			ParametroDto parametroDto = new ParametroDto();
			BeanUtils.copyPropertiesParametros(parametroDto, parParametro);
			return parametroDto;
		}
		else {
			return null;
		}		
	}

	/**
	 * Metodo encargado de buscar parametros agrupados
	 * @param cValGrupoParam
	 * @return List<ParametroDto> 
	 */
	public List<ParametroDto> buscarPorcValGrupoParam(String cValGrupoParam) {
		List<ParParametro> list = parametroRespository.findBycValGrupoParam(cValGrupoParam);
		List<ParametroDto> listDto = new ArrayList<>();
		ParametroDto parametroDto = null;
		for (ParParametro parParametro : list) {
			parametroDto = new ParametroDto();
			BeanUtils.copyPropertiesParametros(parametroDto, parParametro);
			listDto.add(parametroDto);
		}
		return listDto;
	}

	/**
	 * Metodo encargado de buscar un grupo de parametros por id padre
	 * @param cCodPadreParam
	 * @return List<ParametroDto> 
	 */
	public List<ParametroDto> buscarPorcCodPadreParam(String cCodPadreParam) {
		List<ParParametro> list = parametroRespository.findBycCodPadreParam(cCodPadreParam);
		List<ParametroDto> listDto = new ArrayList<>();
		ParametroDto parametroDto = null;
		for (ParParametro parParametro : list) {
			parametroDto = new ParametroDto();
			BeanUtils.copyPropertiesParametros(parametroDto, parParametro);
			listDto.add(parametroDto);
		}
		return listDto;
	}

	/**
	 * Metodo encargado de listar las preguntas frecuentes
	 * @return List<PreguntaFrecuenteDto>
	 */
	public List<PreguntaFrecuenteDto> listarPreguntasFrecuentes() {

		List<ParametroDto> listaParametro = this.buscarPorcValGrupoParam(ParametroGrupoEnum.PREGUNTA_FRECUENTE.name());
		Collections.sort(listaParametro, (x, y) -> x.getnValOrdenParam().compareTo(y.getnValOrdenParam()));
		List<PreguntaFrecuenteDto> listaDto = new ArrayList<PreguntaFrecuenteDto>();
		PreguntaFrecuenteDto preguntaDto;

		for (ParametroDto parParametro : listaParametro) {
			if (parParametro.getnValEstado().intValue() == EstadosEnum.ACTIVO.getValue()
					&& parParametro.getcCodPadreParam() == null) {
				preguntaDto = new PreguntaFrecuenteDto();
				preguntaDto.setIdPregunta(parParametro.getnIdParametro().toString());
				preguntaDto.setPregunta(parParametro.getcValValorParam());
				listaDto.add(preguntaDto);
			}
		}

		for (ParametroDto parParametro : listaParametro) {
			if (parParametro.getnValEstado().intValue() == EstadosEnum.ACTIVO.getValue()
					&& parParametro.getcCodPadreParam() != null) {
				for (PreguntaFrecuenteDto respuestaDto : listaDto) {
					if (parParametro.getcCodPadreParam().equals(respuestaDto.getIdPregunta())) {
						respuestaDto.setIdRespuesta(parParametro.getnIdParametro().toString());
						respuestaDto.setRespuesta(parParametro.getcValValorParam());
					}
				}
			}
		}
		return listaDto;
	}

	/**
	 * Metodo encargado de verificar si un parametro existe con una clave
	 * @param cValClaveParam
	 * @return boolean
	 */
	public boolean existePorcValClaveParam(String cValClaveParam) {
		return parametroRespository.existsBycValClaveParam(cValClaveParam);
	}

	/**
	 * Metodo encargado de verificar si un parametro existe por un id
	 * @param idParametro
	 * @return boolean
	 */
	public boolean existePornIdParametro(BigDecimal idParametro) {
		return parametroRespository.existsById(idParametro);
	}
}
