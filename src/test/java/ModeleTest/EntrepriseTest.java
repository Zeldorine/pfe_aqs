package ModeleTest;

import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Zeldorine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntrepriseTest {

    EntityManager entityManager;
    static long enterpriseId;

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {
                EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM ENTREPRISE").executeUpdate();
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
    public void test1Insert() {
        entityManager.getTransaction().begin();

        Date now = Calendar.getInstance().getTime();
        Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);

        entityManager.persist(entreprise);
        entityManager.getTransaction().commit();

        enterpriseId = entreprise.getId();
    }

    @Test
    public void test2Find() {
        Entreprise entreprise = entityManager.find(Entreprise.class, enterpriseId);

        Assert.assertEquals("ENT_Test", entreprise.getNom());
        Assert.assertEquals("mission d'insertion", entreprise.getMission());
        Assert.assertEquals(ApprobationType.ZERO_APPROBATION, entreprise.getApprobationType());
    }

    @Test
    public void test3Update() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE FROM Entreprise e SET e.nom = 'ENT_Test_UPDATE' where e.id = " + enterpriseId);
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test4Find() {
        Entreprise entreprise = entityManager.find(Entreprise.class, enterpriseId);

        Assert.assertEquals("ENT_Test_UPDATE", entreprise.getNom());
        Assert.assertEquals("mission d'insertion", entreprise.getMission());
        Assert.assertEquals(ApprobationType.ZERO_APPROBATION, entreprise.getApprobationType());
    }

    @Test
    public void test5Delete() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM Entreprise e where e.id = " + enterpriseId);
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test6Find() {
        Entreprise entreprise = entityManager.find(Entreprise.class, enterpriseId);

        Assert.assertNull(entreprise);
    }
}
