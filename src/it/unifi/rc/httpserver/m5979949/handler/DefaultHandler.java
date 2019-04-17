package it.unifi.rc.httpserver.m5979949.handler;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;

/**
 * Class that handle all the request as error 501
 */
public class DefaultHandler implements HTTPHandler {

    /**
     * Empty constructor
     */
    public DefaultHandler() {
    }

    /**
     * Handle the HTTPRequest with a default error.
     *
     * @param request request.
     * @return an error HTTPReply.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) {
        HTTPReply httpReply = new MyHTTPReply(request.getVersion(),"501","Not Implemented",null,null);
        return httpReply;
    }
}
