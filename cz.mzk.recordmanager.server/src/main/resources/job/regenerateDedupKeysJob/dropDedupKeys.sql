UPDATE harvested_record SET cluster_id = NULL, uuid = NULL, issn_series = NULL, issn_series_order = NULL, publication_year = NULL, scale = NULL, author_string = NULL, author_auth_key = NULL, raw_001_id = NULL, dedup_keys_hash = NULL, source_info = NULL;
DELETE FROM title;
DELETE FROM isbn;
DELETE FROM issn;
DELETE FROM cnb;
DELETE FROM language;
DELETE FROM oclc;
DELETE FROM ean;
DELETE FROM short_title;