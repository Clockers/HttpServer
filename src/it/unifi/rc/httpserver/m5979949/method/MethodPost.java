package it.unifi.rc.httpserver.m5979949.method;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;

import java.io.File;

/**
 * Class that identify the POST method.
 */
public class MethodPost extends MethodGet{

    /**
     * Constructor that call the super
     * @param host host String
     * @param root root File
     */
    public MethodPost(String host, File root) {
        super(host, root);
    }

    /**
     * Handle a POST HTTPRequest
     * @param request request to manage.
     * @return a HTTPReply or null.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        HTTPReply reply = super.handle(request);
        if (request.getEntityBody() != null) {
            System.out.println("Processing POST data: " + request.getEntityBody());
        }
        return reply;
    }

}
