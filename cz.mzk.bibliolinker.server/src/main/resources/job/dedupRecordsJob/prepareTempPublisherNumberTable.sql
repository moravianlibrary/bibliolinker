DROP TABLE IF EXISTS tmp_simmilar_publisher_number;

CREATE TABLE tmp_simmilar_publisher_number AS
SELECT
  nextval('tmp_table_id_seq') as row_id,
  pn.publisher_number,
  t.title,
  hr.publication_year,
  hrl.harvested_record_format_id,
  array_to_string(array_agg(hr.id), ',')  id_array
FROM harvested_record hr 
  INNER JOIN publisher_number pn ON hr.id = pn.harvested_record_id 
  INNER JOIN title t ON hr.id = t.harvested_record_id
  INNER JOIN harvested_record_format_link hrl ON hr.id = hrl.harvested_record_id
WHERE t.order_in_record = 1 
  AND pn.order_in_record = 1
  AND hrl.harvested_record_format_id IN (5,12,13,14,15,16,17)
GROUP BY pn.publisher_number,t.title,hr.publication_year,hrl.harvested_record_format_id
HAVING COUNT(DISTINCT hr.id) > 1 
  AND count(DISTINCT dedup_record_id) + sum(case when dedup_record_id is null then 1 else 0 end) != 1
  AND bool_or(next_dedup_flag) IS TRUE;

CREATE INDEX tmp_publisher_number_idx ON tmp_simmilar_publisher_number(row_id);