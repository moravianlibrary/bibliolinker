package cz.mzk.recordmanager.server.imports;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mzk.recordmanager.server.model.ObalkyKnihTOC;
import cz.mzk.recordmanager.server.oai.dao.ObalkyKnihTOCDAO;

public class ObalkyKnihRecordsWriter implements ItemWriter<ObalkyKnihTOC> {

	@Autowired
	private ObalkyKnihTOCDAO obalkyKnihTOCDao;

	@Override
	public void write(List<? extends ObalkyKnihTOC> items) throws Exception {
		for (ObalkyKnihTOC item : items) {
			List<ObalkyKnihTOC> tocs = obalkyKnihTOCDao.findByExample(item);
			if (tocs.isEmpty()) {
				obalkyKnihTOCDao.persist(item);
			} else {
				ObalkyKnihTOC toc = tocs.get(0);
				toc.setToc(item.getToc());
				obalkyKnihTOCDao.persist(toc);
			}
		}
	}

}