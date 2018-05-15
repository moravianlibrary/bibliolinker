package cz.mzk.recordmanager.server.util;

public class Constants {

	public static final String JOB_ID_HARVEST			= "oaiHarvestJob";
	public static final String JOB_ID_HARVEST_PART		= "oaiPartitionedHarvestJob";
	public static final String JOB_ID_HARVEST_AUTH		= "oaiHarvestAuthorityJob";
	public static final String JOB_ID_HARVEST_ONE_BY_ONE = "oaiHarvestOneByOneJob";
	public static final String JOB_ID_HARVEST_KRAMERIUS = "krameriusHarvestJob";
	public static final String JOB_ID_HARVEST_COSMOTRON = "cosmotronHarvestJob";
	public static final String JOB_ID_FULLTEXT_KRAMERIUS = "krameriusFulltextJob";
	public static final String JOB_ID_MISSING_FULLTEXT_KRAMERIUS = "krameriusMissingFulltextJob";
	public static final String JOB_ID_HARVEST_SINGLE	= "oaiHarvestSingleRecordJob";
	public static final String JOB_ID_DEDUP				= "dedupRecordsJob";
	public static final String JOB_ID_SOLR_INDEX_ALL_RECORDS		= "indexAllRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_HARVESTED_RECORDS	= "indexHarvestedRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX					= "indexRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_INDIVIDUAL_RECORDS	= "indexIndividualRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_INDIVIDUAL_HARVESTED_RECORDS	= "indexIndividualHarvestedRecordsToSolrJob";
	public static final String JOB_ID_EXPORT						= "exportRecordsJob";
	public static final String JOB_ID_EXPORT_COSMOTRON_996			= "exportCosmotron996Job";
	public static final String JOB_ID_EXPORT_SFX					= "exportSfxRecordsJob";
	public static final String JOB_ID_IMPORT						= "importRecordsJob";
	public static final String JOB_ID_MULTI_THREADS_IMPORT			= "multiImportRecordsJob";
	public static final String JOB_ID_DOWNLOAD_IMPORT				= "downloadAndImportRecordsJob";
	public static final String JOB_ID_IMPORT_ANTIKVARIATY			= "antikvariatyImportRecordsJob";
	public static final String JOB_ID_IMPORT_OAI					= "importOaiRecordsJob";
	public static final String JOB_ID_IMPORT_COSMOTRON_996			= "importCosmotron996RecordsJob";
	public static final String JOB_ID_IMPORT_INSPIRATION			= "importInspirationJob";
	public static final String JOB_ID_IMPORT_TEZAURUS				= "importTezaurusRecordsJob";
	public static final String JOB_ID_DELETE_INSPIRATION			= "deleteInspirationJob";
	public static final String JOB_ID_REGEN_DEDUP_KEYS				= "regenerateDedupKeysJob";
	public static final String JOB_ID_REGEN_MISSING_DEDUP_KEYS		= "regenerateMissingDedupKeysJob";
	public static final String JOB_ID_DELETE_ALL_RECORDS_FROM_SOLR	= "deleteAllRecordsFromSolrJob";
	public static final String JOB_ID_GENERATE_SKAT_DEDUP_KEYS		= "generateSkatDedupKeys";
	public static final String JOB_ID_GENERATE_LOCAL_SKAT_DEDUP_KEYS	= "generateLocalSkatDedupKeysJob";
	public static final String JOB_ID_MANUALLY_MERGED_SKAT_DEDUP_KEYS	= "generateManuallyMergedSkatDedupKeysJob";
	public static final String JOB_ID_GENERATE_ITEM_ID				= "generateItemIdJob";
	public static final String JOB_ID_HARVEST_OBALKY_KNIH		= "obalkyKnihHarvestJob";
	public static final String JOB_ID_HARVEST_ZAKONYPROLIDI			= "zakonyProLidiHarvestJob";
	public static final String JOB_ID_FULLTEXT_ZAKONYPROLIDI		= "zakonyProLidiFulltextJob";
	public static final String JOB_ID_FILTER_CASLIN					= "filterCaslinRecordsJob";
	public static final String JOB_ID_HARVEST_ADRESAR				= "adresarHarvestJob";
	public static final String JOB_ID_FULLTEXT_MANUSCRIPTORIUM		= "manuscriptoriumFulltextJob";
	public static final String JOB_ID_EXPORT_RECORDS_FOR_CLASSIFIER = "exportRecordsForClassifierJob";
	public static final String JOB_ID_AGROVOC_CONVERTOR				= "convertAgrovocRecordsJob";
	
	public static final String JOB_PARAM_CONF_ID 		= "configurationId";
	public static final String JOB_PARAM_FROM_DATE 		= "from";
	public static final String JOB_PARAM_UNTIL_DATE 	= "to";
	public static final String JOB_PARAM_SOLR_URL		= "solrUrl";
	public static final String JOB_PARAM_SOLR_QUERY		= "query";
	public static final String JOB_PARAM_FORMAT			= "format";
	public static final String JOB_PARAM_RESUMPTION_TOKEN = "resumptionToken";
	public static final String JOB_PARAM_FULLTEXT_FIRST	= "firstId";
	public static final String JOB_PARAM_FULLTEXT_LAST  = "lastId";
	public static final String JOB_PARAM_REPEAT    		= "repeat";
	public static final String JOB_PARAM_INCREMENTAL	= "incremental";
	public static final String JOB_PARAM_REHARVEST		= "reharvest";
	public static final String JOB_PARAM_RECORD_IDS		= "recordIds";
	public static final String JOB_PARAM_START_TIME		= "startTime";
	public static final String JOB_PARAM_UUID			= "uuid";
	public static final String JOB_PARAM_FIRST_ID		= "firstId";
	public static final String JOB_PARAM_LAST_ID		= "lastId";
	public static final String JOB_PARAM_SINGLE_ID		= "singleId";
	public static final String JOB_PARAM_DELETED		= "deleted"; // for export

	public static final String JOB_PARAM_TRUE_VALUE		= "true";
	public static final String JOB_PARAM_ONE_VALUE		= "1";

	public static final String JOB_PARAM_OUT_FILE		= "outFile";
	public static final String JOB_PARAM_IN_FILE		= "inFile";
	public static final String JOB_PARAM_DELETED_OUT_FILE = "deletedOutFile";
	public static final String JOB_PARAM_DELETE_INSPIRATION = "inspiration";
	
	public static final String JOB_PARAM_TIMESTAMP		= "timestamp";
	
	public static final String METADATA_FORMAT_MARC21      = "marc21-xml";
	public static final String METADATA_FORMAT_XML_MARC    = "xml-marc";
	public static final String METADATA_FORMAT_MARC_CPK    = "marccpk";
	public static final String METADATA_FORMAT_OAI_MARCXML_CPK = "oai_marcxml_cpk";
	public static final String METADATA_FORMAT_MARC21E     = "marc21e";
	public static final String METADATA_FORMAT_DUBLIN_CORE = "dublinCore";
	public static final String METADATA_FORMAT_ESE		= "ese";
	
	public static final String PREFIX_AGROVOC			= "agrovoc";
	public static final String PREFIX_ANL				= "anl";
	public static final String PREFIX_AUTH				= "auth";
	public static final String PREFIX_BMC				= "bmc";
	public static final String PREFIX_CASLIN			= "caslin";
	public static final String PREFIX_CBVK				= "cbvk";
	public static final String PREFIX_CELITEBIB			= "celitebib";
	public static final String PREFIX_IIR				= "iir";
	public static final String PREFIX_KKVY				= "kkvy";
	public static final String PREFIX_KNAV				= "knav";
	public static final String PREFIX_KRAM_KNAV			= "kram-knav";
	public static final String PREFIX_KRAM_MZK			= "kram-mzk";
	public static final String PREFIX_KRAM_NKP			= "kram-nkp";
	public static final String PREFIX_KRAM_NTK			= "kram-ntk";
	public static final String PREFIX_KRAM3_NKP			= "kram3-nkp";
	public static final String PREFIX_KVKL				= "kvkl";
	public static final String PREFIX_TDKIV				= "tdkiv";
	public static final String PREFIX_LIBRARY			= "library";
	public static final String PREFIX_MANUSCRIPTORIUM	= "manuscript";
	public static final String PREFIX_MESH				= "mesh";
	public static final String PREFIX_MKP_EBOOKS		= "mkpe";
	public static final String PREFIX_MKUO				= "mkuo";
	public static final String PREFIX_MZK				= "mzk";
	public static final String PREFIX_MZKNORMS			= "unmz";
	public static final String PREFIX_NKP				= "nkp";
	public static final String PREFIX_NLK				= "nlk";
	public static final String PREFIX_NTK				= "ntk";
	public static final String PREFIX_OPENLIB			= "openlib";
	public static final String PREFIX_OSOBNOSTI			= "osobnosti";
	public static final String PREFIX_PKJAK				= "pkjak";
	public static final String PREFIX_SFXJIBCBVK		= "sfxjibcbvk";
	public static final String PREFIX_SFXJIBFREE		= "sfxjibfree";
	public static final String PREFIX_SFXJIBKFBZ		= "sfxjibkfbz";
	public static final String PREFIX_SFXJIBKNAV		= "sfxjibknav";
	public static final String PREFIX_SFXJIBKVKL		= "sfxjibkvkl";
	public static final String PREFIX_SFXJIBMKP			= "sfxjibmkp";
	public static final String PREFIX_SFXJIBMUNI		= "sfxjibmuni";
	public static final String PREFIX_SFXJIBMZK			= "sfxjibmzk";
	public static final String PREFIX_SFXJIBNKP			= "sfxjibnkp";
	public static final String PREFIX_SFXJIBNLK			= "sfxjibnlk";
	public static final String PREFIX_SFXTECHLIBNTK			= "sfxjibntk";
	public static final String PREFIX_SFXJIBSVKHK		= "sfxjibsvkhk";
	public static final String PREFIX_SFXJIBSVKOS		= "sfxjibsvkos";
	public static final String PREFIX_SFXTECHLIBUOCHB		= "sfxjibuochb";
	public static final String PREFIX_SFXJIBVKOL		= "sfxjibvkol";
	public static final String PREFIX_SFXTECHLIBVSCHT		= "sfxjibvscht";
	public static final String PREFIX_SFXJIBNLK_PERIODICALS	= "sfxnlkper";
	public static final String PREFIX_SFXJIBIREL		= "sfxjibirel";
	public static final String PREFIX_SFXKNAV			= "sfxknav";
	public static final String PREFIX_SVKKL				= "svkkl";
	public static final String PREFIX_SVKUL				= "svkul";
	public static final String PREFIX_TRE				= "tre";
	public static final String PREFIX_VKOL				= "vkol";
	public static final String PREFIX_ZAKONY			= "zakony";
	public static final String PREFIX_UPV				= "upv";
	public static final String PREFIX_FREE				= "free";
	
	public static final String LIBRARY_NAME_KKL			= "KKL";
	public static final String LIBRARY_NAME_NKP			= "NKP";
	public static final String LIBRARY_NAME_SLK			= "SLK";
	public static final String LIBRARY_NAME_STT			= "STT";
	
	public static final long IMPORT_CONF_ID_CASLIN 		= 316L;
	public static final long IMPORT_CONF_ID_SLK			= 321L;
	public static final long IMPORT_CONF_ID_KKL			= 325L;
	public static final long IMPORT_CONF_ID_STT			= 326L;
	public static final long IMPORT_CONF_ID_ZAKONY		= 344L;
	public static final long IMPORT_CONF_ID_UPV			= 347L;
	public static final Long IMPORT_CONF_ID_AUTHORITY	= 400L;
	
	public static final String DOCUMENT_AVAILABILITY_ONLINE     = "online";
	public static final String DOCUMENT_AVAILABILITY_PROTECTED  = "protected";
	public static final String DOCUMENT_AVAILABILITY_UNKNOWN    = "unknown";

	public static final String JOB_PARAM_RECORD_ID		= "recordId";

	public static final String COSMOTRON_RECORD_ID_CHAR = "_";

}	
