package ets.pfe.aqs;


import ets.pfe.aqs.PfeAqsServlet;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsServletTest {

    private static PfeAqsServlet servlet;

    @BeforeClass
    public static void setUpClass() {
        try {
            servlet = new PfeAqsServlet();
        } catch (JAXBException ex) {
            fail();
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void logoutTest(){
        servlet.logout();
    }
}
