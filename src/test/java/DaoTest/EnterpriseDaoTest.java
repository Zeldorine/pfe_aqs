package DaoTest;

import ets.pfe.aqs.dao.EntrepriseDaoImpl;
import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM test.ENTREPRISE").executeUpdate();
        em.getTransaction().commit();
        JPAUtility.closeEntityManager(em);
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
    public void addEntrepriseTest() {
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
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void getEntreprisesTest() {
        try {
            EntrepriseDaoService dao = new EntrepriseDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);
            dao.ajouterEntreprise(entreprise);

            List<Entreprise> enterprises = dao.getEnterprises();
            Assert.assertNotNull(enterprises);
            Assert.assertEquals(1, enterprises.size());
            
            Entreprise entrepriseToCheck =enterprises.get(0);
            Assert.assertEquals(entreprise.getId(), entrepriseToCheck.getId());
            Assert.assertEquals(entreprise.getNom(), entrepriseToCheck.getNom());
            Assert.assertEquals(entreprise.getDateCreation(), entrepriseToCheck.getDateCreation());
            Assert.assertEquals(entreprise.getMission(), entrepriseToCheck.getMission());
            Assert.assertEquals(entreprise.getApprobationType(), entrepriseToCheck.getApprobationType());

            Entreprise entrepriseToRemove = entityManager.find(Entreprise.class, entreprise.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(entrepriseToRemove);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }
    
    @Test
    public void getEntreprisesLevelTest() {
        try {
            EntrepriseDaoService dao = new EntrepriseDaoImpl();
            Date now = Calendar.getInstance().getTime();
            Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ONE_APPROBATION);
            entreprise = dao.ajouterEntreprise(entreprise);

            Assert.assertEquals(ApprobationType.ONE_APPROBATION.getTotalApprobation(), dao.getApprobationLevel(entreprise.getId()).intValue());

            Entreprise entrepriseToRemove = entityManager.find(Entreprise.class, entreprise.getId());
            entityManager.getTransaction().begin();
            entityManager.remove(entrepriseToRemove);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void updateEntrepriseTest() {
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
        } catch (Exception ex) {
            fail();
        }
    }
}
