package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.AuditDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class AuditDaoImpl implements AuditDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditDaoImpl.class);

    /**
     * 
     * @param audit
     * @return
     * @throws PfeAqsException 
     */
    public Audit creerAudit(Audit audit) throws PfeAqsException {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtility.openEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(audit);
            entityManager.getTransaction().commit();
            JPAUtility.closeEntityManager(entityManager);
        } catch (Exception e) {
            LOGGER.error("An error occurred while creating an audit", e);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }
        
        return audit;
    }
}
