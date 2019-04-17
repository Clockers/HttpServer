package it.unifi.rc.httpserver.m5979949.method;

import it.unifi.rc.httpserver.m5979949.HTTPProtocolException;
import it.unifi.rc.httpserver.m5979949.HTTPReply;
import it.unifi.rc.httpserver.m5979949.HTTPRequest;
import it.unifi.rc.httpserver.m5979949.MyHTTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Generic method used to handle the request.
 */
public abstract class Method {

    /**
     * Parameters required by a method.
     */
    private File root;
    private String host;

    /**
     * Constants
     */
    private final String SERVERTYPE = "Salani/5979949";
    protected final String OK = "200";
    protected final String CREATED = "201";
    protected final String BADREQUEST = "400";
    protected final String NOTFOUND = "404";
    protected final String VERSIONNOTSUPPORTED = "505";

    /**
     * Constructor that require the root File and the host
     * @param host host String
     * @param root root File
     */
    public Method(String host, File root) {
        this.root = root;
        this.host = host;
    }

    /**
     * Handle a given HTTPRequest with the specific method, returns a HTTPReply. If this
     * method is not able to manage the request, value null is returned.
     *
     * @param request request to manage.
     * @return a HTTPReply or null.
     * @throws HTTPProtocolException
     */
    public abstract HTTPReply handle(HTTPRequest request) throws HTTPProtocolException;

    /**
     * Method for get a Map that contains a default header for a reply
     * @param length length of the body
     * @return a Map of header line
     */
    protected Map<String, String> getDefaultParams(int length) {
        Map<String,String> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        String nowDate = getDate(calendar.getTimeInMillis());
        params.put("Date", nowDate);
        params.put("Server", SERVERTYPE);
        if(length!=0){
            params.put("Content-Type", "text/html");
            params.put("Content-Length", "" + length);
        }
        return params;
    }

    /**
     * Method for get the message referred to the statusCode
     * @param statusCode status code
     * @return status message
     */
    protected String getMessage(String statusCode) {
        String message;
        switch(statusCode){
            case OK :
                message = "OK";
                break;
            case BADREQUEST :
                message = "Bad Request";
                break;
            case NOTFOUND :
                message = "Not Found";
                break;
            case VERSIONNOTSUPPORTED :
                message = "HTTP Version Not Supported";
                break;
            case CREATED :
                message = "Created";
                break;
            default:
                message = null;
        }
        return message;
    }

    /**
     * Read a File and return the String readed
     * @param file file to read
     * @return String readed
     * @throws HTTPProtocolException
     */
    protected String getData(File file) throws HTTPProtocolException {
        String data = "";
        try {
            BufferedReader reader =  new BufferedReader(new FileReader(file));
            int chr;
            while((chr = reader.read()) != -1){
                data += (char) chr;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new HTTPProtocolException(e);
        } catch (IOException e) {
            throw new HTTPProtocolException("Error reading file");
        }
        return data;
    }

    /**
     * Get the file in path, starting from root directory
     * @param path path used to get the file
     * @return File founded
     */
    protected File getFileFromRoot(String path) {
        File file = new File(root.getPath()+path);
        return file;
    }

    /**
     * Method used to get an error HTTPResponse when an error occur.
     * @param statusCode status code of the error
     * @param version version of Http
     * @return an error reply
     */
    protected HTTPReply getReplyForError(String statusCode, String version) {
        return new MyHTTPReply(version,statusCode,getMessage(statusCode),null,null);
    }

    /**
     * Method that check if the host and the version of the request are valid
     * @param map header of the request
     * @param version version of http
     * @return status code or null if the this host can't handle this request
     */
    protected String checkRequest(Map<String, String> map, String version) {
        String code = OK;
        if (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")){
            code = VERSIONNOTSUPPORTED;
        }
        //This server is not able to handle the request for another host
        if(map.get("Host") != null && host != null && !map.get("Host").equals(host) ) {
            code = null;
        }
        //Host is required in HTTP1.1 request
        if(version.equals("HTTP/1.1")){
            if(map.get("Host") == null) {
                code = BADREQUEST;
            }
        }
        return code;
    }

    /**
     * Method used to write the String body in a file.
     * @param file file to write
     * @param body string to write
     * @throws HTTPProtocolException
     */
    protected void storeFile(File file, String body) throws HTTPProtocolException {
        try {
            //If the directory not exist, create it
            file.getParentFile().mkdirs();
            BufferedWriter writer;
            writer=new BufferedWriter (new FileWriter(file));
            if (body != null)
                writer.write(body);
            writer.close();
        } catch (IOException e) {
            throw new HTTPProtocolException("Error storing file");
        }
    }

    /**
     * Method used for delete a directory
     * @param file directory to delete
     */
    protected void deleteDirectory(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirectory(f);
            }
        }
        //Elimination of the folder only if is not the root folder
        if (!file.getPath().equals(root.getPath())) file.delete();
    }

    /**
     * Get the date based on the millisecond passed as parameters, the date is formatted as HTTP format
     * @param millis date in millisecond
     * @return date as string
     */
    protected String getDate(long millis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date(millis));
    }

    /**
     * Check if the connection needs to be kept open
     * @param version version of HTTP
     * @param connection value of the element "Connection" in the header of the request
     * @return status of the connection
     */
    protected boolean isConnectionPersistent(String version, String connection){
        boolean res = false;
        if (version.equals("HTTP/1.0")){
            if(connection != null){
                res = connection.equals("keep-alive");
            }
        }
        if(version.equals("HTTP/1.1")){
            if(connection != null){
                res = !connection.equals("close");
            }else{
                res = true;
            }
        }
        return res;
    }

}
