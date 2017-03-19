package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;

/**
 *
 * @author Zeldorine
 */
public interface LoginDaoService {
    public Utilisateur connexion(String username, String password) throws PfeAqsException;
}
