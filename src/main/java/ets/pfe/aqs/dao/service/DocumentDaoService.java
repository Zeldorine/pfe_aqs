package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Formulaire;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface DocumentDaoService {
    public List<Formulaire> getAllForm(boolean includeNotApproveForm) throws PfeAqsException, Exception;
    public Formulaire getForm(String formName,  boolean includeNotApproveForm) throws PfeAqsException, Exception;
    public Formulaire approveForm(long id) throws PfeAqsException, Exception;
    public Formulaire rejectForm(long id) throws PfeAqsException, Exception;
    public Formulaire createForm(Formulaire form) throws PfeAqsException, Exception;
}
