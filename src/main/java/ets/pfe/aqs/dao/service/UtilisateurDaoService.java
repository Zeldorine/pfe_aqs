package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Utilisateur;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface UtilisateurDaoService {

    public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws PfeAqsException, Exception;

    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException, Exception;

    public Utilisateur updateUsers(Utilisateur newUser) throws PfeAqsException, Exception;
    
    public Utilisateur changePassword(Long id, String newPassword) throws PfeAqsException, Exception;
    
    public List<Utilisateur> getUsersByEnterprise(Long id) throws PfeAqsException, Exception;
}
