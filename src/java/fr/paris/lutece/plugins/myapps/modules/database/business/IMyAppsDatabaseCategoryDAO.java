package fr.paris.lutece.plugins.myapps.modules.database.business;

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface IMyAppsDatabaseCategoryDAO {

	/**
	 * insert category
	 * @param category object	
	 * @param plugin a plugin
	 */
	void insert(MyAppsDatabaseCategory category, Plugin plugin);

	/**
	 * return category object
	 * @param strCodeCategory the strCodeCategory
	 * @param plugin teh plugin
	 * @return a category object
	 */
	MyAppsDatabaseCategory load(String strCodeCategory, Plugin plugin);

	/**
	 * delete category  by code
	 * @param strCodeCategory the code category  
	 * @param plugin the plugin
	 */
	void delete(String strCodeCategory, Plugin plugin);

	/**
	 * update a category
	 * @param category a category object
	 * @param plugin a plugin
	 */
	void store(MyAppsDatabaseCategory category, Plugin plugin);

	/**
	 *  a List of category
	 * @param plugin the plugin
	 * @return a List of category
	 */
	List<MyAppsDatabaseCategory> selectCategoryList(Plugin plugin);

}