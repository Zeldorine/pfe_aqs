package ets.pfe.aqs.dao.service;

import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Formulaire;
import java.util.List;

/**
 *
 * @author Zeldorine
 */
public interface DocumentDaoService {
    
    /**
     * 
     * @param includeNotApproveForm
     * @return
     * @throws PfeAqsException 
     */
    public List<Formulaire> getAllForm(boolean includeNotApproveForm) throws PfeAqsException;
    
    /**
     * 
     * @param formName
     * @param includeNotApproveForm
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire getForm(String formName,  boolean includeNotApproveForm) throws PfeAqsException;
    
    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire approveForm(long id) throws PfeAqsException;
    
    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire rejectForm(long id) throws PfeAqsException;
    
    /**
     * 
     * @param form
     * @return
     * @throws PfeAqsException 
     */
    public Formulaire createForm(Formulaire form) throws PfeAqsException;
}
