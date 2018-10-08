package com.iapp.reclamos.web.rest.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.iapp.reclamos.service.util.FtpClient;

/**
 * 
 */
public class FtpClientIntegrationUnitTest {
    private FtpClient ftpClient;
 
    @Before
    public void setup() throws IOException {
        ftpClient = new FtpClient("10.100.22.118", 21, "app-reclamos", "reclamos");
        ftpClient.open();
    }
    
    @Test
    public void ftpTest() {
    		OutputStream os = null;
		File f = null;
    		try {
			for (String nombre : Arrays.asList(ftpClient.getInstancia().listNames())) {
				os = new FileOutputStream(nombre);
				ftpClient.getInstancia().retrieveFile(nombre, os);
				f = new File(nombre);
				procesarArchivo(os);
				
			}
				
			ftpClient.getInstancia().retrieveFileStream("");
//			Arrays.asList(ftpClient.getInstancia().listFiles()).forEach(archivo -> 
//				
//			);
		} catch (IOException e) {
			e.printStackTrace();
		};
    }
 
    private void procesarArchivo(OutputStream os) {
    		
	}

	@After
    public void teardown() throws IOException {
        ftpClient.close();
    }
}
