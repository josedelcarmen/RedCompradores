package pe.gob.osce.redcompradores.publicacion.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.publicacion.emtity.TblArchivo;

@Repository
public interface ArchivoRepository extends JpaRepository<TblArchivo, BigDecimal>{

	public boolean existsBynIdArchivo(BigDecimal nIdArchivo);
	
	@Query("select a from TblArchivo as a where a.tblPublicacion.nValEstado = 1")
	public List<TblArchivo> findByPublicacionActiva();
	
}
