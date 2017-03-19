package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;

/**
 *
 * @author Zeldorine
 */
public interface EntrepriseDaoService {
    public Entreprise ajouterEntreprise(Entreprise entreprise) throws PfeAqsException;
    
    public Entreprise updateEntreprise(Entreprise newEnterprise) throws PfeAqsException;
}
