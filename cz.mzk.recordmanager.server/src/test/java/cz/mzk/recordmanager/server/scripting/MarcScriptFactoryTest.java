package cz.mzk.recordmanager.server.scripting;

import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.mzk.recordmanager.server.AbstractTest;
import cz.mzk.recordmanager.server.marc.MarcRecord;
import cz.mzk.recordmanager.server.marc.MarcXmlParser;

public class MarcScriptFactoryTest extends AbstractTest {

	@Autowired
	private MarcXmlParser parser;

	@Autowired
	private MarcScriptFactory factory;

	@Test
	public void test() {
		MarcRecord record = parser.parseRecord(this.getClass()
				.getResourceAsStream("/records/marcxml/MZK01-001439241.xml"));
		InputStream is1 = getClass().getResourceAsStream(
				"/groovy/ExtendedMarc.groovy");
		InputStream is2 = getClass().getResourceAsStream(
				"/groovy/BaseMarc.groovy");
		MarcMappingScript script = factory.create(is1, is2);
		Map<String, Object> entries = script.parse(record);
		Assert.assertEquals(entries.get("author"), null);
		Assert.assertEquals(entries.get("published"), "Rožnov pod Radhoštěm : Proxima Bohemia, 2014");
		Assert.assertEquals(entries.get("title"), "Česká republika : města a obce České republiky : tradice, historie, památky, turistika, současnost / [redakce Veronika Ševčíková, Lenka Kovářová]");
		Assert.assertEquals(entries.get("format"), "Book");
	}

}