package it.unifi.rc.httpserver.m5979949;

import java.util.Map;

/**
 * An class that represents HTTP Requests.
 */
public class MyHTTPReply implements HTTPReply {

    /**
     * Parameters of the HTTP Reply.
     */
    private String version;
    private String statusCode;
    private String statusMessage;
    private String data;
    private Map<String, String> parameters;

    /**
     * Creates a reply passing all the parameters.
     * @param version version of the reply.
     * @param statusCode code .
     * @param statusMessage message.
     * @param data body of the reply.
     * @param parameters header parameters.
     */
    public MyHTTPReply(String version, String statusCode, String statusMessage, String data, Map<String, String> parameters) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
        this.parameters = parameters;
    }

    /**
     * Overide of the toString method for return the reply
     * @return String of the reply
     */
    @Override
    public String toString() {
        final String[] str = {version + " " + statusCode + " " + statusMessage + "\r\n"};
        if(parameters != null){
            parameters.forEach((a, b) -> str[0] += a + ": " + b + "\r\n" );
        }
        str[0] +=  "\r\n";
        if(data != null){
            str[0] +=  data;
        }
        return str[0];
    }

    /**
     * HTTP version in status line (can return either "HTTP/1.0" or "HTTP/1.1").
     *
     * @return HTTP version in status line.
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * HTTP Status Code.
     *
     * @return HTTP Status Code.
     */
    @Override
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * HTTP Status Message.
     *
     * @return HTTP Status Message.
     */
    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Reply data.
     *
     * @return Reply data.
     */
    @Override
    public String getData() {
        return data;
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
