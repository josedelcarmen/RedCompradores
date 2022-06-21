package pe.gob.osce.redcompradores.chatroom.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacionArchivo;

@Repository
public interface ComunicacionArchivoRepository extends JpaRepository<TblComunicacionArchivo, BigDecimal> {

}
