package ets.pfe.aqs;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
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
    private static final String NAME = "name";
    private static final String USER = "user";
    private static final String ID = "id";
    private static final String ACTIVATE = "activate";

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
    public Response login(@FormParam("login") String json) {
        LOGGER.info("[Servlet][Rest-service] Login with parameters: {}", json);
        JSONObject data = new JSONObject(json);
        String username = data.getString(USERNAME);

        try {
            pfeAqs.login(username, data.getString("pass"));
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity("{\"login\":\"success\"}").build();
        } catch (PfeAqsException e) {
            LOGGER.warn("No user found for username " + username, e);
            return Response.status(Status.NOT_FOUND).entity("No user found for username " + username).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during login with username :  " + username, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during login with username : " + username + ". Verify informations").build();
        }
    }

    @POST
    @Path("getForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForm(@FormParam("name") String json) {
        LOGGER.info("[Servlet][Rest-service] Get form with parameters: {}", json);
        JSONObject data = new JSONObject(json);
        String formName = data.getString(NAME);

        try {
            JSONObject form = new JSONObject(pfeAqs.getForm(formName));
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(form.toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("No user found for username " + formName, e);
            return Response.status(Status.NOT_FOUND).entity("No Form found for name " + formName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during get form with name :  " + formName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during get form with form name : " + formName + ".").build();
        }
    }

    @GET
    @Path("getAllForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllForm() throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Get all form");

        try {
            JSONObject form = new JSONObject(pfeAqs.getAllForm());
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(form.toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("No form found", e);
            return Response.status(Status.NOT_FOUND).entity("No Form found").build();
        } catch (Exception e) {
            LOGGER.error("An error occured during get all form", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during get all form.").build();
        }
    }

    /*  @POST
    @Path("approveForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveForm(@FormParam("id") String json) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("rejectForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectForm(@FormParam("id") String json) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("createForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForm(@FormParam("form") String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("addEnterprise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEnterprise(@FormParam("enterprise") String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @POST
    @Path("updateEnterprise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnterprise(@FormParam("enterprise") String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    @POST
    @Path("addUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Add user with parameters: {}", jsonData);
        JSONObject userJson = (new JSONObject(jsonData)).getJSONObject(USER);
        String userName = userJson.getString("nom");
        Utilisateur user = null;

        try {
            user = pfeAqs.addUser(userJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create user" + user.getNom(), e);
            return Response.status(Status.NOT_FOUND).entity("Cannot create user " + userName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during adding a user with name :  " + userName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during adding a user name : " + userName + ".").build();
        }
    }

    @POST
    @Path("updateUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Update user with parameters: {}", jsonData);
        JSONObject userJson = (new JSONObject(jsonData)).getJSONObject(USER);
        String userName = userJson.getString("nom");
        Utilisateur user = null;

        try {
            user = pfeAqs.updateUser(userJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create user" + user.getNom(), e);
            return Response.status(Status.NOT_FOUND).entity("Cannot create user " + userName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during adding a user with name :  " + userName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during adding a user name : " + userName + ".").build();
        }
    }

    @POST
    @Path("activateUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateUtilisateur(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Activate/Deactivate user with parameters: {}", jsonData);
        JSONObject userJson = (new JSONObject(jsonData));
        Long id = userJson.getLong(ID);
        Boolean activate = userJson.getBoolean(ACTIVATE);
        Utilisateur user = null;

        try {
            user = pfeAqs.activateUtilisateur(id, activate);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create user" + user.getNom(), e);
            return Response.status(Status.NOT_FOUND).entity("Cannot Activate/Deactivate user " + id).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during Activate/Deactivate a user with name :  " + id, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during Activate/Deactivate a user name : " + id + ".").build();
        }
    }
}
