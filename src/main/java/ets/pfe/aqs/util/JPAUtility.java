package ets.pfe.aqs.util;

import ets.pfe.aqs.PfeAqsServlet;
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
public class JPAUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPAUtility.class);
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("entityManager");
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void open() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("entityManager");
            }
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager openEntityManager() {
        return emf.createEntityManager();
    }

    public static synchronized void close() {
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
        if (entityManager != null) {
            try {
                if (entityManager.getTransaction() != null) {
                    while (entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().rollback();
                    }
                }
            } catch (IllegalStateException ex) {
                LOGGER.warn("Cannot rollback: " + ex.getMessage());
            }
            try {
                while (entityManager.isOpen()) {
                    entityManager.close();
                }
            } catch (IllegalStateException ex) {
                LOGGER.warn("Cannot close entity manager: " + ex.getMessage());
            }
        }
    }

}
