package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.AuditDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;

/**
 *
 * @author Zeldorine
 */
public class AuditDaoImpl implements AuditDaoService {

    public Audit creerAudit(Audit audit) throws PfeAqsException {
        EntityManager entityManager = JPAUtility.openEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(audit);
        entityManager.getTransaction().commit();
        JPAUtility.closeEntityManager(entityManager);
        
        return audit;
    }

}
