/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.myapps.modules.database.business;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * MyAppsDatabaseDAO
 * 
 */
public final class MyAppsDatabaseDAO implements IMyAppsDatabaseDAO {
	// SQL
	private static final String SQL_QUERY_NEW_PK = " SELECT max( id_application ) FROM myapps_database_application ";
	private static final String SQL_QUERY_SELECT = " SELECT id_application, name, description, url, code, password, data, code_heading, data_heading, icon_mime_type,code_category FROM myapps_database_application WHERE id_application = ? ";
	private static final String SQL_QUERY_INSERT = " INSERT INTO myapps_database_application ( id_application, name, description, url, code, password, data, code_heading, data_heading, icon_content, icon_mime_type,code_category) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?, ? , ?, ? ) ";
	private static final String SQL_QUERY_DELETE = " DELETE FROM myapps_database_application WHERE id_application = ? ";
	private static final String SQL_QUERY_UPDATE = " UPDATE myapps_database_application SET name = ?, description = ?, url = ?, code = ?, password = ?, data = ?, code_heading = ?, data_heading = ?, icon_content = ?, icon_mime_type = ?,code_category = ? WHERE id_application = ? ";
	private static final String SQL_QUERY_UPDATE_WITHOUT_ICON = " UPDATE myapps_database_application SET name = ?, description = ?, url = ?, code = ?, password = ?, data = ?, code_heading = ?, data_heading = ?,code_category = ? WHERE id_application = ? ";
	private static final String SQL_QUERY_SELECTALL = " SELECT a.id_application, a.name, a.description, a.url, a.code, a.password, a.data, a.code_heading, a.data_heading, a.icon_mime_type ,code_category FROM myapps_database_application as a ";
	private static final String SQL_QUERY_SELECT_MYAPPS = " SELECT a.id_application, a.name FROM myapps_database_application as a ";
	
	
	private static final String SQL_QUERY_SELECT_ICON_MIME_TYPE = " SELECT icon_mime_type FROM myapps_database_application WHERE id_application = ? ";
	private// SQL clause
	static final String SQL_QUERY_WHERE = "WHERE";
	static final String SQL_QUERY_AND = "AND";
	// filter
	private static final String SQL_FILTER_USER_NAME = " INNER JOIN myapps_database_user as u ON (a.id_application = u.id_application and u.name= ? )";
	private static final String SQL_FILTER_CATEGORY = " a.code_category= ? ";
	// Image resource fetching
	private static final String SQL_QUERY_SELECT_RESOURCE_IMAGE = " SELECT icon_content , icon_mime_type FROM myapps_database_application WHERE id_application= ? ";
	private static final String SQL_ORDER_BY_NAME = " ORDER BY a.name ";
	private static final String SQL_ORDER_BY_APPLICATION_ORDER = " ORDER BY u.application_order";
	private static final String SQL_ASC = " ASC ";
	private static final String SQL_DESC = " DESC ";

	/**
	 * {@inheritDoc}
	 */
	public int newPrimaryKey(Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_NEW_PK, plugin);
		daoUtil.executeQuery();

		int nKey;

		if (!daoUtil.next()) {
			// if the table is empty
			nKey = 1;
		}

		nKey = daoUtil.getInt(1) + 1;
		daoUtil.free();

		return nKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void insert(MyAppsDatabase myApps, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_INSERT, plugin);

		int nIndex = 1;
		myApps.setIdApplication(newPrimaryKey(plugin));

		daoUtil.setInt(nIndex++, myApps.getIdApplication());
		daoUtil.setString(nIndex++, myApps.getName());
		daoUtil.setString(nIndex++, myApps.getDescription());
		daoUtil.setString(nIndex++, myApps.getUrl());
		daoUtil.setString(nIndex++, myApps.getCode());
		daoUtil.setString(nIndex++, myApps.getPassword());
		daoUtil.setString(nIndex++, myApps.getData());
		daoUtil.setString(nIndex++, myApps.getCodeHeading());
		daoUtil.setString(nIndex++, myApps.getDataHeading());

		if ((myApps.getIconContent() == null)) {
			daoUtil.setBytes(nIndex++, null);
			daoUtil.setString(nIndex++, StringUtils.EMPTY);
		} else {
			daoUtil.setBytes(nIndex++, myApps.getIconContent());
			daoUtil.setString(nIndex++, myApps.getIconMimeType());
		}
		daoUtil.setString(nIndex++, myApps.getCodeCategory());

		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * {@inheritDoc}
	 */
	public MyApps load(int nId, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT, plugin);
		daoUtil.setInt(1, nId);
		daoUtil.executeQuery();

		MyAppsDatabase myApps = null;

		if (daoUtil.next()) {
			int nIndex = 1;
			myApps = new MyAppsDatabase();

			myApps.setIdApplication(daoUtil.getInt(nIndex++));
			myApps.setName(daoUtil.getString(nIndex++));
			myApps.setDescription(daoUtil.getString(nIndex++));
			myApps.setUrl(daoUtil.getString(nIndex++));
			myApps.setCode(daoUtil.getString(nIndex++));
			myApps.setPassword(daoUtil.getString(nIndex++));
			myApps.setData(daoUtil.getString(nIndex++));
			myApps.setCodeHeading(daoUtil.getString(nIndex++));
			myApps.setDataHeading(daoUtil.getString(nIndex++));
			myApps.setIconMimeType(daoUtil.getString(nIndex++));
			myApps.setCodeCategory(daoUtil.getString(nIndex++));
		}

		daoUtil.free();

		return myApps;
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(int nMyAppsId, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_DELETE, plugin);
		daoUtil.setInt(1, nMyAppsId);
		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * {@inheritDoc}
	 */
	public void store(MyAppsDatabase myApps, boolean bUpdateIcon, Plugin plugin) {
		String strSQL = bUpdateIcon ? SQL_QUERY_UPDATE
				: SQL_QUERY_UPDATE_WITHOUT_ICON;
		DAOUtil daoUtil = new DAOUtil(strSQL, plugin);

		int nIndex = 1;
		daoUtil.setString(nIndex++, myApps.getName());
		daoUtil.setString(nIndex++, myApps.getDescription());
		daoUtil.setString(nIndex++, myApps.getUrl());
		daoUtil.setString(nIndex++, myApps.getCode());
		daoUtil.setString(nIndex++, myApps.getPassword());
		daoUtil.setString(nIndex++, myApps.getData());
		daoUtil.setString(nIndex++, myApps.getCodeHeading());
		daoUtil.setString(nIndex++, myApps.getDataHeading());

		if (bUpdateIcon) {
			if (myApps.getIconContent() == null) {
				daoUtil.setBytes(nIndex++, null);
				daoUtil.setString(nIndex++, StringUtils.EMPTY);
			} else {
				daoUtil.setBytes(nIndex++, myApps.getIconContent());
				daoUtil.setString(nIndex++, myApps.getIconMimeType());
			}
		}
		daoUtil.setString(nIndex++, myApps.getCodeCategory());
		daoUtil.setInt(nIndex++, myApps.getIdApplication());

		daoUtil.executeUpdate();
		daoUtil.free();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MyApps> selectMyAppsList(MyAppsDatabaseFilter filter,Plugin plugin
			) {
		List<MyApps> myAppsList = new ArrayList<MyApps>();

		StringBuilder sbSQL = new StringBuilder(SQL_QUERY_SELECTALL);
		if (filter!=null && filter.containsCategory()) {
			sbSQL.append(SQL_QUERY_WHERE);
			sbSQL.append(SQL_FILTER_CATEGORY);
		}

		DAOUtil daoUtil = new DAOUtil(sbSQL.toString(), plugin);

		int ncpt = 0;
		if (filter!=null && filter.containsCategory()) {

			daoUtil.setString(++ncpt, filter.getCategory());

		}
		daoUtil.executeQuery();

		while (daoUtil.next()) {
			int nIndex = 1;
			MyAppsDatabase myApps = new MyAppsDatabase();

			myApps.setIdApplication(daoUtil.getInt(nIndex++));
			myApps.setName(daoUtil.getString(nIndex++));
			myApps.setDescription(daoUtil.getString(nIndex++));
			myApps.setUrl(daoUtil.getString(nIndex++));
			myApps.setCode(daoUtil.getString(nIndex++));
			myApps.setPassword(daoUtil.getString(nIndex++));
			myApps.setData(daoUtil.getString(nIndex++));
			myApps.setCodeHeading(daoUtil.getString(nIndex++));
			myApps.setDataHeading(daoUtil.getString(nIndex++));
			myApps.setIconMimeType(daoUtil.getString(nIndex++));
			myApps.setCodeCategory(daoUtil.getString(nIndex++));

			myAppsList.add(myApps);
		}

		daoUtil.free();

		return myAppsList;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MyApps> selectMyAppsListByFilter(MyAppsDatabaseFilter filter,
			boolean bIsAscSort, Plugin plugin) {
		List<MyApps> myAppsList = new ArrayList<MyApps>();
		StringBuilder sbSQL = new StringBuilder(SQL_QUERY_SELECTALL);
		
		if (filter.containsUserName()) {
			sbSQL.append(SQL_FILTER_USER_NAME);
		}
		
		if (filter.containsCategory() ) {
			sbSQL.append(SQL_QUERY_WHERE);
			sbSQL.append(SQL_FILTER_CATEGORY);
		}
		

		sbSQL.append(SQL_ORDER_BY_NAME);
		sbSQL.append(bIsAscSort ? SQL_ASC : SQL_DESC);

		DAOUtil daoUtil = new DAOUtil(sbSQL.toString(), plugin);
		int ncpt = 0;
		
		if (filter.containsUserName()) {

			daoUtil.setString(++ncpt, filter.getUserName());
		}
		if (filter.containsCategory()) {

			daoUtil.setString(++ncpt, filter.getCategory());

		}
		

		daoUtil.executeQuery();

		while (daoUtil.next()) {
			int nIndex = 1;
			MyAppsDatabase myApps = new MyAppsDatabase();

			myApps.setIdApplication(daoUtil.getInt(nIndex++));
			myApps.setName(daoUtil.getString(nIndex++));
			myApps.setDescription(daoUtil.getString(nIndex++));
			myApps.setUrl(daoUtil.getString(nIndex++));
			myApps.setCode(daoUtil.getString(nIndex++));
			myApps.setPassword(daoUtil.getString(nIndex++));
			myApps.setData(daoUtil.getString(nIndex++));
			myApps.setCodeHeading(daoUtil.getString(nIndex++));
			myApps.setDataHeading(daoUtil.getString(nIndex++));
			myApps.setIconMimeType(daoUtil.getString(nIndex++));
			myApps.setCodeCategory(daoUtil.getString(nIndex++));
			
			myAppsList.add(myApps);
		}

		daoUtil.free();

		return myAppsList;
	}

	/**
    * {@inheritDoc}
    */
	public List<MyApps> selectMyAppsListByUser( String strUserName, Plugin plugin )
	{
	    List<MyApps> myAppsList = new ArrayList<MyApps>( );
	    StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECTALL );
	    sbSQL.append( SQL_FILTER_USER_NAME );
	    sbSQL.append( SQL_ORDER_BY_APPLICATION_ORDER );

	    DAOUtil daoUtil = new DAOUtil( sbSQL.toString( ), plugin );
	    daoUtil.setString( NumberUtils.INTEGER_ONE, strUserName );
	    daoUtil.executeQuery( );

	    while ( daoUtil.next( ) ) {
	        int nIndex = 1;
	        MyAppsDatabase myApps = new MyAppsDatabase( );

	        myApps.setIdApplication(daoUtil.getInt(nIndex++));
	        myApps.setName(daoUtil.getString(nIndex++));
	        myApps.setDescription(daoUtil.getString(nIndex++));
	        myApps.setUrl(daoUtil.getString(nIndex++));
	        myApps.setCode(daoUtil.getString(nIndex++));
	        myApps.setPassword(daoUtil.getString(nIndex++));
	        myApps.setData(daoUtil.getString(nIndex++));
	        myApps.setCodeHeading(daoUtil.getString(nIndex++));
	        myApps.setDataHeading(daoUtil.getString(nIndex++));
	        myApps.setIconMimeType(daoUtil.getString(nIndex++));
	        myApps.setCodeCategory(daoUtil.getString(nIndex++));

	        myAppsList.add( myApps );
	    }

	    daoUtil.free( );

	    return myAppsList;
	}
	   
	/**
	 * {@inheritDoc}
	 */
	public ImageResource getIconResource(int nIdMyApps, Plugin plugin) {
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT_RESOURCE_IMAGE, plugin);
		daoUtil.setInt(1, nIdMyApps);
		daoUtil.executeQuery();

		ImageResource image = null;

		if (daoUtil.next()) {
			int nIndex = 1;
			image = new ImageResource();
			image.setImage(daoUtil.getBytes(nIndex++));
			image.setMimeType(daoUtil.getString(nIndex++));
		}

		daoUtil.free();

		return image;
	}

	/**
	 * {@inheritDoc}
	 */
	public ReferenceList getMyAppsList(Plugin plugin) {
		ReferenceList myAppsList = new ReferenceList();
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT_MYAPPS, plugin);
		daoUtil.executeQuery();

		while (daoUtil.next()) {
			int nIndex = 1;
			myAppsList.addItem(daoUtil.getString(nIndex++), daoUtil
					.getString(nIndex++));
		}

		daoUtil.free();

		return myAppsList;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasIcon(int nIdApps, Plugin plugin) {
		boolean bHasIcon = false;
		DAOUtil daoUtil = new DAOUtil(SQL_QUERY_SELECT_ICON_MIME_TYPE, plugin);
		daoUtil.setInt(1, nIdApps);
		daoUtil.executeQuery();

		if (daoUtil.next()) {
			bHasIcon = true;
		}

		daoUtil.free();

		return bHasIcon;
	}
	
}
