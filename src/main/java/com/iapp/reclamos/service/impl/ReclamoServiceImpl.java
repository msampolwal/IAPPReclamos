package com.iapp.reclamos.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.domain.Reclamo;
import com.iapp.reclamos.domain.enumeration.Estado;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.repository.ReclamoRepository;
import com.iapp.reclamos.service.MailService;
import com.iapp.reclamos.service.ReclamoService;
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
    
    private final ParametroRepository parametroRepository;

    public ReclamoServiceImpl(ReclamoRepository reclamoRepository, ReclamoMapper reclamoMapper, MailService mailService, ParametroRepository parametroRepository) {
        this.reclamoRepository = reclamoRepository;
        this.reclamoMapper = reclamoMapper;
        this.mailService = mailService;
        this.parametroRepository = parametroRepository;
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
        mailService.sendMailReclamoFinalizado(reclamoDTO);
        return reclamoMapper.toDto(reclamo);
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
        
        if(reclamoDTO.getNotificaLogistica()) {
	    		reclamo.setEstado(Estado.PENDIENTE_LOGISTICA);
	    		Parametro servicioLogistica = parametroRepository.findByClave("newReclamoLogistica");
	    		if(servicioLogistica != null) {
	    			try {
	    				URL url = new URL(reclamo.getPedido().getTienda().getUrlLogistica()+"logistica/"+reclamoDTO.getPedidoId()+"/complain");
					HttpsURLConnection postConnection = (HttpsURLConnection) url.openConnection();
					postConnection.setRequestMethod("POST");
					postConnection.setRequestProperty("Content-Type", "application/json");
//					String params = "{\"idPedido\": " + reclamoDTO.getPedidoId() + "}";
					
					postConnection.setDoOutput(true);
					postConnection.setDoInput(true);
					OutputStream os = postConnection.getOutputStream();
//					os.write(params.getBytes());
					os.flush();
					os.close();
					
					int responseCode = postConnection.getResponseCode();
					log.debug("POST Response Code: " + responseCode);
					log.debug("POST Response Message: " + postConnection.getResponseMessage());
					
					if (responseCode == HttpURLConnection.HTTP_OK) { //success
				        BufferedReader in = new BufferedReader(new InputStreamReader(
				            postConnection.getInputStream()));
				        String inputLine;
				        StringBuffer response = new StringBuffer();
				        while ((inputLine = in .readLine()) != null) {
				            response.append(inputLine);
				        } in .close();
				        // print result
				        System.out.println(response.toString());
				    } else {
				        System.out.println("POST NOT WORKED");
				    }
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		}
	    }else {
	    		reclamo.setEstado(Estado.PENDIENTE);
	    }
        
        reclamo = reclamoRepository.save(reclamo);
        return reclamoMapper.toDto(reclamo);
    }
}
