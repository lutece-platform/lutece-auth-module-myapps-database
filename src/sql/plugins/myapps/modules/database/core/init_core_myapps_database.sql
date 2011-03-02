--
-- Dumping data for table core_admin_right
--
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES 
('MYAPPS_DATABASE_MANAGEMENT', 'module.myapps.database.adminFeature.myapps_database_management.name', 1, 'jsp/admin/plugins/myapps/modules/database/ManageMyApps.jsp', 'module.myapps.database.adminFeature.myapps_database_management.description', 0, 'myapps-database', 'APPLICATIONS', NULL, NULL);

--
-- Dumping data for table core_user_right
--
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYAPPS_DATABASE_MANAGEMENT',1);

--
-- Init  table core_admin_role
--
INSERT INTO core_admin_role (role_key,role_description) VALUES ('myapps_database_manager', 'MyApps Database management');

--
-- Init  table core_admin_role_resource
--
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES (197,'myapps_database_manager','MYAPPS_DATABASE','*','*');

--
-- Init  table core_user_role
--
INSERT INTO core_user_role (role_key,id_user) VALUES ('myapps_database_manager',1);
