package pe.gob.osce.redcompradores.respository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.gob.osce.redcompradores.entity.VwParticipacionMes;

public interface VwParticipacionMesRepository extends JpaRepository<VwParticipacionMes, BigDecimal>{

	@Query(nativeQuery = true, value = "SELECT * FROM VW_PARTICIPACION_MES  WHERE ANIOS = :anios")
	public List<VwParticipacionMes> findByAnio(@Param("anios") BigDecimal anios);
}
