package testBD;

import ets.pfe.aqs.util.HibernateUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
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
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testApp() {
        entityManager = Persistence.createEntityManagerFactory("entityManager").createEntityManager();
        //entityManager = EntityManagerUtil.getEntityManager();
        entityManager.getTransaction().begin();

        List results = entityManager.createNativeQuery("SELECT * FROM public.\"Test\"").getResultList();
        System.out.println("results : " + results.get(0));
        System.out.println("results : " + results.get(1));
        //entityManager.getTransaction().commit();
        entityManager.close();
    }

    // @Test
    public void testGet() throws Exception {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String sql = "SELECT * FROM public.\"Test\"";
            //SQLQuery query = session.createNativeQuery(sql);
            //List results = query.list();
            List results = null;
            System.out.println("results : " + results.get(0));
            System.out.println("results : " + results.get(1));

            session.flush();
            session.clear();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
