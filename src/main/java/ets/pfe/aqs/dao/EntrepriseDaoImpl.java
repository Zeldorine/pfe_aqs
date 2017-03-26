package ets.pfe.aqs.dao;

import ets.pfe.aqs.dao.service.EntrepriseDaoService;
import ets.pfe.aqs.exception.PfeAqsException;
import ets.pfe.aqs.modele.Entreprise;
import ets.pfe.aqs.util.JPAUtility;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public class EntrepriseDaoImpl implements EntrepriseDaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrepriseDaoImpl.class);
    private static final String GET_ALL_ENTERPRISES_QUERY = "From Entreprise";

    /**
     * 
     * @param entreprise
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Entreprise ajouterEntreprise(Entreprise entreprise) throws PfeAqsException {
        LOGGER.info("Creating enterprise with name: {}", entreprise.getNom());
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtility.openEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entreprise);
            entityManager.getTransaction().commit();

            JPAUtility.closeEntityManager(entityManager);

            LOGGER.info("Enterprise with name: " + entreprise.getNom() + " created");

        } catch (Exception e) {
            LOGGER.error("An error occurred while creating the enterprise {}", entreprise.getNom(), e);
            throw new PfeAqsException("An error occurred while creating the enterprise " + entreprise.getNom());
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return entreprise;
    }

    /**
     * 
     * @param newEnterprise
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Entreprise updateEntreprise(Entreprise newEnterprise) throws PfeAqsException {
        LOGGER.info("update enterprise with id: {}", newEnterprise.getNom());
        EntityManager entityManager = null;
        Entreprise oldEnterprise = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            oldEnterprise = entityManager.find(Entreprise.class, newEnterprise.getId());

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
        } catch (Exception e) {
            LOGGER.error("An error occurred while updating the enterprise {}", newEnterprise.getNom(), e);
            throw new PfeAqsException("An error occurred while updating the enterprise " + newEnterprise.getNom());
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return oldEnterprise;
    }

    /**
     * 
     * @param id
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public Integer getApprobationLevel(Long id) throws PfeAqsException {
        LOGGER.info("Get approbation level for enterprise with id: {}", id);
        EntityManager entityManager = null;
        Entreprise enterprise;

        try {
            entityManager = JPAUtility.openEntityManager();
            enterprise = entityManager.find(Entreprise.class, id);

            if (enterprise != null) {
                JPAUtility.closeEntityManager(entityManager);
                LOGGER.info("Approbation level for enterprise with id: {}, is ", id, enterprise.getApprobationType().getTotalApprobation());
                return enterprise.getApprobationType().getTotalApprobation();
            } else {
                throw new PfeAqsException(String.format("The entreprise %s dosen't exists in database.", id));
            }

        } catch (Exception e) {
            LOGGER.error("An error occurred while getting approbation level for the enterprise {}", id, e);
            throw new PfeAqsException("An error occurred while getting approbation level for the enterprise " + id);
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }
    }

    /**
     * 
     * @return
     * @throws PfeAqsException 
     */
    @Override
    public List<Entreprise> getEnterprises() throws PfeAqsException {
        LOGGER.info("Get all enterprises");
        EntityManager entityManager = null;
        List<Entreprise> enterprises = null;

        try {
            entityManager = JPAUtility.openEntityManager();
            TypedQuery<Entreprise> query = entityManager.createQuery(GET_ALL_ENTERPRISES_QUERY, Entreprise.class);
            enterprises = query.getResultList();

            JPAUtility.closeEntityManager(entityManager);
            LOGGER.info("{} enterprises are found ", enterprises.size());
        } catch (Exception e) {
            LOGGER.error("An error occurred while getting enterprises", e);
            throw new PfeAqsException("An error occurred while getting enterprises");
        } finally {
            JPAUtility.closeEntityManager(entityManager);
        }

        return enterprises;
    }

}
