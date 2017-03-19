package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;

/**
 *
 * @author Zeldorine
 */
public interface UtilisateurDaoService {

    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws PfeAqsException;

    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException;

    public Utilisateur changePassword(Long id, String newPassword) throws PfeAqsException;
}
