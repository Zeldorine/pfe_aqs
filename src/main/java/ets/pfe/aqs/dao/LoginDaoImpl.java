package ets.pfe.aqs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Zeldorine
 */
public class LoginDaoImpl implements LoginDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDaoImpl.class);
    private static final String connexionQuery = "SELECT id FROM Utilisateur WHERE nom_utilisateur = '{username}' AND mot_de_passe = '{pass}'";

    public Utilisateur connexion(String username, String password) throws PfeAqsException, NoResultException {
        LOGGER.info("Ask for login for username : " + username);
        EntityManager entityManager = JPAUtility.openEntityManager();
        
        String queryStr = StringUtils.replace(connexionQuery, "{username}", username);
        queryStr = StringUtils.replace(queryStr, "{pass}", password);
        
        Query query = entityManager.createNativeQuery(queryStr);
        Integer result = (Integer)query.getSingleResult();
        Utilisateur utilisateur = null;
        
        if(result != null){
            Long id = result.longValue();
            utilisateur = entityManager.find(Utilisateur.class, id);
            LOGGER.info("User found : " + utilisateur.getNomUtilisateur());
        }
    
        return utilisateur;
    }
}
