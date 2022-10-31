//package io.github.mat3e;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//
//public class App {
//
//    public static void main(String[] args)  {
//     Logger logger = LoggerFactory.getLogger(App.class);
//        logger.info("Hello World");
//    }
//}

package io.github.mat3e;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.*;

public class App {
    public static void main(String[] args) throws Exception {
        var webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/");
        webapp.setConfigurations(new Configuration[]
                {
                        new AnnotationConfiguration(),
                        new WebInfConfiguration(),
                        new WebXmlConfiguration(),
                        new MetaInfConfiguration(),
                        new FragmentConfiguration(),
                        new EnvConfiguration(),
                        new PlusConfiguration(),
                        new JettyWebXmlConfiguration()
                });
        // skanowanie klas na tym samym poziomie
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
        //   webapp.addServlet(HelloServlet.class, "/api/*");   podpinanie servletów zmapowany pod /api/*
        var server = new Server(8080);
        server.setHandler(webapp);
        //nasłuchiwacz na konkretne zdarzenia naszego serwera
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStopped(LifeCycle event) {
               HibernateUtil.close(); // gdy jetty się zatrzymuje to wywołujemy to
            }
        });
        server.start();
        server.join();
    }
}