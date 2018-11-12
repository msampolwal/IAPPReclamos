package com.iapp.reclamos.web.rest.util;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iapp.reclamos.IappReclamosApp;
import com.iapp.reclamos.service.PedidoService;
import com.iapp.reclamos.service.util.FtpClient;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IappReclamosApp.class)
public class FtpClientIntegrationUnitTest {
	@Autowired
    private PedidoService pedidoService;

    private FtpClient ftpClient;
 
    @Before
    public void setup() throws IOException {
        ftpClient = new FtpClient("192.168.0.6", 2221, "francis", "francis");
        ftpClient.open();
    }
    
//    @Test
//    public void ftpTest() {
//    		OutputStream os = null;
//		File f = null;
//    		try {
//    			System.out.println("******** Lista de Archivos en servidor FTP *********");
//			for (String nombre : Arrays.asList(ftpClient.getInstancia().listNames())) {
//				System.out.println("Archivo: " + nombre);
//				os = new FileOutputStream(nombre);
//				Reader reader = new InputStreamReader(ftpClient.getInstancia().retrieveFileStream(nombre));
//				
//				pedidoService.migrarPedidosDesdeCSV(new Long(1l), new LocalDate(),reader);
//				
//				reader.close();
////				ftpClient.getInstancia().deleteFile(nombre);
//			}
//			System.out.println("******** Fin lista de Archivos en servidor FTP *********");
//		} catch (IOException e) {
//			e.printStackTrace();
//		};
//    }
 
	@After
    public void teardown() throws IOException {
        ftpClient.close();
    }
}
