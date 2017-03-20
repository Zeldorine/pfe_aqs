package ets.pfe.aqs;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ets.pfe.aqs.service.PfeAqsService;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String ERROR_MESSAGE = "ERROR";
    private Utilisateur user;

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
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        return Response.status(200).type(MediaType.TEXT_PLAIN).entity(pfeAqs.sayHello()).build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String json) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Login with parameters: " + json);
        JSONObject data = new JSONObject(json);

        try {
            user = pfeAqs.login(data.getString("username"), data.getString("password"));
            LOGGER.info("[Servlet][Rest-service] Login with parameters: " + json);

            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity("{\"login\":\"success\"}").build();
        } catch (PfeAqsException e) {
            return Response.status(Status.NOT_FOUND).entity("No user found for username " + data.getString("username")).build();
        } catch (Exception e) {
             return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during login with username : " + data.getString("username") + ". Verify informations").build();
        }
    }

    /* @GET
    @Path("getForm")
    public Formulaire getForm(String name) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @GET
    @Path("getAllForm")
    public List<Formulaire> getAllForm() throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("approveForm")
    public Formulaire approveForm(long id) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("rejectForm")
    public Formulaire rejectForm(long id) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("createForm")
    public Formulaire createForm(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("addEnterprise")
    public Entreprise addEnterprise(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("updateEnterprise")
    public Entreprise updateEnterprise(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("addUser")
    public Utilisateur addUser(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("updateUser")
    public Utilisateur updateUser(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("activateUser")
    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
