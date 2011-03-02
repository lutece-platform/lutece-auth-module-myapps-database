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
  PRIMARY KEY (id_application)
);

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
