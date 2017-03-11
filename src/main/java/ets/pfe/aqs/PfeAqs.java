package ets.pfe.aqs;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import ets.pfe.aqs.dao.service.LoginDaoService;
import ets.pfe.aqs.service.PfeAqsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Zeldorine
 */
public class PfeAqs implements PfeAqsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqs.class);

    private LoginDaoService loginDao;

    public PfeAqs() throws JAXBException {
    }

    @PostConstruct
    public void init() {
    }
}
