package com.iapp.reclamos.repository;

import com.iapp.reclamos.domain.Tienda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tienda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>, JpaSpecificationExecutor<Tienda> {

}
