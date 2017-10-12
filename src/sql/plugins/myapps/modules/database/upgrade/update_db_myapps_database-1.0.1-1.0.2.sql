--
-- Add column application_order for table myapps_database_user
-- 

 ALTER TABLE myapps_database_user ADD COLUMN application_order int(6) DEFAULT 1 NOT NULL;