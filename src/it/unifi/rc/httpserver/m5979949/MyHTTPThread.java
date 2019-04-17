package it.unifi.rc.httpserver.m5979949;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Class identifies a Thread that handle a request of a specific client
 */
public class MyHTTPThread extends Thread {

    /**
     * Identifiers of an HttpThread
     */
    private Socket socket;
    private List<HTTPHandler> handlers;
    private boolean threadOnline;
    private HTTPInputStream myHTTPInputStream = null;
    private HTTPOutputStream myHTTPOutputStream = null;

    /**
     * Constructor allowing to specify socket and list of {@link HTTPHandler}
     * @param socket socket used fo comunicate
     * @param handlers list of handlers
     */
    public MyHTTPThread(Socket socket, List<HTTPHandler> handlers) {
        super();
        this.socket = socket;
        this.handlers = handlers;
    }

    /**
     * Main method of the thread
     */
    public void run() {
        System.out.println("Thread started");
        threadOnline = true;
        try {
            initStreams();
            HTTPReply reply;
            while(threadOnline) {
                HTTPRequest request = myHTTPInputStream.readHttpRequest();
                if (request == null) continue;
                //Handle the request
                for (HTTPHandler handler : handlers) {
                    if ((reply = handler.handle(request)) != null) {
                        System.out.println("Request correctly accepted");
                        if(reply.getParameters() != null) {
                            String connection = reply.getParameters().get("Connection");
                            if (connection != null) {
                                threadOnline = false;
                                System.out.println("Connection closed");
                            } else {
                                System.out.println("Connection kept open");
                            }
                        }else{
                            threadOnline = false;
                            System.out.println("Connection closed");
                        }
                        myHTTPOutputStream.writeHttpReply(reply);
                        break;
                    }
                }
            }
        } catch (HTTPProtocolException e) {
            System.out.println("HTTPProtocol error: " + e.getMessage());
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout expired");
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            try {
                socket.close();
                if (myHTTPInputStream != null) myHTTPInputStream.close();
                if (myHTTPOutputStream != null) myHTTPOutputStream.close();
                System.out.println("Thread stopped");
            } catch (IOException e) {
                System.out.println("Closing error");
            }
        }
    }

    /**
     * Method that inizialize the InputStream and OutputStream from socket
     */
    private void initStreams() throws HTTPProtocolException {
        InputStream is;
        OutputStream os;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            if(is != null && os != null) {
                myHTTPInputStream = new MyHTTPInputStream(is);
                myHTTPOutputStream = new MyHTTPOutputStream(os);
            }else{
                throw new HTTPProtocolException("Error creating streams");
            }
        } catch (IOException e) {
            threadOnline = false;
            throw new HTTPProtocolException("Error creating streams");
        }
    }

    /**
     * Method for set thread status
     * @param threadOnline thread status
     */
    public void setThreadOnline(boolean threadOnline) {
        this.threadOnline = threadOnline;
    }

}
