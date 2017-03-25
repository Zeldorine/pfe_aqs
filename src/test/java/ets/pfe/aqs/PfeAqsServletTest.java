package ets.pfe.aqs;

import ets.pfe.aqs.PfeAqsServlet;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Utilisateur;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.fail;
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
    public void flow1_createEnterpriseTest() {
        try {
            Response response = servlet.addEnterprise("{\"enterprise\":{\"nom\":\"nom\","
                    + "\"mission\":\"mission\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow2_updateEnterpriseTest() {
        try {
            Response response = servlet.updateEnterprise("{\"enterprise\":{\"nom\":\"nom\","
                    + "\"mission\":\"missionUpdated\","
                    + "\"approbationType\":\"ONE_APPROBATION\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow3_getEnterpriseTest() {
        try {
            Response response = servlet.getEnterprises();

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow4_createUserTest() {
        try {
            Response response = servlet.addUser("{\"user\":"
                    + "{\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tc@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow5_updateUserTest() {
        try {
            Response response = servlet.updateUser("{\"user\":"
                    + "{\"id\":\"1\","
                    + "\"nom\":\"Cazorla\","
                    + "\"prenom\":\"Tony\","
                    + "\"courriel\":\"tcNew@mail.ca\","
                    + "\"role\":\"APPROBATEUR\","
                    + "\"actif\":\"1\","
                    + "\"entreprise\":\"1\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow6_activateUserTest() {
        try {
            Response response = servlet.activateUtilisateur("{\"id\":\"1\","
                    + "\"activate\":\"true\"}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow7_getUsersTest() {
        try {
            Response response = servlet.getUsersByEnterpriseId("{\"id\":\"1\"}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow8_loginTest() {
        try {
            Response response = servlet.login("{\"username\":\"Zeldorine\","
                    + "\"pass\":\"123soleil\"}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow9_createFormTest() {
        try {
            Response response = servlet.createForm("{\"form\":"
                    + "{\"nom\":\"form A\","
                    + "\"version\":\"1\","
                    + "\"contenu\":{\"nom\":\"nom\",\"mission\":\"mission\",\"approbationType\":\"ONE_APPROBATION\"},"
                    + "\"idTemplate\":\"1\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow10_rejectFormTest() {
        try {
            Response response = servlet.rejectForm("{\"id\":\"1\"}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow12_approveFormTest() {
        try {
            Response response = servlet.approveForm("{\"id\":\"1\"}}");

            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow13_getAllFormTest() {
        try {
            Response response = servlet.getAllForm();
            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow14_getFormTest() {
        try {
            Response response = servlet.getForm("{\"nom\":\"formA\"}");
            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }

    @Test
    public void flow15_logoutTest() {
        try {
            Response response = servlet.logout();
            Assert.assertNotNull(response);
        } catch (PfeAqsException ex) {
            fail();
        }
    }
}
