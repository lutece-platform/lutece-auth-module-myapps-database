--
-- Table structure for table myapps_database_application
--

DROP TABLE IF EXISTS myapps_database_application;
CREATE TABLE myapps_database_application (
  id_application SMALLINT DEFAULT '0' NOT NULL,
  name VARCHAR(255),
  description VARCHAR(255),
  url VARCHAR(255),
  code VARCHAR(255),
  password VARCHAR(255),
  data VARCHAR(255),
  code_heading VARCHAR(255),
  data_heading VARCHAR(255),
  icon_content LONG VARBINARY,
  icon_mime_type VARCHAR(255) DEFAULT NULL,
  code_category VARCHAR(50),
  PRIMARY KEY (id_application)
);

CREATE INDEX code_category_fk ON myapps_database_application (code_category);

--
-- Table structure for table myapps_database_user
--

DROP TABLE IF EXISTS myapps_database_user;
CREATE TABLE myapps_database_user (
  id_myapps_user SMALLINT DEFAULT '0' NOT NULL,
  name VARCHAR(255),
  id_application SMALLINT DEFAULT NULL,
  stored_user_name VARCHAR(255),
  stored_user_password VARCHAR(255),
  stored_user_data VARCHAR(255),
  PRIMARY KEY (id_myapps_user)
);



--
-- Table structure for table myapps_database_category
--

DROP TABLE IF EXISTS myapps_database_category;
CREATE TABLE myapps_database_category ( 
 code_category VARCHAR(50), 
 libelle_category VARCHAR(255),
 PRIMARY KEY (code_category)
 );
 
 
 ALTER TABLE myapps_database_application ADD CONSTRAINT fk_code_category FOREIGN KEY (code_category)
     REFERENCES myapps_database_category (code_category)  ON DELETE RESTRICT ON UPDATE RESTRICT ;

