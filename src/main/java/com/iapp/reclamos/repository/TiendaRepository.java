package com.iapp.reclamos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iapp.reclamos.domain.Tienda;


/**
 * Spring Data  repository for the Tienda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>, JpaSpecificationExecutor<Tienda> {

	@Query("SELECT t FROM Tienda t join t.pedidos p where p.id = :idpedido")
	Optional<Tienda> findTiendaByPedido(@Param("idpedido") Long idPedido);
}
