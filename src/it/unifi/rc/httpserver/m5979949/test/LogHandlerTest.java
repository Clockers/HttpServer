package it.unifi.rc.httpserver.m5979949.test;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.handler.LogHandler;
import it.unifi.rc.httpserver.m5979949.MyHTTPRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the LogHandler class
 */
class LogHandlerTest {

    private HTTPHandler handler;
    private HTTPRequest httpRequest;

    /**
     * Set up the objects for testing
     */
    @BeforeEach
    void setUp() {
        handler = new LogHandler();
        httpRequest = new MyHTTPRequest("HTTP/1.0", "GET", "/file.html", null, null);
    }

    /**
     * Delete the file created
     */
    @Test
    void handle() throws HTTPProtocolException {
        File f = new File("log.txt");
        f.delete();
        HTTPReply httpReply = handler.handle(httpRequest);
        String res = readFile(f);
        assertEquals(null, httpReply);
        assertEquals("GET /file.html HTTP/1.0\r\n\r\n\r\n", res);
    }

    /**
     * Method used to read a file
     *
     * @param file file to read
     * @return string readed
     */
    protected String readFile(File file) {
        String data = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int chr;
            while ((chr = reader.read()) != -1) {
                data += (char) chr;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}