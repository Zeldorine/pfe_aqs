package ets.pfe.aqs;

import ets.pfe.aqs.util.HibernateUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsApplication.class);

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                PfeAqsServlet.class.getCanonicalName());

        LOGGER.info("Jetty init success !");
        
       // initConnectionBD();

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static void initConnectionBD() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //session.createNativeQuery("SELECT 1 FROM DUAL").getResultList();

        if (session != null) {
            session.close();
        }
    }
}
