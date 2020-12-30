package org.how.tomcat.works.ex01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author dongyang
 * @since 2020-12-30 10:52
 */
public class Client {


    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new Client("192.168.1.217", 8082).run();
    }

    public void run() {

        try {
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("GET /index.jsp HTTP/1.1"); //拼装HTTP请求信息
            out.println("Host:localhost:8080");
            out.println("Connection:Close");
            out.println();
            //readtheresponse
            boolean loop = true;
            StringBuilder sb = new StringBuilder(8096);
            while (loop) {
                if (in.ready()) {
                    int i = in.read();
                    while (i != -1) {
                        sb.append((char) i);
                        i = in.read();
                    }
                    loop = false;
                }
                Thread.sleep(50); //由于是阻塞写入，暂停50ms，保证可以写入。
            }
            System.out.println(sb.toString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
