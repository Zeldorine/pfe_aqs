package ModeleTest;

import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Formulaire;
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
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Zeldorine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormulaireTest {

    private final String jsonContent = "{"
            + "     \"firstName\": \"John\","
            + "     \"lastName\": \"Smith\","
            + "     \"age\": 25,"
            + "     \"address\": {"
            + "         \"streetAddress\": \"21 2nd Street\","
            + "         \"city\": \"New York\","
            + "         \"state\": \"NY\","
            + "         \"postalCode\": \"10021\""
            + "     },"
            + "     \"phoneNumber\": ["
            + "         { \"type\": \"home\", \"number\": \"212 555-1234\" },"
            + "         { \"type\": \"fax\", \"number\": \"646 555-4567\" }"
            + "     ],"
            + "     \"newSubscription\": false,"
            + "     \"companyName\": null"
            + " }";

    EntityManager entityManager;
    static long formulaireId;

    @BeforeClass
    public static void setUpClass() {
        JPAUtility.open();
    }

    @AfterClass
    public static void tearDownClass() {
                EntityManager em = JPAUtility.openEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM FORMULAIRE").executeUpdate();
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
        Formulaire formulaire = new Formulaire("Formulaire a", 1, jsonContent, now, 1, 1, ApprobationType.ZERO_APPROBATION.getTotalApprobation());

        entityManager.persist(formulaire);
        entityManager.getTransaction().commit();
        
        formulaireId = formulaire.getId();
    }

    @Test
    public void test2Find() {
        Formulaire formulaire = entityManager.find(Formulaire.class, formulaireId);
        
        Assert.assertEquals("Formulaire a", formulaire.getNom());
        Assert.assertEquals(1, formulaire.getVersion());
        Assert.assertEquals(jsonContent, formulaire.getContenu());
        Assert.assertEquals(1, formulaire.getIdCreateur());
        Assert.assertEquals(1, formulaire.getIdTemplate());
        Assert.assertEquals(0, formulaire.getApprobation());
    }

    @Test
    public void test3Update() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE FROM Formulaire f SET f.version = 2 where f.id = "+formulaireId);
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test4Find() {
        Formulaire formulaire = entityManager.find(Formulaire.class, formulaireId);
        
        Assert.assertEquals("Formulaire a", formulaire.getNom());
        Assert.assertEquals(2, formulaire.getVersion());
        Assert.assertEquals(jsonContent, formulaire.getContenu());
        Assert.assertEquals(1, formulaire.getIdCreateur());
        Assert.assertEquals(1, formulaire.getIdTemplate());
        Assert.assertEquals(0, formulaire.getApprobation());
    }

    @Test
    public void test5Delete() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM Formulaire f where f.id = " + formulaireId);
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test6Find() {
        Formulaire formulaire = entityManager.find(Formulaire.class, formulaireId);
        Assert.assertNull(formulaire);
    }
}
