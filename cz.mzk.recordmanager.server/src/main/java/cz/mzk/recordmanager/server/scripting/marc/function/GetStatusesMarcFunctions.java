package cz.mzk.recordmanager.server.scripting.marc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.springframework.stereotype.Component;

import cz.mzk.recordmanager.server.marc.MarcRecord;
import cz.mzk.recordmanager.server.scripting.marc.MarcFunctionContext;
import cz.mzk.recordmanager.server.util.Constants;
import cz.mzk.recordmanager.server.util.SolrUtils;

@Component
public class GetStatusesMarcFunctions implements MarcRecordFunctions {

	protected final static String ABSENT = "a";

	protected final static String PRESENT = "p";
	
	protected final static Pattern STOP_WORDS = Pattern.compile("(?i).*obsah.*|.*tituln.*|.*abstrakt.*|"
			+ ".*content.*|.*informace.*časopise.*");
	
	protected final static String FREESTACK = "0";

	public List<String> getStatuses(MarcFunctionContext ctx) {
		MarcRecord record = ctx.record();
		List<String> statuses = new ArrayList<String>();
		statuses.addAll(ctx.metadataRecord().getDefaultStatuses());
		if (statuses.isEmpty()) statuses.addAll(getStatusFrom856(record, "856"));
		statuses.addAll(getStatuses(record, "996"));
		return statuses;
	}

	private List<String> getStatusFrom856(MarcRecord record, String statusField) {
		List<String> result = new ArrayList<>();
		for (DataField field: record.getDataFields(statusField)) {
			if ((field.getSubfield('3') == null || !STOP_WORDS.matcher(field.getSubfield('3').getData()).matches()) 
					|| (field.getSubfield('y') == null || !STOP_WORDS.matcher(field.getSubfield('y').getData()).matches())) {
				result.addAll(SolrUtils.createHierarchicFacetValues(Constants.DOCUMENT_AVAILABILITY_ONLINE, Constants.DOCUMENT_AVAILABILITY_UNKNOWN));
			}
		}
		return result;
	}

	private List<String> getStatuses(MarcRecord record, String statusField) {
		List<DataField> fields = record.getAllFields().get(statusField);
		if (fields == null || fields.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> statuses = new ArrayList<String>();
		boolean present = false;
		boolean absent = false;
		boolean freestack = false;
		for (DataField field : fields) {
			Subfield s = field.getSubfield('s');
			if (s == null) {
				continue;
			}
			String statusInS = s.getData().toLowerCase();
			switch (statusInS) {
			case ABSENT:
				absent = true;
				break;
			case PRESENT:
				present = true;
				break;
			}
			
			s = field.getSubfield('a');
			if (s == null) {
				continue;
			}
			String statusInA = s.getData().toLowerCase();
			switch (statusInA) {
			case FREESTACK:
				freestack = true;
				break;
			}
		}
		if (absent) {
			statuses.addAll(SolrUtils.createHierarchicFacetValues("absent"));
		}
		if (present) {
			statuses.addAll(SolrUtils.createHierarchicFacetValues("present"));
		}
		if (freestack) {
			statuses.addAll(SolrUtils.createHierarchicFacetValues("freestack"));
		}
		return statuses;
	}

}
