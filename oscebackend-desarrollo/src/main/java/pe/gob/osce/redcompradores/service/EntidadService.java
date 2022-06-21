package pe.gob.osce.redcompradores.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.dto.EntidadDto;
import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.respository.EntidadRepository;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de las entidades
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
public class EntidadService {

	@Autowired
	private EntidadRepository entidadRespository;

	/**
	 * Metodo encargado de listar entidades activas
	 * 
	 * @return List<EntidadDto>
	 */
	public List<EntidadDto> listarEntidad() {
		List<MprEntidad> list = entidadRespository.findAll();
		List<EntidadDto> listDto = new ArrayList<>();
		EntidadDto entidadDto = null;
		for (MprEntidad mprEntidad : list) {
			entidadDto = new EntidadDto();
			BeanUtils.copyPropertiesEntidad(entidadDto, mprEntidad);
			listDto.add(entidadDto);
		}
		return listDto;
	}

	/**
	 * Metodo encargado de guardar informacion de una entidad
	 * 
	 * @param entidad
	 */
	public void guardarEntidad(MprEntidad entidad) {
		entidadRespository.save(entidad);
	}

	/**
	 * Metodo encargado de buscar una entidad por id
	 * 
	 * @param id
	 * @return MprEntidad
	 */
	public MprEntidad buscarPorId(BigDecimal id) {
		Optional<MprEntidad> entidadOptional = entidadRespository.findById(id);
		if (entidadOptional.isPresent()) {
			return entidadOptional.get();
		} else {
			return null;
		}
	}

	/**
	 * Metodo encargado de buscar una entidad por id
	 * 
	 * @param id
	 * @return EntidadDto
	 */
	public EntidadDto buscarEntidadDtoPorId(BigDecimal id) {
		Optional<MprEntidad> entidadOptional = entidadRespository.findById(id);
		if (entidadOptional.isPresent()) {
			MprEntidad entidad = entidadOptional.get();
			EntidadDto entidadDto = new EntidadDto();
			BeanUtils.copyPropertiesEntidad(entidadDto, entidad);
			return entidadDto;
		} else {
			return null;
		}
	}

	/**
	 * Metodo encargado de eliminar una entidad por id
	 * 
	 * @param id
	 */
	public void eliminarEntidad(BigDecimal id) {
		entidadRespository.deleteById(id);
	}

	/**
	 * Metodo encargado de verificar la existencia de una entidad por nombre de
	 * dominio
	 * 
	 * @param cNomDominio
	 * @return boolean
	 */
	public boolean existePorcNomDominio(String cNomDominio) {
		return entidadRespository.existsBycNomDominio(cNomDominio);
	}

	/**
	 * Metodo encargado de verificar la existencia de una entidad por id
	 * 
	 * @param nIdEntidad
	 * @return boolean
	 */
	public boolean existePorcIdEntidad(BigDecimal nIdEntidad) {
		return entidadRespository.existsBynIdEntidad(nIdEntidad);
	}
}
