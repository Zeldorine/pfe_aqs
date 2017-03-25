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

    public Utilisateur login(String name, String password) throws PfeAqsException;

    public void logout() throws PfeAqsException;

    public Formulaire getForm(String name) throws PfeAqsException;

    public List<Formulaire> getAllForm() throws PfeAqsException;

    public Formulaire approveForm(long id) throws PfeAqsException;

    public Formulaire rejectForm(long id) throws PfeAqsException;

    public Formulaire createForm(JSONObject jsonData) throws PfeAqsException;

    public Entreprise addEnterprise(JSONObject jsonData) throws PfeAqsException;

    public Entreprise updateEnterprise(JSONObject jsonData) throws PfeAqsException;

    public Utilisateur addUser(JSONObject jsonData) throws PfeAqsException;

    public Utilisateur updateUser(JSONObject jsonData) throws PfeAqsException;

    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException;
}
