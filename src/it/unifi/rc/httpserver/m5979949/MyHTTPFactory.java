package it.unifi.rc.httpserver.m5979949;

import it.unifi.rc.httpserver.m5979949.handler.DefaultHandler;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_0;
import it.unifi.rc.httpserver.m5979949.handler.Handler1_1;
import it.unifi.rc.httpserver.m5979949.handler.LogHandler;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

/**
 * Class that provide the methods for creates instance of all the classes in the project'.
 */
public class MyHTTPFactory implements HTTPFactory {

    /**
     * Given an {@link InputStream} returns an instance of {@link HTTPInputStream} that can be used to read HTTP
     * requests/replies.
     *
     * @param is an {@link InputStream}.
     * @return an instance of {@link HTTPInputStream} that can be used to read HTTP.
     * requests/replies.
     */
    @Override
    public HTTPInputStream getHTTPInputStream(InputStream is) {
        return new MyHTTPInputStream(is);
    }

    /**
     * Given an {@link OutputStream} returns an instance of {@link HTTPOutputStream} that can be used to write HTTP
     * requests/replies.
     *
     * @param os an {@link OutputStream}.
     * @return an instance of {@link HTTPOutputStream} that can be used to write HTTP.
     * requests/replies.
     */
    @Override
    public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
        return new MyHTTPOutputStream(os);
    }

    /**
     * Creates a {@link HTTPServer} listening at address:port with a given backlog.
     * This server uses the handlers passed as parameters.
     *
     * @param port server port.
     * @param backlog server backlog.
     * @param address server address.
     * @param handlers HTTP handlers.
     * @return a {@link HTTPServer}.
     */
    public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
        MyHTTPServer server = new MyHTTPServer(port, backlog, address);
        server.addHandler(new LogHandler());
        for (HTTPHandler handler : handlers) {
            server.addHandler(handler);
        }
        server.addHandler(new DefaultHandler());
        return server;
    }

    /**
     * Creates a {@link HTTPHandler} that resolves data starting from directory <code>root</code>. The handler
     * implements HTTP/1.0 methods.
     *
     * @param root document root.
     * @return a {@link HTTPHandler} that resolves data starting from directory <code>root</code>.
     */
    @Override
    public HTTPHandler getFileSystemHandler1_0(File root) {
        return new Handler1_0(root);
    }

    /**
     * Creates a {@link HTTPHandler} that resolves data starting from directory <code>root</code> and handling requests
     * for a specific <code>host</code>. The handler implements HTTP/1.0 methods.
     *
     * @param host server host.
     * @param root document root.
     * @return a {@link HTTPHandler} that resolves data starting from directory <code>root</code>.
     */
    @Override
    public HTTPHandler getFileSystemHandler1_0(String host, File root) {
        return new Handler1_0(host, root);
    }

    /**
     * Creates a {@link HTTPHandler} that resolves data starting from directory <code>root</code>. The handler
     * implements HTTP/1.1 methods.
     *
     * @param root document root.
     * @return a {@link HTTPHandler} that resolves data starting from directory <code>root</code>.
     */
    @Override
    public HTTPHandler getFileSystemHandler1_1(File root) {
        return new Handler1_1(root);
    }

    /**
     * Creates a {@link HTTPHandler} that resolves data starting from directory <code>root</code>  and handling requests
     * for a specific <code>host</code>. The handler implements HTTP/1.1 methods.
     *
     * @param host server host.
     * @param root document root.
     * @return a {@link HTTPHandler} that resolves data starting from directory <code>root</code>.
     */
    @Override
    public HTTPHandler getFileSystemHandler1_1(String host, File root) {
        return new Handler1_1(host, root);
    }

    /**
     * Creates a {@link HTTPHandler} implementing a proxy.
     *
     * @return a {@link HTTPHandler} implementing a proxy.
     */
    @Override
    public HTTPHandler getProxyHandler() {
        return null;
    }
}
