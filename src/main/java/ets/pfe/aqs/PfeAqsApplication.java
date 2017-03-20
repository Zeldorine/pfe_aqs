package ets.pfe.aqs;

import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.JPAUtility;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Zeldorine
 */
public abstract class PfeAqsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsApplication.class);
    private static final int DEFAULT_PORT = 8085;
    private static ConfigUtil config;
    private static Server jettyServer;

    private PfeAqsApplication() {
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext sprinContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-context.xml");

        LOGGER.info("Retrieve config...");
        config = (ConfigUtil) sprinContext.getBean("configUtil");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Integer port = config.getServerPort() == null ? DEFAULT_PORT : Integer.valueOf(config.getServerPort());
        jettyServer = new Server(Integer.valueOf(port));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                PfeAqsServlet.class.getCanonicalName());

        LOGGER.info("[MAIN][PFE_AQS] Jetty started");

        try {
            JPAUtility.open();
            jettyServer.start();
            jettyServer.join();
        } finally {
            stop();
        }
    }

    public static void stop() {
        try {
            jettyServer.stop();
            while (jettyServer.isRunning()) {
                jettyServer.stop();
            }

            jettyServer.destroy();
            JPAUtility.close();
        } catch (Exception ex) {
            LOGGER.error("An error occured while stopping server", ex);
        }
    }
    
    public static ConfigUtil getConfig() {
        return config;
    }
}
