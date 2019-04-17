package it.unifi.rc.httpserver.m5979949;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * A class that reads {@link HTTPRequest}/{@link HTTPReply} from a given {@link InputStream}.
 */
public class MyHTTPInputStream extends HTTPInputStream {

    /**
     * {@link InputStream} used to read HTTP request/ reply.
     */
    private InputStream is;

    /**
     * Creates a new instance that uses <code>is</code>.
     *
     * @param is {@link InputStream} used to retrieves data.
     */
    public MyHTTPInputStream(InputStream is) {
        super(is);
    }

    /**
     * Set internal data structures to refer to a given InputStream.
     *
     * @param is <code>InputStream</code> used to retrieves data.
     */
    @Override
    protected void setInputStream(InputStream is) {
        this.is = is;
    }

    /**
     * Get {@link InputStream} used to read request and reply.
     * @return an {@link InputStream}.
     */
    public InputStream getIs() {
        return is;
    }

    /**
     * Reads next {@link HTTPRequest} from the inner {@link InputStream}.
     *
     * @return a {@link HTTPRequest} read from the inner {@link InputStream}.
     * @throws HTTPProtocolException
     */
    @Override
    public HTTPRequest readHttpRequest() throws HTTPProtocolException {
        Map<String, String> parameters;
        String entityBody = null;
        String[] requestLine;
        String str;
        String length;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if ((str = reader.readLine()) != null){
                requestLine =  str.split(" ");
                parameters = getParameters(reader);
                length = parameters.get("Content-Length");
                if (length != null){
                    entityBody = getEntityBody(reader,Integer.parseInt(length)-1);
                }
                return new MyHTTPRequest(requestLine[2],requestLine[0],requestLine[1],entityBody,parameters);
            }
        } catch (IOException e) {
            throw new HTTPProtocolException(e);
        }
        return null;
    }

    /**
     * Get all the lines of the request header.
     * @param reader a {@link BufferedReader} to read parameters.
     * @return a {@link Map<String, String>} that contains the request header.
     * @throws HTTPProtocolException
     */
    private Map<String, String> getParameters(BufferedReader reader) throws HTTPProtocolException {
        Map<String, String> parameters= new HashMap<>();
        String line;
        try {
            while(!(line = reader.readLine()).equals("")){
                String[] split = line.split(": ");
                parameters.put(split[0],split[1]);
            }
        } catch (IOException e) {
            throw new HTTPProtocolException(e);
        }
        return parameters;
    }

    /**
     * Get all the lines of the entity body of the request.
     * @param reader a {@link BufferedReader} to the entity body.
     * @param length number of bytes to read.
     * @return string read.
     * @throws IOException if an error occurs.
     */
    private String getEntityBody(BufferedReader reader,int length) throws HTTPProtocolException {
        char[] arr = new char[length+1];
        String entityBody = "";
        try {
            reader.read(arr,0,length+1);
            for (int i = 0; i < length+1; i++) {
                entityBody += arr[i];
            }
        } catch (IOException e) {
            throw new HTTPProtocolException(e);
        }
        return entityBody;
    }

    /**
     * Reads next {@link HTTPReply} from the inner {@link InputStream}.
     *
     * @return a {@link HTTPReply} read from the inner {@link InputStream}.
     */
    @Override
    public HTTPReply readHttpReply() throws HTTPProtocolException {
        return null;
    }

    /**
     * Closes this input stream and releases any system resources associated with the stream.
     *
     * @throws IOException if an error occurs.
     */
    @Override
    public void close() throws HTTPProtocolException {
        try {
            this.is.close();
        } catch (IOException e) {
            throw new HTTPProtocolException(e);
        }
    }


}
