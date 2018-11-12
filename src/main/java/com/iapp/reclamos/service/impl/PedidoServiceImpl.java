package com.iapp.reclamos.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iapp.reclamos.domain.Pedido;
import com.iapp.reclamos.repository.PedidoRepository;
import com.iapp.reclamos.service.PedidoService;
import com.iapp.reclamos.service.dto.PedidoDTO;
import com.iapp.reclamos.service.mapper.PedidoMapper;
import com.opencsv.CSVReader;
/**
 * Service Implementation for managing Pedido.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        log.debug("Request to save Pedido : {}", pedidoDTO);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable)
            .map(pedidoMapper::toDto);
    }

    /**
     *  get all the pedidos where Reclamo is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PedidoDTO> findAllWhereReclamoIsNull() {
        log.debug("Request to get all pedidos where Reclamo is null");
        return StreamSupport
            .stream(pedidoRepository.findAll().spliterator(), false)
            .filter(pedido -> pedido.getReclamo() == null)
            .map(pedidoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pedido by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDTO> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findById(id)
            .map(pedidoMapper::toDto);
    }

    /**
     * Delete the pedido by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }
    
    public void migrarPedidosDesdeCSV(Long idTienda, LocalDate fechaPedidos, Reader fileReader) {
		CSVReader reader = null;
	    try {
	        reader = new CSVReader(fileReader);
	        String[] line;
	        while ((line = reader.readNext()) != null) {
	        		try {
	        			PedidoDTO pedidoDTO = arrayToPedidoDTO(idTienda, fechaPedidos, line);
	        			Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
	        			System.out.println(pedido.toString());
	        	        pedido = pedidoRepository.save(pedido);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    private PedidoDTO arrayToPedidoDTO(Long idTienda, LocalDate fechaPedidos, String[] pedido) throws Exception {
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setTiendaId(idTienda);
		if(StringUtils.isNotBlank(pedido[0])) {
			pedidoDTO.setDescripcionProducto(pedido[0]);
		}else {
			throw new Exception("La descripcion del producto no puede ser nula");
		}
		
		if(StringUtils.isNotBlank(pedido[1])) {
			pedidoDTO.setDniCliente(pedido[1]);
		}else {
			throw new Exception("El dni del cliente no puede ser nulo");
		}
		
		if(StringUtils.isNotBlank(pedido[2])) {
			try {
				pedidoDTO.setId(Long.parseLong(pedido[2]));
			} catch (Exception e) {
				throw new Exception("El id Pedido debe ser de tipo entero");
			}
		}else {
			throw new Exception("El id Pedido no puede ser nulo");
		}
		
		if(StringUtils.isNotBlank(pedido[3])) {
			pedidoDTO.setIdProducto(pedido[3]);
		}else {
			throw new Exception("El id del producto no puede ser nulo");
		}
		
		if(StringUtils.isNotBlank(pedido[4])) {
			pedidoDTO.setMailCliente(pedido[4]);
		}else {
			throw new Exception("El email del cliente no puede ser nulo");
		}
		
		if(StringUtils.isNotBlank(pedido[5])) {
			try {
				pedidoDTO.setMontoCompra(Float.parseFloat(pedido[5]));
			} catch (Exception e) {
				throw new Exception("El monto de la compra debe tener formato decimal");
			}
		}else {
			throw new Exception("El monto de la compra no puede ser nulo");
		}
		
		if(StringUtils.isNotBlank(pedido[6])) {
			pedidoDTO.setNombreCliente(pedido[6]);
		}else {
			throw new Exception("El nombre del cliente no puede ser nulo");
		}
		
//		if(StringUtils.isNotBlank(pedido[1])) {
//		try {
//			pedidoDTO.setFechaEntrega(LocalDate.parse(pedido[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		} catch (Exception e) {
//			throw new Exception("La fecha de Entrega debe ser de tipo string con formato yyyy-mm-dd ");
//		}
//	}else {
//		throw new Exception("La fecha de Entrega no puede ser nula");
//	}
		
		return pedidoDTO;
	}
}