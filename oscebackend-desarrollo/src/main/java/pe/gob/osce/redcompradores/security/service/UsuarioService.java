package pe.gob.osce.redcompradores.security.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.security.dto.UsuarioDto;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.repository.UsuarioRepository;
import pe.gob.osce.redcompradores.util.BeanUtils;

/**
 * Servicio transacional de los usuarios
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
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Metodo encargado de buscar usuarios por id
	 * @param nIdUsuario
	 * @return UsuarioDto
	 */
	public UsuarioDto buscarPorIdUsuario(BigDecimal nIdUsuario) {
		Optional<MprUsuario> usuarioOptional = usuarioRepository.findBynIdUsuario(nIdUsuario);
		if (usuarioOptional.isPresent()) {
			MprUsuario mprUsuario = usuarioOptional.get();
			UsuarioDto usuarioDto = new UsuarioDto();
			BeanUtils.copyPropertiesUsuario(usuarioDto, mprUsuario);
			return usuarioDto;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Metodo encargado de buscar usuarios por id
	 * @param nIdUsuario
	 * @return MprUsuario
	 */
	public MprUsuario buscarPorIdUsuarioEntity(BigDecimal nIdUsuario) {
		Optional<MprUsuario> usuarioOptional = usuarioRepository.findBynIdUsuario(nIdUsuario);
		if (usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Metodo encargado de listar los usuarios activos del sistema
	 * @return List<UsuarioDto>
	 */
	public List<UsuarioDto> listarUsuarios(){
		List<MprUsuario> list = usuarioRepository.findAllWithoutAdmin();
		List<UsuarioDto> listUsuario = new ArrayList<UsuarioDto>();
		UsuarioDto usuarioDto = null;
		for (MprUsuario mprUsuario : list) {
			usuarioDto = new UsuarioDto();
			BeanUtils.copyPropertiesUsuario(usuarioDto, mprUsuario);
			listUsuario.add(usuarioDto);
		}		
		return listUsuario;
	}
	
	/**
	 * Metodo encargado de listar usuarios activos por entidad
	 * @param mprEntidad
	 * @return List<UsuarioDto>
	 */
	public List<UsuarioDto> listarUsuariosPorEntidad(MprEntidad mprEntidad){
		List<MprUsuario> list = usuarioRepository.findByMprEntidad(mprEntidad);
		List<UsuarioDto> listUsuario = new ArrayList<UsuarioDto>();
		UsuarioDto usuarioDto = null;
		for (MprUsuario mprUsuario : list) {
			usuarioDto = new UsuarioDto();
			BeanUtils.copyPropertiesUsuario(usuarioDto, mprUsuario);
			listUsuario.add(usuarioDto);
		}		
		return listUsuario;
	}

	/**
	 * Metodo encargado de buscar un usuario por correo
	 * @param correoUsuario
	 * @return MprUsuario
	 */
	public Optional<MprUsuario> buscarPorCValCorreo(String correoUsuario) {
		return usuarioRepository.findBycValCorreo(correoUsuario);
	}
	
	/**
	 * Metodo encargado de buscar un usuario por documento
	 * @param nNumDocumento
	 * @return MprUsuario
	 */
	public Optional<MprUsuario> buscarPorNumDocumento(String nNumDocumento){
		return usuarioRepository.findBynNumDocumento(nNumDocumento);
	}

	/**
	 * Metodo encargado de verificar la existencia de un usuario por id
	 * @param nIdUsuario
	 * @return boolean
	 */
	public boolean existePorIdUsuario(BigDecimal nIdUsuario) {
		return usuarioRepository.existsBynIdUsuario(nIdUsuario);
	}
	
	/**
	 * Metodo encargado de verificar la existencia de un usuario por correo
	 * @param correoUsuario
	 * @return boolean
	 */
	public boolean existePorCorreo(String correoUsuario) {
		return usuarioRepository.existsBycValCorreo(correoUsuario);
	}

	/**
	 * Metodo encargado de verificar la existencia de un usuario por nro de documento
	 * @param nNumDocumento
	 * @return boolean
	 */
	public boolean existePorNumDocumento(String nNumDocumento) {
		return usuarioRepository.existsBynNumDocumento(nNumDocumento);
	}

	/**
	 * Metodo encargado de registrar y/o actualizar usuario
	 * @param usuario
	 */
	public void guardarUsuario(MprUsuario usuario) {
		usuarioRepository.save(usuario);
	}
}
