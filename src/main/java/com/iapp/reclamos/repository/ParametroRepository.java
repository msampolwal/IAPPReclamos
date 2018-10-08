package com.iapp.reclamos.repository;

import com.iapp.reclamos.domain.Parametro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.lang.String;
import java.util.List;


/**
 * Spring Data  repository for the Parametro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {
	
	Parametro findByClave(String clave);
}
