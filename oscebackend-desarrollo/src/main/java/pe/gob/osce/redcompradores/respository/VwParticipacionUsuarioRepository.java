package pe.gob.osce.redcompradores.respository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.gob.osce.redcompradores.entity.VwParticipacionUsuario;

public interface VwParticipacionUsuarioRepository extends JpaRepository<VwParticipacionUsuario, BigDecimal> {

}
