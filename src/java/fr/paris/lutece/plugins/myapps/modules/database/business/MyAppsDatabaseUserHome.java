/*
 * Copyright (c) 2002-2010, Mairie de Paris
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
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabasePlugin;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.Collection;


/**
 *
 * MyAppsDatabaseUserHome
 *
 */
public final class MyAppsDatabaseUserHome
{
    // Static variable pointed at the DAO instance
    private static IMyAppsDatabaseUserDAO _dao = (IMyAppsDatabaseUserDAO) SpringContextService.getPluginBean( MyAppsDatabasePlugin.PLUGIN_NAME,
            MyAppsDatabaseConstants.BEAN_MYAPPS_DATABASE_USER_DAO );

    /**
     * Private constructor
     */
    private MyAppsDatabaseUserHome(  )
    {
    }

    /**
     * Creation of an instance of myAppsUser
     *
     * @param myAppsUser The instance of the MyAppsUser which contains the informations to store
     * @param plugin the Plugin
     */
    public static void create( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        _dao.insert( myAppsUser, plugin );
    }

    /**
     * Update of the myAppsUser which is specified in parameter
     *
     * @param myAppsUser The instance of the MyAppsUser which contains the data to store
     * @param plugin the Plugin
     */
    public static void update( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        _dao.store( myAppsUser, plugin );
    }

    /**
     * Remove the myAppsUser whose identifier is specified in parameter
     *
     * @param nMyAppsUserId The myAppsUser Id
     * @param plugin the Plugin
     */
    public static void remove( int nMyAppsUserId, Plugin plugin )
    {
        _dao.delete( nMyAppsUserId, plugin );
    }

    /**
     * Remove the myAppsUser whose identifier is specified in parameter
     *
     * @param nMyAppId The Id of the application
     * @param strUserName the user name
     * @param plugin the Plugin
     */
    public static void remove( int nMyAppId, String strUserName, Plugin plugin )
    {
        _dao.delete( nMyAppId, strUserName, plugin );
    }

    /**
     * Returns an instance of a myAppsUser whose identifier is specified in parameter
     *
     * @param nMyAppsUserId The myAppsUser primary key
     * @param plugin the Plugin
     * @return an instance of MyAppsUser
     */
    public static MyAppsUser findByPrimaryKey( int nMyAppsUserId, Plugin plugin )
    {
        return _dao.load( nMyAppsUserId, plugin );
    }

    /**
     * Loads the data of all the myAppsUsers and returns them in form of a collection
     *
     * @param plugin the Plugin
     * @return the collection which contains the data of all the myAppsUsers
     */
    public static Collection<MyAppsUser> getmyAppsUsersList( Plugin plugin )
    {
        return _dao.selectMyAppsUsersList( plugin );
    }

    /**
    * Loads the data of all the myAppsUsers and returns them in form of a collection
    *
    * @param nMyAppId the ID of the appication
    * @param strUserName the user name
    * @param plugin the Plugin
    * @return a {@link MyAppsUser}
    */
    public static MyAppsUser getCredentials( int nMyAppId, String strUserName, Plugin plugin )
    {
        return _dao.getCredentials( nMyAppId, strUserName, plugin );
    }
}
