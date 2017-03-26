package ets.pfe.aqs;

import ets.pfe.aqs.dao.AuditDaoImpl;
import ets.pfe.aqs.dao.DocumentDaoImpl;
import ets.pfe.aqs.dao.EntrepriseDaoImpl;
import ets.pfe.aqs.dao.LoginDaoImpl;
import ets.pfe.aqs.dao.UtilisateurDaoImpl;
import ets.pfe.aqs.dao.service.AuditDaoService;
import ets.pfe.aqs.dao.service.DocumentDaoService;
import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import javax.xml.bind.JAXBException;
import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.dao.service.UtilisateurDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.modele.AuditType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.service.PfeAqsService;
import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.EmailUtil;
import ets.pfe.aqs.util.SecurityUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsController implements PfeAqsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsController.class);
    private static Utilisateur authenticateUser;
    private final ConfigUtil config;
    private final LoginDaoService loginDao;
    private final AuditDaoService auditDao;
    private final DocumentDaoService documentDao;
    private final EntrepriseDaoService enterpriseDao;
    private final UtilisateurDaoService userDao;

    private static final String NO_FORM_FOUND = "No form found for id ";
/**
 * 
 * @throws JAXBException 
 */
    public PfeAqsController() throws JAXBException {
        config = PfeAqsApplication.getConfig();
        loginDao = new LoginDaoImpl();
        auditDao = new AuditDaoImpl();
        documentDao = new DocumentDaoImpl();
        enterpriseDao = new EntrepriseDaoImpl();
        userDao = new UtilisateurDaoImpl();
    }

    /**
     * 
     * @param config
     * @throws JAXBException 
     */
    public PfeAqsController(ConfigUtil config) throws JAXBException {
        this.config = config;
        loginDao = new LoginDaoImpl();
        auditDao = new AuditDaoImpl();
        documentDao = new DocumentDaoImpl();
        enterpriseDao = new EntrepriseDaoImpl();
        userDao = new UtilisateurDaoImpl();
    }

    /**
     * 
     * @param user 
     */
    static void setAuthenticateUser(Utilisateur user) {
        authenticateUser = user;
    }

    /**
     * 
     * @param name
     * @param password
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur login(final String name, final String password) throws PfeAqsException {
        LOGGER.info("[Controller]Login with username {}", name);

        try {
            setAuthenticateUser(loginDao.connexion(name, SecurityUtil.cryptWithMD5(password)));

            if (authenticateUser != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.CONNEXION, Calendar.getInstance().getTime(), config.getAppName()));
            } else {
                throw new PfeAqsException("No user found for username " + name);
            }
        } catch (NoResultException e) {
            LOGGER.warn("No user found for username {}", name, e);
            throw new PfeAqsException("No user found for username " + name);
        } catch (Exception e) {
            LOGGER.warn("An error occured while logging the user {}", name, e);
            throw new PfeAqsException("N\"An error occured while logging the user " + name);
        }

        return authenticateUser;
    }

    /**
     * 
     * @throws PfeAqsException 
     */
    @Override
    public void logout() throws PfeAqsException {
        LOGGER.info("[Controller] logout the user {}", authenticateUser.getId());
        auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.DECONNEXION, Calendar.getInstance().getTime(), config.getAppName()));
        setAuthenticateUser(null);
    }

    /**
     * 
     * @param formName
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Formulaire getForm(final String formName) throws PfeAqsException {
        LOGGER.info("[Controller]Login with username {}", formName);
        Formulaire form;

        if (!authenticateUserIsEditorOrApprover()) {
            LOGGER.error("Authenticated user has not appropriate role to get form, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to get form, it is a " + authenticateUser.getRole());
        }

        try {
            boolean isApprover = authenticateUser.getRole() == Role.APPROBATEUR;
            form = documentDao.getForm(formName, isApprover);

            if (form != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.GET_FORMULAIRE, Calendar.getInstance().getTime(), formName));
            } else {
                throw new PfeAqsException("No form found for name " + formName);
            }
        } catch (NoResultException e) {
            LOGGER.warn("No form found for name {}", formName, e);
            throw new PfeAqsException("No form found for name " + formName);
        } catch (Exception e) {
            LOGGER.warn("An error occured while getting form {}", formName, e);
            throw new PfeAqsException("An error occured while getting form " + formName);
        }

        return form;
    }

    /**
     * 
     * @return 
     */
    private boolean authenticateUserIsEditorOrApprover() {
        return authenticateUser.getRole() == Role.EDITEUR || authenticateUser.getRole() == Role.APPROBATEUR;
    }

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public List<Formulaire> getAllForm() throws PfeAqsException {
        LOGGER.info("[Controller] Get all form");
        List<Formulaire> forms;

        if (!authenticateUserIsEditorOrApprover()) {
            LOGGER.error("Authenticated user has not appropriate role to get forms, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to get forms, it is a " + authenticateUser.getRole());
        }

        try {
            boolean isApprover = authenticateUser.getRole() == Role.APPROBATEUR;
            forms = documentDao.getAllForm(isApprover);

            if (forms != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.GET_FORMULAIRE, Calendar.getInstance().getTime(), "All forms"));
            } else {
                throw new PfeAqsException("No forms found");
            }
        } catch (NoResultException e) {
            LOGGER.warn("No form found", e);
            throw new PfeAqsException("No form found");
        } catch (Exception e) {
            LOGGER.warn("An error occured while getting forms", e);
            throw new PfeAqsException("An error occured while getting forms");
        }

        return forms;
    }

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Formulaire approveForm(final Long id) throws PfeAqsException {
        LOGGER.info("[Controller] Approve form {}", id);
        Formulaire form;

        if (authenticateUser.getRole() != Role.APPROBATEUR) {
            LOGGER.error("Authenticated user has not appropriate role to approve forms, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to approve forms, it is a " + authenticateUser.getRole());
        }

        try {
            form = documentDao.approveForm(id);

            if (form != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.APPROVE_FORM, Calendar.getInstance().getTime(), id.toString()));
            } else {
                throw new PfeAqsException("An error occurred while approving the form " + id);
            }
        } catch (NoResultException e) {
            LOGGER.warn("An error occurred while approving the form {}, it no exists", id, e);
            throw new PfeAqsException(NO_FORM_FOUND + id);
        } catch (Exception e) {
            LOGGER.warn("An error occured while approving form {}", id, e);
            throw new PfeAqsException("An error occured while approving form " + id);
        }

        return form;
    }

    /**
     * 
     * @param formId
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Formulaire rejectForm(final Long formId) throws PfeAqsException {
        LOGGER.info("[Controller] Reject form {}", formId);
        Formulaire form;

        if (authenticateUser.getRole() != Role.APPROBATEUR) {
            LOGGER.error("Authenticated user has not appropriate role to reject forms, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to reject forms, it is a " + authenticateUser.getRole());
        }

        try {
            form = documentDao.rejectForm(formId);

            if (form != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.REJECT_FORM, Calendar.getInstance().getTime(), formId.toString()));
            } else {
                throw new PfeAqsException("An error occurred while rejecting the form " + formId);
            }
        } catch (NoResultException e) {
            LOGGER.warn("An error occurred while rejecting the form {}, it no exists", formId, e);
            throw new PfeAqsException(NO_FORM_FOUND + formId);
        } catch (Exception e) {
            LOGGER.warn("An error occured while rejecting form {}", formId, e);
            throw new PfeAqsException("An error occured while rejecting form " + formId);
        }

        return form;
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Formulaire createForm(final JSONObject jsonData) throws PfeAqsException {
        LOGGER.info("[Controller] Create form/revision");
        Formulaire form;

        if (!authenticateUserIsEditorOrApprover()) {
            LOGGER.error("Authenticated user has not appropriate role to create form, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to create form, it is a " + authenticateUser.getRole());
        }

        try {
            form = documentDao.createForm(getFormFromJson(jsonData));

            if (form != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.CREATE_FORMULAIRE, Calendar.getInstance().getTime(), form.getId().toString()));
            } else {
                throw new PfeAqsException("An error occurred while creating the form/revision ");
            }
        } catch (Exception e) {
            LOGGER.warn("An error occurred while creating the form/revision", e);
            throw new PfeAqsException(NO_FORM_FOUND);
        }

        return form;
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    private Formulaire getFormFromJson(final JSONObject jsonData) throws PfeAqsException {
        try {
            Integer id = null;
            if (jsonData.has("id")) {
                id = jsonData.getInt("id");
            }

            String nom = jsonData.getString("nom");
            int version = jsonData.getInt("version");
            String contenu = jsonData.getJSONObject("contenu").toString();
            Date dateCreation = Calendar.getInstance().getTime();
            int idTemplate = jsonData.getInt("idTemplate");
            Long idCreateur = authenticateUser.getId();
            int approbation = enterpriseDao.getApprobationLevel(authenticateUser.getEntreprise());

            Formulaire form = new Formulaire(nom, version, contenu, dateCreation, idTemplate, idCreateur.intValue(), approbation);
            if (id != null) {
                return new Formulaire(id, form);
            } else {
                return form;
            }
        } catch (Exception e) {
            LOGGER.warn("An error occured while creating form from json", e);
            throw new PfeAqsException("An error occured while creating form from json");
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Entreprise addEnterprise(final JSONObject jsonData) throws PfeAqsException {
        LOGGER.info("[Controller] Add enterprise");
        String error = "An error occurred during creating a enterprise";

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_SYSTEM) {
            LOGGER.error("Authenticated user has not appropriate role to add a enterprise, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to add a enterprise, it is a " + authenticateUser.getRole());
        }
        Entreprise enterprise = null;

        try {
            enterprise = enterpriseDao.ajouterEntreprise(getEnterpriseFromJson(jsonData));

            if (enterprise != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.CREATE_ENTREPRISE, Calendar.getInstance().getTime(), enterprise.getId().toString()));
            } else {
                throw new PfeAqsException(error);
            }
        } catch (Exception e) {
            LOGGER.warn(error, e);
            throw new PfeAqsException(error);
        }

        return enterprise;
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Entreprise updateEnterprise(final JSONObject jsonData) throws PfeAqsException {
        LOGGER.info("[Controller] Update enterprise");

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_SYSTEM) {
            LOGGER.error("Authenticated user has not appropriate role to update a enterprise, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to update a enterprise, it is a " + authenticateUser.getRole());
        }

        Entreprise enterprise = getEnterpriseFromJson(jsonData);

        try {
            enterprise = enterpriseDao.updateEntreprise(enterprise);

            if (enterprise != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.UPDATE_ENTERPRISE, Calendar.getInstance().getTime(), enterprise.getId().toString()));
            } else {
                throw new PfeAqsException("An error occurred during updating a enterprise");
            }
        } catch (Exception e) {
            LOGGER.warn("An error occurred during updating enterprise", e);
            throw new PfeAqsException("An error occurred during updating enterprise");
        }

        return enterprise;
    }

    /*
    
    */
    @Override
    public List<Entreprise> getEnterprises() throws PfeAqsException {
        LOGGER.info("[Controller] get enterprises");
        String error = "An error occurred during getting enterprises";

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_SYSTEM) {
            LOGGER.error("Authenticated user has not appropriate role to get enterprises, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to get enterprises, it is a " + authenticateUser.getRole());
        }

        List<Entreprise> enterprises = null;
        try {
            enterprises = enterpriseDao.getEnterprises();

            if (enterprises != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.GET_ENTERPRISES, Calendar.getInstance().getTime(), ""));
            } else {
                throw new PfeAqsException(error);
            }
        } catch (Exception e) {
            LOGGER.warn(error, e);
            throw new PfeAqsException(error);
        }

        return enterprises;
    }

    /**
     * 
     * @param jsonData
     * @return 
     */
    private Entreprise getEnterpriseFromJson(final JSONObject jsonData) {
        Integer id = null;
        if (jsonData.has("id")) {
            id = jsonData.getInt("id");
        }

        String nom = jsonData.getString("nom");
        String mission = jsonData.getString("mission");
        Date dateCreation = Calendar.getInstance().getTime();
        ApprobationType approbationType = ApprobationType.valueOf(jsonData.getString("approbationType"));

        if (id == null) {
            return new Entreprise(nom, mission, dateCreation, approbationType);
        } else {
            return new Entreprise(id, nom, mission, dateCreation, approbationType);
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur addUser(final JSONObject jsonData) throws PfeAqsException {
        LOGGER.info("[Controller] Add user");
        String error = "An error occurred during creating a user";

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_ENTREPRISE) {
            LOGGER.error("Authenticated user has not appropriate role to add a user, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to add a user, it is a " + authenticateUser.getRole());
        }
        Utilisateur user = null;

        try {
            user = userDao.creerUtilisateur(getUserFromJson(jsonData));

            if (user != null) {
                user = userDao.changePassword(user.getId(), SecurityUtil.cryptWithMD5(config.getDefaultPass()));
                EmailUtil.sendEmailCreateAccount(user.getCourriel(), config, user.getNomUtilisateur(), config.getDefaultPass());
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.GET_FORMULAIRE, Calendar.getInstance().getTime(), user.getId().toString()));
            } else {
                throw new PfeAqsException(error);
            }
        } catch (Exception e) {
            LOGGER.warn(error, e);
            throw new PfeAqsException(error);
        }

        return user;
    }

    /**
     * 
     * @param jsonData
     * @return 
     */
    private Utilisateur getUserFromJson(final JSONObject jsonData) {
        Integer id = null;
        if (jsonData.has("id")) {
            id = jsonData.getInt("id");
        }

        String nomUtilisateur = jsonData.getString("nomUtilisateur");
        String nom = jsonData.getString("nom");
        String prenom = jsonData.getString("prenom");
        String courriel = jsonData.getString("courriel");
        Role role = Role.valueOf(jsonData.getString("role"));
        int actif = jsonData.getInt("actif");
        long entreprise = jsonData.getLong("entreprise");

        Utilisateur user = new Utilisateur(nomUtilisateur, nom, prenom, courriel, role, actif, entreprise);

        if (id != null) {
            return new Utilisateur(id, user);
        } else {
            return user;
        }
    }

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur updateUser(final JSONObject jsonData) throws PfeAqsException {
        LOGGER.info("[Controller] Update user");

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_ENTREPRISE) {
            LOGGER.error("Authenticated user has not appropriate role to update a user, it is a {}", authenticateUser.getRole());
            throw new PfeAqsException("Authenticated user has not appropriate role to update a user, it is a " + authenticateUser.getRole());
        }

        Utilisateur user = getUserFromJson(jsonData);

        try {
            user = userDao.updateUsers(user);

            if (user != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.UPDATE_USER, Calendar.getInstance().getTime(), user.getId().toString()));
            } else {
                throw new PfeAqsException("An error occurred during updating a user");
            }
        } catch (Exception e) {
            LOGGER.warn("An error occurred during updating user", e);
            throw new PfeAqsException("An error occurred during updating user");
        }

        return user;
    }

    /**
     * 
     * @param id
     * @param activate
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur activateUtilisateur(final Long id, final boolean activate) throws PfeAqsException {
        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_ENTREPRISE) {
            LOGGER.error("Authenticated user has not appropriate role to activate/deactivate a user, it is a {}", authenticatedUserRole);
            throw new PfeAqsException("Authenticated user has not appropriate role to activate/deactivate a user, it is a " + authenticatedUserRole);
        }

        String logInfo = activate ? "activating" : "deactivating";
        LOGGER.info("[Controller] {} user", logInfo);
        String error = "An error occurred during ";
        Utilisateur user = null;

        try {
            user = userDao.activateUtilisateur(id, activate);

            if (user != null) {
                AuditType auditType = activate ? AuditType.ACTIVATION : AuditType.DESACTIVATION;
                auditDao.creerAudit(new Audit(authenticateUser.getId(), auditType, Calendar.getInstance().getTime(), user.getId().toString()));
            } else {
                throw new PfeAqsException(error + logInfo + " a user");
            }
        } catch (Exception e) {
            LOGGER.warn(error + logInfo + " user", e);
            throw new PfeAqsException(error + logInfo + " user");
        }

        return user;
    }

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public List<Utilisateur> getUtilisateurByEnterpriseId(Long id) throws PfeAqsException {
        LOGGER.info("[Controller] get users for the enterprises id {}", id);

        Role authenticatedUserRole = authenticateUser.getRole();
        if (authenticatedUserRole != Role.ADMIN_ENTREPRISE) {
            LOGGER.error("Authenticated user has not appropriate role to get users, it is a {}", authenticatedUserRole);
            throw new PfeAqsException("Authenticated user has not appropriate role to get users, it is a " + authenticatedUserRole);
        }

        List<Utilisateur> users = null;
        try {
            users = userDao.getUsersByEnterprise(id);

            if (users != null) {
                auditDao.creerAudit(new Audit(authenticateUser.getId(), AuditType.GET_USERS, Calendar.getInstance().getTime(), "" + id));
            } else {
                throw new PfeAqsException("An error occurred during getting users for the enterprise id " + id);
            }
        } catch (Exception e) {
            LOGGER.warn("An error occurred during getting users for the enterprise id {}", id, e);
            throw new PfeAqsException("An error occurred during getting users for the enterprise id " + id);
        }

        return users;
    }
}
