package UtilTest;

import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class JPAUtilTest {
    @Test
    public void initTest(){
        JPAUtility.open();
        EntityManager em = JPAUtility.openEntityManager();
        Assert.assertNotNull(em);
        Assert.assertEquals(true, em.isOpen());
        JPAUtility.closeEntityManager(em);
        Assert.assertEquals(false, em.isOpen());
        JPAUtility.close();
    }
}
