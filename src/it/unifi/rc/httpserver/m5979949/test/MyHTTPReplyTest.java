package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test of the MyHTTPReply class
 */
class MyHTTPReplyTest {

    HTTPReply testedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        Map<String, String> params = new HashMap<>();
        params.put("Last-Modified", "Wed, 21 Oct 2017 07:28:00 GMT");
        params.put("Date", "Wed, 21 Oct 2017 07:47:00 GMT");
        params.put("Content-Type", "text/html");
        params.put("Content-Length", "9");
        testedReply = new MyHTTPReply("HTTP/1.0", "200", "OK", "test data", params);
    }

    /**
     * Test toString of an HTTPReply
     */
    @Test
    void toStringTest() {
        String result = "HTTP/1.0 200 OK\r\n" +
                "Last-Modified: Wed, 21 Oct 2017 07:28:00 GMT\r\n" +
                "Content-Length: 9\r\n" +
                "Date: Wed, 21 Oct 2017 07:47:00 GMT\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "test data";
        Assertions.assertTrue(result.equals(testedReply.toString()));
    }

    /**
     * Test Version of an HTTPReply
     */
    @Test
    void getVersion() {
        Assertions.assertEquals("HTTP/1.0", testedReply.getVersion());
    }

    /**
     * Test StatusCode of an HTTPReply
     */
    @Test
    void getStatusCode() {
        Assertions.assertEquals("200", testedReply.getStatusCode());
    }

    /**
     * Test StatusMessage of an HTTPReply
     */
    @Test
    void getStatusMessage() {
        Assertions.assertEquals("OK", testedReply.getStatusMessage());
    }

    /**
     * Test Data of an HTTPReply
     */
    @Test
    void getData() {
        Assertions.assertEquals("test data", testedReply.getData());
    }

    /**
     * Test Parameters of an HTTPReply
     */
    @Test
    void getParameters() {
        Map<String, String> params = testedReply.getParameters();
        Assertions.assertEquals(4, params.size());
        Assertions.assertEquals("9", params.get("Content-Length"));
        Assertions.assertEquals("text/html", params.get("Content-Type"));
        Assertions.assertEquals("Wed, 21 Oct 2017 07:47:00 GMT", params.get("Date"));
        Assertions.assertEquals("Wed, 21 Oct 2017 07:28:00 GMT", params.get("Last-Modified"));
    }

}