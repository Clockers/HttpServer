package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_1;
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
class Handler1_1Test {

    Handler1_1 handler;
    HTTPReply expectedReply;
    HTTPRequest httpRequest;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() throws IOException {
        handler = new Handler1_1(null,new File("dir"));
    }

    /**
     * Delete the file created
     */
    @AfterEach
    void AfterEach(){
        File f = new File("dir/file.html");
        if(f.exists()){
            f.delete();
            f.getParentFile().delete();
        }

    }

    /**
     * Test the reply of a Handler1_0 for a PUT request
     */
    @Test
    void handlePut() throws HTTPProtocolException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Content-type","text/html");
        parameters.put("Content-Length","4");
        parameters.put("Host","localhost");
        httpRequest = new MyHTTPRequest("HTTP/1.1","PUT","/file.html","body",parameters);

        expectedReply = new MyHTTPReply("HTTP/1.1","201","Created",null,null);
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("/file.html",httpReply.getParameters().get("Content-Location"));
    }

    /**
     * Test the reply of a Handler1_0 for a DELETE request
     */
    @Test
    void handleDelete() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Host","localhost");
        httpRequest = new MyHTTPRequest("HTTP/1.1","DELETE","/file.html",null,parameters);

        createFile();
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK","data",null);
        HTTPReply httpReply = handler.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("/file.html",httpReply.getParameters().get("Content-Location"));
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



}