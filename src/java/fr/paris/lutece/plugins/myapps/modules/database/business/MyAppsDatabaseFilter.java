package fr.paris.lutece.plugins.myapps.modules.database.business;

import org.apache.commons.lang.StringUtils;

/**
 * 	Filter MyAppsDatabaseFilter
 * @author merlinfe
 *
 */
public class MyAppsDatabaseFilter {

	//category
	private String _strCategory;
	//User name
	private String _strUserName;

	/**
	 * 
	 * @return true of the filter contains a category
	 */
	public boolean containsCategory() {
		return StringUtils.isNotBlank(_strCategory);
	}

	/**
	 * set the category
	 * @param _strCategory set the category
	 */
	public void setCategory(String _strCategory) {
		this._strCategory = _strCategory;
	}
	
	/**
	 * return the category
	 * @return the category
	 */
	public String getCategory() {
		return _strCategory;
	}
	
	/**
	 * set the user name
	 * @param _strUserName _strUserName
	 */
	public void setUserName(String _strUserName) {
		this._strUserName = _strUserName;
	}
	/**
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return _strUserName;
	}
	/**
	 * 
	 * @return true of the filter contains the user name
	 */
	public boolean containsUserName() {
		return StringUtils.isNotBlank(_strUserName );
	}

}
