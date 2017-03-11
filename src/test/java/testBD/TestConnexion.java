package testBD;

import ets.pfe.aqs.util.HibernateUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class TestConnexion {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConnexion.class);

    EntityManager entityManager;
    Session session;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        entityManager = HibernateUtil.getEntityManager();
    }

    @After
    public void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    public void testApp() {
        entityManager.getTransaction().begin();

        List<String> results = entityManager.createNativeQuery("SELECT * FROM public.\"Test\"").getResultList();
        
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains(4));
        Assert.assertTrue(results.contains(5));
            
        entityManager.close();
    }
}
