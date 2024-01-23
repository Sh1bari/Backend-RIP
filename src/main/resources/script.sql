EXPLAIN ANALYZE SELECT * FROM event;
EXPLAIN ANALYZE SELECT * FROM event WHERE name LIKE '%event1%';

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_event_name_trgm ON event USING gin(name gin_trgm_ops);

DROP INDEX IF EXISTS idx_event_name_trgm;