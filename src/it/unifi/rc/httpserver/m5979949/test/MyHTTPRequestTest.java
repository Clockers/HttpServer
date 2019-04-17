package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test of the MyHTTPRequest class
 */
class MyHTTPRequestTest {

    HTTPRequest testedRequest;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        Map<String, String> params = new HashMap<>();
        params.put("Host", "host.com");
        params.put("Content-Type", "text/html");
        params.put("Content-Length", "16");
        testedRequest = new MyHTTPRequest("HTTP/1.1", "PUT", "/test/file.html", "test entity body", params);
    }

    /**
     * Test toString of an HTTPRequest
     */
    @Test
    void toStringTest() {
        String result = "PUT /test/file.html HTTP/1.1\r\n" +
                "Host: host.com\r\n" +
                "Content-Length: 16\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "test entity body";
        Assertions.assertTrue(result.equals(testedRequest.toString()));
    }

    /**
     * Test Version of an HTTPRequest
     */
    @Test
    void getVersion() {
        Assertions.assertEquals("HTTP/1.1", testedRequest.getVersion());
    }

    /**
     * Test Method of an HTTPRequest
     */
    @Test
    void getMethod() {
        Assertions.assertEquals("PUT", testedRequest.getMethod());
    }

    /**
     * Test Path of an HTTPRequest
     */
    @Test
    void getPath() {
        Assertions.assertEquals("/test/file.html", testedRequest.getPath());
    }

    /**
     * Test EntityBody of an HTTPRequest
     */
    @Test
    void getEntityBody() {
        Assertions.assertEquals("test entity body", testedRequest.getEntityBody());
    }

    /**
     * Test Parameters of an HTTPRequest
     */
    @Test
    void getParameters() {
        Map<String, String> params = testedRequest.getParameters();
        Assertions.assertEquals(3, params.size());
        Assertions.assertEquals("16", params.get("Content-Length"));
        Assertions.assertEquals("text/html", params.get("Content-Type"));
        Assertions.assertEquals("host.com", params.get("Host"));
    }

}