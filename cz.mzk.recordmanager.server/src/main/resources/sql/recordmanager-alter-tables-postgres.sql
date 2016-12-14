-- 19. 2. 2015 - xrosecky
ALTER TABLE dedup_record ADD updated TIMESTAMP;

DROP VIEW dedup_record_last_update;

CREATE VIEW dedup_record_last_update AS
SELECT
  dr.id dedup_record_id,
  MAX(CASE WHEN dr.updated > hr.harvested THEN dr.updated ELSE hr.harvested END) last_update
FROM
  dedup_record dr JOIN 
  record_link rl ON rl.dedup_record_id = dr.id JOIN
  harvested_record hr ON hr.id = rl.harvested_record_id
GROUP BY
  dr.id
;

CREATE VIEW dedup_record_orphaned AS
SELECT
  dr.id dedup_record_id,
  dr.updated AS orphaned
FROM
  dedup_record dr
WHERE
  NOT EXISTS(SELECT 1 FROM record_link rl WHERE rl.dedup_record_id = dr.id)
;

-- 2. 3. 2015 - xrosecky
ALTER TABLE harvested_record ADD publication_year  DECIMAL(4),
                             ADD physical_format   VARCHAR(255)
;

ALTER TABLE dedup_record ADD publication_year  DECIMAL(4),
                         ADD physical_format   VARCHAR(255)
;

-- 26. 3. 2015 - mertam
ALTER TABLE harvested_record ADD unique_id VARCHAR(100) UNIQUE;

-- 3. 4. 2015 - tomascejpek
ALTER TABLE harvested_record DROP unique_id;
ALTER TABLE harvested_record RENAME oai_record_id TO record_id;

-- 7. 4. 2015 - xrosecky
ALTER TABLE oai_harvest_conf ADD COLUMN id_prefix VARCHAR(10);

-- 10. 4. 2015 - xrosecky
ALTER TABLE harvested_record ADD COLUMN dedup_record_id DECIMAL(10);
UPDATE harvested_record hr 
  SET dedup_record_id = (
    SELECT
      dedup_record_id
    FROM
      record_link rl
    WHERE
      rl.harvested_record_id = hr.id
    LIMIT 1
  )
;
DROP VIEW dedup_record_orphaned;
DROP VIEW dedup_record_last_update;
DROP TABLE record_link;
DROP VIEW harvested_record_view;
ALTER TABLE harvested_record DROP COLUMN id;
DROP INDEX harvested_record_sec_key;
ALTER TABLE harvested_record ADD CONSTRAINT harvested_record_pk PRIMARY KEY (oai_harvest_conf_id, oai_record_id);
ALTER TABLE harvested_record RENAME oai_record_id TO record_id;
CREATE VIEW dedup_record_last_update AS
SELECT
  dr.id dedup_record_id,
  MAX(CASE WHEN dr.updated > hr.harvested THEN dr.updated ELSE hr.harvested END) last_update
FROM
  dedup_record dr JOIN 
  harvested_record hr ON hr.dedup_record_id = dr.id 
GROUP BY
  dr.id
;
CREATE VIEW dedup_record_orphaned AS
SELECT
  dr.id dedup_record_id,
  dr.updated AS orphaned
FROM
  dedup_record dr
WHERE
  NOT EXISTS(SELECT 1 FROM harvested_record hr WHERE hr.dedup_record_id = dr.id)
;
CREATE VIEW harvested_record_view AS
SELECT
  oai_harvest_conf_id,
  record_id,
  deleted,
  format,
  isbn,
  title,
  convert_from(raw_record, 'UTF8') AS raw_record
FROM
  harvested_record
;

-- 4. 5. 2015 - xrosecky
ALTER TABLE harvested_record ADD COLUMN id DECIMAL(10);
CREATE SEQUENCE harvested_record_id_seq START 1;
UPDATE harvested_record SET id = nextval('harvested_record_id_seq');
ALTER TABLE harvested_record DROP CONSTRAINT harvested_record_pk;
ALTER TABLE harvested_record ADD CONSTRAINT harvested_record_pk PRIMARY KEY (id);

-- 6. 5. 2015 - mertam
ALTER TABLE harvested_record DROP COLUMN title;
ALTER TABLE harvested_record DROP COLUMN isbn;
ALTER TABLE harvested_record DROP COLUMN physical_format; 

-- 7. 5. 2015 - tomascejpek
ALTER TABLE harvested_record ADD COLUMN weight DECIMAL(10);
ALTER TABLE oai_harvest_conf ADD COLUMN base_weight DECIMAL(10);

-- 12. 5. 2015 - tomascejpek
ALTER TABLE harvested_record ADD COLUMN cluster_id VARCHAR(20);
ALTER TABLE oai_harvest_conf ADD COLUMN cluster_id_enabled BOOLEAN DEFAULT(FALSE);

-- 4. 6. 2015 mertam
ALTER TABLE harvested_record ADD COLUMN pages DECIMAL(10);

-- 12. 6. 2015 mertam 
ALTER TABLE oai_harvest_conf ADD COLUMN id_prefix VARCHAR(10);

-- 24. 6. 2015 - xrosecky
CREATE TABLE import_conf (
  id                   DECIMAL(10) PRIMARY KEY,
  library_id           DECIMAL(10),
  contact_person_id    DECIMAL(10),
  id_prefix            VARCHAR(10),
  base_weight          DECIMAL(10),
  cluster_id_enabled   BOOLEAN DEFAULT FALSE,
  CONSTRAINT import_conf_library_id_fk        FOREIGN KEY (library_id)        REFERENCES library(id),
  CONSTRAINT import_conf_contact_person_id_fk FOREIGN KEY (contact_person_id) REFERENCES contact_person(id)
);

CREATE TABLE kramerius_conf (
  import_conf_id       DECIMAL(10)  PRIMARY KEY,
  url                  VARCHAR(128),
  model                VARCHAR(128),
  query_rows           DECIMAL(10),
  metadata_stream      VARCHAR(128),
  CONSTRAINT kramerius_conf_import_conf_fk FOREIGN KEY (import_conf_id) REFERENCES import_conf(id)
);

INSERT INTO import_conf(id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled)
  SELECT id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled FROM oai_harvest_conf;

ALTER TABLE oai_harvest_conf ADD COLUMN import_conf_id DECIMAL(10);
UPDATE oai_harvest_conf SET import_conf_id = id;
ALTER TABLE oai_harvest_conf DROP CONSTRAINT oai_harvest_conf_pkey;

ALTER TABLE harvested_record DROP CONSTRAINT harvested_record_oai_harvest_conf_id_fkey;
ALTER TABLE harvested_record RENAME oai_harvest_conf_id TO import_conf_id;
ALTER TABLE harvested_record ADD CONSTRAINT harvested_record_import_conf_id_fk FOREIGN KEY (import_conf_id) REFERENCES import_conf(id);

ALTER TABLE authority_record DROP CONSTRAINT authority_record_oai_harvest_conf_id_fkey;
ALTER TABLE authority_record RENAME oai_harvest_conf_id TO import_conf_id;
ALTER TABLE authority_record ADD CONSTRAINT authority_record_import_conf_id_fk FOREIGN KEY (import_conf_id) REFERENCES import_conf(id);

ALTER TABLE oai_harvest_conf DROP COLUMN library_id;
ALTER TABLE oai_harvest_conf DROP COLUMN id;
ALTER TABLE oai_harvest_conf DROP COLUMN contact_person_id;
ALTER TABLE oai_harvest_conf DROP COLUMN base_weight;
ALTER TABLE oai_harvest_conf DROP COLUMN cluster_id_enabled;

-- 7. 7. 2015 - xrosecky
CREATE TABLE download_import_conf (
  import_conf_id       DECIMAL(10)  PRIMARY KEY,
  url                  VARCHAR(128),
  CONSTRAINT download_conf_import_conf_fk FOREIGN KEY (import_conf_id) REFERENCES import_conf(id)
);

CREATE TABLE antikvariaty (
  id                   DECIMAL(10) PRIMARY KEY,
  updated              TIMESTAMP,
  url                  VARCHAR(500),
  title                VARCHAR(255),
  pub_year             DECIMAL(5)
);

CREATE TABLE antikvariaty_catids (
  id_from_catalogue   VARCHAR(100),
  antikvariaty_id     DECIMAL(10),
  CONSTRAINT antikvariaty_catids_pk PRIMARY KEY (id_from_catalogue, antikvariaty_id),
  CONSTRAINT antikvariaty_catids_fk FOREIGN KEY (antikvariaty_id) REFERENCES antikvariaty(id)
);

-- 17. 7. 2015 - xrosecky
CREATE INDEX harvested_record_dedup_record_id_updated_idx ON harvested_record(dedup_record_id, updated);
DROP INDEX harvested_record_dedup_record_idx;

CREATE OR REPLACE VIEW dedup_record_last_update AS
SELECT
  dr.id dedup_record_id,
  MAX(CASE WHEN dr.updated > hr.updated THEN dr.updated ELSE hr.updated END) last_update
FROM
  dedup_record dr JOIN 
  harvested_record hr ON hr.dedup_record_id = dr.id 
GROUP BY
  dr.id
;

-- 31. 7. 2015 - mertam
CREATE TABLE oclc (
  id                   DECIMAL(10) PRIMARY KEY,
  harvested_record_id  DECIMAL(10),
  oclc                 VARCHAR(20),
  CONSTRAINT oclc_fk  FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id)
);

CREATE TABLE language (
  id                   DECIMAL(10) PRIMARY KEY,
  harvested_record_id  DECIMAL(10),
  lang                 VARCHAR(5),
  CONSTRAINT language_fk  FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id)
);


ALTER TABLE import_conf ADD COLUMN filtering_enabled boolean;

--3. 8. 2015 - mertam; set default filtering values
UPDATE import_conf SET filtering_enabled = false;

CREATE INDEX oclc_harvested_record_idx ON oclc(harvested_record_id); 
CREATE INDEX language_harvested_record_idx ON language(harvested_record_id); 

-- 12. 8. 2015 - mertam; changes in auth_record table
ALTER TABLE authority_record RENAME COLUMN oai_harvest_conf_id TO import_conf_id;
ALTER TABLE authority_record RENAME COLUMN authority_type TO authority_code;
ALTER TABLE authority_record ADD CONSTRAINT authority_code_unique UNIQUE(authority_code);
CREATE INDEX authority_code_idx ON authority_record(authority_code);

-- 14. 8. 2015 mertam; 
ALTER TABLE import_conf ADD COLUMN interception_enabled BOOLEAN DEFAULT FALSE;

-- 20. 8. 2015 tomascejpek
ALTER TABLE import_conf ADD COLUMN is_library BOOLEAN DEFAULT FALSE;

-- 2. 9. 2015 xrosecky
ALTER TABLE language DROP CONSTRAINT language_pkey;
ALTER TABLE language DROP COLUMN id;
DELETE FROM language WHERE harvested_record_id IS NULL;
ALTER TABLE language ADD CONSTRAINT language_pk PRIMARY KEY (harvested_record_id, lang);

-- 6. 9. 2015 mertam 
UPDATE import_conf set id_prefix = 'unmz' where id = 320;
update oai_harvest_conf set set_spec = 'CPK' where import_conf_id = 307;

--14.9. 2015 mjtecka
CREATE TABLE fulltext_monography
(
  id numeric(10,0) NOT NULL,
  harvested_record_id numeric(10,0),
  uuid_page character varying(42),
  is_private boolean, 
  order_in_monography numeric(10,0),
  page character varying(20),
  fulltext bytea,
  CONSTRAINT fulltext_monography_pkey PRIMARY KEY (id),
  CONSTRAINT fulltext_monography_harvested_record_id_fk FOREIGN KEY (harvested_record_id)
); 

-- 16. 9. 2015 mertam
CREATE TABLE skat_keys (
  skat_record_id      DECIMAL(10),
  sigla               VARCHAR(20),
  local_record_id     VARCHAR(128),
  manually_merged     BOOLEAN DEFAULT FALSE,
  CONSTRAINT skat_keys_pk PRIMARY KEY(skat_record_id,sigla,local_record_id)
);

CREATE TABLE sigla (
  import_conf_id       DECIMAL(10),
  sigla                VARCHAR(20),
  CONSTRAINT sigla_pk PRIMARY KEY(import_conf_id,sigla)
);

-- 17.9.2015 mertam
alter table sigla add CONSTRAINT sigla_import_conf_fk FOREIGN KEY (import_conf_id) REFERENCES import_conf(id)

--18.9.2015 mertam 
ALTER TABLE harvested_record ADD COLUMN raw_001_id VARCHAR(128); 

-- 21.9.2015 mertam 
ALTER TABLE isbn DROP CONSTRAINT isbn_harvested_record_id_fkey;
ALTER TABLE isbn ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE issn DROP CONSTRAINT issn_harvested_record_id_fkey;
ALTER TABLE issn ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE cnb DROP CONSTRAINT cnb_harvested_record_id_fkey;
ALTER TABLE cnb ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE title DROP CONSTRAINT title_harvested_record_id_fkey;
ALTER TABLE title ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE oclc DROP CONSTRAINT oclc_fk;
ALTER TABLE oclc ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE language DROP CONSTRAINT language_fk;
ALTER TABLE language ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;
ALTER TABLE harvested_record_format_link DROP CONSTRAINT format_link_hr_id_fk;
ALTER TABLE harvested_record_format_link ADD FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE;

-- 21.10.2015 tomascejpek
ALTER TABLE format ALTER COLUMN format TYPE VARCHAR(15);

-- 26.10.2015 tomascejpek
UPDATE import_conf SET id_prefix = 'openlib' WHERE id = 327;
UPDATE import_conf SET interception_enabled = true WHERE id = 327;
UPDATE library SET name = 'OPENLIB' WHERE id = 127;

-- 5. 11. 2015 mjtecka
ALTER TABLE kramerius_conf ADD COLUMN auth_token varchar(128);

-- 5. 11. 2015 mjtecka
ALTER TABLE kramerius_conf ADD COLUMN download_private_fulltexts boolean DEFAULT FALSE;

-- 9. 11. 2015 xrosecky
ALTER TABLE kramerius_conf ADD COLUMN fulltext_harvest_type VARCHAR(128) DEFAULT 'fedora';

-- 13.11.2015 mjtecka
ALTER TABLE fulltext_monography ALTER COLUMN uuid_page TYPE VARCHAR(50);

-- 23.11.2015 tomascejpek
CREATE TABLE cosmotron_996 (
  id                   DECIMAL(10) PRIMARY KEY,
  harvested_record_id  DECIMAL(10),
  import_conf_id       DECIMAL(10),
  record_id            VARCHAR(128),
  harvested            TIMESTAMP,
  updated              TIMESTAMP,
  deleted              TIMESTAMP,
  raw_record           BYTEA,
  FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE
);

-- 23. 11. 2015 xrosecky
ALTER TABLE import_conf ADD COLUMN harvest_frequency CHAR(1) DEFAULT 'U';

-- 1. 12. 2015 tomascejpek
UPDATE oai_harvest_conf SET url='http://opac.moderniknihovna.cz/cgi-bin/koha/oai.pl' WHERE import_conf_id=306;

-- 3. 12. 2015 tomascejpek
UPDATE oai_harvest_conf SET set_spec='CPK1' WHERE import_conf_id=328;

-- 4.12. 2015 mjtecka
ALTER TABLE kramerius_conf ADD COLUMN url_solr character varying(128);

-- 14. 12. 2015 tomascejpek
UPDATE import_conf SET id_prefix='vktatest' WHERE id=329;

-- 17. 12. 2015 tomascejpek
UPDATE import_conf SET harvest_frequency='D' WHERE id IN (300,301,304,306,307,311,312,313,314,315,316,320,323,324);

-- 17. 12. 2015 mertam
ALTER TABLE harvested_record ADD COLUMN dedup_keys_hash CHAR(40);
ALTER TABLE harvested_record ADD COLUMN next_dedup_flag BOOLEAN DEFAULT TRUE;
ALTER TABLE harvested_record ADD COLUMN oai_timestamp TIMESTAMP;

-- 4. 1. 2016 tomascejpek
UPDATE oai_harvest_conf SET url='http://web2.mlp.cz/cgi/oaie' WHERE import_conf_id=302;
UPDATE oai_harvest_conf SET metadata_prefix='marc21e' WHERE import_conf_id=302;

-- 4. 1. 2016 xrosecky 
CREATE TABLE obalkyknih_toc (
  id                   DECIMAL(10) PRIMARY KEY,
  book_id              DECIMAL(10),
  nbn                  VARCHAR(32),
  oclc                 VARCHAR(32),
  ean                  VARCHAR(32),
  isbn                 VARCHAR(32),
  toc                  VARCHAR(1048576)
);

CREATE INDEX obalkyknih_toc_book_idx ON obalkyknih_toc(book_id);
CREATE INDEX obalkyknih_toc_oclc_idx ON obalkyknih_toc(oclc);
CREATE INDEX obalkyknih_toc_ean_idx ON obalkyknih_toc(ean);
CREATE INDEX obalkyknih_toc_isbn_idx ON obalkyknih_toc(isbn);
CREATE INDEX obalkyknih_toc_nbn_idx ON obalkyknih_toc(nbn);

ALTER TABLE obalkyknih_toc ALTER COLUMN isbn TYPE DECIMAL(13) USING isbn::numeric(13,0);

-- 25. 1. 2016 xrosecky
ALTER TABLE oai_harvest_conf ADD COLUMN extract_id_regex VARCHAR(128);

-- 27. 1. 2016 tomascejpek
UPDATE oai_harvest_conf SET extract_id_regex='[^:]+:(.*)' WHERE import_conf_id in (319,321,325,326);
UPDATE oai_harvest_conf SET extract_id_regex='[^:]+:[^:]+:MZK04-(.*)' WHERE import_conf_id=320;

-- 2. 3. 2016 tomascejpek
UPDATE import_conf SET library_id=130 WHERE id=99003;

-- 3. 3. 2016 tomascejpek
UPDATE oai_harvest_conf SET extract_id_regex='UOG505:(.*)' WHERE import_conf_id=306;

-- 7. 3. 2016 tomascejpek
UPDATE harvested_record SET record_id = substring(record_id FROM 'UOG505:(.*)') where import_conf_id=306 and record_id ~ 'UOG505:';
CREATE TABLE inspiration (
  id					DECIMAL(10) PRIMARY KEY,
  harvested_record_id	DECIMAL(10),
  name					VARCHAR(32),
  FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE
);
CREATE INDEX inspiration_harvested_record_idx ON inspiration(harvested_record_id);
UPDATE import_conf SET id_prefix = 'mzk' WHERE id=324;

-- 16. 3. 2016 mjtecka
ALTER TABLE fulltext_monography ALTER COLUMN page TYPE VARCHAR(50);

ALTER TABLE fulltext_monography RENAME TO fulltext_kramerius;
ALTER TABLE fulltext_kramerius RENAME COLUMN order_in_monography TO order_in_document;

DROP INDEX fulltext_monography_harvested_record_idx;
CREATE INDEX fulltext_kramerius_harvested_record_idx ON fulltext_kramerius(harvested_record_id);

ALTER TABLE kramerius_conf DROP COLUMN model;

-- 18. 3. 2016 xrosecky
UPDATE import_conf SET harvest_frequency = 'D'
WHERE harvest_frequency = 'U' AND id IN (SELECT import_conf_id FROM oai_harvest_conf WHERE url IS NOT NULL AND url NOT LIKE '%i2.ws.oai.cls' /* Cosmotron */);

-- 22. 3. 2016 tomascejpek
ALTER TABLE sigla ADD COLUMN id DECIMAL(10) UNIQUE;

-- 22. 3. 2016 xrosecky
ALTER TABLE oai_harvest_conf ADD COLUMN harvest_job_name VARCHAR(128);

-- 24. 3. 2016 tomascejpek
UPDATE import_conf SET filtering_enabled=true WHERE id=316;

-- 01. 4. 2016 tomascejpek
UPDATE oai_harvest_conf SET url='http://katalog.svkos.cz/OAI' WHERE import_conf_id=335;
UPDATE oai_harvest_conf SET set_spec='MZK-CPK' WHERE import_conf_id=335;

-- 20. 4. 2016 tomascejpek
UPDATE oai_harvest_conf SET url='http://aleph.knihovna-pardubice.cz/OAI' WHERE import_conf_id=333;
UPDATE oai_harvest_conf SET set_spec='PAG_OAI_CPK_MARC21' WHERE import_conf_id=333;

-- 9. 5. 2016 xrosecky
ALTER TABLE import_conf ADD COLUMN mapping_script VARCHAR(256);

-- 18. 5. 2016 tomascejpek
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (1304, 130, 200, 'sfxknav', 8, false, false, false, true, 'U');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (1304,NULL,NULL,'marc21',NULL);

-- 19. 5. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (400, 'AUTHORITY', 'nkp.cz', 'aleph.nkp.cz', NULL);
UPDATE import_conf SET library_id=400 WHERE id=400;
UPDATE import_conf SET is_library=false WHERE id=400;
UPDATE import_conf SET mapping_script='AuthorityMarc.groovy' WHERE id=400;
INSERT INTO harvested_record_format(id, name) VALUES (28, 'PERSON');

-- 24. 5. 2016 tomascejpek
ALTER TABLE title ADD COLUMN similarity_enabled BOOLEAN DEFAULT(FALSE);

-- 10. 6. 2016 tomascejpek
UPDATE import_conf SET library_id=104 WHERE id in (321,325,326);
UPDATE import_conf SET is_library=true WHERE id in (325,326);

-- 14. 6. 2016 tomascejpek
UPDATE import_conf SET filtering_enabled=true WHERE id=400;

-- 22. 6. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (140, 'MKUO', 'knihovna-uo.cz', 'vufind.knihovna-uo.cz/vufind/', 'Ústí nad Orlicí');
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (340, 140, 200, 'mkuo', 8, false, true, false, true, 'D');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity,extract_id_regex) VALUES (340,'http://katalog-usti.knihovna-uo.cz/cgi-bin/koha/oai.pl','CPK','marc21',NULL,'UOG001:(.*)');

-- 22. 6. 2016 xrosecky
ALTER TABLE import_conf ADD COLUMN generate_dedup_keys BOOLEAN DEFAULT(TRUE);

-- 28. 6. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (141, 'OSOBNOSTI', 'osobnostiregionu.cz', 'http://hledani.osobnostiregionu.cz/', NULL);
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency,mapping_script) VALUES (341, 141, 200, 'osobnosti', 0, false, false, false, false, 'U', 'AuthorityMarc.groovy');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (341,NULL,NULL,'marc21',NULL);

-- 30. 6. 2016 tomascejpek
UPDATE oai_harvest_conf SET metadata_prefix='marccpk' WHERE import_conf_id=340;

-- 12. 7. 2016 tomascejpek
UPDATE import_conf SET harvest_frequency='D' WHERE id=334;
UPDATE oai_harvest_conf SET url='http://109.73.209.153/clavius/l.dll', set_spec='NKP' WHERE import_conf_id=334;
UPDATE import_conf SET harvest_frequency='D' WHERE id=338;
UPDATE oai_harvest_conf SET url='http://katalogold.iir.cz:81/l.dll', set_spec='CPK' WHERE import_conf_id=338;

-- 13. 7. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (142, 'BMC', 'nlk.cz', 'www.medvik.cz/bmc/', NULL);
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (342, 142, 200, 'bmc', 8, false, false, false, false, 'D');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (342,'http://oai.medvik.cz/bmc/oai',NULL,'xml-marc',NULL);

-- 19. 7. 2016 tomascejpek
UPDATE oai_harvest_conf SET set_spec='CPK' WHERE import_conf_id=334;

-- 22. 7. 2016 tomascejpek
UPDATE import_conf SET harvest_frequency='U' WHERE id in (329,323);

-- 16. 8. 2016 tomascejpek
UPDATE import_conf SET harvest_frequency='D' WHERE id=336;
UPDATE oai_harvest_conf SET url='http://svk7.svkkl.cz/i2/i2.ws.oai.cls', set_spec='CPK1', metadata_prefix='oai_marcxml_cpk', harvest_job_name='cosmotronHarvestJob' WHERE import_conf_id=336;

-- 16. 8. 2016 tomascejpek
CREATE TABLE ismn (
  id                   DECIMAL(10) PRIMARY KEY,
  harvested_record_id  DECIMAL(10),
  ismn                 DECIMAL(13),
  order_in_record      DECIMAL(4),
  note                 VARCHAR(300),
  FOREIGN KEY (harvested_record_id) REFERENCES harvested_record(id) ON DELETE CASCADE
);
COMMENT ON TABLE ismn IS 'dedup_keys: table contatining ISMNs';
CREATE INDEX ismn_harvested_record_idx ON ismn(harvested_record_id);

-- 18. 8. 2016 tomascejpek
ALTER TABLE download_import_conf ADD COLUMN import_job_name VARCHAR(128);
ALTER TABLE download_import_conf ADD COLUMN format VARCHAR(128);
ALTER TABLE download_import_conf ADD COLUMN extract_id_regex VARCHAR(128);

-- 19. 8. 2016 tomascejpek
INSERT INTO 
  import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency)
SELECT 500, 110, 200, 'antik', null, false, false, false, false, 'U' 
WHERE 
  NOT EXISTS (SELECT id FROM import_conf WHERE id=500)
;
INSERT INTO 
  download_import_conf (import_conf_id,url)
SELECT 500,'http://muj-antikvariat.cz/oai-all.xml'
WHERE 
  NOT EXISTS (SELECT import_conf_id FROM download_import_conf WHERE import_conf_id=500)
;
UPDATE download_import_conf SET import_job_name='antikvariatyImportRecordsJob' WHERE import_conf_id=500;
DELETE FROM oai_harvest_conf WHERE import_conf_id=341;
INSERT INTO download_import_conf (import_conf_id,url,import_job_name,format) VALUES (341,'http://www.osobnostiregionu.cz/export_online.php','downloadAndImportRecordsJob','osobnosti');

-- 24. 8. 2016 tomascejpek
UPDATE harvested_record_format SET name='OTHER_PERSON' WHERE id=28;

-- 24. 8. 2016 tomascejpek
ALTER TABLE kramerius_conf ADD COLUMN harvest_job_name VARCHAR(128);
DROP VIEW IF EXISTS oai_harvest_job_stat CASCADE;
CREATE OR REPLACE VIEW oai_harvest_job_stat AS
SELECT
  bje.job_execution_id,
  (array_agg(ic.id))[1]  import_conf_id,
  l.name library_name,
  ohc.url url,
  ohc.set_spec,
  bje.start_time,
  bje.end_time,
  bje.status,
  from_param.date_val from_param,
  to_param.date_val to_param,
  (SELECT sum(read_count) FROM batch_step_execution bse WHERE bse.job_execution_id = bje.job_execution_id) no_of_records
FROM batch_job_instance bji
  JOIN batch_job_execution bje ON bje.job_instance_id = bji.job_instance_id
  JOIN batch_job_execution_params conf_id_param ON conf_id_param.job_execution_id = bje.job_execution_id AND conf_id_param.key_name = 'configurationId'
  LEFT JOIN batch_job_execution_params to_param ON to_param.job_execution_id = bje.job_execution_id AND to_param.key_name = 'to'
  LEFT JOIN batch_job_execution_params from_param ON from_param.job_execution_id = bje.job_execution_id AND from_param.key_name = 'from'
  LEFT JOIN oai_harvest_conf ohc ON ohc.import_conf_id = conf_id_param.long_val
  LEFT JOIN kramerius_conf kc ON kc.import_conf_id = conf_id_param.long_val
  JOIN import_conf ic ON ic.id = ohc.import_conf_id OR ic.id = kc.import_conf_id
  JOIN library l ON l.id = ic.library_id
WHERE bji.job_name IN ('oaiHarvestJob', 'oaiReharvestJob', 'oaiPartitionedHarvestJob', 'cosmotronHarvestJob', 'krameriusHarvestJob', 'krameriusHarvestNoSortingJob')
GROUP BY bje.job_execution_id,l.name,ohc.url,ohc.set_spec,from_param.date_val,to_param.date_val
;
CREATE OR REPLACE VIEW oai_harvest_summary AS
WITH last_harvest_date AS (
  SELECT
    import_conf_id,
    COALESCE(MAX(CASE WHEN status = 'COMPLETED' THEN to_param END), MAX(CASE WHEN status = 'COMPLETED' THEN end_time END)) last_successful_harvest_date,
    COALESCE(MAX(CASE WHEN status = 'FAILED' THEN to_param END), MAX(CASE WHEN status = 'FAILED' THEN end_time END)) last_failed_harvest_date,
    COALESCE(MIN(end_time), MIN(to_param)) first_harvest_date,
    COUNT(1) no_of_harvests
  FROM oai_harvest_job_stat
  GROUP BY import_conf_id
)
SELECT l.name, ic.id_prefix, ohc.url, ohc.set_spec, lhd.last_successful_harvest_date, lhd.last_failed_harvest_date, lhd.first_harvest_date, lhd.no_of_harvests
FROM last_harvest_date lhd
  JOIN import_conf ic ON ic.id = lhd.import_conf_id
  LEFT JOIN oai_harvest_conf ohc ON ohc.import_conf_id = ic.id
  LEFT JOIN kramerius_conf kc ON kc.import_conf_id = ic.id
  JOIN library l ON l.id = ic.library_id
;
CREATE OR REPLACE VIEW oai_last_failed_harvests AS
SELECT name, url, set_spec, last_failed_harvest_date
FROM oai_harvest_summary
WHERE last_failed_harvest_date > last_successful_harvest_date OR (last_failed_harvest_date IS NOT NULL AND last_successful_harvest_date IS NULL)
;
CREATE OR REPLACE VIEW solr_index_summary AS
SELECT params1.string_val solr_url, COALESCE(MAX(params2.date_val), MAX(bje.end_time)) last_index_time
FROM batch_job_instance bji
  JOIN batch_job_execution bje ON bje.job_instance_id = bji.job_instance_id
  JOIN batch_job_execution_params params1 ON params1.job_execution_id = bje.job_execution_id AND params1.key_name = 'solrUrl'
  LEFT JOIN batch_job_execution_params params2 ON params2.job_execution_id = bje.job_execution_id AND params2.key_name = 'to'
WHERE bji.job_name IN ('indexRecordsToSolrJob', 'indexAllRecordsToSolrJob', 'indexHarvestedRecordsToSolrJob')
  AND bje.status = 'COMPLETED'
GROUP BY params1.string_val
;

-- 25. 8. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (143, 'KFBZ', 'kfbz.cz', 'katalog.kfbz.cz/', 'Zlín');
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (343, 143, 200, 'kfbz', 12, false, false, false, true, 'D');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (343,'http://katalog.kfbz.cz/api/oai','0','marc21',NULL);

-- 5. 9. 2016 tomascejpek
ALTER TABLE inspiration ALTER COLUMN name TYPE VARCHAR(128);

-- 6. 9. 2016 tomascejpek
UPDATE import_conf SET id_prefix='nkp' WHERE id in (321,325,326);
UPDATE oai_harvest_conf SET extract_id_regex='s/[^:]+:(.*)/SLK01-$1/' WHERE import_conf_id=321;
UPDATE oai_harvest_conf SET extract_id_regex='s/[^:]+:(.*)/KKL01-$1/' WHERE import_conf_id=325;
UPDATE oai_harvest_conf SET extract_id_regex='s/[^:]+:(.*)/STT01-$1/' WHERE import_conf_id=326;
UPDATE oai_harvest_conf SET url='http://ipac.kvkli.cz/i2/i2.ws.oai.cls' WHERE import_conf_id=308;

-- 6. 10. 2016 tomascejpek
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (345, 102, 200, 'mkpe', 8, false, false, false, true, 'U');
INSERT INTO oai_harvest_conf (import_conf_id, url, set_spec, metadata_prefix, granularity, extract_id_regex, harvest_job_name) VALUES (345, 'http://web2.mlp.cz/cgi/oai', 'ebook', 'marc21', null, null, null);

-- 7. 10. 2016 tomascejpek
INSERT INTO harvested_record_format(id, name) VALUES (29, 'LEGISLATIVE_GOVERNMENT_ORDERS');
INSERT INTO harvested_record_format(id, name) VALUES (30, 'LEGISLATIVE_REGULATIONS');
INSERT INTO harvested_record_format(id, name) VALUES (31, 'LEGISLATIVE_COMMUNICATION');
INSERT INTO harvested_record_format(id, name) VALUES (32, 'LEGISLATIVE_LAWS');
INSERT INTO harvested_record_format(id, name) VALUES (33, 'LEGISLATIVE_LAWS_TEXT');
INSERT INTO harvested_record_format(id, name) VALUES (34, 'LEGISLATIVE_FINDING');
INSERT INTO harvested_record_format(id, name) VALUES (35, 'LEGISLATIVE_CONSTITUTIONAL_LAWS');
INSERT INTO harvested_record_format(id, name) VALUES (36, 'LEGISLATIVE_DECISIONS');

-- 10. 10. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (144, 'ZAKONY', 'zakonyprolidi.cz', '', NULL);
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (344, 144, 200, 'zakony', 8, false, true, false, false, 'U');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (344,NULL,NULL,'marc21',NULL);

-- 12. 10. 2016 tomascejpek
INSERT INTO harvested_record_format(id, name) VALUES (37, 'LEGISLATIVE_DECREES');
INSERT INTO harvested_record_format(id, name) VALUES (38, 'LEGISLATIVE_EDICTS');
INSERT INTO harvested_record_format(id, name) VALUES (39, 'LEGISLATIVE_RESOLUTIONS');
INSERT INTO harvested_record_format(id, name) VALUES (40, 'LEGISLATIVE_MEASURES');
INSERT INTO harvested_record_format(id, name) VALUES (41, 'LEGISLATIVE_DIRECTIVES');
INSERT INTO harvested_record_format(id, name) VALUES (42, 'LEGISLATIVE_TREATIES');
INSERT INTO harvested_record_format(id, name) VALUES (43, 'LEGISLATIVE_EDITORIAL');
INSERT INTO harvested_record_format(id, name) VALUES (44, 'LEGISLATIVE_RULES');
INSERT INTO harvested_record_format(id, name) VALUES (45, 'LEGISLATIVE_ORDERS');
INSERT INTO harvested_record_format(id, name) VALUES (46, 'LEGISLATIVE_PROCEDURES');
INSERT INTO harvested_record_format(id, name) VALUES (47, 'LEGISLATIVE_STATUTES');
INSERT INTO harvested_record_format(id, name) VALUES (48, 'LEGISLATIVE_CONVENTIONS');
INSERT INTO harvested_record_format(id, name) VALUES (49, 'LEGISLATIVE_PRINCIPLES');
INSERT INTO harvested_record_format(id, name) VALUES (50, 'LEGISLATIVE_AGREEMENTS');
INSERT INTO harvested_record_format(id, name) VALUES (51, 'LEGISLATIVE_GUIDELINES');
INSERT INTO harvested_record_format(id, name) VALUES (52, 'LEGISLATIVE_ORDINANCES');
INSERT INTO harvested_record_format(id, name) VALUES (53, 'LEGISLATIVE_SENATE_MEASURES');
INSERT INTO harvested_record_format(id, name) VALUES (54, 'LEGISLATIVE_CONDITIONS');
INSERT INTO harvested_record_format(id, name) VALUES (55, 'LEGISLATIVE_OTHERS');
INSERT INTO harvested_record_format(id, name) VALUES (56, 'LEGISLATIVE_NOTICE');
INSERT INTO harvested_record_format(id, name) VALUES (57, 'LEGISLATIVE_CODE');

-- 14. 11. 2016 tomascejpek
DELETE FROM sigla WHERE id IN (4,26,27,31);
INSERT INTO sigla (id, import_conf_id, sigla) VALUES (33, 340, 'UOG001');
INSERT INTO sigla (id, import_conf_id, sigla) VALUES (34, 340, 'UOG009');
INSERT INTO sigla (id, import_conf_id, sigla) VALUES (35, 340, 'UOG010');

-- 06. 12. 2016 tomascejpek
CREATE OR REPLACE VIEW dedup_record_orphaned AS
SELECT
  dr.id dedup_record_id,
  dr.updated AS orphaned
FROM
  dedup_record dr
WHERE
  NOT EXISTS(SELECT 1 FROM harvested_record hr WHERE hr.dedup_record_id = dr.id and deleted is null)
;

-- 06. 12. 2016 tomascejpek
UPDATE import_conf SET interception_enabled='true' WHERE id=312;

-- 09. 12. 2016 tomascejpek
INSERT INTO library (id, name, url, catalog_url, city) VALUES (146, 'MKPR', 'http://knihovnaprerov.cz/', 'katalog.knihovnaprerov.cz/', 'Přerov');
INSERT INTO import_conf (id, library_id, contact_person_id, id_prefix, base_weight, cluster_id_enabled, filtering_enabled, interception_enabled, is_library, harvest_frequency) VALUES (346, 146, 200, 'mkpr', 12, false, false, false, true, 'U');
INSERT INTO oai_harvest_conf (import_conf_id,url,set_spec,metadata_prefix,granularity) VALUES (346,'http://katalog.knihovnaprerov.cz/l.dll','CPK','marc21',NULL);

-- 09. 12. 2016 tomascejpek
UPDATE import_conf SET base_weight=9 WHERE id=346;

-- 12. 12. 2016 tomascejpek
DELETE FROM oai_harvest_conf WHERE import_conf_id in (319,321,325,326);
INSERT INTO download_import_conf (import_conf_id,url,import_job_name,format,extract_id_regex) VALUES (319,'local:/data/imports/aleph.ANL','importOaiRecordsJob',null,'[^:]+:(.*)');
INSERT INTO download_import_conf (import_conf_id,url,import_job_name,format,extract_id_regex) VALUES (321,'local:/data/imports/aleph.SLK','importOaiRecordsJob',null,'s/[^:]+:(.*)/SLK01-$1/');
INSERT INTO download_import_conf (import_conf_id,url,import_job_name,format,extract_id_regex) VALUES (325,'local:/data/imports/aleph.KKL','importOaiRecordsJob',null,'s/[^:]+:(.*)/KKL01-$1/');
INSERT INTO download_import_conf (import_conf_id,url,import_job_name,format,extract_id_regex) VALUES (326,'local:/data/imports/aleph.STT','importOaiRecordsJob',null,'s/[^:]+:(.*)/STT01-$1/');

-- 14. 12. 2016 tomascejpek
ALTER TABLE harvested_record ADD COLUMN source_info VARCHAR(255);
CREATE INDEX harvested_record_source_info_idx ON harvested_record(source_info);
