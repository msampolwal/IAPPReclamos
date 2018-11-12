package com.iapp.reclamos.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iapp.reclamos.domain.Pedido;
import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.Tienda;
import com.iapp.reclamos.repository.TiendaRepository;
import com.iapp.reclamos.service.TiendaService;
import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.dto.ReporteReclamoDTO;
import com.iapp.reclamos.service.dto.TiendaDTO;
import com.iapp.reclamos.service.mapper.ReclamoMapper;
import com.iapp.reclamos.service.mapper.TiendaMapper;
/**
 * Service Implementation for managing Tienda.
 */
@Service
@Transactional
public class TiendaServiceImpl implements TiendaService {

    private final Logger log = LoggerFactory.getLogger(TiendaServiceImpl.class);

    private final TiendaRepository tiendaRepository;
    
    private final TiendaMapper tiendaMapper;
    
    private final ReclamoMapper reclamoMapper;
    
    private final RestTemplate restTemplate;

    public TiendaServiceImpl(TiendaRepository tiendaRepository, ReclamoMapper reclamoMapper, TiendaMapper tiendaMapper, RestTemplate restTemplate) {
        this.tiendaRepository = tiendaRepository;
        this.tiendaMapper = tiendaMapper;
        this.reclamoMapper = reclamoMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * Save a tienda.
     *
     * @param tiendaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TiendaDTO save(TiendaDTO tiendaDTO) {
        log.debug("Request to save Tienda : {}", tiendaDTO);
        Tienda tienda = tiendaMapper.toEntity(tiendaDTO);
        tienda = tiendaRepository.save(tienda);
        return tiendaMapper.toDto(tienda);
    }

    /**
     * Get all the tiendas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tiendas");
        return tiendaRepository.findAll(pageable)
            .map(tiendaMapper::toDto);
    }


    /**
     * Get one tienda by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TiendaDTO> findOne(Long id) {
        log.debug("Request to get Tienda : {}", id);
        return tiendaRepository.findById(id)
            .map(tiendaMapper::toDto);
    }

    /**
     * Delete the tienda by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tienda : {}", id);
        tiendaRepository.deleteById(id);
    }

	@Override
	public void notificarTienda(ReclamoDTO reclamoDTO) {
		Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
		Pedido pedido = reclamo.getPedido();
		if(pedido != null) {
			Optional<Tienda> tienda = tiendaRepository.findTiendaByPedido(pedido.getId());
			if(tienda.isPresent()) {
				String url = tienda.get().getUrl();
				url = url.replaceAll("\\{id\\}", pedido.getId().toString());
				log.info("********************" + url);
				try {
					URI uri = new URI(url);
					ReporteReclamoDTO dto = new ReporteReclamoDTO(reclamo.getId(), reclamo.getObservacion(), reclamo.getEstado().name());
					
					restTemplate.postForObject(uri, dto, ReporteReclamoDTO.class);
					
				} catch (RestClientException e) {
					log.error("Error al conectar con el servidor de Tienda: " + url);
				} catch (URISyntaxException e) {
					log.error("Error de sintaxis al intentar armar la url de Tienda: " + url);
				}
			}else {
				log.error("No existe tienda para el pedido numero " + reclamoDTO.getPedidoId());
			}
		}else {
			log.error("No existe pedido para el reclamo numero " + reclamoDTO.getId());
		}
	}
	
	@Override
	public void notificarLogistica(ReclamoDTO reclamoDTO) {
		
		Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
		Pedido pedido = reclamo.getPedido();
		if(pedido != null) {
			Optional<Tienda> tienda = tiendaRepository.findTiendaByPedido(pedido.getId());
			if(tienda.isPresent()) {
				String url = tienda.get().getUrlLogistica();
				url = url.replaceAll("\\{id\\}", pedido.getId().toString());
				log.info("********************" + url);
				try {
					URI uri = new URI(url);
					restTemplate.postForObject(uri, null, String.class);
				} catch (RestClientException e) {
					log.error("Error al conectar con el servidor de Logistica: " + url);
				} catch (URISyntaxException e) {
					log.error("Error de sintaxis al intentar armar la url de Logistica: " + url);
				}
			}else {
				log.error("No existe tienda para el pedido numero " + reclamoDTO.getPedidoId());
			}
		}else {
			log.error("No existe pedido para el reclamo numero " + reclamoDTO.getId());
		}
	}
}
