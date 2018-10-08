package com.iapp.reclamos.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.util.FtpClient;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {
	private final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);
	private FtpClient ftpClient;
	
	@Autowired
    private ParametroRepository parametroRepository;

	@Scheduled(fixedDelay = 10000)
	public void procesarArchivoTienda() {
		log.info("Procesando archivos de Tienda");
		Parametro ipFtp = parametroRepository.findByClave("ipFtp");
		Parametro usuarioFtp = parametroRepository.findByClave("usuarioFtp");
		Parametro claveFtp = parametroRepository.findByClave("claveFtp");
		Parametro puertoFtp = parametroRepository.findByClave("puertoFtp");
		ftpClient = new FtpClient(ipFtp.getValor(), Integer.parseInt(puertoFtp.getValor()), usuarioFtp.getValor(), claveFtp.getValor());
        try {
			ftpClient.open();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
