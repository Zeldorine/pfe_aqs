/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeleTest;

import ets.pfe.aqs.modele.ApprobationType;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.modele.Role;
import ets.pfe.aqs.modele.Utilisateur;
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
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Zeldorine
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtilisateurTest {

    EntityManager entityManager;
    static int enterpriseId;
    static int userId;

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
    public void test0PrepareTest() {
        System.out.println("ModeleTest.UtilisateurTest.prepareTest()");
        entityManager.getTransaction().begin();
        Date now = Calendar.getInstance().getTime();
        Entreprise entreprise = new Entreprise("ENT_Test", "mission d'insertion", now, ApprobationType.ZERO_APPROBATION);
        entityManager.persist(entreprise);
        entityManager.getTransaction().commit();

        enterpriseId = entreprise.getId();
    }
     
    @Test
    public void test1Insert() {
        Entreprise entAttach = entityManager.find(Entreprise.class, enterpriseId);

        assertNotNull(entAttach);
        System.out.println("entAttach " + entAttach.getId());
        entityManager.getTransaction().begin();
        Utilisateur utilisateur = new Utilisateur("username", "nom", "prenom", "nom@mail.com", Role.EDITEUR, 1, enterpriseId);
        entityManager.persist(utilisateur);
        entityManager.getTransaction().commit();
        
        userId = utilisateur.getId();
    }

    @Test
    public void test2Find() {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, userId);

        Assert.assertNotNull(utilisateur);
        Assert.assertEquals("username", utilisateur.getNomUtilisateur());
        Assert.assertEquals("nom", utilisateur.getNom());
        Assert.assertEquals("prenom", utilisateur.getPrenom());
        Assert.assertEquals("nom@mail.com", utilisateur.getCourriel());
        Assert.assertEquals(Role.EDITEUR, utilisateur.getRole());
        Assert.assertEquals(true, utilisateur.isActif());
        Assert.assertEquals(enterpriseId, utilisateur.getEntreprise());

        userId = utilisateur.getId();
    }
    
    @Test
    public void test3Update() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE FROM Utilisateur u SET u.actif = 0 where u.id = '" + userId + "'");
        int result = query.executeUpdate();
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, result);
    }

    @Test
    public void test4Find() {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, userId);

        Assert.assertNotNull(utilisateur);
        Assert.assertEquals("username", utilisateur.getNomUtilisateur());
        Assert.assertEquals("nom", utilisateur.getNom());
        Assert.assertEquals("prenom", utilisateur.getPrenom());
        Assert.assertEquals("nom@mail.com", utilisateur.getCourriel());
        Assert.assertEquals(Role.EDITEUR, utilisateur.getRole());
        Assert.assertEquals(false, utilisateur.isActif());
        Assert.assertEquals(enterpriseId, utilisateur.getEntreprise());
    }

    @Test
    public void test5CleanTest() {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, userId);
        entityManager.getTransaction().begin();
        entityManager.remove(utilisateur);
        entityManager.getTransaction().commit();

        Entreprise test = entityManager.find(Entreprise.class, enterpriseId);
        entityManager.getTransaction().begin();
        entityManager.remove(test);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}