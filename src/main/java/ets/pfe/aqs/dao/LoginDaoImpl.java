package ets.pfe.aqs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Zeldorine
 */
public class LoginDaoImpl implements LoginDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDaoImpl.class);
    private static final String CONNEXION_QUERY = "SELECT id FROM test.Utilisateur WHERE nom_utilisateur = '{username}' AND mot_de_passe = '{pass}'";

    /**
     * 
     * @param username
     * @param password
     * @return
     * @throws PfeAqsException 
     */
    public Utilisateur connexion(String username, String password) throws PfeAqsException {
        LOGGER.info("Ask for login for username : {}", username);
        EntityManager entityManager = null;
        Utilisateur utilisateur = null;

        try {
            entityManager = JPAUtility.openEntityManager();

            String queryStr = StringUtils.replace(CONNEXION_QUERY, "{username}", username);
            queryStr = StringUtils.replace(queryStr, "{pass}", password);

            Query query = entityManager.createNativeQuery(queryStr);
            Integer result = (Integer) query.getSingleResult();

            if (result != null) {
                Long id = result.longValue();
                utilisateur = entityManager.find(Utilisateur.class, id);
                LOGGER.info("User found : " + utilisateur.getNomUtilisateur());
            }

        } catch (Exception e) {
            LOGGER.error("An error occurred while logging the user {}", username, e);
            throw new PfeAqsException("An error occurred while logging the user " + username);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }
}
