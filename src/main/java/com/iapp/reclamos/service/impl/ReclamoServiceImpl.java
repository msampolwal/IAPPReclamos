package com.iapp.reclamos.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.enumeration.Estado;
import com.iapp.reclamos.repository.ReclamoRepository;
import com.iapp.reclamos.service.MailService;
import com.iapp.reclamos.service.ReclamoService;
import com.iapp.reclamos.service.TiendaService;
import com.iapp.reclamos.service.dto.ReclamoDTO;
import com.iapp.reclamos.service.mapper.ReclamoMapper;
/**
 * Service Implementation for managing Reclamo.
 */
@Service
@Transactional
public class ReclamoServiceImpl implements ReclamoService {

    private final Logger log = LoggerFactory.getLogger(ReclamoServiceImpl.class);

    private final ReclamoRepository reclamoRepository;

    private final ReclamoMapper reclamoMapper;
    
    private final MailService mailService;
    
    private final TiendaService tiendaService;
    
    public ReclamoServiceImpl(ReclamoRepository reclamoRepository, ReclamoMapper reclamoMapper, MailService mailService, TiendaService tiendaService) {
        this.reclamoRepository = reclamoRepository;
        this.reclamoMapper = reclamoMapper;
        this.mailService = mailService;
        this.tiendaService = tiendaService;
    }

    /**
     * Save a reclamo.
     *
     * @param reclamoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReclamoDTO save(ReclamoDTO reclamoDTO) {
        log.debug("Request to save Reclamo : {}", reclamoDTO);
        
        Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
        reclamo = reclamoRepository.save(reclamo);
        return reclamoMapper.toDto(reclamo);
    }

    /**
     * Get all the reclamos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReclamoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reclamos");
        return reclamoRepository.findAll(pageable)
            .map(reclamoMapper::toDto);
    }


    /**
     * Get one reclamo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReclamoDTO> findOne(Long id) {
        log.debug("Request to get Reclamo : {}", id);
        return reclamoRepository.findById(id)
            .map(reclamoMapper::toDto);
    }

    /**
     * Delete the reclamo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reclamo : {}", id);
        reclamoRepository.deleteById(id);
    }
    
    /**
     * Finaliza un reclamo.
     *
     * @param reclamoDTO de la entidad a finalizar
     * @return the persisted entity
     */
    @Override
    public ReclamoDTO finalizar(ReclamoDTO reclamoDTO) {
        log.debug("Request to close Reclamo : {}", reclamoDTO);
        Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
        reclamo.setEstado(Estado.FINALIZADO);
        reclamo = reclamoRepository.save(reclamo);
        reclamoDTO = reclamoMapper.toDto(reclamo);
        mailService.sendMailReclamoFinalizado(reclamoDTO);
        tiendaService.notificarTienda(reclamoDTO);
        return reclamoDTO;
    }
    
    /**
     * Save a reclamo.
     *
     * @param reclamoDTO the entity to save
     * @return the persisted entity
     */
    @Override
	public ReclamoDTO createReclamo(ReclamoDTO reclamoDTO) {
		log.debug("Request to save Reclamo : {}", reclamoDTO);
		Reclamo reclamo = reclamoMapper.toEntity(reclamoDTO);
		if (reclamoDTO.getNotificaLogistica()) {
			reclamo.setEstado(Estado.PENDIENTE_LOGISTICA);
		} else {
			reclamo.setEstado(Estado.PENDIENTE);
		}

		reclamo = reclamoRepository.save(reclamo);
		reclamoDTO = reclamoMapper.toDto(reclamo);
		
		if (reclamoDTO.getNotificaLogistica()) 
			tiendaService.notificarLogistica(reclamoDTO);
		
		tiendaService.notificarTienda(reclamoDTO);
		return reclamoDTO;
	}
}
