package org.how.tomcat.works.ex01;

import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Ares
 * @version : 1.0
 * @createTime : Aug 21, 2012 9:45:01 PM
 * @description :
 */
public class HttpServer {

    /**
     * WEB_ROOT is the directory where our HTML and other files reside. For this
     * package, WEB_ROOT is the "webroot" directory under the working directory.
     * The working directory is the location in the file system from where the
     * java command was invoked.
     */
    public static final String WEB_ROOT = System.getProperty("user.dir")
        + File.separator + "webroot";

    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // the shutdown command received
    private boolean shutdown = false;

    public static void main(String[] args) {
        System.out.println(WEB_ROOT);
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8082;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("192.168.1.217"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Loop waiting for a request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();
                // create Request object and parse
                Request request = new Request(input);
                request.parse();
                if (request.getUri() == null) {
                    System.out.println("reach end.");
                    continue;
                }
                // create Response object
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // Close the socket
                socket.close();
                // check if the previous URI is a shutdown command
                shutdown = SHUTDOWN_COMMAND.equals(request.getUri());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }


}
