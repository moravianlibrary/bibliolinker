package cz.mzk.recordmanager.server.oai.dao.hibernate;

import cz.mzk.recordmanager.server.model.Cosmotron996;
import cz.mzk.recordmanager.server.model.HarvestedRecord.HarvestedRecordUniqueId;
import cz.mzk.recordmanager.server.model.ImportConfiguration;
import cz.mzk.recordmanager.server.oai.dao.Cosmotron996DAO;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cosmotron996DAOHibernate extends AbstractDomainDAOHibernate<Long, Cosmotron996>
	implements Cosmotron996DAO{

	@Override
	public Cosmotron996 findByIdAndHarvestConfiguration(String recordId,
			ImportConfiguration configuration) {
		return findByIdAndHarvestConfiguration(recordId, configuration.getId());
	}

	@Override
	public Cosmotron996 findByIdAndHarvestConfiguration(String recordId,
			Long configurationId) {
		Session session = sessionFactory.getCurrentSession();
		return (Cosmotron996) session
				.createQuery("FROM Cosmotron996 WHERE recordId = ? AND harvestedFrom = ?")
				.setParameter(0, recordId).setParameter(1, configurationId)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cosmotron996> findByParentId(Long configurationId, String parentRecordId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<Cosmotron996>) session
				.createQuery(
						"FROM Cosmotron996 WHERE harvestedFrom = ? AND parentRecordId = ? AND deleted IS NULL")
				.setParameter(0, configurationId).setParameter(1, parentRecordId)
				.list();
	}

	@Override
	public List<Cosmotron996> findByParentId(HarvestedRecordUniqueId parentUniqueId) {
		return findByParentId(parentUniqueId.getHarvestedFromId(), parentUniqueId.getRecordId());
	}

}
