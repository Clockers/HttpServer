package it.unifi.rc.httpserver.m5979949.method;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that identify the DELETE method.
 */
public class MethodDelete extends Method {

    /**
     * Constructor that call the super
     * @param host host String
     * @param root root File
     */
    public MethodDelete(String host, File root) {
        super(host, root);
    }

    /**
     * Handle a DELETE HTTPRequest
     * @param request request to manage.
     * @return a HTTPReply or null.
     */
    @Override
    public HTTPReply handle(HTTPRequest request) throws HTTPProtocolException {
        String statusCode;
        Map<String, String> params = new HashMap<>();
        String data = "";
        File file;
        //Check if the request is valid
        statusCode = checkRequest(request.getParameters(), request.getVersion());
        if (statusCode == null) {
            //This request can't be managed by this host
            return null;
        }
        if(!statusCode.equals(OK))
            return getReplyForError(statusCode,request.getVersion());

        file = getFileFromRoot(request.getPath());
        if (file.exists()) {
            statusCode = OK;
        } else {
            statusCode = NOTFOUND;
        }
        if(!statusCode.equals(OK))
            return getReplyForError(statusCode,request.getVersion());

        if(file.isFile()){
            data = getData(file);
            file.delete();
            params.putAll(getDefaultParams(data.length(),request.getPath()));
        }else{
            deleteDirectory(file);
        }

        boolean persistence = isConnectionPersistent(request.getVersion(),request.getParameters().get("Connection"));
        if(!persistence){
            params.put("Connection","close");
        }

        return new MyHTTPReply(request.getVersion(), statusCode, getMessage(statusCode), data, params);
    }

    /**
     * Method that returns the default header for this method
     * @param length length of the data
     * @param path path of the file managed
     * @return a Map of header line
     */
    private Map<String, String> getDefaultParams(int length,String path) {
        Map<String,String> params = new HashMap<>();
        params.putAll(super.getDefaultParams(length));
        params.put("Content-Location", path);
        return params;
    }

}
