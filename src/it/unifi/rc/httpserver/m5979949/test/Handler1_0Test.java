package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_0;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;
import it.unifi.rc.httpserver.m5979949.MyHTTPRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the Handler of 1.0 request
 */
class Handler1_0Test {

    Handler1_0 handler;
    Map<String, String> parameters;
    HTTPReply expectedReply;
    HTTPRequest httpRequest;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() throws IOException {
        handler = new Handler1_0(null,new File("dir"));
        parameters = new HashMap<>();
        parameters.put("Accept","text/html");
        parameters.put("Host","localhost");
        createFile();
    }

    /**
     * Test the reply of a Handler1_0 for a GET request
     */
    @Test
    void handleGet() throws HTTPProtocolException {
        httpRequest = new MyHTTPRequest("HTTP/1.1","GET","/file.html",null,parameters);
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK","data",null);
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("text/html",httpReply.getParameters().get("Content-Type"));
        assertEquals("4",httpReply.getParameters().get("Content-Length"));
    }

    /**
     * Test the reply of a Handler1_0 for a POST request
     */
    @Test
    void handlePost() throws HTTPProtocolException {
        httpRequest = new MyHTTPRequest("HTTP/1.1","POST","/file.html",null,parameters);
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK","data",null);
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("text/html",httpReply.getParameters().get("Content-Type"));
        assertEquals("4",httpReply.getParameters().get("Content-Length"));
    }

    /**
     * Test the reply of a Handler1_0 for a HEAD request
     */
    @Test
    void handleHead() throws HTTPProtocolException {
        httpRequest = new MyHTTPRequest("HTTP/1.1","HEAD","/file.html",null,parameters);
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK",null,null);
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
    }

    /**
     * Test the value of root File
     */
    @Test
    void getRoot() {
        assertEquals(new File("dir"),handler.getRoot());
    }

    /**
     * Test the value of host String
     */
    @Test
    void getHost() {
        assertEquals(null,handler.getHost());
    }

    /**
     * Method used to create a file
     * @throws IOException
     */
    private void createFile() throws IOException {
        File f = new File("dir/file.html");
        f.getParentFile().mkdirs();
        f.createNewFile();
        BufferedWriter b = new BufferedWriter (new FileWriter(f));
        b.write("data");
        b.close();
    }

    /**
     * Delete the file created
     */
    @AfterEach
    void AfterEach(){
        new File("dir/file.html").delete();
        new File("dir").delete();
    }

}