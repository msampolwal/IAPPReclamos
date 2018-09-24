package com.iapp.reclamos.web.rest.util;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import com.iapp.reclamos.service.util.FtpClient;

/**
 * 
 */
public class FtpClientIntegrationUnitTest {
	private FakeFtpServer fakeFtpServer;
	 
    private FtpClient ftpClient;
 
    @Before
    public void setup() throws IOException {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("ym", "meolvide", "/data"));
 
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
 
        fakeFtpServer.start();
 
        ftpClient = new FtpClient("10.15.24.6", fakeFtpServer.getServerControlPort(), "ym", "meolvide");
        ftpClient.open();
    }
 
    @After
    public void teardown() throws IOException {
        ftpClient.close();
        fakeFtpServer.stop();
    }
}
