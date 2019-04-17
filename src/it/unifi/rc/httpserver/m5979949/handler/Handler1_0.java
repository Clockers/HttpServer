package it.unifi.rc.httpserver.m5979949.handler;

import it.unifi.rc.httpserver.m5979949.HTTPHandler;
import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.method.Method;
import it.unifi.rc.httpserver.m5979949.method.MethodGet;
import it.unifi.rc.httpserver.m5979949.method.MethodHead;
import it.unifi.rc.httpserver.m5979949.method.MethodPost;

import java.io.File;

/**
 * Class that handle 1.0 HTTPRequest.
 */
public class Handler1_0 implements HTTPHandler {

    /**
     * Parameters of the HTTPHandler.
     */
    protected File root;
    protected String host;

    /**
     * Constructor that requires only a File.
     * @param root File root.
     */
    public Handler1_0(File root) {
        this.root = root;
        this.host = null;
    }

    /**
     * Constructor that requires a File and an Host.
     * @param host String host.
     * @param root File root.
     */
    public Handler1_0(String host, File root) {
        this.root = root;
        this.host = host;
    }

    /**
     * Handle a given GET, POST or HEAD HTTPRequest and returns a HTTPReply. If this
     * handler is not able to manage the request, value null is returned.
     *
     * @param request an HTTPRequest
     * @return a HTTPReply that is null if this handler cannot manage the request.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        Method methodUsed;
        String method = request.getMethod();
        switch(method){
            case "GET" :
                methodUsed = new MethodGet(host, root);
                break;
            case "POST" :
                methodUsed = new MethodPost(host, root);
                break;
            case "HEAD":
                methodUsed = new MethodHead(host, root);
                break;
            default:
                return null;
        }
        HTTPReply reply = methodUsed.handle(request);
        return reply;
    }

    /**
     * Get the File of the root.
     * @return File root.
     */
    public File getRoot() {
        return root;
    }

    /**
     * Get the String of the host.
     * @return String host.
     */
    public String getHost() {
        return host;
    }

}


