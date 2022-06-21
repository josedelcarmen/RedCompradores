package pe.gob.osce.redcompradores.publicacion.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.publicacion.emtity.TblComentario;
import pe.gob.osce.redcompradores.publicacion.emtity.TblPublicacion;

@Repository
public interface ComentarioRepository extends JpaRepository<TblComentario, BigDecimal>{

	List<TblComentario> findByTblPublicacion(TblPublicacion tblPublicacion);
}
