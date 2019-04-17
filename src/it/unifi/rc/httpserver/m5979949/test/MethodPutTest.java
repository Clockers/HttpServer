package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.method.MethodPut;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;
import it.unifi.rc.httpserver.m5979949.MyHTTPRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the MethodPut class
 */
class MethodPutTest {

    MethodPut method;
    HTTPRequest httpRequest;
    HTTPReply expectedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        method = new MethodPut(null,new File("dir"));
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Content-type","text/html");
        parameters.put("Content-Length","4");
        parameters.put("Host","localhost");
        httpRequest = new MyHTTPRequest("HTTP/1.1","PUT","/file.html","body",parameters);
    }

    /**
     * Test if the file is deleted
     */
    @AfterEach
    void AfterEach(){
        File f = new File("dir/file.html");
        assertTrue(f.exists());
        f.delete();
        f.getParentFile().delete();
    }

    /**
     * Test the reply of a PUT request
     */
    @Test
    void handleCreated() throws HTTPProtocolException {
        expectedReply = new MyHTTPReply("HTTP/1.1","201","Created",null,null);
        HTTPReply httpReply = method.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("/file.html",httpReply.getParameters().get("Content-Location"));
    }

    /**
     * Test the reply of a PUT request
     */
    @Test
    void handleOk() throws IOException {
        File f = new File("dir/file.html");
        f.getParentFile().mkdirs();
        f.createNewFile();
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK",null,null);
        HTTPReply httpReply = method.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("/file.html",httpReply.getParameters().get("Content-Location"));
    }

}