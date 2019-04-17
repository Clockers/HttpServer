package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.handler.DefaultHandler;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;
import it.unifi.rc.httpserver.m5979949.MyHTTPRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the DefaultHandler class
 */
class DefaultHandlerTest {

    private HTTPHandler handler;
    private HTTPRequest httpRequest;
    private HTTPReply expectedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        handler = new DefaultHandler();
        httpRequest = new MyHTTPRequest("HTTP/1.0",null,null,null,null);
        expectedReply = new MyHTTPReply(httpRequest.getVersion(),"501","Not Implemented",null,null);
    }

    /**
     * Test the reply of a DefaultHandler
     */
    @Test
    void handle() throws HTTPProtocolException {
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals(expectedReply.getParameters(),httpReply.getParameters());
    }
}