package cz.mzk.recordmanager.server.imports;

import java.util.List;

import org.marc4j.marc.Record;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.mzk.recordmanager.server.model.AntikvariatyRecord;
import cz.mzk.recordmanager.server.model.HarvestedRecord;
import cz.mzk.recordmanager.server.oai.harvest.HarvestedRecordWriter;
import cz.mzk.recordmanager.server.oai.harvest.OAIItemProcessor;
import cz.mzk.recordmanager.server.oai.model.OAIRecord;
import cz.mzk.recordmanager.server.springbatch.JobFailureListener;
import cz.mzk.recordmanager.server.util.Constants;

@Configuration
public class ImportRecordJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	private static final String STRING_OVERRIDEN_BY_EXPRESSION = null;

	private static final Long LONG_OVERRIDEN_BY_EXPRESSION = null;

	@Bean
	public Job ImportRecordsJob(
			@Qualifier(Constants.JOB_ID_IMPORT +":importRecordsStep") Step importRecordsStep) {
		return jobs.get(Constants.JOB_ID_IMPORT)
				.validator(new ImportRecordsJobParametersValidator())
				.listener(JobFailureListener.INSTANCE).flow(importRecordsStep)
				.end().build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT +":importRecordsStep")
	public Step importRecordsStep() throws Exception {
		return steps.get("updateRecordsJobStep")
				.<List<Record>, List<Record>> chunk(20)//
				.reader(importRecordsReader(STRING_OVERRIDEN_BY_EXPRESSION, STRING_OVERRIDEN_BY_EXPRESSION))//
				.writer(importRecordsWriter(LONG_OVERRIDEN_BY_EXPRESSION)) //
				.build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT +":importRecordsReader")
	@StepScope
	public ItemReader<List<Record>> importRecordsReader(
			@Value("#{jobParameters[" + Constants.JOB_PARAM_FORMAT + "]}") String strFormat,
			@Value("#{jobParameters[" + Constants.JOB_PARAM_IN_FILE + "]}") String filename)
			throws Exception {
			return new ImportRecordsFileReader(filename, strFormat);
	}
	
	@Bean(name=Constants.JOB_ID_IMPORT +":writer")
	@StepScope
	public ItemWriter<List<Record>> importRecordsWriter(@Value("#{jobParameters[" + Constants.JOB_PARAM_CONF_ID + "]}") Long configurationId) {
		return new ImportRecordsWriter(configurationId);
	}
	
	// Antikvariaty
	@Bean
	public Job AntikvariatyImportRecordsJob(
			@Qualifier(Constants.JOB_ID_IMPORT_ANTIKVARIATY +":importRecordsStep") Step importRecordsStep) {
		return jobs.get(Constants.JOB_ID_IMPORT_ANTIKVARIATY)
				.validator(new AntikvariatyImportJobParametersValidator())
				.listener(JobFailureListener.INSTANCE).flow(importRecordsStep)
				.end().build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT_ANTIKVARIATY +":importRecordsStep")
	public Step importAntikvariatyRecordsStep() throws Exception {
		return steps.get("antikvariaty:importRecordsStep")
				.<AntikvariatyRecord, AntikvariatyRecord> chunk(10)//
				.reader(importAntikvariatyReader(LONG_OVERRIDEN_BY_EXPRESSION))//
				.writer(importAntikvariatyWriter()) //
				.build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT_ANTIKVARIATY + ":reader")
	@StepScope
	public ItemReader<AntikvariatyRecord> importAntikvariatyReader(@Value("#{jobParameters[" + Constants.JOB_PARAM_CONF_ID + "]}") Long configId) throws Exception {
		return new AntikvariatyRecordsReader(configId);
	}

	@Bean(name=Constants.JOB_ID_IMPORT_ANTIKVARIATY + ":writer")
	@StepScope
	public ItemWriter<AntikvariatyRecord> importAntikvariatyWriter() {
		return new AntikvariatyRecordsWriter();
	}

	// Oai format
	@Bean
	public Job OaiImportRecordsJob(
			@Qualifier(Constants.JOB_ID_IMPORT_OAI +":importRecordsStep") Step importRecordsStep) {
		return jobs.get(Constants.JOB_ID_IMPORT_OAI)
				.validator(new ImportOaiRecordsJobParametersValidator())
				.listener(JobFailureListener.INSTANCE).flow(importRecordsStep)
				.end().build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT_OAI +":importRecordsStep")
	public Step importOaiRecordsStep() throws Exception {
		return steps.get(Constants.JOB_ID_IMPORT_OAI+"importRecordsStep")
				.<List<OAIRecord>, List<HarvestedRecord>> chunk(100)//
				.reader(importOaiRecordsReader(STRING_OVERRIDEN_BY_EXPRESSION))//
				.processor(oaiItemProcessor())
				.writer(harvestedRecordWriter()) //
				.build();
	}

	/**
	 * filename format:
	 * 1) /directory/file.txt - takes file file.txt
	 * 2) /directory/ - takes all files from directory
	 * @param filename
	 * @return
	 */
	@Bean(name=Constants.JOB_ID_IMPORT_OAI +":importRecordsReader")
	@StepScope
	public ItemReader<List<OAIRecord>> importOaiRecordsReader(
			@Value("#{jobParameters[" + Constants.JOB_PARAM_IN_FILE + "]}") String filename)
			throws Exception {
			return new ImportOaiRecordsFileReader(filename);
	}

	@Bean(name=Constants.JOB_ID_IMPORT_OAI+":processor")
    @StepScope
    public OAIItemProcessor oaiItemProcessor() {
    	return new OAIItemProcessor();
    }

	@Bean(name=Constants.JOB_ID_IMPORT_OAI+":HarvestedRecordWriter")
    @StepScope
    public ItemWriter<List<HarvestedRecord>> harvestedRecordWriter() {
    	return new HarvestedRecordWriter();
    }
	
	@Bean
	public Job importCosmotron996RecordsJob(
			@Qualifier(Constants.JOB_ID_IMPORT_COSMOTRON_996 +":importRecordsStep") Step importRecordsStep) {
		return jobs.get(Constants.JOB_ID_IMPORT_COSMOTRON_996)
				.validator(new ImportRecordsJobParametersValidator())
				.listener(JobFailureListener.INSTANCE).flow(importRecordsStep)
				.end().build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT_COSMOTRON_996 +":importRecordsStep")
	public Step importCosmotron996RecordsStep() throws Exception {
		return steps.get(Constants.JOB_ID_IMPORT_COSMOTRON_996+"importRecordsStep")
				.<List<Record>, List<Record>> chunk(100)//
				.reader(importRecordsReader(STRING_OVERRIDEN_BY_EXPRESSION, STRING_OVERRIDEN_BY_EXPRESSION))//
				.writer(importCosmotron996RecordsWriter(LONG_OVERRIDEN_BY_EXPRESSION)) //
				.build();
	}

	@Bean(name=Constants.JOB_ID_IMPORT_COSMOTRON_996 +":writer")
	@StepScope
	public ItemWriter<List<Record>> importCosmotron996RecordsWriter(@Value("#{jobParameters[" + Constants.JOB_PARAM_CONF_ID + "]}") Long configurationId) {
		return new ImportCosmotron996RecordsWriter(configurationId);
	}
}
