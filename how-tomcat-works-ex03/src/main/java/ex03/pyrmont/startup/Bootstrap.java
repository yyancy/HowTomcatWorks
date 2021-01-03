package ex03.pyrmont.startup;

import ex03.pyrmont.connector.http.HttpConnector;

public final class Bootstrap {
  public static void main(String[] args) {
//    System.setProperty("user.dir", "how-tomcat-works-ex03");
//    ModernServlet modernServlet = new ModernServlet();
    HttpConnector connector = new HttpConnector();
    connector.start();
  }
}