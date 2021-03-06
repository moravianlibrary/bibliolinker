package cz.mzk.recordmanager.server.scripting.dc;

import java.io.InputStream;

import cz.mzk.recordmanager.server.scripting.MappingScript;
import cz.mzk.recordmanager.server.scripting.MappingScriptFactory;

public interface DublinCoreScriptFactory extends MappingScriptFactory<DublinCoreFunctionContext> {
	
	@Override
	MappingScript<DublinCoreFunctionContext> create(InputStream... scripts);

}
