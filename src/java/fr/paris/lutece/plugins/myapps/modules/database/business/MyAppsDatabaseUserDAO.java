/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.myapps.business.MyAppsUser;
import fr.paris.lutece.plugins.myapps.util.crypto.CryptoUtil;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * MyAppsDatabaseUserDAO
 *
 */
public final class MyAppsDatabaseUserDAO implements IMyAppsDatabaseUserDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_myapps_user ) 	FROM myapps_database_user ";
    private static final String SQL_QUERY_SELECT = " SELECT id_myapps_user, name, id_application, stored_user_name, stored_user_password, stored_user_data FROM myapps_database_user WHERE id_myapps_user = ?";
    private static final String SQL_QUERY_INSERT = " INSERT INTO myapps_database_user ( id_myapps_user, name, id_application, stored_user_name, stored_user_password, stored_user_data ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM myapps_database_user WHERE id_myapps_user = ? ";
    private static final String SQL_QUERY_DELETE_FROM_MYAPP_ID_AND_USER_NAME = " DELETE FROM myapps_database_user WHERE id_application = ? AND name = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE myapps_database_user SET id_myapps_user = ?, name = ?, id_application = ?, stored_user_name = ?, stored_user_password = ?, stored_user_data = ? WHERE id_myapps_user = ? ";
    private static final String SQL_QUERY_SELECTALL = " SELECT id_myapps_user, name, id_application, stored_user_name, stored_user_password, stored_user_data FROM myapps_database_user ";
    private static final String SQL_QUERY_BY_USER = " SELECT id_myapps_user, name, id_application, stored_user_name, stored_user_password, stored_user_data FROM myapps_database_user " +
        " WHERE name = ? AND id_application = ? ";

    //Encryption param
    private static final String PROPERTY_CRYPTO_KEY = "crypto.key";
    private static final String KEY = AppPropertiesService.getProperty( PROPERTY_CRYPTO_KEY );

    /**
     * {@inheritDoc}
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    public void insert( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        myAppsUser.setMyAppsUserId( newPrimaryKey( plugin ) );

        // Encrypt username and password
        String strUsername = CryptoUtil.encrypt( myAppsUser.getStoredUserName(  ), KEY );
        String strPassword = CryptoUtil.encrypt( myAppsUser.getStoredUserPassword(  ), KEY );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, myAppsUser.getMyAppsUserId(  ) );
        daoUtil.setString( nIndex++, myAppsUser.getName(  ) );
        daoUtil.setInt( nIndex++, myAppsUser.getIdApplication(  ) );
        daoUtil.setString( nIndex++, strUsername );
        daoUtil.setString( nIndex++, strPassword );
        daoUtil.setString( nIndex++, myAppsUser.getStoredUserData(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public MyAppsDatabaseUser load( int nMyAppsUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nMyAppsUserId );
        daoUtil.executeQuery(  );

        MyAppsDatabaseUser myAppsUser = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            myAppsUser = new MyAppsDatabaseUser(  );

            myAppsUser.setMyAppsUserId( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setIdApplication( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setStoredUserName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setStoredUserPassword( daoUtil.getString( nIndex++ ) );
            myAppsUser.setStoredUserData( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return myAppsUser;
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nMyAppsUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nMyAppsUserId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nMyAppId, String strUserName, Plugin plugin )
    {
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_FROM_MYAPP_ID_AND_USER_NAME, plugin );
        daoUtil.setInt( nIndex++, nMyAppId );
        daoUtil.setString( nIndex++, strUserName );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void store( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, myAppsUser.getMyAppsUserId(  ) );
        daoUtil.setString( nIndex++, myAppsUser.getName(  ) );
        daoUtil.setInt( nIndex++, myAppsUser.getIdApplication(  ) );

        String strUsername = CryptoUtil.encrypt( myAppsUser.getStoredUserName(  ), KEY );
        String strPassword = CryptoUtil.encrypt( myAppsUser.getStoredUserPassword(  ), KEY );
        daoUtil.setString( nIndex++, strUsername );
        daoUtil.setString( nIndex++, strPassword );
        daoUtil.setString( nIndex++, myAppsUser.getStoredUserData(  ) );
        daoUtil.setInt( nIndex++, myAppsUser.getMyAppsUserId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public List<MyAppsUser> selectMyAppsUsersList( Plugin plugin )
    {
        List<MyAppsUser> myAppsUserList = new ArrayList<MyAppsUser>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            MyAppsDatabaseUser myAppsUser = new MyAppsDatabaseUser(  );

            myAppsUser.setMyAppsUserId( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setIdApplication( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setStoredUserName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setStoredUserPassword( daoUtil.getString( nIndex++ ) );
            myAppsUser.setStoredUserData( daoUtil.getString( nIndex++ ) );

            myAppsUserList.add( myAppsUser );
        }

        daoUtil.free(  );

        return myAppsUserList;
    }

    /**
     * {@inheritDoc}
     */
    public MyAppsDatabaseUser getCredentials( int nApplicationId, String strUserName, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_BY_USER, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, strUserName );
        daoUtil.setInt( nIndex++, nApplicationId );

        daoUtil.executeQuery(  );

        MyAppsDatabaseUser myAppsUser = null;

        if ( daoUtil.next(  ) )
        {
            nIndex = 1;
            myAppsUser = new MyAppsDatabaseUser(  );

            myAppsUser.setMyAppsUserId( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setIdApplication( daoUtil.getInt( nIndex++ ) );

            // Decrypt username and password
            String strUsername = CryptoUtil.decrypt( daoUtil.getString( nIndex++ ), KEY );
            String strPassword = CryptoUtil.decrypt( daoUtil.getString( nIndex++ ), KEY );
            myAppsUser.setStoredUserName( strUsername );
            myAppsUser.setStoredUserPassword( strPassword );
            myAppsUser.setStoredUserData( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return myAppsUser;
    }

    /**
     * {@inheritDoc}
     */
    public MyAppsDatabaseUser getCredentials( int nMyAppsUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nMyAppsUserId );
        daoUtil.executeQuery(  );

        MyAppsDatabaseUser myAppsUser = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            myAppsUser = new MyAppsDatabaseUser(  );

            myAppsUser.setMyAppsUserId( daoUtil.getInt( nIndex++ ) );
            myAppsUser.setName( daoUtil.getString( nIndex++ ) );
            myAppsUser.setIdApplication( daoUtil.getInt( nIndex++ ) );

            // Decrypt username and password
            String strUsername = CryptoUtil.decrypt( daoUtil.getString( nIndex++ ), KEY );
            String strPassword = CryptoUtil.decrypt( daoUtil.getString( nIndex++ ), KEY );
            myAppsUser.setStoredUserName( strUsername );
            myAppsUser.setStoredUserPassword( strPassword );
            myAppsUser.setStoredUserData( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free(  );

        return myAppsUser;
    }
}
