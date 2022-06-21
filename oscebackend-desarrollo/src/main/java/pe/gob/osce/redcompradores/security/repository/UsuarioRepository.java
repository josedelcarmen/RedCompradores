package pe.gob.osce.redcompradores.security.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.entity.MprEntidad;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;

@Repository
public interface UsuarioRepository extends JpaRepository<MprUsuario, BigDecimal> {

	public Optional<MprUsuario> findBynIdUsuario(BigDecimal nIdUsuario);
	
	public Optional<MprUsuario> findBycValCorreo(String correoUsuario);
	
	public Optional<MprUsuario> findBynNumDocumento(String nNumDocumento);
	
	public boolean existsBynIdUsuario(BigDecimal nIdUsuario);

	public boolean existsBycValCorreo(String correoUsuario);

	public boolean existsBynNumDocumento(String nNumDocumento);
	
	public List<MprUsuario> findByMprEntidad(MprEntidad mprEntidad);
	
	@Query("FROM MprUsuario U WHERE U.cCodRol NOT IN ('0')")
	public List<MprUsuario> findAllWithoutAdmin();
}
