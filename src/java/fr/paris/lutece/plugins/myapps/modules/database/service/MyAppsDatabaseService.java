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
package fr.paris.lutece.plugins.myapps.modules.database.service;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.business.MyAppsUser;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseFilter;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseHome;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseUser;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseUserHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * MyAppsDatabaseService
 *
 */
public final class MyAppsDatabaseService
{
    private static MyAppsDatabaseService _singleton;

    /**
     * Initialize the Database service
     */
    public void init( )
    {
    }

    /**
     * Returns the instance of the singleton
     *
     * @return The instance of the singleton
     */
    public static synchronized MyAppsDatabaseService getInstance( )
    {
        if ( _singleton == null )
        {
            _singleton = new MyAppsDatabaseService( );
        }

        return _singleton;
    }

    /**
     * Get the list of MyApps
     *
     * @param plugin
     *            {@link Plugin}
     * @return a list of {@link MyAppsDatabase}
     */
    public ReferenceList getMyApps( Plugin plugin )
    {
        return MyAppsDatabaseHome.getMyAppsList( plugin );
    }

    /**
     * Returns an instance of a myApps whose identifier is specified in parameter
     *
     * @param nMyAppId
     *            The myApps primary key
     * @param plugin
     *            the Plugin
     * @return an instance of MyApps
     */
    public MyApps findByPrimaryKey( int nMyAppId, Plugin plugin )
    {
        return MyAppsDatabaseHome.findByPrimaryKey( nMyAppId, plugin );
    }

    /**
     * Select the MyApps list
     * 
     * @param filter
     *            the app filter
     * @param plugin
     *            {@link Plugin}
     * @return a list of {@link MyApps}
     */
    public List<MyApps> selectMyAppsList( MyAppsDatabaseFilter filter, Plugin plugin )
    {
        return MyAppsDatabaseHome.selectMyAppsList( filter, plugin );
    }

    /**
     * Get the permissions for each {@link MyApps}
     *
     * @param listMyApps
     *            a list of {@link MyApps}
     * @param user
     *            the current {@link AdminUser}
     * @return a map of permissions
     */
    public Map<String, Map<String, Boolean>> getMyAppsPermissions( List<MyApps> listMyApps, AdminUser user )
    {
        Map<String, Map<String, Boolean>> mapPermissions = new HashMap<String, Map<String, Boolean>>( );

        for ( MyApps myApps : listMyApps )
        {
            Map<String, Boolean> listPermissions = new HashMap<String, Boolean>( );
            boolean bPermissionModify = RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, String.valueOf( myApps.getIdApplication( ) ),
                    MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, user );
            boolean bPermissionDelete = RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, String.valueOf( myApps.getIdApplication( ) ),
                    MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE, user );
            listPermissions.put( MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, bPermissionModify );
            listPermissions.put( MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE, bPermissionDelete );

            mapPermissions.put( String.valueOf( myApps.getIdApplication( ) ), listPermissions );
        }

        return mapPermissions;
    }

    /**
     * Create a new {@link MyAppsDatabase}
     *
     * @param myApp
     *            the {@link MyAppsDatabase} to create
     * @param plugin
     *            {@link Plugin}
     */
    public void create( MyAppsDatabase myApp, Plugin plugin )
    {
        MyAppsDatabaseHome.create( myApp, plugin );
    }

    /**
     * Update a {@link MyAppsDatabase}
     *
     * @param myApp
     *            the {@link MyAppsDatabase} to update
     * @param bUpdateIcon
     *            true if the icon must be updated, false otherwise
     * @param plugin
     *            {@link Plugin}
     */
    public void update( MyAppsDatabase myApp, boolean bUpdateIcon, Plugin plugin )
    {
        MyAppsDatabaseHome.update( myApp, bUpdateIcon, plugin );
    }

    /**
     * Remove a {@link MyAppsDatabase}
     *
     * @param nMyAppId
     *            the MyApp Id
     * @param plugin
     *            {@link Plugin}
     */
    public void remove( int nMyAppId, Plugin plugin )
    {
        MyAppsDatabaseHome.remove( nMyAppId, plugin );
    }

    /**
     * Get the image resource
     *
     * @param nMyAppId
     *            the MyApp Id
     * @param plugin
     *            {@link Plugin}
     * @return the image resource
     */
    public ImageResource getImageResource( int nMyAppId, Plugin plugin )
    {
        return MyAppsDatabaseHome.getImageResource( nMyAppId, plugin );
    }

    /**
     * Create a new {@link MyAppsDatabaseUser}
     *
     * @param myAppsUser
     *            the {@link MyAppsDatabaseUser} to create
     * @param plugin
     *            {@link Plugin}
     */
    public void createMyAppUser( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        MyAppsDatabaseUserHome.create( myAppsUser, plugin );
    }

    /**
     * Update a {@link MyAppsDatabaseUser}
     *
     * @param myAppsUser
     *            the {@link MyAppsDatabaseUser} to update
     * @param plugin
     *            {@link Plugin}
     */
    public void updateMyAppUser( MyAppsDatabaseUser myAppsUser, Plugin plugin )
    {
        MyAppsDatabaseUserHome.update( myAppsUser, plugin );
    }

    /**
     * Delete a {@link MyAppsDatabaseUser}
     *
     * @param nMyAppsUserId
     *            the MyAppsUser Id
     * @param plugin
     *            {@link Plugin}
     */
    public void removeMyAppUser( int nMyAppsUserId, Plugin plugin )
    {
        MyAppsDatabaseUserHome.remove( nMyAppsUserId, plugin );
    }

    /**
     * Delete a {@link MyAppsDatabaseUser}
     *
     * @param nMyAppId
     *            the MyApp Id
     * @param strUserName
     *            the user name
     * @param plugin
     *            {@link Plugin}
     */
    public void removeMyAppUser( int nMyAppId, String strUserName, Plugin plugin )
    {
        MyAppsDatabaseUserHome.remove( nMyAppId, strUserName, plugin );
    }

    /**
     * Loads a list of myApps belonging to a filter
     *
     * @param filter
     *            the app filter
     * @param bIsAscSort
     *            true if it is sorted ascendly, false otherwise
     * @param plugin
     *            the Plugin
     * @return the collection which contains the data of all the myApps
     */
    public List<MyApps> getMyAppsListByFilter( MyAppsDatabaseFilter filter, boolean bIsAscSort, Plugin plugin )
    {
        return MyAppsDatabaseHome.getMyAppsListByFilter( filter, bIsAscSort, plugin );
    }

    /**
     * Management of the image associated to the application
     *
     * @param strMyAppId
     *            The myapp identifier
     * @param plugin
     *            {@link Plugin}
     * @return the URL of the image resource
     */
    public String getResourceImageUrl( String strMyAppId, Plugin plugin )
    {
        String strUrl = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppsId = Integer.parseInt( strMyAppId );

            if ( MyAppsDatabaseHome.hasIcon( nMyAppsId, plugin ) )
            {
                String strResourceType = MyAppsDatabaseImgProvider.getInstance( ).getResourceTypeId( );
                UrlItem url = new UrlItem( Parameters.IMAGE_SERVLET );
                url.addParameter( Parameters.RESOURCE_TYPE, strResourceType );
                url.addParameter( Parameters.RESOURCE_ID, strMyAppId );
                strUrl = url.getUrlWithEntity( );
            }
        }

        return strUrl;
    }

    /**
     * Loads the data of all the myAppsUsers and returns them in form of a collection
     *
     * @param nMyAppId
     *            the ID of the appication
     * @param strUserName
     *            the user name
     * @param plugin
     *            the Plugin
     * @return a {@link MyAppsUser}
     *
     */
    public MyAppsUser getCredential( int nMyAppId, String strUserName, Plugin plugin )
    {
        return MyAppsDatabaseUserHome.getCredentials( nMyAppId, strUserName, plugin );
    }
}
