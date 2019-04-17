package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.method.MethodGet;
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
 * Test of the MethodGet class
 */
class MethodGetTest {

    MethodGet method;
    HTTPRequest httpRequest;
    HTTPReply expectedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() throws IOException {
        method = new MethodGet(null,new File("dir"));
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Accept","text/html");
        parameters.put("Host","localhost");
        httpRequest = new MyHTTPRequest("HTTP/1.1","GET","/file.html",null,parameters);
        createFile();
    }

    /**
     * Delete the file
     */
    @AfterEach
    void AfterEach(){
        new File("dir/file.html").delete();
        new File("dir").delete();
    }

    /**
     * Test the reply of a GET request
     */
    @Test
    void handle() throws HTTPProtocolException {
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK","data",null);
        HTTPReply httpReply = method.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("text/html",httpReply.getParameters().get("Content-Type"));
        assertEquals("4",httpReply.getParameters().get("Content-Length"));
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


}