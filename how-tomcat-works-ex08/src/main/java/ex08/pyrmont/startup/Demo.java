package ex08.pyrmont.startup;


import sun.net.www.protocol.http.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * @author dongyang
 * @since 2021-01-05 15:55
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            public URLStreamHandler createURLStreamHandler(String protocol) {
                return new Handler();
            }
        });
        URL url = new URL("yancy://www.baidu.com");
        System.out.println(url.toExternalForm());
        System.out.println(url.getAuthority());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        MyString myString = new MyString();


        System.out.println();
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
