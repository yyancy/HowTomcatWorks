package ex20.pyrmont.standardmbeantest;

import javax.management.Attribute;
import javax.management.ObjectName;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class StandardAgent {

    private MBeanServer mBeanServer = null;

    public StandardAgent() {
//        mBeanServer = MBeanServerFactory.createMBeanServer();
        mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public MBeanServer getMBeanServer() {
        return mBeanServer;
    }

    public ObjectName createObjectName(String name) {
        ObjectName objectName = null;
        try {
            objectName = new ObjectName(name);
        } catch (Exception e) {
        }
        return objectName;
    }

    private void createStandardBean(ObjectName objectName,
                                    String managedResourceClassName) {
        try {
            mBeanServer.createMBean(managedResourceClassName, objectName);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        StandardAgent agent = new StandardAgent();
        MBeanServer mBeanServer = agent.getMBeanServer();
        String domain = mBeanServer.getDefaultDomain();
        String managedResourceClassName = "ex20.pyrmont.standardmbeantest.Car";
        ObjectName objectName = agent.createObjectName(domain + ":type="
            + managedResourceClassName);
        agent.createStandardBean(objectName, managedResourceClassName);


        // manage MBean
        try {
            Attribute colorAttribute = new Attribute("Color", "blue");
            mBeanServer.setAttribute(objectName, colorAttribute);
            System.out.println(mBeanServer.getAttribute(objectName, "Color"));
            mBeanServer.invoke(objectName, "drive", null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
        }


        // manage MBean
        try {
            System.out.println(mBeanServer.getAttribute(objectName, "Color"));
            mBeanServer.invoke(objectName, "drive", null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
