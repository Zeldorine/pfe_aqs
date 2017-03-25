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

    @Override
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws PfeAqsException, Exception {
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
            throw e;
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }

    @Override
    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException, Exception {
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
                throw new PfeAqsException("The user id " + id + " dosen't exists in database.");
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("User with id: {} is actif: {}", id, activate);
        } catch (Exception e) {
            LOGGER.error("An error occurred while activating the user " + id, e);
            throw e;
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }

    @Override
    public Utilisateur updateUsers(Utilisateur newUser) throws PfeAqsException, Exception {
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
                throw new PfeAqsException("The user id " + id + " dosen't exists in database.");
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("Update user id: {}", id);

        } catch (Exception e) {
            LOGGER.error("An error occurred while updating the user " + id, e);
            throw e;
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return utilisateur;
    }

    @Override
    public Utilisateur changePassword(Long id, String newPassword) throws PfeAqsException, Exception {
        LOGGER.info("Changind user's password with id: {}", id);
        EntityManager entityManager = null;
        Utilisateur utilisateur = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            utilisateur = entityManager.find(Utilisateur.class, id);

            if (utilisateur != null) {
                entityManager.getTransaction().begin();
                Query query = entityManager.createQuery(CHANGE_PASS_QUERY);
                query.setParameter("pass", newPassword);
                query.setParameter("id", id);

                int result = query.executeUpdate();
                if (result != 1) {
                    LOGGER.info("Rollback because more than one user is modified. Total user changed : {}", result);
                    entityManager.getTransaction().rollback();
                }
                entityManager.getTransaction().commit();

            } else {
                throw new PfeAqsException("The user id " + id + " dosen't exists in database.");
            }

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("Password change for user id: {}", id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while chqnging password for the user " + id, e);
            throw e;
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }
        return utilisateur;
    }

    public List<Utilisateur> getUsersByEnterprise(Long id) throws PfeAqsException, Exception {
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
            LOGGER.error("An error occurred while getting list of users for the enterprise " + id, e);
            throw e;
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return users;
    }
}
