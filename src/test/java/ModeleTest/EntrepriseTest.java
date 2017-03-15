package ModeleTest;

import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.util.JPAUtility;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
    public void test1Insert() {
        entityManager.getTransaction().begin();

        Date now = Calendar.getInstance().getTime();
        Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);

        entityManager.persist(entreprise);
        entityManager.getTransaction().commit();
    }

    @Test
    public void test2Find() {
        TypedQuery<Entreprise> query = entityManager.createQuery("FROM Entreprise where nom = 'ENT_Test'", Entreprise.class);
        List<Entreprise> entreprise = query.getResultList();

        Assert.assertNotNull(entreprise);
        Assert.assertEquals(1, entreprise.size());
        
        Entreprise ent = entreprise.get(0);
        Assert.assertEquals("ENT_Test", ent.getNom());
        Assert.assertEquals("mission d'insertion", ent.getMission());
        Assert.assertEquals(ApprobationType.ZERO_APPROBATION, ent.getApprobationType());
    }

    @Test
    public void test3Update() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE FROM Entreprise e SET e.nom = 'ENT_Test_UPDATE' where e.nom = 'ENT_Test'");
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test4Find() {
        TypedQuery<Entreprise> queryUpdate = entityManager.createQuery("FROM Entreprise where nom = 'ENT_Test_UPDATE'", Entreprise.class);
        List<Entreprise> entreprise = queryUpdate.getResultList();

        Assert.assertNotNull(entreprise);
        Assert.assertEquals(1, entreprise.size());
        
        Entreprise ent = entreprise.get(0);
        Assert.assertEquals("ENT_Test_UPDATE", ent.getNom());
        Assert.assertEquals("mission d'insertion", ent.getMission());
        Assert.assertEquals(ApprobationType.ZERO_APPROBATION, ent.getApprobationType());
    }

    @Test
    public void test5Delete() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM Entreprise e where e.nom = 'ENT_Test_UPDATE'");
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test6Find() {
        TypedQuery<Entreprise> queryUpdate = entityManager.createQuery("FROM Entreprise where nom = 'ENT_Test_UPDATE'", Entreprise.class);
        List<Entreprise> entrepriseUpdate = queryUpdate.getResultList();

        Assert.assertNotNull(entrepriseUpdate);
        Assert.assertEquals(0, entrepriseUpdate.size());
    }
}
