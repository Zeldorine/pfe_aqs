package ets.pfe.aqs.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Utilisateur;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Zeldorine
 */
public interface PfeAqsService {

    /**
     * 
     * @param name
     * @param password
     * @return
     * @throws PfeAqsException 
     */
    public Utilisateur login(String name, String password) throws PfeAqsException;

    /**
     * 
     * @throws PfeAqsException 
     */
    public void logout() throws PfeAqsException;

    /**
     * 
     * @param name
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire getForm(String name) throws PfeAqsException;

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    public List<Formulaire> getAllForm() throws PfeAqsException;

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire approveForm(Long id) throws PfeAqsException;

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire rejectForm(Long id) throws PfeAqsException;

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire createForm(JSONObject jsonData) throws PfeAqsException;

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    public Entreprise addEnterprise(JSONObject jsonData) throws PfeAqsException;

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    public Entreprise updateEnterprise(JSONObject jsonData) throws PfeAqsException;
    
    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    public List<Entreprise> getEnterprises() throws PfeAqsException;

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    public Utilisateur addUser(JSONObject jsonData) throws PfeAqsException;

    /**
     * 
     * @param jsonData
     * @return
     * @throws PfeAqsException 
     */
    public Utilisateur updateUser(JSONObject jsonData) throws PfeAqsException;

    /**
     * 
     * @param id
     * @param activate
     * @return
     * @throws PfeAqsException 
     */
    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException;
    
    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public List<Utilisateur> getUtilisateurByEnterpriseId(Long id) throws PfeAqsException;
}
