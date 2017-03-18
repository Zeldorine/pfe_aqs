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
public class PfeAqsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsApplication.class);
    private static ConfigUtil config;

    public static void main(String[] args) throws Exception {
        ApplicationContext sprinContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-context.xml");

        LOGGER.info("Retrieve config...");
        config = (ConfigUtil) sprinContext.getBean("configUtil");
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
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
            jettyServer.destroy();
            JPAUtility.close();
        }
    }

    public static ConfigUtil getConfig() {
        return config;
    }
}
