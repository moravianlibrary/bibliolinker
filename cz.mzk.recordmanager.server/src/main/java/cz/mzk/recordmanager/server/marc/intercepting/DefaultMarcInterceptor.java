package cz.mzk.recordmanager.server.marc.intercepting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.mzk.recordmanager.server.ClasspathResourceProvider;
import cz.mzk.recordmanager.server.export.IOFormat;
import cz.mzk.recordmanager.server.marc.MarcRecord;
import cz.mzk.recordmanager.server.marc.MarcRecordImpl;
import cz.mzk.recordmanager.server.marc.marc4j.MarcFactoryImpl;
import cz.mzk.recordmanager.server.marc.marc4j.RecordImpl;
import cz.mzk.recordmanager.server.model.ImportConfiguration;
import cz.mzk.recordmanager.server.model.Sigla;
import cz.mzk.recordmanager.server.scripting.MappingResolver;
import cz.mzk.recordmanager.server.scripting.ResourceMappingResolver;

public class DefaultMarcInterceptor implements MarcRecordInterceptor {

	private static Logger logger = LoggerFactory.getLogger(DefaultMarcInterceptor.class);
	private static final MappingResolver SIGLA_RESOLVER = new ResourceMappingResolver(new ClasspathResourceProvider());
	private Record record;
	private ImportConfiguration conf;
	protected static final MarcFactory MARC_FACTORY = new MarcFactoryImpl();
	private static final char ITEM_ID_SUBFIELD_CHAR = 't';
	private static final String SIGLA_MAP = "item_id_sigla.map";

	public DefaultMarcInterceptor(Record record) {
		this.record = record;
	}
	
	public DefaultMarcInterceptor(Record record, ImportConfiguration conf) {
		this.record = record;
		this.conf = conf;
	}

	@Override
	public byte[] intercept() {
		if (conf.getItemId() == null) {
			MarcRecord marcRecord = new MarcRecordImpl(record);
			return marcRecord.export(IOFormat.XML_MARC).getBytes(StandardCharsets.UTF_8);
		}
		Record newRecord = new RecordImpl();
		newRecord.setLeader(record.getLeader());
		for (ControlField cf : record.getControlFields()) {
			newRecord.addVariableField(cf);
		}

		for (DataField df : record.getDataFields()) {
			processField996(df);
			newRecord.addVariableField(df);
		}

		return new MarcRecordImpl(newRecord).export(IOFormat.XML_MARC).getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * add item id to field 996
	 * @param df
	 */
	protected void processField996(DataField df) {
		if (df.getTag().equals("996")) {
			Long importConfId = conf.getId();
			String itemIdType = conf.getItemId();
			for (Subfield sf : df.getSubfields(ITEM_ID_SUBFIELD_CHAR)) {
				df.removeSubfield(sf);
			}
			boolean missing = false;
			String sigla;
			List<String> getSiglas = null;
			try {
				getSiglas = SIGLA_RESOLVER.resolve(SIGLA_MAP).get(importConfId.toString());
			} catch (IOException e) {
			}
			if (getSiglas != null && !getSiglas.isEmpty()) {
				sigla = getSiglas.get(0);
			}
			else {
				List<Sigla> siglas = conf.getSiglas();
				sigla = !siglas.isEmpty() ? siglas.get(0).getUniqueId().getSigla() : "";
			}
			if (itemIdType.equals("aleph")) {
				String j = df.getSubfield('j') != null ? df.getSubfield('j').getData().toUpperCase() : "";
				String w = df.getSubfield('w') != null ? df.getSubfield('w').getData() : "";
				String u = df.getSubfield('u') != null ? df.getSubfield('u').getData() : "";
				if (j.equals("") || w.equals("") || u.equals("")) missing = true;
				else df.addSubfield(MARC_FACTORY.newSubfield(ITEM_ID_SUBFIELD_CHAR, sigla + "." + j + w + u));
			}
			else if (itemIdType.equals("tre")) {
				String w = df.getSubfield('w') != null ? df.getSubfield('w').getData() : "";
				if (w.equals("")) missing = true;
				else df.addSubfield(MARC_FACTORY.newSubfield(ITEM_ID_SUBFIELD_CHAR, sigla + "." + w));
			}
			else if (itemIdType.equals("nlk")) {
				String a = df.getSubfield('a') != null ? df.getSubfield('a').getData() : "";
				if (a.equals("")) missing = true;
				else df.addSubfield(MARC_FACTORY.newSubfield(ITEM_ID_SUBFIELD_CHAR, sigla + "." + a));
			}
			else if (itemIdType.equals("svkul")) {
				String b = df.getSubfield('b') != null ? df.getSubfield('b').getData() : "";
				if (b.equals("")) missing = true;
				else {
					if (b.startsWith("31480") && b.length() >= 8) {
						b = b.substring(5);
					}
					df.addSubfield(MARC_FACTORY.newSubfield(ITEM_ID_SUBFIELD_CHAR, sigla + "." + b));
				}
			}
			else if (itemIdType.equals("other")) {
				String b = df.getSubfield('b') != null ? df.getSubfield('b').getData() : "";
				if (b.equals("")) missing = true;
				else df.addSubfield(MARC_FACTORY.newSubfield(ITEM_ID_SUBFIELD_CHAR, sigla + "." + b));
			}
			if (missing) logger.info(String.format("Missing data for itemId: import_confid=%d, 001=%s",
					conf.getId(), record.getControlNumber()));
		}
	}
	
	protected Record getRecord() {
		return this.record;
	}
}
