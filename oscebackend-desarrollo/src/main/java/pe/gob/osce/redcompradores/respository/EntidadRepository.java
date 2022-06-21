package pe.gob.osce.redcompradores.respository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.entity.MprEntidad;

@Repository
public interface EntidadRepository extends JpaRepository<MprEntidad, BigDecimal>{

	Optional<MprEntidad> findBycNomDominio(String cNomDominio);
	
	boolean existsBycNomDominio(String cNomDominio);
	
	boolean existsBynIdEntidad(BigDecimal nIdEntidad);
}
