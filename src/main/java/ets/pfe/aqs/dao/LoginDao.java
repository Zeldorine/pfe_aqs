package ets.pfe.aqs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ets.pfe.aqs.dao.service.LoginDaoService;

/**
 * 
 * @author Zeldorine
 */
public class LoginDao implements LoginDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);

    private static final String SQL_RESET_EXT_MESSAGE_DIGEST = "UPDATE EXTERNALMESSAGE SET DIGEST = NULL";

    @PersistenceUnit(unitName = "fileLauncher")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


}
