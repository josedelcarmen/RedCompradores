package pe.gob.osce.redcompradores.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.security.entity.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String correoUsuario) throws UsernameNotFoundException {
		Optional<MprUsuario> usuarioOptional = usuarioService.buscarPorCValCorreo(correoUsuario);
		if (usuarioOptional.isPresent()) {
			return UsuarioPrincipal.build(usuarioOptional.get());
		}
		else {
			return null;
		}
		
	}
}
