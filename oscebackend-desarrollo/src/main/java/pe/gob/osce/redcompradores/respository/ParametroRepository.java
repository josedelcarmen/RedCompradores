package pe.gob.osce.redcompradores.respository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.entity.ParParametro;

@Repository
public interface ParametroRepository extends JpaRepository<ParParametro, BigDecimal>{

	Optional<ParParametro> findBycValClaveParam(String cValClaveParam);
	
	public List<ParParametro> findBycValGrupoParam(String cValGrupoParam);
	
	public List<ParParametro> findBycCodPadreParam(String nCodPadreParam);
		
	boolean existsBycValClaveParam(String cValClaveParam);
	
	boolean existsBynIdParametro(BigDecimal idParametro);
}
