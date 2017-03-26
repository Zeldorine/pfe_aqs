package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface EntrepriseDaoService {

    /**
     * 
     * @param entreprise
     * @return
     * @throws PfeAqsException 
     */
    public Entreprise ajouterEntreprise(Entreprise entreprise) throws PfeAqsException;

    /**
     * 
     * @param newEnterprise
     * @return
     * @throws PfeAqsException 
     */
    public Entreprise updateEntreprise(Entreprise newEnterprise) throws PfeAqsException;

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    public List<Entreprise> getEnterprises() throws PfeAqsException;

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public Integer getApprobationLevel(Long id) throws PfeAqsException;
}
