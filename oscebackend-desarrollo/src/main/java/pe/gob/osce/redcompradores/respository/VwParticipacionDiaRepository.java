package pe.gob.osce.redcompradores.respository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.gob.osce.redcompradores.entity.VwParticipacionDia;

public interface VwParticipacionDiaRepository extends JpaRepository<VwParticipacionDia, BigDecimal> {

	@Query(nativeQuery = true, value = "SELECT * FROM VW_PARTICIPACION_DIA WHERE ANIOS = :anios AND IDMES = :idmes")
	List<VwParticipacionDia> findByAnioAndMes(@Param("anios") BigDecimal anios,
			@Param("idmes") BigDecimal idmes);
}
