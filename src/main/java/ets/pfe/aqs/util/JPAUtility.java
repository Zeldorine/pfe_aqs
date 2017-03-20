package ets.pfe.aqs.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Zeldorine
 */
public abstract class JPAUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPAUtility.class);
    private static EntityManagerFactory emf;

    private JPAUtility(){}
    
    static {
        try {
            LOGGER.info("Create entity manager factory");
            emf = Persistence.createEntityManagerFactory("entityManager");
        } catch (Exception ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void open() {
        try {
            if (emf == null) {
                LOGGER.info("Create entity manager factory");
                emf = Persistence.createEntityManagerFactory("entityManager");
            }
        } catch (Exception ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager openEntityManager() {
        LOGGER.info("Create entity manager");
        return emf.createEntityManager();
    }

    public static synchronized void close() {
        LOGGER.info("Close entity manager factory");

        if (emf != null && emf.isOpen()) {
            while (emf.isOpen()) {
                emf.close();
            }
        }

        emf = null;
    }

    /**
     *
     * @param entityManager EntityManager
     */
    public static synchronized void closeEntityManager(EntityManager entityManager) {
        LOGGER.info("Close entity manager");

        if (entityManager != null) {
            try {
                while (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            } catch (IllegalStateException ex) {
                LOGGER.warn("Cannot rollback: " + ex.getMessage());
                throw ex;
            }
            try {
                while (entityManager.isOpen()) {
                    entityManager.close();
                }
            } catch (IllegalStateException ex) {
                LOGGER.warn("Cannot close entity manager: " + ex.getMessage());
                throw ex;
            }
        }
    }

}
