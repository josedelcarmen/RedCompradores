package pe.gob.osce.redcompradores.publicacion.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.publicacion.emtity.TblCalificacion;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;

@Repository
public interface CalificacionRepository extends JpaRepository<TblCalificacion, BigDecimal> {

	Integer countBynValCalificacion(BigDecimal nValCalificacion);

	@Query(" SELECT count(c.tblPublicacion.nIdPublicacion) AS likes FROM TblCalificacion c where c.tblPublicacion.nIdPublicacion = :nIdPublicacion")
	Integer countBynIdPublicacion(BigDecimal nIdPublicacion);

	@Query(" SELECT count(c.tblPublicacion.nIdPublicacion) AS likes FROM TblCalificacion c where c.nValCalificacion = 1 and c.tblPublicacion.nIdPublicacion = :nIdPublicacion")
	Integer countLikesBynIdPublicacion(@Param("nIdPublicacion") BigDecimal nIdPublicacion);

	@Query(" SELECT count(c.tblPublicacion.nIdPublicacion) AS likes FROM TblCalificacion c where c.nValCalificacion = 9 and c.tblPublicacion.nIdPublicacion = :nIdPublicacion")
	Integer countDisslikesBynIdPublicacion(@Param("nIdPublicacion") BigDecimal nIdPublicacion);

	@Query(" SELECT count(c.mprUsuario.nIdUsuario) AS likes FROM TblCalificacion AS c where c.tblPublicacion.nIdPublicacion IN ("
			+ " SELECT p.nIdPublicacion FROM TblPublicacion AS p WHERE p.mprUsuario.nIdUsuario = :nIdUsuario)")
	Integer countBynIdUsuario(@Param("nIdUsuario") BigDecimal nIdUsuario);
	
	@Query(" SELECT count(c.mprUsuario.nIdUsuario) AS likes FROM TblCalificacion AS c where c.tblPublicacion.nIdPublicacion IN ("
			+ " SELECT p.nIdPublicacion FROM TblPublicacion AS p WHERE p.mprUsuario.nIdUsuario = :nIdUsuario)"
			+ " AND c.nValCalificacion = 1")
	Integer countLikesBynIdUsuario(@Param("nIdUsuario") BigDecimal nIdUsuario);

	@Query(" SELECT count(c.mprUsuario.nIdUsuario) AS likes FROM TblCalificacion AS c where c.tblPublicacion.nIdPublicacion IN ("
			+ " SELECT p.nIdPublicacion FROM TblPublicacion AS p WHERE p.mprUsuario.nIdUsuario = :nIdUsuario)"
			+ " AND c.nValCalificacion = 9")
	Integer countDisslikesBynIdUsuario(@Param("nIdUsuario") BigDecimal nIdUsuario);
	
	boolean existsByMprUsuario(MprUsuario mprUsuario);
	
	boolean existsByTblPublicacion(TblPublicacion tblPublicacion);
	
	boolean existsByMprUsuarioAndTblPublicacion(MprUsuario mprUsuario, TblPublicacion tblPublicacion);
	
	TblCalificacion findByMprUsuarioAndTblPublicacion(MprUsuario mprUsuario, TblPublicacion tblPublicacion);
}
