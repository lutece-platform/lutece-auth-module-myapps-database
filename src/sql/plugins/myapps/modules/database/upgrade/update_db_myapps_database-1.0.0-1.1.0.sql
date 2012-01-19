--
-- Table structure for table myapps_database_category
--

DROP TABLE IF EXISTS myapps_database_category;
CREATE TABLE myapps_database_category ( 
 code_category VARCHAR(50), 
 libelle_category VARCHAR(255),
 PRIMARY KEY (code_category)
 );

--
-- Add column code_category for table myapps_database_application
-- 

 ALTER TABLE myapps_database_application ADD COLUMN code_category VARCHAR(50);
 ALTER TABLE myapps_database_application ADD CONSTRAINT fk_code_category FOREIGN KEY (code_category)
     REFERENCES myapps_database_category (code_category)  ON DELETE RESTRICT ON UPDATE RESTRICT ;
 CREATE INDEX code_category_fk ON myapps_database_application (code_category);