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
public class PfeAqsController implements PfeAqsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsController.class);

    private LoginDaoService loginDao;

    public PfeAqsController() throws JAXBException {
    }

    @PostConstruct
    public void init() {
    }
}
