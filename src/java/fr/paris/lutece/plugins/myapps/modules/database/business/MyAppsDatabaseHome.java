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

import java.util.List;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabasePlugin;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

/**
 *
 * MyAppsDatabaseHome
 *
 */
public final class MyAppsDatabaseHome
{
    // Static variable pointed at the DAO instance
    private static IMyAppsDatabaseDAO _dao = (IMyAppsDatabaseDAO) SpringContextService.getPluginBean( MyAppsDatabasePlugin.PLUGIN_NAME,
            MyAppsDatabaseConstants.BEAN_MYAPPS_DATABASE_DAO );

    /**
     * Private constructor
     */
    private MyAppsDatabaseHome( )
    {
    }

    /**
     * Get a new primary key
     *
     * @param plugin
     *            {@link Plugin}
     * @return a new primary key
     */
    public static int newPrimaryKey( Plugin plugin )
    {
        return _dao.newPrimaryKey( plugin );
    }

    /**
     * Creation of an instance of myApps
     *
     * @param myApp
     *            The instance of the MyApps which contains the informations to store
     * @param plugin
     *            the Plugin
     */
    public static void create( MyAppsDatabase myApp, Plugin plugin )
    {
        _dao.insert( myApp, plugin );
    }

    /**
     * Update of the myApps which is specified in parameter
     *
     * @param myApp
     *            The instance of the MyApps which contains the data to store
     * @param bUpdateIcon
     *            true if the icon must also be updated, false otherwise
     * @param plugin
     *            the Plugin
     */
    public static void update( MyAppsDatabase myApp, boolean bUpdateIcon, Plugin plugin )
    {
        _dao.store( myApp, bUpdateIcon, plugin );
    }

    /**
     * Remove the myApps whose identifier is specified in parameter
     *
     * @param nMyAppId
     *            The myApp Id
     * @param plugin
     *            the Plugin
     */
    public static void remove( int nMyAppId, Plugin plugin )
    {
        _dao.delete( nMyAppId, plugin );
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
    public static MyApps findByPrimaryKey( int nMyAppId, Plugin plugin )
    {
        return _dao.load( nMyAppId, plugin );
    }

    /**
     * Loads a list of all myApps
     *
     * @param filter
     *            the app filter
     * @param plugin
     *            the Plugin
     * @return the collection which contains the data of all the myApps
     */
    public static List<MyApps> selectMyAppsList( MyAppsDatabaseFilter filter, Plugin plugin )
    {
        return _dao.selectMyAppsList( filter, plugin );
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
    public static List<MyApps> getMyAppsListByFilter( MyAppsDatabaseFilter filter, boolean bIsAscSort, Plugin plugin )
    {
        return _dao.selectMyAppsListByFilter( filter, bIsAscSort, plugin );
    }

    /**
     * Loads all the myapps belonging to a user ordered by the order specify by the user
     *
     * @param strUserName
     *            the name of the user
     * @param plugin
     *            the Plugin
     * @return the list which contains the myApps of the user ordered
     */
    public static List<MyApps> getMyAppsListByUser( String strUserName, Plugin plugin )
    {
        return _dao.selectMyAppsListByUser( strUserName, plugin );
    }

    /**
     * Get the image resource
     *
     * @param nMyAppId
     *            The myApp Id
     * @param plugin
     *            the Plugin
     * @return ImageResource
     */
    public static ImageResource getImageResource( int nMyAppId, Plugin plugin )
    {
        return _dao.getIconResource( nMyAppId, plugin );
    }

    /**
     * Get the list of myApps
     *
     * @param plugin
     *            {@link Plugin}
     * @return a list of {@link MyApps}
     */
    public static ReferenceList getMyAppsList( Plugin plugin )
    {
        return _dao.getMyAppsList( plugin );
    }

    /**
     * Check if the given MyApp Id has an icon or not
     *
     * @param nMyAppId
     *            the MyApp Id
     * @param plugin
     *            {@link Plugin}
     * @return true if it has an icon, false otherwise
     */
    public static boolean hasIcon( int nMyAppId, Plugin plugin )
    {
        return _dao.hasIcon( nMyAppId, plugin );
    }
}
