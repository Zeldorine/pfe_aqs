package ets.pfe.aqs;

import ets.pfe.aqs.exception.PfeAqsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ets.pfe.aqs.service.PfeAqsService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 
 * @author Zeldorine
 */
@Path("/")
public class PfeAqsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsServlet.class);

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String ERROR_MESSAGE = "ERROR";

    private PfeAqsService pfeAqs;

    public PfeAqsService getPfeAqs() {
        return pfeAqs;
    }

    public void setPfeAqs(PfeAqsService fileLauncher) {
        this.pfeAqs = fileLauncher;
    }


    @GET
    @Path("test")
    public String test() {
        return "Welcome to Pfe AQS ! ";
    }
}
