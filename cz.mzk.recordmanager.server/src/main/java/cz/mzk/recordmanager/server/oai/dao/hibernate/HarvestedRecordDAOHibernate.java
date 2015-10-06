package cz.mzk.recordmanager.server.oai.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import cz.mzk.recordmanager.server.model.DedupRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecord.HarvestedRecordUniqueId;
import cz.mzk.recordmanager.server.model.ImportConfiguration;
import cz.mzk.recordmanager.server.oai.dao.HarvestedRecordDAO;

@Component
public class HarvestedRecordDAOHibernate extends
		AbstractDomainDAOHibernate<Long, HarvestedRecord> implements
		HarvestedRecordDAO {

	@Override
	public HarvestedRecord findByIdAndHarvestConfiguration(String recordId,
			ImportConfiguration configuration) {
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session
				.createQuery(
						"from HarvestedRecord where uniqueId.recordId = ? and uniqueId.harvestedFromId = ?")
				.setParameter(0, recordId).setParameter(1, configuration.getId())
				.uniqueResult();
	}

	@Override
	public HarvestedRecord findByIdAndHarvestConfiguration(String recordId,
			Long configurationId) {
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session
				.createQuery(
						"from HarvestedRecord where uniqueId.recordId = ? and uniqueId.harvestedFromId = ?")
				.setParameter(0, recordId).setParameter(1, configurationId)
				.uniqueResult();
	}
	
	@Override
	public HarvestedRecord findByRecordId(String uniqueId) {
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session
				.createQuery("from HarvestedRecord where uniqueId.recordId = ?")
				.setParameter(0, uniqueId)
				.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<HarvestedRecord> getByDedupRecord(DedupRecord dedupRecord) {
		Session session = sessionFactory.getCurrentSession();
		return (List<HarvestedRecord>) session
				.createQuery("from HarvestedRecord where dedupRecord = ?")
				.setParameter(0, dedupRecord)
				.list();
	}

	@Override
	public HarvestedRecord get(HarvestedRecordUniqueId uniqueId) {
		return findByIdAndHarvestConfiguration(uniqueId.getRecordId(), uniqueId.getHarvestedFromId());
	}

	/* <MJ.> */
	@Override
	public List<HarvestedRecord> getByHarvestConfiguration(ImportConfiguration configuration) {
		Session session = sessionFactory.getCurrentSession();
		return (List<HarvestedRecord>) session
				.createQuery("from HarvestedRecord where import_conf_id = ?")
				.setParameter(0, configuration.getId())
				.list();
	}
	
	/* <MJ.> */
	public int deleteFulltextMonography(HarvestedRecord harvestedRecord) {
		Session session = sessionFactory.getCurrentSession();
		//System.out.println("-----trying to delete fulltext monographies for record ("+ harvestedRecord.getId()+"): "+harvestedRecord.getUniqueId().getRecordId());
		return (int) session
				.createQuery("delete FulltextMonography where harvested_record_id = ?")
				.setParameter(0, harvestedRecord.getId())
				.executeUpdate();
	}
}
