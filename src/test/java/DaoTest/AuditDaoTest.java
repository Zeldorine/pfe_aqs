package DaoTest;

import ets.pfe.aqs.dao.AuditDaoImpl;
import ets.pfe.aqs.dao.service.AuditDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Audit;
import ets.pfe.aqs.modele.AuditType;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class AuditDaoTest {
    EntityManager entityManager;

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {

        JPAUtility.close();
    }

    @Before
    public void setUp() {
        entityManager = JPAUtility.openEntityManager();
    }

    @After
    public void tearDown() {
        JPAUtility.closeEntityManager(entityManager);
    }
    
    @Test
    public void createTest(){
        try {
            AuditDaoService dao = new AuditDaoImpl();
            Date date = Calendar.getInstance().getTime();
            Audit audit = new Audit(1, AuditType.CONNEXION, date, "Pfe AQS - Plateform");
            audit = dao.creerAudit(audit);
            
            Audit auditToCheck = entityManager.find(Audit.class, audit.getId());
            Assert.assertNotNull(auditToCheck);
            Assert.assertEquals(audit.getId(), auditToCheck.getId());
            Assert.assertEquals(audit.getUtilisateurId(), auditToCheck.getUtilisateurId());
            Assert.assertEquals(audit.getObjet(), auditToCheck.getObjet());
            
            entityManager.getTransaction().begin();
            entityManager.remove(auditToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }
}
