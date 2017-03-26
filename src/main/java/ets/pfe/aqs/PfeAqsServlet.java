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
    private static final String NAME = "nom";
    private static final String USER = "user";
    private static final String ENTERPRISE = "enterprise";
    private static final String FORM = "form";
    private static final String ID = "id";
    private static final String ACTIVATE = "activate";

    private final transient PfeAqsService pfeAqs;
/**
 * 
 * @throws JAXBException 
 */
    public PfeAqsServlet() throws JAXBException {
        try {
            this.pfeAqs = new PfeAqsController();
        } catch (JAXBException ex) {
            LOGGER.error("An error occured during init servlet");
            throw ex;
        }
    }

    /**
     * 
     * @param json
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String json) throws PfeAqsException {
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

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] logout");

        try {
            pfeAqs.logout();
        } catch (Exception e) {
            LOGGER.error("An error occured during logout user", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during logout user").build();
        }

        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * 
     * @param json
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("getForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForm(@FormParam("form") String json) throws PfeAqsException {
        JSONObject data = new JSONObject(json);
        String formName = data.getString(NAME);
        LOGGER.info("[Servlet][Rest-service] Get form {}", formName);

        try {
            JSONObject form = new JSONObject(pfeAqs.getForm(formName));
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(form.toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("No form found", e);
            return Response.status(Status.NOT_FOUND).entity("No Form found").build();
        } catch (Exception e) {
            LOGGER.error("An error occured during get form", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during get form.").build();
        }
    }

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
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

    /*
    
    */
    @POST
    @Path("approveForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveForm(@FormParam("form") String json) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Approve form");
        JSONObject data = new JSONObject(json);
        Long formId = data.getLong(ID);

        try {
            JSONObject form = new JSONObject(pfeAqs.approveForm(formId));
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(form.toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("An error occurred while approving form {}", formId, e);
            return Response.status(Status.NOT_FOUND).entity("An error occured while approving form." + formId).build();
        } catch (Exception e) {
            LOGGER.error("An error occured approving form {}", formId, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured while approving form." + formId).build();
        }
    }

    /**
     * 
     * @param json
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("rejectForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectForm(@FormParam("form") String json) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Reject form");
        JSONObject data = new JSONObject(json);
        Long id = data.getLong(ID);

        try {
            JSONObject form = new JSONObject(pfeAqs.rejectForm(id));
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(form.toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("An error occurred while rejecting form {}", id, e);
            return Response.status(Status.NOT_FOUND).entity("An error occured while rejecting form." + id).build();
        } catch (Exception e) {
            LOGGER.error("An error occured rejecting form {}", id, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured while rejecting form." + id).build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("createForm")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForm(@FormParam("form") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Add form with parameters: {}", jsonData);
        JSONObject formJson = (new JSONObject(jsonData)).getJSONObject(FORM);
        String formName = formJson.getString(NAME);
        Formulaire form;

        try {
            form = pfeAqs.createForm(formJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(form).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create form" + formName, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot create form " + formName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during adding a form with name :  " + formName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during adding a form name : " + formName + ".").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("addEnterprise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEnterprise(@FormParam("enterprise") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Add enterprise with parameters: {}", jsonData);
        JSONObject enterpriseJson = (new JSONObject(jsonData)).getJSONObject(ENTERPRISE);
        String enterpriseName = enterpriseJson.getString(NAME);
        Entreprise enterprise;

        try {
            enterprise = pfeAqs.addEnterprise(enterpriseJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(enterprise).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create enterprise" + enterpriseName, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot create enterprise " + enterpriseName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during adding a enterprise with name :  " + enterpriseName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during adding a enterprise name : " + enterpriseName + ".").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("updateEnterprise")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnterprise(@FormParam("enterprise") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Update enterprise with parameters: {}", jsonData);
        JSONObject enterpriseJson = (new JSONObject(jsonData)).getJSONObject(ENTERPRISE);
        String enterpriseName = enterpriseJson.getString(NAME);
        Entreprise enterprise;

        try {
            enterprise = pfeAqs.updateEnterprise(enterpriseJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(enterprise).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot update enterprise" + enterpriseName, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot update enterprise " + enterpriseName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during updating a enterprise with name :  " + enterpriseName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during updating a enterprise name : " + enterpriseName + ".").build();
        }
    }

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    @GET
    @Path("getEnterprises")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnterprises() throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] get enterprises ");
        List<Entreprise> enterprises;

        try {
            enterprises = pfeAqs.getEnterprises();
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(enterprises).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot get enterprises", e);
            return Response.status(Status.NOT_FOUND).entity("Cannot get enterprises").build();
        } catch (Exception e) {
            LOGGER.error("An error occured during getting enterprises", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during getting enterprises.").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("addUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Add user with parameters: {}", jsonData);
        JSONObject userJson = (new JSONObject(jsonData)).getJSONObject(USER);
        String nomUtilisateur = userJson.getString(NAME);
        Utilisateur user;

        try {
            user = pfeAqs.addUser(userJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create user" + nomUtilisateur, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot create user " + nomUtilisateur).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during adding a user with name :  " + nomUtilisateur, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during adding a user name : " + nomUtilisateur + ".").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("updateUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Update user with parameters: {}", jsonData);
        JSONObject userJson = (new JSONObject(jsonData)).getJSONObject(USER);
        String userName = userJson.getString(NAME);
        Utilisateur user;

        try {
            user = pfeAqs.updateUser(userJson);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot update user" + userName, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot update user " + userName).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during updating a user with name :  " + userName, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during updating a user name : " + userName + ".").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("activateUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateUtilisateur(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Activate/Deactivate user with parameters: {}", jsonData);
        JSONObject userJson = new JSONObject(jsonData);
        Long id = userJson.getLong(ID);
        Boolean activate = userJson.getBoolean(ACTIVATE);
        Utilisateur user;

        try {
            user = pfeAqs.activateUtilisateur(id, activate);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(user).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot create user" + id, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot Activate/Deactivate user " + id).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during Activate/Deactivate a user with name :  " + id, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during Activate/Deactivate a user name : " + id + ".").build();
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @POST
    @Path("getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByEnterpriseId(@FormParam("user") String jsonData) throws PfeAqsException {
        LOGGER.info("[Servlet][Rest-service] Update user with parameters: {}", jsonData);
        Long id = (new JSONObject(jsonData)).getLong(ID);
        List<Utilisateur> users;

        try {
            users = pfeAqs.getUtilisateurByEnterpriseId(id);
            return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(new JSONObject(users).toString()).build();
        } catch (PfeAqsException e) {
            LOGGER.warn("Cannot get users for the enterprise id {}", id, e);
            return Response.status(Status.NOT_FOUND).entity("Cannot get users for the enterprise " + id).build();
        } catch (Exception e) {
            LOGGER.error("An error occured during getting users for the enterprise {}", id, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("An error occured during getting users for the enterprise " + id).build();
        }
    }
}
