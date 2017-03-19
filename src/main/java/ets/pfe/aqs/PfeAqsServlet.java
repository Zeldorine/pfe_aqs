package ets.pfe.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ets.pfe.aqs.service.PfeAqsService;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBException;

/**
 * 
 * @author Zeldorine
 */
@Path("/")
public class PfeAqsServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsServlet.class);

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String ERROR_MESSAGE = "ERROR";

    private PfeAqsService pfeAqs;

    public PfeAqsServlet() {
        try {
            this.pfeAqs = new PfeAqsController();
        } catch (JAXBException ex) {
            LOGGER.error("An error occured during init servlet");
        }
    }

    @GET
    @Path("test")
    public String test() {
        return pfeAqs.sayHello();
    }
}
