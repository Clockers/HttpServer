package it.unifi.rc.httpserver.m5979949.method;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;

import java.io.File;
import java.util.*;

/**
 * Class that identify the HEAD method.
 */
public class MethodHead extends Method {

    /**
     * Data of the file readed
     */
    protected String data;

    /**
     * Constructor that call the super
     * @param host host String
     * @param root root File
     */
    public MethodHead(String host, File root) {
        super(host, root);
    }

    /**
     * Handle a HEAD HTTPRequest
     * @param request request to manage.
     * @return a HTTPReply or null.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        Map<String,String> params = new HashMap<>();
        File file;
        //Check if the request is valid
        String statusCode = checkRequest(request.getParameters(),request.getVersion());
        if (statusCode == null){
            //This request can't be managed by this host
            return null;
        }

        file = getFileFromRoot(request.getPath());
        if(!file.exists()) {
            statusCode = NOTFOUND;
        }

        if(!statusCode.equals(OK))
            return getReplyForError(statusCode,request.getVersion());

        data = getData(file);

        boolean persistence = isConnectionPersistent(request.getVersion(),request.getParameters().get("Connection"));
        if(!persistence){
            params.put("Connection","close");
        }
        params.putAll(getDefaultParams(data.length(),file.lastModified()));
        return new MyHTTPReply(request.getVersion(),statusCode,getMessage(statusCode),null,params);
    }

    /**
     * Method that returns the default header for this method
     * @param length length of the data
     * @param lastModified millisecond for the date
     * @return a Map of header line
     */
    private Map<String, String> getDefaultParams(int length, long lastModified) {
        Map<String,String> params = new HashMap<>();
        params.putAll(super.getDefaultParams(length));
        params.put("Last-Modified", getDate(lastModified));
        return params;
    }
}
