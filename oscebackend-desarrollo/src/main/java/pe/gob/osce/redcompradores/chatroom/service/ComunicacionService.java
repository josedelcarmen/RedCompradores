package pe.gob.osce.redcompradores.chatroom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.chatroom.dto.ComunicacionDto;
import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacion;
import pe.gob.osce.redcompradores.chatroom.repository.ComunicacionRepository;
import pe.gob.osce.redcompradores.entity.ParParametro;
import pe.gob.osce.redcompradores.enums.ParametroClaveEnum;
import pe.gob.osce.redcompradores.respository.ParametroRepository;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de las comunicaciones del chatroom
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
public class ComunicacionService {

	@Autowired
	private ComunicacionRepository comunicacionRepository;

	@Autowired
	private ParametroRepository parametroRespository;

	/**
	 * Metodo encargado listar los mensajes del chatroom
	 * @return List<ComunicacionDto>
	 */
	public List<ComunicacionDto> listarMensajesLimit() {
		Optional<ParParametro> parametro = parametroRespository
				.findBycValClaveParam(ParametroClaveEnum.LIMIT_MESSAGE_CHATROOM.name());
		Pageable filterTop;
		List<TblComunicacion> lista;
		
		if (parametro.isPresent()) {
			ParParametro parParametro = parametro.get();
			BigDecimal limitList = new BigDecimal(parParametro.getCValValorParam());
			filterTop = PageRequest.of(0, limitList.intValue(), Sort.by("nIdComunicacion").descending());
			Page<TblComunicacion> page = comunicacionRepository.findAll(filterTop);
			lista = page.getContent();
		}
		else {
			lista = comunicacionRepository.findAll();
		}
		List<ComunicacionDto> listaDto = new ArrayList<ComunicacionDto>();
		ComunicacionDto comunicacionDto;
		for (TblComunicacion tblComunicacion : lista) {
			comunicacionDto = new ComunicacionDto();
			BeanUtils.copyPropertiesComunicacion(comunicacionDto, tblComunicacion);
			listaDto.add(comunicacionDto);
		}
		return listaDto;
	}

	/**
	 * Metodo encargado de guardar la informacion de un 
	 * mensaje del chatroom
	 * @param entity
	 */
	public void guardarComunicacion(TblComunicacion entity) {
		this.comunicacionRepository.save(entity);
	}
}
