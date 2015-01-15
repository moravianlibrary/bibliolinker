package cz.mzk.recordmanager.server.oai.harvest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cz.mzk.recordmanager.server.oai.model.OAIListRecords;
import cz.mzk.recordmanager.server.util.HttpClient;
import cz.mzk.recordmanager.server.util.UrlUtils;

public class OAIHarvester {

	private static Logger logger = LoggerFactory.getLogger(OAIHarvester.class);

	private final HttpClient httpClient;

	private final OAIHarvesterParams parameters;
	
	private final Unmarshaller unmarshaller;

	public OAIHarvester(HttpClient httpClient, OAIHarvesterParams parameters) {
		Preconditions.checkArgument(parameters.getUrl() != null, "missing url in parameters");
		Preconditions.checkArgument(parameters.getMetadataPrefix() != null, "missing metadataPrefix in parameters");
		this.parameters = parameters;
		this.httpClient = httpClient;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(OAIListRecords.class);
			this.unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException je) {
			throw new RuntimeException(je);
		}
	}

	public OAIListRecords listRecords(String resumptionToken) {
		String url = createUrl(resumptionToken);
		logger.info("About to harvest url: {}", url);
		try (InputStream is = httpClient.executeGet(url)) {
			OAIListRecords records = (OAIListRecords) unmarshaller
					.unmarshal(is);
			logger.info("Finished harvesting of url: {}", url);
			return records;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} catch (JAXBException je) {
			throw new RuntimeException(je);
		}
	}

	private String createUrl(String resumptionToken) {
		Map<String, String> params = new HashMap<String, String>();
		if (resumptionToken == null) {
			params.put("metadataPrefix", parameters.getMetadataPrefix());
			if (parameters.getSet() != null) {
				params.put("set", parameters.getSet());
			}
		} else {
			params.put("resumptionToken", resumptionToken);
		}
		params.put("verb", "ListRecords");
		return UrlUtils.buildUrl(parameters.getUrl(), params);
	}

}
