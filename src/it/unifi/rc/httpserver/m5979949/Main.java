package it.unifi.rc.httpserver.m5979949;

import it.unifi.rc.httpserver.m5979949.handler.Handler1_1;
import java.io.*;
import java.net.InetAddress;

public class Main {
    public static void main(String [] args) throws IOException {
        //Server used for Python tests
        int port = 9999;
        InetAddress inetAddress = InetAddress.getByName("localhost");
        HTTPHandler handler = new Handler1_1(new File(".\\data"));
        MyHTTPFactory factory = new MyHTTPFactory();
        HTTPServer server = factory.getHTTPServer(port ,50,inetAddress,handler);
        server.start();
    }
}
