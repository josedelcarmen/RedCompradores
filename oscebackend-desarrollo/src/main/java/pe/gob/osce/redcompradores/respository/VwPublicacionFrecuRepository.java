package pe.gob.osce.redcompradores.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.entity.VwPublicacionFrecu;

@Repository
public interface VwPublicacionFrecuRepository extends JpaRepository<VwPublicacionFrecu, String> {

}
