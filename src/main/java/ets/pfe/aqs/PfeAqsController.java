package ets.pfe.aqs;

import ets.pfe.aqs.dao.AuditDaoImpl;
import ets.pfe.aqs.dao.LoginDaoImpl;
import ets.pfe.aqs.dao.service.AuditDaoService;
import javax.xml.bind.JAXBException;
import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.modele.AuditType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.service.PfeAqsService;
import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.SecurityUtil;
import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsController implements PfeAqsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsController.class);
    private ConfigUtil config;
    private LoginDaoService loginDao;
    private AuditDaoService auditDao;

    public PfeAqsController() throws JAXBException {
        config = PfeAqsApplication.getConfig();
        loginDao = new LoginDaoImpl();
        auditDao = new AuditDaoImpl();
    }

    public String sayHello() {
        return "Hello ! Welcome to Pfe AQS ! ";
    }

    public Utilisateur login(String name, String password) throws PfeAqsException {
        LOGGER.info("[Controller]Login with username {}", name);
        Utilisateur user = null;

        try {
            user = loginDao.connexion(name, SecurityUtil.cryptWithMD5(password));

            if (user != null) {
                auditDao.creerAudit(new Audit(user.getId(), AuditType.CONNEXION, Calendar.getInstance().getTime(), config.getAppName()));
            } else {
                throw new PfeAqsException("No user found for username " + name);
            }
        } catch (NoResultException e) {
            LOGGER.warn("No user found for username {}", name, e);
            throw new PfeAqsException("No user found for username " + name);
        }

        return user;
    }

    public Formulaire getForm(String name) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Formulaire> getAllForm() throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Formulaire approveForm(long id) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Formulaire rejectForm(long id) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Formulaire createForm(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Entreprise addEnterprise(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Entreprise updateEnterprise(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Utilisateur addUser(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Utilisateur updateUser(String jsonData) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }

    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException {
        throw new UnsupportedOperationException(); //To change body of generated methods, choose Tools | Templates.
    }
}
