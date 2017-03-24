package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface EntrepriseDaoService {

    public Entreprise ajouterEntreprise(Entreprise entreprise) throws PfeAqsException;

    public Entreprise updateEntreprise(Entreprise newEnterprise) throws PfeAqsException;

    public List<Entreprise> getEnterprises() throws PfeAqsException;

    public int getApprobationLevel(Long id) throws PfeAqsException;
}
