package it.unifi.rc.httpserver.m5979949.method;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;

import java.io.*;

/**
 * Class that identify the GET method.
 */
public class MethodGet extends MethodHead {

    /**
     * Constructor that call the super
     * @param host host String
     * @param root root File
     */
    public MethodGet(String host, File root) {
        super(host, root);
    }

    /**
     * Handle a GET HTTPRequest
     * @param request request to manage.
     * @return a HTTPReply or null.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        HTTPReply header = super.handle(request);
        if(header != null)
            return new MyHTTPReply(header.getVersion(),header.getStatusCode(),header.getStatusMessage(),data,header.getParameters());
        return null;
    }
}
