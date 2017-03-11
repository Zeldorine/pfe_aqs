package ets.pfe.aqs.dao.service;

import javax.persistence.EntityManagerFactory;


/**
 * 
 * @author Zeldorine
 */
public interface LoginDaoService {
    public EntityManagerFactory getEntityManagerFactory();
    
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) ;
}
