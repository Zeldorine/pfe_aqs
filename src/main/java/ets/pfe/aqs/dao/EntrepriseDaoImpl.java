package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.util.JPAUtility;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class EntrepriseDaoImpl implements EntrepriseDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseDaoImpl.class);

    @Override
    public Entreprise ajouterEntreprise(Entreprise entreprise) throws PfeAqsException {
        LOGGER.info("Creating enterprise with name: " + entreprise.getNom());

        EntityManager entityManager = JPAUtility.openEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entreprise);
        entityManager.getTransaction().commit();

        JPAUtility.closeEntityManager(entityManager);

        LOGGER.info("Enterprise with name: " + entreprise.getNom() + " created");
        return entreprise;
    }

    @Override
    public Entreprise updateEntreprise(Entreprise newEnterprise) throws PfeAqsException {
        LOGGER.info("Activate user with id: " + newEnterprise.getNom());
        EntityManager entityManager = JPAUtility.openEntityManager();
        Entreprise oldEnterprise = entityManager.find(Entreprise.class, newEnterprise.getId());

        if (oldEnterprise != null) {
            entityManager.getTransaction().begin();
            oldEnterprise.setApprobationType(newEnterprise.getApprobationType());
            oldEnterprise.setMission(newEnterprise.getMission());
            entityManager.getTransaction().commit();
        } else {
            throw new PfeAqsException("The entreprise " + newEnterprise.getNom() + " dosen't exists in database.");
        }

        JPAUtility.closeEntityManager(entityManager);
        LOGGER.info("Enterprise: " + oldEnterprise.getNom() + " updated");

        return oldEnterprise;
    }

}
