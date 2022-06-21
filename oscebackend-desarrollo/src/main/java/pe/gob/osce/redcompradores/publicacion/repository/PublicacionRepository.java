package pe.gob.osce.redcompradores.publicacion.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;

@Repository
public interface PublicacionRepository extends JpaRepository<TblPublicacion, BigDecimal> {

	Page<TblPublicacion> findBynValEstado(BigDecimal nValEstado, Pageable page);
	
	List<TblPublicacion> findBynValEstado(BigDecimal nValEstado);

	Page<TblPublicacion> findBynValEstadoAndMprUsuario(BigDecimal nValEstado, MprUsuario mprUsuario, Pageable page);
	
	List<TblPublicacion> findBynValEstadoAndMprUsuario(BigDecimal nValEstado, MprUsuario mprUsuario);

	@Query("SELECT c FROM TblPublicacion AS c where trim(c.cCodTema) in ?1 AND c.nValEstado = ?2")
	Page<TblPublicacion> findByFilterTema(@Param("temas") List<String> temas,
			@Param("nValEstado") BigDecimal nValEstado, Pageable page);

	@Query("SELECT c FROM TblPublicacion AS c where c.nValEstado = 1 AND c.mprUsuario.nIdUsuario = :idUsuario AND "
			+ "trim(c.cCodTema) in :temas ")
	public List<TblPublicacion> findByUsuarioFilterTema(@Param("idUsuario") BigDecimal idUsuario,
			@Param("temas") List<String> temas);

}
