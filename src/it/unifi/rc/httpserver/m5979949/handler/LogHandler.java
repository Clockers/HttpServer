package it.unifi.rc.httpserver.m5979949.handler;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class that handle the request write a log
 */
public class LogHandler implements HTTPHandler {

    /**
     * Empty constructor
     */
    public LogHandler() {
    }

    /**
     * Handle the HTTPRequest writing it on a file.
     *
     * @param request request.
     * @return null.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        try {
            BufferedWriter b =new BufferedWriter (new FileWriter("log.txt",true));
            b.write(request.toString());
            b.write("\r\n");
            b.close();
        } catch (IOException e) {
            throw new HTTPProtocolException("Error appending to log file");
        }
        return null;
    }
}
