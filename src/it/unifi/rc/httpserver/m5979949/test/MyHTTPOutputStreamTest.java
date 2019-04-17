package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.MyHTTPOutputStream;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the MyHTTPOutputStream class
 */
class MyHTTPOutputStreamTest {

    MyHTTPOutputStream myHTTPOutputStream;
    ByteArrayOutputStream outputStream;
    HTTPReply testedReply;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        myHTTPOutputStream = new MyHTTPOutputStream(outputStream);

        Map<String, String> params = new HashMap<>();
        params.put("Last-Modified", "Wed, 21 Oct 2017 07:28:00 GMT");
        params.put("Date", "Wed, 21 Oct 2017 07:47:00 GMT");
        params.put("Content-Type", "text/html");
        params.put("Content-Length", "9");
        testedReply = new MyHTTPReply("HTTP/1.0", "200", "OK", "test data", params);

    }

    /**
     * Test writing on the HTTPOutputStream
     */
    @Test
    void writeHttpReply() {
        try {
            myHTTPOutputStream.writeHttpReply(testedReply);
        } catch (IOException e) {
            Assertions.assertTrue(false);
        }
        String result = "HTTP/1.0 200 OK\r\n" +
                "Last-Modified: Wed, 21 Oct 2017 07:28:00 GMT\r\n" +
                "Content-Length: 9\r\n" +
                "Date: Wed, 21 Oct 2017 07:47:00 GMT\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "test data";
        assertTrue(result.equals(new String(outputStream.toByteArray())));
    }

    /**
     * Test closing of the HTTPInputStream
     */
    @Test
    void close() {
        try {
            outputStream.close();
            Assertions.assertTrue(true);
        } catch (IOException e) {
            Assertions.assertTrue(false);
        }

    }


}