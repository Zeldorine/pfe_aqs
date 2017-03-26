package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.UtilisateurDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
import ets.pfe.aqs.util.JPAUtility;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class UtilisateurDaoImpl implements UtilisateurDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurDaoImpl.class);
    private static final String CHANGE_PASS_QUERY = "UPDATE Utilisateur set mot_de_passe = :pass where id = :id";
    private static final String GET_ALL_USERS_QUERY = "FROM Utilisateur where id_entreprise = :id_entreprise";

    /**
     * 
     * @param utilisateur
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws PfeAqsException {
        EntityManager entityManager = null;
        try {
            LOGGER.info("Creating user with name: {}", utilisateur.getNom());

            entityManager = JPAUtility.openEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(utilisateur);
            entityManager.getTransaction().commit();

            JPAUtility.closeEntityManager(entityManager);

            LOGGER.info("User with name: " + utilisateur.getNom() + " created");
        } catch (Exception e) {
            LOGGER.error("An error occurred while creating the user", e);
            throw new PfeAqsException("An error occurred while creating the user");
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }

    /**
     * 
     * @param id
     * @param activate
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException {
        LOGGER.info("Activate user with id: {}", id);
        EntityManager entityManager = null;
        Utilisateur utilisateur = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            utilisateur = entityManager.find(Utilisateur.class, id);

            if (utilisateur != null) {
                entityManager.getTransaction().begin();
                utilisateur.setActif(activate);
                entityManager.getTransaction().commit();
            } else {
                throw new PfeAqsException(getErrorNoResult(id));
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("User with id: {} is actif: {}", id, activate);
        } catch (Exception e) {
            LOGGER.error("An error occurred while activating the user {}", id, e);
            throw new PfeAqsException("An error occurred while activating the user " + id);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }
    
    /**
     * 
     * @param userId
     * @return 
     */
    private String getErrorNoResult(Long userId){
        return "The user id " + userId + " dosen't exists in database.";
    }

    /**
     * 
     * @param newUser
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur updateUsers(Utilisateur newUser) throws PfeAqsException {
        long id = newUser.getId();
        LOGGER.info("Changind user's password with id: {}", id);
        EntityManager entityManager = null;
        Utilisateur utilisateur = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            utilisateur = entityManager.find(Utilisateur.class, id);

            if (utilisateur != null) {
                entityManager.getTransaction().begin();
                utilisateur.setCourriel(newUser.getCourriel());
                utilisateur.setRole(newUser.getRole());
                utilisateur.setActif(newUser.isActif());
                entityManager.getTransaction().commit();
            } else {
                throw new PfeAqsException(getErrorNoResult(id));
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("Update user id: {}", id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while updating the user {}", id, e);
            throw new PfeAqsException("An error occurred while updating the user " + id);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }

    /**
     * 
     * @param userId
     * @param newPassword
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Utilisateur changePassword(Long userId, String newPassword) throws PfeAqsException {
        LOGGER.info("Changind user's password with id: {}", userId);
        EntityManager entityManager = null;
        Utilisateur user = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            user = entityManager.find(Utilisateur.class, userId);

            if (user != null) {
                entityManager.getTransaction().begin();
                Query query = entityManager.createQuery(CHANGE_PASS_QUERY);
                query.setParameter("pass", newPassword);
                query.setParameter("id", userId);

                int result = query.executeUpdate();
                if (result != 1) {
                    LOGGER.info("Rollback because more than one user is modified. Total user changed : {}", result);
                    entityManager.getTransaction().rollback();
                }
                entityManager.getTransaction().commit();
            } else {
                throw new PfeAqsException(getErrorNoResult(userId));
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("Password change for user id: {}", userId);
        } catch (Exception e) {
            LOGGER.error("An error occurred while changing password for the user {}", userId, e);
            throw new PfeAqsException("An error occurred while chqnging password for the user " + userId);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }
        return user;
    }

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public List<Utilisateur> getUsersByEnterprise(Long id) throws PfeAqsException {
        LOGGER.info("Get all users for id enterprise {}", id);
        EntityManager entityManager = null;
        List<Utilisateur> users = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            TypedQuery<Utilisateur> query = entityManager.createQuery(GET_ALL_USERS_QUERY, Utilisateur.class);
            query.setParameter("id_entreprise", id);
            users = query.getResultList();

            JPAUtility.closeEntityManager(entityManager);

            LOGGER.info("{} users are found ", users.size());

        } catch (Exception e) {
            LOGGER.error("An error occurred while getting list of users for the enterprise {}", id, e);
            throw new PfeAqsException("An error occurred while getting list of users for the enterprise " + id);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return users;
    }
}
