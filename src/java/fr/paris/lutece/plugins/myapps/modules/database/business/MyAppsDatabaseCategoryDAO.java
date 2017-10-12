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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * MyAppsDatabaseCategoryDAO
 * 
 */
public final class MyAppsDatabaseCategoryDAO implements IMyAppsDatabaseCategoryDAO
{
    // SQL
    private static final String SQL_QUERY_SELECTALL = " SELECT code_category,libelle_category FROM myapps_database_category ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO myapps_database_category ( code_category, libelle_category) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM myapps_database_category WHERE code_category = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE myapps_database_category SET code_category = ?, libelle_category = ? WHERE code_category = ? ";
    private static final String SQL_FILTER_CATEGORY = " WHERE code_category = ? ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECTALL + SQL_FILTER_CATEGORY;
    private static final String SQL_ORDER_BY_CATEGORY = " ORDER BY libelle_category ASC";

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.myapps.modules.database.business.IMyAppsDatabaseCategoryDAO#insert(fr.paris.lutece.plugins.myapps.modules.database.business.
     * MyAppsDatabaseCategory, fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public void insert( MyAppsDatabaseCategory category, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nIndex = 1;

        daoUtil.setString( nIndex++, category.getCodeCategory( ) );
        daoUtil.setString( nIndex++, category.getLibelleCategory( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.myapps.modules.database.business.IMyAppsDatabaseCategoryDAO#load(java.lang.String,
     * fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public MyAppsDatabaseCategory load( String strCodeCategory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strCodeCategory );
        daoUtil.executeQuery( );

        MyAppsDatabaseCategory category = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;
            category = new MyAppsDatabaseCategory( );
            category.setCodeCategory( daoUtil.getString( nIndex++ ) );
            category.setLibelleCategory( daoUtil.getString( nIndex++ ) );

        }

        daoUtil.free( );

        return category;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.myapps.modules.database.business.IMyAppsDatabaseCategoryDAO#delete(java.lang.String,
     * fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public void delete( String strCodeCategory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, strCodeCategory );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.myapps.modules.database.business.IMyAppsDatabaseCategoryDAO#store(fr.paris.lutece.plugins.myapps.modules.database.business.
     * MyAppsDatabaseCategory, fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public void store( MyAppsDatabaseCategory category, Plugin plugin )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nIndex = 1;

        daoUtil.setString( nIndex++, category.getCodeCategory( ) );
        daoUtil.setString( nIndex++, category.getLibelleCategory( ) );
        daoUtil.setString( nIndex++, category.getCodeCategory( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.myapps.modules.database.business.IMyAppsDatabaseCategoryDAO#selectCategoryList(fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public List<MyAppsDatabaseCategory> selectCategoryList( Plugin plugin )
    {
        List<MyAppsDatabaseCategory> myAppsCategoryList = new ArrayList<MyAppsDatabaseCategory>( );
        MyAppsDatabaseCategory category = null;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL + SQL_ORDER_BY_CATEGORY, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;
            category = new MyAppsDatabaseCategory( );
            category.setCodeCategory( daoUtil.getString( nIndex++ ) );
            category.setLibelleCategory( daoUtil.getString( nIndex++ ) );
            myAppsCategoryList.add( category );
        }

        daoUtil.free( );

        return myAppsCategoryList;
    }

}
