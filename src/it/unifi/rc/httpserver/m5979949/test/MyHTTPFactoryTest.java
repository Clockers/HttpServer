package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPFactory;
import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_0;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_1;
import it.unifi.rc.httpserver.m5979949.MyHTTPFactory;
import it.unifi.rc.httpserver.m5979949.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5979949.MyHTTPOutputStream;
import it.unifi.rc.httpserver.m5979949.MyHTTPServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the MyHTTPFactory class
 */
class MyHTTPFactoryTest {

    HTTPFactory httpFactory;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        httpFactory = new MyHTTPFactory();
    }

    /**
     * Test the method for get the HTTPInputStream
     */
    @Test
    void getHTTPInputStream() {
        InputStream is;
        try {
            is = new ByteArrayInputStream("".getBytes("US-ASCII"));
            MyHTTPInputStream httpInputStream = (MyHTTPInputStream) httpFactory.getHTTPInputStream(is);
            assertTrue(is.equals(httpInputStream.getIs()));
        } catch (UnsupportedEncodingException e) {
            assertTrue(false);
        }
    }

    /**
     * Test the method for get the HTTPOutputStream
     */
    @Test
    void getHTTPOutputStream() {
        OutputStream os = new ByteArrayOutputStream();
        MyHTTPOutputStream httpOutputStream = (MyHTTPOutputStream) httpFactory.getHTTPOutputStream(os);
        assertTrue(os.equals(httpOutputStream.getOs()));
    }

    /**
     * Test the method for get the HTTPServer
     */
    @Test
    void getHTTPServer() {
        MyHTTPServer myHTTPServer = null;
        InetAddress inetAddress = null;
        HTTPHandler httpHandler = new Handler1_1("localhost",new File("dir"));
        try {
            inetAddress = InetAddress.getByName("localhost");
            myHTTPServer = (MyHTTPServer) httpFactory.getHTTPServer(9999, 50, inetAddress, httpHandler);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assertEquals(9999, myHTTPServer.getPort());
        assertEquals(50, myHTTPServer.getBacklog());
        assertTrue(inetAddress.equals(myHTTPServer.getAddress()));
        assertTrue(myHTTPServer.getHandlers().contains(httpHandler));
    }

    /**
     * Test the method for get the Handler1_0
     */
    @Test
    void getFileSystemHandler1_0() {
        Handler1_0 handler = (Handler1_0) httpFactory.getFileSystemHandler1_0(new File("dir"));
        assertEquals(null, handler.getHost());
        assertEquals(new File("dir"),handler.getRoot());
        handler = (Handler1_0) httpFactory.getFileSystemHandler1_0("localhost",new File("dir"));
        assertEquals("localhost", handler.getHost());
        assertEquals(new File("dir"),handler.getRoot());
    }

    /**
     * Test the method for get the Handler1_1
     */
    @Test
    void getFileSystemHandler1_1() {
        Handler1_1 handler = (Handler1_1) httpFactory.getFileSystemHandler1_1(new File("dir"));
        assertEquals(null, handler.getHost());
        assertEquals(new File("dir"),handler.getRoot());
        handler = (Handler1_1) httpFactory.getFileSystemHandler1_1("localhost",new File("dir"));
        assertEquals("localhost", handler.getHost());
        assertEquals(new File("dir"),handler.getRoot());
    }
}