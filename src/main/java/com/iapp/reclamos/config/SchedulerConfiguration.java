package com.iapp.reclamos.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.iapp.reclamos.domain.Parametro;
import com.iapp.reclamos.repository.ParametroRepository;
import com.iapp.reclamos.service.PedidoService;
import com.iapp.reclamos.service.util.FtpClient;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {
	private final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);
	private FtpClient ftpClient;
	
	@Autowired
    private ParametroRepository parametroRepository;
	@Autowired
    private PedidoService pedidoService;

	@Scheduled(fixedDelay = 10000)
	public void procesarArchivoTienda() {
		log.info("******* Inicia tarea - Procesando archivos de Tienda *******");
		Parametro ipFtp = parametroRepository.findByClave("ipFtp");
		Parametro usuarioFtp = parametroRepository.findByClave("usuarioFtp");
		Parametro claveFtp = parametroRepository.findByClave("claveFtp");
		Parametro puertoFtp = parametroRepository.findByClave("puertoFtp");
        try {
        		ftpClient = new FtpClient(ipFtp.getValor(), Integer.parseInt(puertoFtp.getValor()), usuarioFtp.getValor(), claveFtp.getValor());
        		ftpClient.open();
        		log.info("******* Se conecto con servidor FTP " + usuarioFtp.getValor() + ":" + claveFtp.getValor() + "@" + ipFtp.getValor() + ":" + puertoFtp.getValor() + " *******");
        		
			for (String nombre : Arrays.asList(ftpClient.getInstancia().listNames())) {
				System.out.println("Se procesa Archivo: " + nombre);
				Reader reader = new InputStreamReader(ftpClient.getInstancia().retrieveFileStream(nombre));
//				String[] nombreAr = StringUtils.split(StringUtils.replace(nombre,".csv",""), "_");
//				Long idTienda = Long.parseLong(nombreAr[0]);
//				LocalDate fechaPedidos = LocalDate.parse(nombreAr[1], DateTimeFormatter.ofPattern("yyyyMMdd"));
				
				pedidoService.migrarPedidosDesdeCSV(null, null, reader);
				
				reader.close();
				ftpClient.getInstancia().deleteFile(nombre);
				System.out.println("Se elimina Archivo: " + nombre);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error("No fue posible conectarse con el servidor FTP, verifique los parametros de conexion.");
		} finally {
			try {
				ftpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("******* Finaliza tarea - Procesando archivos de Tienda *******");
		}
	}
}
