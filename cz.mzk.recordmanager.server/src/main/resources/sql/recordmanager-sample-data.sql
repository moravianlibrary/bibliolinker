INSERT INTO library (id, name, url, catalog_url, city) VALUES (100, 'MZK', 'www.mzk.cz', 'vufind.mzk.cz', 'Brno');
INSERT INTO contact_person (id, library_id, name, email, phone) VALUES (200, 100, 'Jan Novak', 'jan.novak@neexistuje.cz', '728 123 456');

INSERT INTO library (id, name, url, catalog_url, city) VALUES (101, 'NLK', 'medvik.cz', 'medivk.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (102, 'MKP', 'www.mlp.cz', 'search.mlp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (103, 'KJM', 'kjm.cz', 'http://katalog.kjm.cz:8080/Carmen/', 'Brno');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (104, 'NKP', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (105, 'VPK', 'vpk.cz', 'vpk.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (106, 'TRE', 'katalogknih.cz', 'vufind.katalogknih.c', 'Česká Třebová');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (107, 'NTK', 'techlib.cz', 'aleph.techlib.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (108, 'KVKL', 'http://www.kvkli.cz/', 'pac.kvkli.cz/arl-li/', 'Liberec');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (109, 'VPK', 'vpk.cz', 'vpk.cz', 'Liberec');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (110, 'ANTIKVARIATY', 'muj-antikvariat.cz', 'muj-antikvariat.cz', null);
INSERT INTO library (id, name, url, catalog_url, city) VALUES (111, 'VKTA', 'knihovnatabor.cz', 'vkta.cz/Clavius', 'Tábor');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (112, 'KKVY', 'kkvysociny.cz', 'kkvysociny.cz/clavius', 'Havlíčkův Brod');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (113, 'SVKHK', 'svkhk.cz', 'aleph2.svkhk.cz', 'Hradec Králové');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (114, 'SVKUL', 'svkul.cz', 'katalog.svkul.cz', 'Ústí nad Labem');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (115, 'VKOL', 'vkol.cz', 'www.vkol.cz/cs/katalog/', 'Olomouc');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (116, 'CASLIN', 'caslin.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (117, 'NKP2', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (119, 'ANL', 'nkp.cz', 'aleph.nkp.cz', NULL);
INSERT INTO library (id, name, url, catalog_url, city) VALUES (120, 'MZKNORMS', 'mzk.cz', 'aleph.mzk.cz', NULL);
INSERT INTO library (id, name, url, catalog_url, city) VALUES (121, 'SLK', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (122, 'KPSYS', '', '', 'KPSYS');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (123, 'CNB', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (125, 'KKL', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (126, 'STT', 'nkp.cz', 'aleph.nkp.cz', 'Praha');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (127, 'OPENLIB', 'openlibrary.org', '', NULL);
INSERT INTO library (id, name, url, catalog_url, city) VALUES (128, 'CBVK', 'cbvk.cz', 'katalog.cbvk.cz', 'České Budějovice');
INSERT INTO library (id, name, url, catalog_url, city) VALUES (129, 'VKTATEST', 'knihovnatabor.cz', 'vkta.cz/Clavius', 'Tábor');

INSERT INTO format(format, description) VALUES('marc21-xml', 'MARC21 XML');
INSERT INTO format(format, description) VALUES('xml-marc', 'MARC21 XML');
INSERT INTO format(format, description) VALUES('marccpk', 'MARC21 XML');
INSERT INTO format(format, description) VALUES('oai_marcxml_cpk', 'MARC21 XML');
INSERT INTO format(format, description) VALUES('dublinCore', 'Dublin Core');


INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (300,100,200,'mzk',13,true,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (301,101,200,'nlk',14,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (302,102,200,'mkp',8,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (303,103,200,'kjm',0,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (304,104,200,'nkp',14,true,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (305,105,200,'vpk',14,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (306,106,200,'tre',11,false,true,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (307,107,200,'ntk',14,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (308,108,200,'kvkl',14,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (309,109,200,'vpk',14,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (311,111,200,'vkta',10,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (312,112,200,'kkvy',12,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (313,113,200,'svkhk',14,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (314,114,200,'svkul',14,false,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (315,115,200,'vkol',14,true,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (316,116,200,'caslin',11,false,false,true,false,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (317,117,200,'nkp2',14,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (319,119,200,'anl',14,false,false,false,false,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (320,120,200,'unmz',10,false,false,true,false,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (321,121,200,'slk',10,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (322,122,200,'kpsys',12,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (323,123,200,'cnb',11,false,false,false,false,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (324,100,200,'mzk03',13,true,false,false,true,'D');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (325,125,200,'kkl',11,false,false,false,false,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (326,126,200,'stt',11,false,false,false,false,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (327,127,200,'openlib',8,false,false,true,false,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (328,128,200,'cbvk',14,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (329,129,200,'vktatest',10,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (400,104,200,null,null,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (99001,100,200,'kram-mzk',null,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (99002,107,200,'kram-ntk',null,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (99003,100,200,'kram-knav',null,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (99004,104,200,'kram-nkp',null,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (500,110,200,'antik',null,false,false,false,false,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (1300,100,200,'sfxjibmzk',8,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (1301,101,200,'sfxjibnlk',8,false,false,false,true,'U');
INSERT INTO import_conf (id,library_id,contact_person_id,id_prefix,base_weight,cluster_id_enabled,filtering_enabled,interception_enabled,is_library,harvest_frequency) VALUES (1302,101,200,'sfxnlkper',8,false,false,false,true,'U');

INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (300,'http://aleph.mzk.cz/OAI','MZK01-CPK','marc21','SECOND');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (301,'http://oai.medvik.cz/medvik2cpk/oai',null,'xml-marc','DAY');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (302,'http://web2.mlp.cz/cgi/oaie','complete','marc21',null);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (303,'http://katalog.kjm.cz/l.dll',null,'marc21','DAY');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (304,'http://aleph.nkp.cz/OAI','NKC','marc21','SECOND');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (305,'http://sc.vpk.cz/cgi-bin/oai2',null,'marc21',null);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (306,'http://opac.moderniknihovna.cz/cgi-bin/koha/oai.pl','CPK','marccpk',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (307,'http://aleph.techlib.cz/OAI','CPK','marc21','SECOND');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (308,'http://93.99.138.143/i2/i2.ws.oai.cls','CPK1','oai_marcxml_cpk',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (309,'http://sc.vpk.cz/cgi-bin/oai2',null,'marc21','SECOND');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (311,'http://www.vkta.cz/Clavius/l.dll','CPK','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (312,'http://www.kkvysociny.cz/clavius/l.dll','CPK','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (313,'http://aleph.svkhk.cz/OAI','HKAOAI','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (314,'http://katalog.svkul.cz/l.dll','CPK','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (315,'http://aleph.vkol.cz/OAI','VKOLOAI','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (316,'http://aleph.nkp.cz/OAI','SKC','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (317,'http://aleph.nkp.cz/OAI','NKC-CPK','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (319,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (320,'http://aleph.mzk.cz/OAI','MZK04','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (321,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (322,'http://portaro.eu/pracovni/api/oai','0','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (323,'http://aleph.nkp.cz/OAI','CNB','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (324,'http://aleph.mzk.cz/OAI','MZK03-CPK','marc21','SECOND');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (325,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (326,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (327,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (328,'http://katalog.cbvk.cz/i2/i2.ws.oai.cls','CPK1','oai_marcxml_cpk',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (329,'http://www.clavius.sk/carmentest/l.dll','CPK','marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (400,'http://aleph.nkp.cz/OAI','AUT','marc21',null);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (1300,NULL,NULL,'marc21',NULL);
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (1301,NULL,NULL,'marc21',NULL); 
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (1302,NULL,NULL,'marc21',NULL);

INSERT INTO kramerius_conf (import_conf_id,url,model,query_rows,metadata_stream) VALUES (99001,'http://kramerius.mzk.cz/search/api/v5.0','monograph',20,'DC');
INSERT INTO kramerius_conf (import_conf_id,url,model,query_rows,metadata_stream) VALUES (99002,'http://k4.techlib.cz/search/api/v5.0','monograph',20,'DC');
INSERT INTO kramerius_conf (import_conf_id,url,model,query_rows,metadata_stream) VALUES (99003,'http://kramerius.lib.cas.cz/search/api/v5.0','monograph',20,'DC');
INSERT INTO kramerius_conf (import_conf_id,url,model,query_rows,metadata_stream) VALUES (99004,'http://kramerius4.nkp.cz/search/api/v5.0','monograph',20,'DC');

INSERT INTO download_import_conf (import_conf_id,url) VALUES (500,'http://muj-antikvariat.cz/oai-all.xml');

INSERT INTO harvested_record_format(id, name) VALUES (1, 'BOOKS'); 
INSERT INTO harvested_record_format(id, name) VALUES (2, 'PERIODICALS');
INSERT INTO harvested_record_format(id, name) VALUES (3, 'ARTICLES');
INSERT INTO harvested_record_format(id, name) VALUES (4, 'MAPS');
INSERT INTO harvested_record_format(id, name) VALUES (5, 'MUSICAL_SCORES'); 
INSERT INTO harvested_record_format(id, name) VALUES (6, 'VISUAL_DOCUMENTS'); 
INSERT INTO harvested_record_format(id, name) VALUES (7, 'MANUSCRIPTS');
INSERT INTO harvested_record_format(id, name) VALUES (8, 'OTHER_MICROFORMS');
INSERT INTO harvested_record_format(id, name) VALUES (9, 'LARGE_PRINTS'); 
INSERT INTO harvested_record_format(id, name) VALUES (10, 'OTHER_BRAILLE');
INSERT INTO harvested_record_format(id, name) VALUES (11, 'ELECTRONIC_SOURCE'); 
INSERT INTO harvested_record_format(id, name) VALUES (12, 'AUDIO_DOCUMENTS'); 
INSERT INTO harvested_record_format(id, name) VALUES (13, 'AUDIO_CD');
INSERT INTO harvested_record_format(id, name) VALUES (14, 'AUDIO_DVD'); 
INSERT INTO harvested_record_format(id, name) VALUES (15, 'AUDIO_LP'); 
INSERT INTO harvested_record_format(id, name) VALUES (16, 'AUDIO_CASSETTE'); 
INSERT INTO harvested_record_format(id, name) VALUES (17, 'AUDIO_OTHER'); 
INSERT INTO harvested_record_format(id, name) VALUES (18, 'VIDEO_DOCUMENTS');
INSERT INTO harvested_record_format(id, name) VALUES (19, 'VIDEO_BLURAY');
INSERT INTO harvested_record_format(id, name) VALUES (20, 'VIDEO_VHS');
INSERT INTO harvested_record_format(id, name) VALUES (21, 'VIDEO_DVD');
INSERT INTO harvested_record_format(id, name) VALUES (22, 'VIDEO_CD');
INSERT INTO harvested_record_format(id, name) VALUES (23, 'VIDEO_OTHER');
INSERT INTO harvested_record_format(id, name) VALUES (24, 'OTHER_KIT');
INSERT INTO harvested_record_format(id, name) VALUES (25, 'OTHER_OBJECT');
INSERT INTO harvested_record_format(id, name) VALUES (26, 'OTHER_MIX_DOCUMENT');
INSERT INTO harvested_record_format(id, name) VALUES (27, 'NORMS');
INSERT INTO harvested_record_format(id, name) VALUES (100, 'OTHER_UNSPECIFIED');


INSERT INTO sigla (import_conf_id, sigla) VALUES (300, 'BOA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (302, 'ABA008');
INSERT INTO sigla (import_conf_id, sigla) VALUES (302, 'ABG001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (303, 'BOG001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA003');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA004');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA018');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA019');
INSERT INTO sigla (import_conf_id, sigla) VALUES (304, 'ABA025');
INSERT INTO sigla (import_conf_id, sigla) VALUES (306, 'UOG505');
INSERT INTO sigla (import_conf_id, sigla) VALUES (307, 'ABA013');
INSERT INTO sigla (import_conf_id, sigla) VALUES (307, 'ABA031');
INSERT INTO sigla (import_conf_id, sigla) VALUES (308, 'LIA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (311, 'TAG001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (312, 'HBG001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (313, 'HKA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (314, 'ULG001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (315, 'OLA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (321, 'ABA013');
INSERT INTO sigla (import_conf_id, sigla) VALUES (324, 'BOA001');
INSERT INTO sigla (import_conf_id, sigla) VALUES (325, 'ABA003');
INSERT INTO sigla (import_conf_id, sigla) VALUES (328, 'CBA001');
