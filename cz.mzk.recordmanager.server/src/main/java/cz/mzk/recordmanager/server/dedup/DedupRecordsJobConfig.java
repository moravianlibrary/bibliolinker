package cz.mzk.recordmanager.server.dedup;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.io.CharStreams;

import cz.mzk.recordmanager.server.model.HarvestedRecord;
import cz.mzk.recordmanager.server.springbatch.SqlCommandTasklet;

@Configuration
public class DedupRecordsJobConfig {
	
	@Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    
    @Autowired
    private DataSource dataSource;
    
    private String updateDedupRecordSql = CharStreams.toString(new InputStreamReader(getClass() //
    		.getClassLoader().getResourceAsStream("job/dedupRecordsJob/updateDedupRecord.sql"), "UTF-8"));
    
    private String deleteRecordLinkSql = CharStreams.toString(new InputStreamReader(getClass() //
    		.getClassLoader().getResourceAsStream("job/dedupRecordsJob/deleteRecordLink.sql"), "UTF-8"));
    
    public DedupRecordsJobConfig() throws IOException {
    }
    
    @Bean
    public Job dedupRecordsJob(@Qualifier("dedupRecordsJob:deleteStep") Step deleteStep,
    		@Qualifier("dedupRecordsJob:updateStep") Step updateStep) {
        return jobs.get("dedupRecordsJob")
        		.validator(new DedupRecordsJobParametersValidator())
        		.flow(deleteStep)
				.next(updateStep)
				.end()
				.build();
    }
    
    @Bean(name="dedupRecordsJob:deleteStep")
    public Step deleteStep() throws Exception {
		return steps.get("dedupRecordsStep")
				.tasklet(updateDedupRecordTasklet())
				.build();
    }
    
    @Bean(name="dedupRecordsJob:updateStep")
    public Step step() throws Exception {
		return steps.get("dedupRecordsUpdateStep")
            .<HarvestedRecord, HarvestedRecord> chunk(100)
            .reader(reader())
            .writer(writer())
            .build();
    }
    
    @Bean(name="dedupRecordsJob:finalUpdateStep")
    public Step finalUpdateStep() throws Exception {
		return steps.get("dedupRecordsFinalUpdateStep")
				.tasklet(updateDedupRecordTasklet())
				.build();
    }
    
	
    @Bean(name="dedupRecordsJob:reader")
	@StepScope
    public ItemReader<HarvestedRecord> reader() throws Exception {
		JdbcPagingItemReader<HarvestedRecord> reader = new JdbcPagingItemReader<HarvestedRecord>();
		SqlPagingQueryProviderFactoryBean pqpf = new SqlPagingQueryProviderFactoryBean();
		pqpf.setDataSource(dataSource);
		pqpf.setSelectClause("SELECT id,isbn,title,publication_year,physical_format");
		pqpf.setFromClause("FROM harvested_record hr");
		//TODO && updated > last updated
		pqpf.setWhereClause("WHERE hr.updated IS NULL");
		pqpf.setSortKey("id");
		reader.setRowMapper(HarvestedRecordLimitedRowMapper.INSTANCE);
		reader.setPageSize(20);
    	reader.setQueryProvider(pqpf.getObject());
    	reader.setDataSource(dataSource);
    	reader.afterPropertiesSet();
    	return reader;
    }
    
    @Bean(name="dedupRecordsJob:processor")
	@StepScope
	public UpdateHarvestedRecordProcessor processor() {
		return new UpdateHarvestedRecordProcessor();
	}
    
    @Bean(name="dedupRecordsJob:writer")
	@StepScope
    public DedupRecordsWriter writer() {
    	return new DedupRecordsWriter();
    }
    
    @Bean(name="dedupRecordsJob:updateDedupRecordTasklet")
	@StepScope
    public Tasklet updateDedupRecordTasklet() {
    	return new SqlCommandTasklet(updateDedupRecordSql);
    }

}
