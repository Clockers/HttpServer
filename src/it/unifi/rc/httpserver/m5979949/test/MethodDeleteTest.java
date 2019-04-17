package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.method.MethodDelete;
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
 * Test of the MethodDelete class
 */
class MethodDeleteTest {

    MethodDelete method;
    HTTPRequest httpRequest;
    HTTPReply expectedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp(){
        method = new MethodDelete(null,new File("dir"));
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Host","localhost");
        httpRequest = new MyHTTPRequest("HTTP/1.1","DELETE","/file.html",null,parameters);
    }

    /**
     * Test if the file is deleted
     */
    @AfterEach
    void AfterEach(){
        File f = new File("dir/file.html");
        assertFalse(f.exists());
    }

    /**
     * Test the reply of a correct DELETE request
     */
    @Test
    void handleDelete() throws IOException {
        createFile();
        expectedReply = new MyHTTPReply("HTTP/1.1","200","OK","data",null);
        HTTPReply httpReply = method.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals("Salani/5979949",httpReply.getParameters().get("Server"));
        assertEquals("/file.html",httpReply.getParameters().get("Content-Location"));
    }

    /**
     * Test the reply of a not found DELETE request
     */
    @Test
    void handleNotFound() throws IOException {
        expectedReply = new MyHTTPReply("HTTP/1.1","404","Not Found",null,null);
        HTTPReply httpReply = method.handle(httpRequest);
        assertEquals(expectedReply.getVersion(),httpReply.getVersion());
        assertEquals(expectedReply.getStatusCode(),httpReply.getStatusCode());
        assertEquals(expectedReply.getStatusMessage(),httpReply.getStatusMessage());
        assertEquals(expectedReply.getData(),httpReply.getData());
        assertEquals(null,httpReply.getParameters());
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