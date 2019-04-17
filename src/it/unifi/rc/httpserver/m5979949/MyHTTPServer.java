package it.unifi.rc.httpserver.m5979949;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * A HTTP Server.
 *
 */
public class MyHTTPServer implements HTTPServer {

    /**
     * Identifiers of an HttpServer
     */
    private List<HTTPHandler> handlers;
    private int port;
    private int backlog;
    private InetAddress address;

    /**
     * Variables for thread management
     */
    private List<MyHTTPThread> threads;
    private boolean serverOnline = true;
    private final int TIMEOUT = 20000;

    /**
     * Constructor allowing to specify port, backlog and address
     * @param port server port
     * @param backlog server backlog
     * @param address server address
     */
    public MyHTTPServer(int port, int backlog, InetAddress address) {
        this.port = port;
        this.backlog = backlog;
        this.address = address;
        handlers = new LinkedList<>();
        threads = new LinkedList<>();
    }

    /**
     * Adds a new handler in the server. Each server has a list of {@link HTTPHandler}. A request is
     * handled by the first handler that is able to handle it.
     *
     * @param handler an {@link HTTPHandler}.
     */
    @Override
    public void addHandler(HTTPHandler handler) {
        handlers.add(handler);
    }

    /**
     * Start the server.
     */
    @Override
    public void start(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, backlog, address);
        } catch (IOException e) {
            System.out.println("Unable to start server at port " + port + ", address " + address);
        }
        System.out.println("Server started");
        try {
            while (serverOnline) {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(TIMEOUT);
                MyHTTPThread myThread = new MyHTTPThread(socket,handlers);
                threads.add(myThread);
                myThread.start();
            }
        } catch (IOException e) {
            System.out.println("Unable to start server at port " + port + ", address " + address);
        }
        try {
            serverSocket.close();
            System.out.println("Server stopped");
        } catch (IOException e) {
            System.out.println("Error stopping server socket");
        }
    }

    /**
     * Stops the server.
     */
    @Override
    public void stop() {
        serverOnline = false;
        for (MyHTTPThread thread :
                threads) {
            thread.setThreadOnline(false);
        }
    }

    /**
     * Return the list of {@link HTTPHandler}
     * @return list of {@link HTTPHandler}
     */
    public List<HTTPHandler> getHandlers() {
        return handlers;
    }

    /**
     * Return the port of the server
     * @return port of the server
     */
    public int getPort() {
        return port;
    }

    /**
     * Return the backlog of the server
     * @return backlog of the server
     */
    public int getBacklog() {
        return backlog;
    }

    /**
     * Return the address of the server
     * @return InetAddress of the server
     */
    public InetAddress getAddress() {
        return address;
    }

}
