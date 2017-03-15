package testBD;

import ets.pfe.aqs.modele.TestDao;
import ets.pfe.aqs.util.JPAUtility;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestConnexion {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConnexion.class);

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
    public void test1App() {
        List<String> results = entityManager.createNativeQuery("SELECT * FROM public.\"Test\"").getResultList();

        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(4));
        Assert.assertTrue(results.contains(5));

        entityManager.close();
    }

    @Test
    public void test2Find() {
        TestDao test = entityManager.find(TestDao.class, 4);

        Assert.assertNotNull(test);
        Assert.assertEquals(4, test.getTest());

        entityManager.close();
    }

    @Test
    public void test3Insert() {
        entityManager.getTransaction().begin();

        TestDao newTest = new TestDao(666);

        entityManager.persist(newTest);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void test4Find() {
        TestDao test = entityManager.find(TestDao.class, 666);

        Assert.assertNotNull(test);
        Assert.assertEquals(666, test.getTest());

        entityManager.close();
    }

    @Test
    public void test5Delete() {
        TestDao test = entityManager.find(TestDao.class, 666);
        entityManager.getTransaction().begin();
        entityManager.remove(test);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void test6Find() {
        TestDao test = entityManager.find(TestDao.class, 666);

        Assert.assertNull(test);

        entityManager.close();
    }
}
