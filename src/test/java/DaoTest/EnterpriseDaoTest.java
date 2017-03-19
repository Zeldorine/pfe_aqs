package DaoTest;

import ets.pfe.aqs.dao.EntrepriseDaoImpl;
import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
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
public class EnterpriseDaoTest {

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
    public void addTest() {
        try {
            EntrepriseDaoService dao = new EntrepriseDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);
            dao.ajouterEntreprise(entreprise);

            Entreprise entrepriseToCheck = entityManager.find(Entreprise.class, entreprise.getId());
            Assert.assertNotNull(entrepriseToCheck);
            Assert.assertEquals(entreprise.getId(), entrepriseToCheck.getId());
            Assert.assertEquals(entreprise.getNom(), entrepriseToCheck.getNom());
            Assert.assertEquals(entreprise.getDateCreation(), entrepriseToCheck.getDateCreation());
            Assert.assertEquals(entreprise.getMission(), entrepriseToCheck.getMission());
            Assert.assertEquals(entreprise.getApprobationType(), entrepriseToCheck.getApprobationType());

            entityManager.getTransaction().begin();
            entityManager.remove(entrepriseToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void updateTest() {
        try {
            EntrepriseDaoService dao = new EntrepriseDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);
            entreprise = dao.ajouterEntreprise(entreprise);

            entreprise.setMission("New mission");
            entreprise.setApprobationType(ApprobationType.TWO_APPROBATION);
            entreprise = dao.updateEntreprise(entreprise);

            Entreprise entrepriseToCheck = entityManager.find(Entreprise.class, entreprise.getId());
            Assert.assertNotNull(entrepriseToCheck);
            Assert.assertEquals(entreprise.getId(), entrepriseToCheck.getId());
            Assert.assertEquals(entreprise.getNom(), entrepriseToCheck.getNom());
            Assert.assertEquals(entreprise.getDateCreation(), entrepriseToCheck.getDateCreation());
            Assert.assertEquals("New mission", entrepriseToCheck.getMission());
            Assert.assertEquals(ApprobationType.TWO_APPROBATION, entrepriseToCheck.getApprobationType());

            entityManager.getTransaction().begin();
            entityManager.remove(entrepriseToCheck);
            entityManager.getTransaction().commit();
        } catch (PfeAqsException ex) {
            fail();
        }
    }
}
