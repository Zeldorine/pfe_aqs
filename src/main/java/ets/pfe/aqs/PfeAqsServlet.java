package ets.pfe.aqs;

import ets.pfe.aqs.exception.PfeAqsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ets.pfe.aqs.service.PfeAqsService;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;
import org.json.JSONObject;

/**
 *
 * @author Zeldorine
 */
@Path("/")
public class PfeAqsServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsServlet.class);
    private static final String USERNAME = "username";

    private final transient PfeAqsService pfeAqs;

    public PfeAqsServlet() throws JAXBException {
        try {
            this.pfeAqs = new PfeAqsController();
        } catch (JAXBException ex) {
            LOGGER.error("An error occured during init servlet");
            throw ex;
        }
    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        return Response.status(200).type(MediaType.TEXT_PLAIN).entity(pfeAqs.sayHello()).build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String json) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Login with parameters: {}", json);
        JSONObject data = new JSONObject(json);
        String username = data.getString(USERNAME);

        try {
            pfeAqs.login(username, data.getString("pass"));
            LOGGER.info("[Servlet][Rest-service] Login with parameters: {}", json);

            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity("{\"login\":\"success\"}").build();
        } catch (PfeAqsException e) {
            LOGGER.warn("No user found for username " + username, e);
            return Response.status(Status.NOT_FOUND).entity("No user found for username " + username).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during login with username :  " + username, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during login with username : " + username + ". Verify informations").build();
        }
    }
}