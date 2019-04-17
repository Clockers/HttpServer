package it.unifi.rc.httpserver.m5979949;

import java.util.Map;

/**
 * A class describing an HTTP Requests.
 */
public class MyHTTPRequest implements HTTPRequest {

    /**
     * Parameters of the HTTP Request.
     */
    private String version;
    private String method;
    private String path;
    private String entityBody;
    private Map<String, String> parameters;

    /**
     * Creates a request passing all the parameters.
     * @param version version of the request.
     * @param method http method.
     * @param path path of the request file.
     * @param entityBody body of the request.
     * @param parameters header parameters.
     */
    public MyHTTPRequest(String version, String method, String path, String entityBody, Map<String, String> parameters) {
        this.version = version;
        this.method = method;
        this.path = path;
        this.entityBody = entityBody;
        this.parameters = parameters;
    }

    /**
     * Overide of the toString method for return the request
     * @return String of the request
     */
    @Override
    public String toString() {
        final String[] str = {method + " " + path + " " + version + "\r\n"};
        if (parameters != null){
            parameters.forEach((a, b) -> str[0] += a + ": " + b + "\r\n");
        }
        str[0] += "\r\n";
        if(entityBody != null){
            str[0] +=  entityBody;
        }
        return str[0];
    }

    /**
     * HTTP version in request line (can return either "HTTP/1.0" or "HTTP/1.1").
     *
     * @return HTTP version in request line.
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * HTTP Method in request line.
     *
     * @return HTTP Method in request line.
     */
    @Override
    public String getMethod() {
        return method;
    }

    /**
     * Path in request line.
     *
     * @return Path in request line.
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * Entity body.
     *
     * @return Entity body.
     */

    @Override
    public String getEntityBody() {
        return entityBody;
    }

    /**
     * Return a Map that associates each key in the header lines with the corresponding value.
     *
     * @return a Map
     */
    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }
}
