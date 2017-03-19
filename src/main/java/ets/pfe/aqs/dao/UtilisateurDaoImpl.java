package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.UtilisateurDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class UtilisateurDaoImpl implements UtilisateurDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurDaoImpl.class);
    private static final String changePassQuery = "UPDATE Utilisateur set mot_de_passe = :password where id = :id";

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws PfeAqsException {
        LOGGER.info("Creating user with name: " + utilisateur.getNom());
        
        EntityManager entityManager = JPAUtility.openEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(utilisateur);
        entityManager.getTransaction().commit();
        
        JPAUtility.closeEntityManager(entityManager);

        LOGGER.info("User with name: " + utilisateur.getNom() + " created");
        return utilisateur;
    }

    @Override
    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException {
        LOGGER.info("Activate user with id: " + id);
        EntityManager entityManager = JPAUtility.openEntityManager();
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);

        if (utilisateur != null) {
            entityManager.getTransaction().begin();
            utilisateur.setActif(activate);
            entityManager.getTransaction().commit();
        } else {
            throw new PfeAqsException("The user id " + id + " dosen't exists in database.");
        }

        JPAUtility.closeEntityManager(entityManager);
        LOGGER.info("User with id: " + id + " is actif: " + activate);

        return utilisateur;
    }

    @Override
    public Utilisateur changePassword(Long id, String newPassword) throws PfeAqsException {
        LOGGER.info("Changind user's password with id: " + id);
        EntityManager entityManager = JPAUtility.openEntityManager();
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);

        if (utilisateur != null) {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery(changePassQuery);
            query.setParameter("password", newPassword);
            query.setParameter("id", id);

            int result = query.executeUpdate();
            if (result != 1) {
                LOGGER.info("Rollback because more than one user is modified. Total user changed : " + result);
                entityManager.getTransaction().rollback();
            }
            entityManager.getTransaction().commit();

        } else {
            throw new PfeAqsException("The user id " + id + " dosen't exists in database.");
        }

        JPAUtility.closeEntityManager(entityManager);
        LOGGER.info("Password change for user id: " + id);

        return utilisateur;
    }
}
