package pe.gob.osce.redcompradores.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.entity.VwParticipacionAnio;
import pe.gob.osce.redcompradores.entity.VwParticipacionDia;
import pe.gob.osce.redcompradores.entity.VwParticipacionMes;
import pe.gob.osce.redcompradores.entity.VwParticipacionUsuario;
import pe.gob.osce.redcompradores.entity.VwPublicacionFrecu;
import pe.gob.osce.redcompradores.respository.VwParticipacionAnioRepository;
import pe.gob.osce.redcompradores.respository.VwParticipacionDiaRepository;
import pe.gob.osce.redcompradores.respository.VwParticipacionMesRepository;
import pe.gob.osce.redcompradores.respository.VwParticipacionUsuarioRepository;
import pe.gob.osce.redcompradores.respository.VwPublicacionFrecuRepository;

/**
 * Servicio de los reportes
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
public class ReportesService {

	@Autowired
	private VwPublicacionFrecuRepository publicacionFrecuRepository;
	
	@Autowired
	private VwParticipacionUsuarioRepository participacionUsuarioRepository;
	
	@Autowired
	private VwParticipacionAnioRepository participacionAnioRepository;
	
	@Autowired
	private VwParticipacionMesRepository participacionMesRepository;
	
	@Autowired
	private VwParticipacionDiaRepository participacionDiaRepository;
	
	/**
	 * Metodo encargado de listar el top 10 de los temas mas
	 * usados en publicaciones
	 * @return List<VwPublicacionFrecu>
	 */
	public List<VwPublicacionFrecu> listarPublicacionesFrecuentes(){		
		Pageable filterTop10 = PageRequest.of(0, 10);
		Page<VwPublicacionFrecu> page = publicacionFrecuRepository.findAll(filterTop10);		
		return page.getContent();
	}
	
	/**
	 * Metodo encargado de listar el top 10 de los usuarios con
	 * mas participaciones
	 * @return List<VwParticipacionUsuario> 
	 */
	public List<VwParticipacionUsuario> listarParticipacionUsuario(){
		Pageable filterTop10 = PageRequest.of(0, 10);
		Page<VwParticipacionUsuario> page = participacionUsuarioRepository.findAll(filterTop10);	
		return page.getContent();
	}
	
	/**
	 * Metodo encargado de listar la cantidad de publicaciones por año
	 * @return List<VwParticipacionAnio> 
	 */
	public List<VwParticipacionAnio> listarParticipacionAnio(){
		return participacionAnioRepository.findAll();
	}
	
	/**
	 * Metodo encargado de listar la cantidad de publicaciones por mes
	 * de un año determinado
	 * @param nAnioPub
	 * @return List<VwParticipacionMes>
	 */
	public List<VwParticipacionMes> listarParticipacionMes(BigDecimal nAnioPub){
		return participacionMesRepository.findByAnio(nAnioPub);
	}
	
	/**
	 * Metodo encargado de listar la cantidad de publicaciones por dia 
	 * de un mes y año determinado
	 * @param nAnioPub
	 * @param nMesPub
	 * @return List<VwParticipacionDia>
	 */
	public List<VwParticipacionDia> listarParticipacionDia(BigDecimal nAnioPub, BigDecimal nMesPub){
		return participacionDiaRepository.findByAnioAndMes(nAnioPub, nMesPub);
	}
}
