package cz.mzk.recordmanager.server.imports;

import cz.mzk.recordmanager.server.bibliolinker.keys.DelegatingBiblioLinkerKeysParser;
import cz.mzk.recordmanager.server.marc.MarcXmlParser;
import cz.mzk.recordmanager.server.marc.marc4j.MarcFactoryImpl;
import cz.mzk.recordmanager.server.metadata.MetadataMarcRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecord;
import cz.mzk.recordmanager.server.oai.dao.HarvestedRecordDAO;
import cz.mzk.recordmanager.server.oai.dao.IsbnDAO;
import cz.mzk.recordmanager.server.util.identifier.ISBNUtils;
import org.hibernate.SessionFactory;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportPalmknihyWriter extends ImportRecordsWriter implements ItemWriter<List<Record>>, StepExecutionListener {

	@Autowired
	protected DelegatingBiblioLinkerKeysParser biblioLinkerKeysParser;

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private MarcXmlParser marcXmlParser;

	@Autowired
	private HarvestedRecordDAO hrDao;

	@Autowired
	private IsbnDAO isbnDAO;

	private final MarcFactory factory = MarcFactoryImpl.newInstance();

	public ImportPalmknihyWriter(Long configurationId) {
		super(configurationId);
	}

	@Override
	public void write(List<? extends List<Record>> items) throws Exception {
		try {
			writeInner(items);
		} finally {
			sessionFactory.getCurrentSession().flush();
			sessionFactory.getCurrentSession().clear();
		}
	}

	private static final HashMap<Long, Pattern> ID_PARSER = new HashMap<>();

	static {
		ID_PARSER.put(328L, Pattern.compile("\\d+"));
		ID_PARSER.put(333L, Pattern.compile("\\d{9}"));
	}

	private static final HashMap<Long, String> URL = new HashMap<>();

	static {
		URL.put(328L, "https://katalog.cbvk.cz/arl-cbvk/cs/detail-cbvk_us_cat-%s-titul");
		URL.put(333L, "https://aleph.knihovna-pardubice.cz/F/?func=direct&doc_number=%s");
	}


	protected void writeInner(List<? extends List<Record>> items) {
		for (List<Record> records : items) {
			for (Record currentRecord : records) {
				Long isbn = null;
				DataField field856 = null;
				String vyp = "0";
				for (DataField df : currentRecord.getDataFields()) {
					if (df.getTag().equals("020") && df.getSubfield('a') != null) {
						isbn = ISBNUtils.toISBN13Long(df.getSubfield('a').getData());
					}
					if (df.getTag().equals("856")) field856 = df;
					if (df.getTag().equals("VYP")) vyp = df.getSubfield('a').getData();
				}
				if (isbn == null || field856 == null || !vyp.equals("1")) continue;
				for (byte[] rawRecord : isbnDAO.findCaslinHrByIsbn(isbn)) {
					Record palmknihy = marcXmlParser.parseUnderlyingRecord(rawRecord);
					palmknihy.addVariableField(field856);
					Matcher matcher = MetadataMarcRecord.PALMKNIHY_ID.matcher(field856.getSubfield('u').getData());
					if (!matcher.matches()) continue;
					for (HarvestedRecord hrLib : hrDao.getByPalmknihyId(matcher.group(1))) {
						String record_id = hrLib.getUniqueId().getRecordId();
						if (!ID_PARSER.containsKey(hrLib.getHarvestedFrom().getId())) continue;
						Matcher matcher1 = ID_PARSER.get(hrLib.getHarvestedFrom().getId())
								.matcher(hrLib.getUniqueId().getRecordId());
						if (matcher1.find()) {
							record_id = matcher1.group(0);
						}
						if (!URL.containsKey(hrLib.getHarvestedFrom().getId())) continue;
						palmknihy.addVariableField(factory.newDataField("856", ' ', ' ', "u",
								String.format(URL.get(hrLib.getHarvestedFrom().getId()), record_id),
								"z", hrLib.getHarvestedFrom().getIdPrefix()));
					}
					palmknihy.addVariableField(factory.newDataField("OAI", ' ', ' ', "a", currentRecord.getControlNumber()));
					processRecord(palmknihy);
					break;
				}
			}
		}
	}

}
