package com.iapp.reclamos.repository;

import com.iapp.reclamos.domain.TipoReclamo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoReclamo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoReclamoRepository extends JpaRepository<TipoReclamo, Long>, JpaSpecificationExecutor<TipoReclamo> {

}
