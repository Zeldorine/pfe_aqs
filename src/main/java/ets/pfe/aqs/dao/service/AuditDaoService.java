package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Audit;

/**
 *
 * @author Zeldorine
 */
public interface AuditDaoService {
    public Audit creerAudit(Audit audit) throws PfeAqsException;
}
