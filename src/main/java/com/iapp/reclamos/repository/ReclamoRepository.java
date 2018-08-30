package com.iapp.reclamos.repository;

import com.iapp.reclamos.domain.Reclamo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reclamo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long>, JpaSpecificationExecutor<Reclamo> {

}
