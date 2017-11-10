package cz.mzk.recordmanager.server.oai.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.mzk.recordmanager.server.util.Constants;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cz.mzk.recordmanager.server.model.Cnb;
import cz.mzk.recordmanager.server.model.DedupRecord;
import cz.mzk.recordmanager.server.model.Ean;
import cz.mzk.recordmanager.server.model.HarvestedRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecord.HarvestedRecordUniqueId;
import cz.mzk.recordmanager.server.model.HarvestedRecordFormat;
import cz.mzk.recordmanager.server.model.ImportConfiguration;
import cz.mzk.recordmanager.server.model.Isbn;
import cz.mzk.recordmanager.server.model.Ismn;
import cz.mzk.recordmanager.server.model.Issn;
import cz.mzk.recordmanager.server.model.Oclc;
import cz.mzk.recordmanager.server.model.ShortTitle;
import cz.mzk.recordmanager.server.model.Title;
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

	public HarvestedRecord findBySolrId(String solrId) {
		if (!solrId.contains(".")) {
			return this.findByRecordId(solrId);
		}
		String[] parts = solrId.split("\\.", 2);
		String prefix = parts[0];
		String id = parts[1];
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session //
				.createQuery("from HarvestedRecord where uniqueId.recordId = ? and harvestedFrom.idPrefix = ?") // 
				.setParameter(0, id) //
				.setParameter(1, prefix) //
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
	public HarvestedRecord findByHarvestConfAndRaw001Id(Long configurationId, String id001) {
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session
				.createQuery("from HarvestedRecord where uniqueId.harvestedFromId = ? and raw001Id = ?")
				.setParameter(0, configurationId)
				.setParameter(1, id001)
				.uniqueResult();
	}

	@Override
	public HarvestedRecord findByHarvestConfAndTezaurus(Long configurationId, String tezaurus) {
		Session session = sessionFactory.getCurrentSession();
		return (HarvestedRecord) session
				.createQuery(
						"from HarvestedRecord where uniqueId.harvestedFromId = ? and tezaurus = ?")
				.setParameter(0, configurationId).setParameter(1, tezaurus)
				.uniqueResult();
	}

	@Override
	public List<HarvestedRecord> getByDedupRecord(DedupRecord dedupRecord) {
		return getByDedupRecord(dedupRecord, false);
	}

	@Override
	public List<HarvestedRecord> getByDedupRecordWithDeleted(
			DedupRecord dedupRecord) {
		return getByDedupRecord(dedupRecord, true);
	}

	@Override
	public HarvestedRecord get(HarvestedRecordUniqueId uniqueId) {
		return findByIdAndHarvestConfiguration(uniqueId.getRecordId(), uniqueId.getHarvestedFromId());
	}


	/* <MJ.> */
	@SuppressWarnings("unchecked")
	@Override
	public List<HarvestedRecord> getByHarvestConfiguration(ImportConfiguration configuration) {
		Session session = sessionFactory.getCurrentSession();
		return (List<HarvestedRecord>) session
				.createQuery("from HarvestedRecord where import_conf_id = ?")
				.setParameter(0, configuration.getId())
				.list();
	}

	@Override
	public boolean existsByDedupRecord(DedupRecord dedupRecord) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HarvestedRecord.class);
		crit.add(Restrictions.eq("dedupRecord", dedupRecord));
		crit.setProjection(Projections.id());
		crit.setMaxResults(1);
		return crit.uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	protected List<HarvestedRecord> getByDedupRecord(DedupRecord dedupRecord, boolean alsoDeleted) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HarvestedRecord.class);
		crit.add(Restrictions.eq("dedupRecord", dedupRecord));
		crit.addOrder(Order.desc("weight").nulls(NullPrecedence.LAST));
		if (!alsoDeleted) {
			crit.add(Restrictions.isNull("deleted"));
		}
		return crit.list();
	}

	@Override
	public boolean existsUpvApplicationId(String applId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(HarvestedRecord.class);
		crit.add(Restrictions.eq("upvApplicationId", applId));
		crit.setProjection(Projections.id());
		crit.setMaxResults(1);
		return crit.uniqueResult() != null;
	}

	@Override
	public void deleteUpvApplicationRecord(String appId) {
		Session session = sessionFactory.getCurrentSession();
		HarvestedRecord hr = (HarvestedRecord) session
				.createQuery("from HarvestedRecord where uniqueId.harvestedFromId = ? and uniqueId.recordId = ?")
				.setParameter(0, Constants.IMPORT_CONF_ID_UPV)
				.setParameter(1, appId)
				.uniqueResult();
		if (hr != null) {
			hr.setUpdated(new Date());
			hr.setDeleted(new Date());
			sessionFactory.getCurrentSession().persist(hr);
		}
	}

	@Override
	public void dropDedupKeys(HarvestedRecord hr) {
		if (hr == null || hr.getId() == null) {
			return;
		}

		Session session = sessionFactory.getCurrentSession();
		// don't delete keys for not managed entities
		if (!session.contains(hr)) {
			System.out.println("NOT CONT");
			return;
		}

		hr.setAuthorAuthKey(null);
		hr.setAuthorString(null);
		hr.setClusterId(null);
		hr.setPages(null);
		hr.setPublicationYear(null);
		hr.setRaw001Id(null);
		hr.setScale(null);
		hr.setUuid(null);
		hr.setSourceInfo(null);
		hr.setIssnSeries(null);
		hr.setIssnSeriesOrder(null);
		hr.setWeight(null);

		List<Title> titles =  hr.getTitles();
		hr.setTitles(new ArrayList<>());
		for (Title t: titles) {
			session.delete(t);
		}

		List<ShortTitle> shortTitles = hr.getShortTitles();
		hr.setShortTitles(new ArrayList<>());
		for (ShortTitle st: shortTitles) {
			session.delete(st);
		}

		List<Isbn> isbns =  hr.getIsbns();
		hr.setIsbns(new ArrayList<>());
		for (Isbn i: isbns) {
			session.delete(i);
		}

		List<Issn> issns =  hr.getIssns();
		hr.setIssns(new ArrayList<>());
		for (Issn i: issns) {
			session.delete(i);
		}

		List<Ismn> ismns =  hr.getIsmns();
		hr.setIsmns(new ArrayList<>());
		for (Ismn i: ismns) {
			session.delete(i);
		}

		List<Oclc> oclcs = hr.getOclcs();
		hr.setOclcs(new ArrayList<>());
		for (Oclc o: oclcs) {
			session.delete(o);
		}

		List<Cnb> cnbs = hr.getCnb();
		hr.setCnb(new ArrayList<>());
		for (Cnb c: cnbs) {
			session.delete(c);
		}

		List<Ean> eans = hr.getEans();
		hr.setEans(new ArrayList<>());
		for (Ean ean : eans) {
			session.delete(ean);
		}

		List<HarvestedRecordFormat> physicalFormats = hr.getPhysicalFormats();
		hr.getPhysicalFormats().clear();
		for (HarvestedRecordFormat hrf: physicalFormats) {
			session.delete(hrf);
		}

		hr.setLanguages(new ArrayList<>());
		session.update(hr);
		session.flush();
	}

	@Override
	public void updateTimestampOnly(HarvestedRecord hr) {
		Session session = sessionFactory.getCurrentSession();
		Query update = session.createQuery("UPDATE HarvestedRecord set updated = :updated WHERE id = :id");
		update.setParameter("id", hr.getId());
		update.setParameter("updated", hr.getUpdated());
		update.executeUpdate();
	}

}
