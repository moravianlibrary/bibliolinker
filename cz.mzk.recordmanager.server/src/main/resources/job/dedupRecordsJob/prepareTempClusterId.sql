DROP TABLE IF EXISTS tmp_cluster_ids;

CREATE TABLE tmp_cluster_ids AS
SELECT 
  nextval('tmp_table_id_seq') as row_id,
  array_to_string(array_agg(harvested_record.id), ',') AS id_array 
FROM harvested_record WHERE cluster_id IN 
  (SELECT cluster_id FROM harvested_record WHERE dedup_record_id is NULL OR next_dedup_flag IS TRUE GROUP BY cluster_id) 
GROUP BY cluster_id
HAVING COUNT(harvested_record.id) > 1 AND bool_or(next_dedup_flag) IS TRUE;
  
CREATE INDEX tmp_cluster_idx ON tmp_cluster_ids(row_id);
