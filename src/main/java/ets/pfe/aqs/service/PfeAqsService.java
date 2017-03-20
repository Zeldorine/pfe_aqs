package ets.pfe.aqs.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Formulaire;
import ets.pfe.aqs.modele.Utilisateur;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface PfeAqsService {

    public String sayHello();

    public Utilisateur login(String name, String password) throws PfeAqsException;

    public Formulaire getForm(String name) throws PfeAqsException;

    public List<Formulaire> getAllForm() throws PfeAqsException;

    public Formulaire approveForm(long id) throws PfeAqsException;

    public Formulaire rejectForm(long id) throws PfeAqsException;

    public Formulaire createForm(String jsonData) throws PfeAqsException;

    public Entreprise addEnterprise(String jsonData) throws PfeAqsException;

    public Entreprise updateEnterprise(String jsonData) throws PfeAqsException;

    public Utilisateur addUser(String jsonData) throws PfeAqsException;

    public Utilisateur updateUser(String jsonData) throws PfeAqsException;

    public Utilisateur activateUtilisateur(Long id, boolean activate) throws PfeAqsException;
}
