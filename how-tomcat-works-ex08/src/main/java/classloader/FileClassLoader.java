package classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * @author dongyang
 * @since 2021-01-12 12:07
 */
public class FileClassLoader extends URLClassLoader {

    String rootDir;

    public FileClassLoader(URL[] urls) {
        super(urls);
    }


    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        String rootDir = "";
        File file = new File("E:/src/HowTomcatWorks/how-tomcat-works-ex08/target/classes/");

        URL url = file.toURI().toURL();
        System.out.println(url);
        FileClassLoader loader1 = new FileClassLoader(new URL[]{new URL("file:///E:/src/HowTomcatWorks/how-tomcat-works-ex08/target/classes/")});
        FileClassLoader loader2 = new FileClassLoader(new URL[]{url});


        //加载指定的class文件,调用loadClass()
        Class<?> object1 = loader1.loadClass("ex08.pyrmont.core.Hello");
        Class<?> object2 = loader2.loadClass("ex08.pyrmont.core.Hello");

        System.out.println("loadClass->obj1:" + object1.hashCode());
        System.out.println("loadClass->obj2:" + object2.hashCode());

        //加载指定的class文件,直接调用findClass(),绕过检测机制，创建不同class对象。
        Class<?> object3 = loader1.findClass("ex08.pyrmont.core.Hello");
        Class<?> object4 = loader2.findClass("ex08.pyrmont.core.Hello");

        System.out.println("loadClass->obj3:" + object3.hashCode());
        System.out.println("loadClass->obj4:" + object4.hashCode());


        System.out.println("自定义类加载器的父加载器: " + loader1.getParent());
        System.out.println("系统默认的AppClassLoader: " + ClassLoader.getSystemClassLoader());
        System.out.println("AppClassLoader的父类加载器: " + ClassLoader.getSystemClassLoader().getParent());
        System.out.println("ExtClassLoader的父类加载器: " + ClassLoader.getSystemClassLoader().getParent().getParent());

        /**
         输出结果:
         自定义类加载器的父加载器: sun.misc.Launcher$AppClassLoader@29453f44
         系统默认的AppClassLoader: sun.misc.Launcher$AppClassLoader@29453f44
         AppClassLoader的父类加载器: sun.misc.Launcher$ExtClassLoader@6f94fa3e
         ExtClassLoader的父类加载器: null
         */

    }
}
