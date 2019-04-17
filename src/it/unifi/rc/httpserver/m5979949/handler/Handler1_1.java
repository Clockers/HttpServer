package it.unifi.rc.httpserver.m5979949.handler;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.method.Method;
import it.unifi.rc.httpserver.m5979949.method.MethodDelete;
import it.unifi.rc.httpserver.m5979949.method.MethodPut;

import java.io.File;

/**
 * Class that handle 1.1 HTTPRequest.
 */
public class Handler1_1 extends Handler1_0 {

    /**
     * Constructor that requires only a File.
     * @param root File root.
     */
    public Handler1_1(File root) {
        super(root);
    }

    /**
     * Constructor that requires a File and an Host.
     * @param host String host.
     * @param root File root.
     */
    public Handler1_1(String host, File root) {
        super(host, root);
    }

    /**
     * Handle a given PUT or DELETE HTTPRequest and returns a HTTPReply. If this
     * handler is not able to manage PUT or DELETE request, send the request to the superclass.
     *
     * @param request an HTTPRequest
     * @return a HTTPReply that is null if this handler cannot manage the request.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        Method methodUsed;
        String method = request.getMethod();
        if(request.getVersion().equals("HTTP/1.1")) {
            switch (method) {
                case "PUT":
                    methodUsed = new MethodPut(host, root);
                    break;
                case "DELETE":
                    methodUsed = new MethodDelete(host, root);
                    break;
                default:
                    return super.handle(request);
            }
        }else{
            return super.handle(request);
        }
        HTTPReply reply = methodUsed.handle(request);
        return reply;
    }
}


