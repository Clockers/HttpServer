package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.handler.DefaultHandler;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_0;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_1;
import it.unifi.rc.httpserver.m5979949.handler.LogHandler;
import it.unifi.rc.httpserver.m5979949.MyHTTPServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the MyHTTPServer class
 */
class MyHTTPServerTest {

    MyHTTPServer httpServer;
    List<HTTPHandler> handlers;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        try {
            httpServer = new MyHTTPServer(9999,50, InetAddress.getByName("localhost"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        handlers = new LinkedList<>();
        handlers.add(new LogHandler());
        handlers.add(new Handler1_0(new File("dir")));
        handlers.add(new Handler1_1(new File("dir")));
        handlers.add(new DefaultHandler());
    }

    /**
     * Test adding an Handler to HTTPServer
     */
    @Test
    void addHandler() {
        for (HTTPHandler handler:
             handlers) {
            httpServer.addHandler(handler);
        }
        assertEquals(handlers.size(),httpServer.getHandlers().size());
    }

    /**
     * Test getting the handlers from an HTTPServer
     */
    @Test
    void getHandlers() {
        List<HTTPHandler> res = httpServer.getHandlers();
        for (HTTPHandler handler :
                res) {
            assertTrue(handlers.contains(handler));
        }
    }

    /**
     * Test Port of HTTPServer
     */
    @Test
    void getPort() {
        assertEquals(9999,httpServer.getPort());
    }

    /**
     * Test Backlog of HTTPServer
     */
    @Test
    void getBacklog() {
        assertEquals(50,httpServer.getBacklog());
    }

    /**
     * Test Address of HTTPServer
     */
    @Test
    void getAddress() {
        try {
            assertEquals(InetAddress.getByName("localhost"),httpServer.getAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}