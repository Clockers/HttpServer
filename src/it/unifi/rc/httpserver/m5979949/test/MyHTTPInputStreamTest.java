package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Test of the MyHTTPInputStream class
 */
class MyHTTPInputStreamTest {

    MyHTTPInputStream myHTTPInputStream;
    InputStream inputStream;
    String request = "PUT /test/file.html HTTP/1.1\r\n" +
            "Host: host.com\r\n" +
            "Content-Length: 16\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "test entity body";

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        try {
            inputStream = new ByteArrayInputStream(request.getBytes("US-ASCII"));
        } catch (UnsupportedEncodingException e) {
            Assertions.assertTrue(false);
        }
        myHTTPInputStream = new MyHTTPInputStream(inputStream);
    }

    /**
     * Test the reading from the HTTPInputStream
     */
    @Test
    void readHttpRequest() {
        try {
            HTTPRequest testedRequest = myHTTPInputStream.readHttpRequest();
            Assertions.assertTrue(request.equals(testedRequest.toString()));
            Assertions.assertEquals("HTTP/1.1", testedRequest.getVersion());
            Assertions.assertEquals("PUT", testedRequest.getMethod());
            Assertions.assertEquals("/test/file.html", testedRequest.getPath());
            Assertions.assertEquals("test entity body", testedRequest.getEntityBody());
            Map<String, String> params = testedRequest.getParameters();
            Assertions.assertEquals(3, params.size());
            Assertions.assertEquals("16", params.get("Content-Length"));
            Assertions.assertEquals("text/html", params.get("Content-Type"));
            Assertions.assertEquals("host.com", params.get("Host"));
        } catch (IOException e) {
            Assertions.assertTrue(false);
        }
    }

    /**
     * Test closing of the HTTPInputStream
     */
    @Test
    void close() {
        try {
            inputStream.close();
            Assertions.assertTrue(true);
        } catch (IOException e) {
            Assertions.assertTrue(false);
        }
    }

}