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
	public static final String JOB_ID_BIBLIO_LINKER		= "biblioLinkerJob";
	public static final String JOB_ID_BIBLIO_LINKER_SIMILAR	= "biblioLinkerSimilarJob";
	public static final String JOB_ID_SOLR_INDEX_ALL_RECORDS		= "indexAllRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_ORPHANED_RECORDS	= "indexOrphanedRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_HARVESTED_RECORDS	= "indexHarvestedRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX					= "indexRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_INDIVIDUAL_RECORDS	= "indexIndividualRecordsToSolrJob";
	public static final String JOB_ID_SOLR_INDEX_INDIVIDUAL_HARVESTED_RECORDS	= "indexIndividualHarvestedRecordsToSolrJob";
	public static final String JOB_ID_EXPORT						= "exportRecordsJob";
	public static final String JOB_ID_EXPORT_COSMOTRON_996			= "exportCosmotron996Job";
	public static final String JOB_ID_EXPORT_SFX					= "exportSfxRecordsJob";
	public static final String JOB_ID_EXPORT_DUPLICITY				= "exportDuplicityJob";
	public static final String JOB_ID_IMPORT						= "importRecordsJob";
	public static final String JOB_ID_MULTI_THREADS_IMPORT			= "multiImportRecordsJob";
	public static final String JOB_ID_DOWNLOAD_IMPORT				= "downloadAndImportRecordsJob";
	public static final String JOB_ID_IMPORT_ANTIKVARIATY			= "antikvariatyImportRecordsJob";
	public static final String JOB_ID_IMPORT_OAI					= "importOaiRecordsJob";
	public static final String JOB_ID_IMPORT_OAI_COSMOTRON			= "importOaiCosmotronRecordsJob";
	public static final String JOB_ID_IMPORT_COSMOTRON_996			= "importCosmotron996RecordsJob";
	public static final String JOB_ID_IMPORT_INSPIRATION			= "importInspirationJob";
	public static final String JOB_ID_IMPORT_TEZAURUS				= "importTezaurusRecordsJob";
	public static final String JOB_ID_IMPORT_TITLE_OLD_SPELLING		= "importTitleOldSpellingJob";
	public static final String JOB_ID_DELETE_INSPIRATION			= "deleteInspirationJob";
	public static final String JOB_ID_DROP_DEDUP_KEYS				= "dropDedupKeysJob";
	public static final String JOB_ID_REGEN_DEDUP_KEYS				= "regenerateDedupKeysJob";
	public static final String JOB_ID_REGEN_MISSING_DEDUP_KEYS		= "regenerateMissingDedupKeysJob";
	public static final String JOB_ID_DELETE_ALL_RECORDS_FROM_SOLR	= "deleteAllRecordsFromSolrJob";
	public static final String JOB_ID_GENERATE_SKAT_DEDUP_KEYS		= "generateSkatDedupKeys";
	public static final String JOB_ID_GENERATE_LOCAL_SKAT_DEDUP_KEYS	= "generateLocalSkatDedupKeysJob";
	public static final String JOB_ID_MANUALLY_MERGED_SKAT_DEDUP_KEYS	= "generateManuallyMergedSkatDedupKeysJob";
	public static final String JOB_ID_GENERATE_ITEM_ID				= "generateItemIdJob";
	public static final String JOB_ID_UPDATE_CASLIN_VIEW			= "updateCaslinRecordsViewJob";
	public static final String JOB_ID_HARVEST_OBALKY_KNIH		= "obalkyKnihHarvestJob";
	public static final String JOB_ID_HARVEST_ZAKONYPROLIDI			= "zakonyProLidiHarvestJob";
	public static final String JOB_ID_FULLTEXT_ZAKONYPROLIDI		= "zakonyProLidiFulltextJob";
	public static final String JOB_ID_FILTER_CASLIN					= "filterCaslinRecordsJob";
	public static final String JOB_ID_HARVEST_ADRESAR				= "adresarHarvestJob";
	public static final String JOB_ID_FULLTEXT_MANUSCRIPTORIUM		= "manuscriptoriumFulltextJob";
	public static final String JOB_ID_EXPORT_RECORDS_FOR_CLASSIFIER = "exportRecordsForClassifierJob";
	public static final String JOB_ID_AGROVOC_CONVERTOR				= "convertAgrovocRecordsJob";
	public static final String JOB_ID_IMPORT_ANNOTATIONS 			= "importAnnotationsObalkyJob";
	public static final String JOB_ID_DELETE_ANNOTATIONS 			= "deleteAnnotationsObalkyJob";
	public static final String JOB_ID_REGENERATE_BL_KEYS 			= "regenerateBiblioLinkerKeysJob";
	public static final String JOB_ID_REGENERATE_BL_MISSING_KEYS	= "regenerateBiblioLinkerMissingKeysJob";
	public static final String JOB_ID_HARVEST_KRAM_AVAILABILITY		= "harvestKramAvailabilityJob";
	public static final String JOB_ID_HARVEST_ZISKEJ_LIBRARIES		= "harvestZiskejLibrariesJob";
	public static final String JOB_ID_FULLTEXT_ANALYSER 			= "fulltextAnalyserJob";
	public static final String JOB_ID_SEMANTIC_ENRICHMENT			= "semanticEnrichmentJob";
	public static final String JOB_ID_IMPORT_KNOWLEDGE_BASE			= "importKnowledgeBaseJob";
	public static final String JOB_ID_CLASSIFIER					= "classifierJob";
	public static final String JOB_ID_REGEN_DNNT					= "regenerateDnntKeysJob";
	public static final String JOB_ID_HARVEST_CASLIN_LINKS			= "harvestCaslinLinksJob";

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
	public static final String JOB_PARAM_TYPE			= "type";
	public static final String JOB_PARAM_VIEW			= "view";
	public static final String JOB_PARAM_UPDATE_TIMESTAMP = "updateTimestamp";
	public static final String JOB_PARAM_INDEXED_FORMAT = "indexedFormat";

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
	public static final String PREFIX_ARCHBIB			= "archbib";
	public static final String PREFIX_BMC				= "bmc";
	public static final String PREFIX_BOOKPORT			= "bookport";
	public static final String PREFIX_CASLIN			= "caslin";
	public static final String PREFIX_CBVK				= "cbvk";
	public static final String PREFIX_CELITEBIB			= "celitebib";
	public static final String PREFIX_CMUZ				= "cmuz";
	public static final String PREFIX_CZHISTBIB			= "czhistbib";
	public static final String PREFIX_DIVABIB			= "divabib";
	public static final String PREFIX_FREE				= "free";
	public static final String PREFIX_IIR				= "iir";
	public static final String PREFIX_KKKV				= "kkkv";
	public static final String PREFIX_KKVY				= "kkvy";
	public static final String PREFIX_KNAV				= "knav";
	public static final String PREFIX_KNEP				= "knep";
	public static final String PREFIX_KRAM3_NKP			= "kram3-nkp";
	public static final String PREFIX_KRAM_CBVK			= "kram-cbvk";
	public static final String PREFIX_KRAM_CUNI			= "kram-cuni";
	public static final String PREFIX_KRAM_CUNIFSV		= "kram-cunifsv";
	public static final String PREFIX_KRAM_CUNILF1		= "kram-cunilf1";
	public static final String PREFIX_KRAM_DIFMOE		= "kram-difmoe";
	public static final String PREFIX_KRAM_DSMO			= "kram-dsmo";
	public static final String PREFIX_KRAM_HMT			= "kram-hmt";
	public static final String PREFIX_KRAM_LMDA			= "kram-lmda";
	public static final String PREFIX_KRAM_MENDELU		= "kram-mendelu";
	public static final String PREFIX_KRAM_MJH			= "kram-mjh";
	public static final String PREFIX_KRAM_MVCHK		= "kram-mvchk";
	public static final String PREFIX_KRAM_NACR			= "kram-nacr";
	public static final String PREFIX_KRAM_NFA			= "kram-nfa";
	public static final String PREFIX_KRAM_NM			= "kram-nm";
	public static final String PREFIX_KRAM_NULK			= "kram-nulk";
	public static final String PREFIX_KRAM_PKJAK		= "kram-pkjak";
	public static final String PREFIX_KRAM_UPM			= "kram-upm";
	public static final String PREFIX_KRAM_VSE			= "kram-vse";
	public static final String PREFIX_KRAM_VSUP			= "kram-vsup";
	public static final String PREFIX_KRAM_VUGTK		= "kram-vugtk";
	public static final String PREFIX_KRAM_ZCM			= "kram-zcm";
	public static final String PREFIX_KRAM_ZMP			= "kram-zmp";
	public static final String PREFIX_KRAM_KFBZ			= "kram-kfbz";
	public static final String PREFIX_KRAM_KKKV			= "kram-kkkv";
	public static final String PREFIX_KRAM_KKPC			= "kram-kkpc";
	public static final String PREFIX_KRAM_KKVY			= "kram-kkvy";
	public static final String PREFIX_KRAM_KNAV			= "kram-knav";
	public static final String PREFIX_KRAM_KVKL			= "kram-kvkl";
	public static final String PREFIX_KRAM_MZK			= "kram-mzk";
	public static final String PREFIX_KRAM_NKP			= "kram-nkp";
	public static final String PREFIX_KRAM_NLK			= "kram-nlk";
	public static final String PREFIX_KRAM_NTK			= "kram-ntk";
	public static final String PREFIX_KRAM_MKP			= "kram-mkp";
	public static final String PREFIX_KRAM_SVKHK		= "kram-svkhk";
	public static final String PREFIX_KRAM_SVKKL		= "kram-svkkl";
	public static final String PREFIX_KRAM_SVKPK		= "kram-svkpk";
	public static final String PREFIX_KRAM_SVKOS		= "kram-svkos";
	public static final String PREFIX_KRAM_SVKUL		= "kram-svkul";
	public static final String PREFIX_KRAM_TRE			= "kram-tre";
	public static final String PREFIX_KRAM_UZEI			= "kram-uzei";
	public static final String PREFIX_KRAM_VKOL			= "kram-vkol";
	public static final String PREFIX_KVKL				= "kvkl";
	public static final String PREFIX_LIBRARY			= "library";
	public static final String PREFIX_MANUSCRIPTORIUM	= "manuscript";
	public static final String PREFIX_MENDELU	        = "mendelu";
	public static final String PREFIX_MESH				= "mesh";
	public static final String PREFIX_MKBREC			= "mkbrec";
	public static final String PREFIX_MKCK				= "mkck";
	public static final String PREFIX_MKFM				= "mkfm";
	public static final String PREFIX_MKHK				= "mkhk";
	public static final String PREFIX_MKHNM				= "mkhnm";
	public static final String PREFIX_MKHOD				= "mkhod";
	public static final String PREFIX_MKKL				= "mkkl";
	public static final String PREFIX_MKMIL				= "mkmil";
	public static final String PREFIX_MKML				= "mkml";
	public static final String PREFIX_MKOR				= "mkor";
	public static final String PREFIX_MKP				= "mkp";
	public static final String PREFIX_MKP_EBOOKS		= "mkpe";
	public static final String PREFIX_MKPEL				= "mkpel";
	public static final String PREFIX_MKPISEK			= "mkpisek";
	public static final String PREFIX_MKSTER			= "mkster";
	public static final String PREFIX_MKTRI				= "mktri";
	public static final String PREFIX_MKUO				= "mkuo";
	public static final String PREFIX_MKZN				= "mkzn";
	public static final String PREFIX_MUNIPRESS			= "munipress";
	public static final String PREFIX_MZK				= "mzk";
	public static final String PREFIX_MZKNORMS			= "unmz";
	public static final String PREFIX_NKP				= "nkp";
	public static final String PREFIX_NLK				= "nlk";
	public static final String PREFIX_NTK				= "ntk";
	public static final String PREFIX_OPENLIB			= "openlib";
	public static final String PREFIX_OSOBNOSTI			= "osobnosti";
	public static final String PREFIX_PKJAK				= "pkjak";
	public static final String PREFIX_RKKA				= "rkka";
	public static final String PREFIX_SFXJIBCBVK		= "sfxjibcbvk";
	public static final String PREFIX_SFXJIBFREE		= "sfxjibfree";
	public static final String PREFIX_SFXJIBIREL		= "sfxjibirel";
	public static final String PREFIX_SFXJIBKFBZ		= "sfxjibkfbz";
	public static final String PREFIX_SFXJIBKIV			= "sfxjibkiv";
	public static final String PREFIX_SFXJIBKKKV		= "sfxjibkkkv";
	public static final String PREFIX_SFXJIBKKVY		= "sfxjibkkvy";
	public static final String PREFIX_SFXJIBKNAV		= "sfxjibknav";
	public static final String PREFIX_SFXJIBKVKL		= "sfxjibkvkl";
	public static final String PREFIX_SFXJIBMKHK		= "sfxjibmkhk";
	public static final String PREFIX_SFXJIBMKP			= "sfxjibmkp";
	public static final String PREFIX_SFXJIBMUNI		= "sfxjibmuni";
	public static final String PREFIX_SFXJIBMUS			= "sfxjibmus";
	public static final String PREFIX_SFXJIBMZK			= "sfxjibmzk";
	public static final String PREFIX_SFXJIBNKP			= "sfxjibnkp";
	public static final String PREFIX_SFXJIBNLK_PERIODICALS	= "sfxnlkper";
	public static final String PREFIX_SFXJIBNLK			= "sfxjibnlk";
	public static final String PREFIX_SFXJIBSVKHK		= "sfxjibsvkhk";
	public static final String PREFIX_SFXJIBSVKOS		= "sfxjibsvkos";
	public static final String PREFIX_SFXJIBSVKPK		= "sfxjibsvkpk";
	public static final String PREFIX_SFXJIBSVKUL		= "sfxjibsvkul";
	public static final String PREFIX_SFXJIBTECH		= "sfxjibtech";
	public static final String PREFIX_SFXJIBUZEI		= "sfxjibuzei";
	public static final String PREFIX_SFXJIBVKOL		= "sfxjibvkol";
	public static final String PREFIX_SFXKNAV			= "sfxknav";
	public static final String PREFIX_SFXTECHLIBNTK		= "sfxjibntk";
	public static final String PREFIX_SFXTECHLIBUOCHB	= "sfxjibuochb";
	public static final String PREFIX_SFXTECHLIBVSCHT	= "sfxjibvscht";
	public static final String PREFIX_SVKKL				= "svkkl";
	public static final String PREFIX_SVKUL				= "svkul";
	public static final String PREFIX_TDKIV				= "tdkiv";
	public static final String PREFIX_TRE				= "tre";
	public static final String PREFIX_UPOL				= "upol";
	public static final String PREFIX_UPV				= "upv";
	public static final String PREFIX_UZEI				= "uzei";
	public static final String PREFIX_VFU				= "vfu";
	public static final String PREFIX_VKOL				= "vkol";
	public static final String PREFIX_ZAKONY			= "zakony";

	public static final String LIBRARY_NAME_KKL			= "KKL";
	public static final String LIBRARY_NAME_NKP			= "NKP";
	public static final String LIBRARY_NAME_SLK			= "SLK";
	public static final String LIBRARY_NAME_STT			= "STT";

	public static final long IMPORT_CONF_MZK			= 300L;
	public static final long IMPORT_CONF_NKP			= 304L;
	public static final long IMPORT_CONF_ID_CASLIN 		= 316L;
	public static final long IMPORT_CONF_ID_ANL 		= 319L;
	public static final long IMPORT_CONF_ID_UNMZ 		= 320L;
	public static final long IMPORT_CONF_ID_SLK			= 321L;
	public static final long IMPORT_CONF_ID_KKL			= 325L;
	public static final long IMPORT_CONF_ID_STT			= 326L;
	public static final long IMPORT_CONF_ID_ZAKONY		= 344L;
	public static final long IMPORT_CONF_ID_UPV			= 347L;
	public static final Long IMPORT_CONF_ID_AUTHORITY	= 400L;
	public static final long IMPORT_CONF_ID_ANTIKVARIATY= 500L;
	public static final long IMPORT_CONF_ID_KRAM_MZK	= 99001L;

	public static final String DOCUMENT_AVAILABILITY_ONLINE     = "online";
	public static final String DOCUMENT_AVAILABILITY_PROTECTED  = "protected";
	public static final String DOCUMENT_AVAILABILITY_UNKNOWN    = "unknown";
	public static final String DOCUMENT_AVAILABILITY_DNNT       = "dnnt";
	public static final String DOCUMENT_AVAILABILITY_ABSENT     = "absent";
	public static final String DOCUMENT_AVAILABILITY_PRESENT    = "present";
	public static final String DOCUMENT_AVAILABILITY_FREESTACK  = "freestack";

	public static final String JOB_PARAM_RECORD_ID		= "recordId";

	public static final String KRAM_EVERSION_COMMENT = "Digitalizovaný dokument";
}	
