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

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;


/**
 * 
 * IMyAppsDatabaseDAO
 * 
 */
public interface IMyAppsDatabaseDAO
{
    /**
     * Insert a new record in the table.
     *
     * @param myApp instance of the MyApps object to insert
     * @param plugin the Plugin
     */
    void insert( MyAppsDatabase myApp, Plugin plugin );

    /**
     * Update the record in the table
     *
     * @param myApp the reference of the MyApps
     * @param bUpdateIcon true if the icon must also be updated, false otherwise
     * @param plugin the Plugin
     */
    void store( MyAppsDatabase myApp, boolean bUpdateIcon, Plugin plugin );

    /**
     * Delete a record from the table
     *
     * @param nMyAppId int identifier of the MyApps to delete
     * @param plugin the Plugin
     */
    void delete( int nMyAppId, Plugin plugin );

    /**
     * load the data of the right from the table
     *
     * @param nMyAppId The identifier of the myApps
     * @param plugin the Plugin
     * @return The instance of the myApps
     */
    MyApps load( int nMyAppId, Plugin plugin );

    /**
     * Loads all the myapps
     *
     * @param plugin the Plugin
     * @return the list which contains the myApps
     */
    List<MyApps> selectMyAppsList( Plugin plugin );

    /**
     * Loads all the myapps belonging to a user
     *
     * @param strUserName the user name
     * @param plugin the Plugin
     * @return the list which contains the myApps
     */
    List<MyApps> selectMyAppsListByUser( String strUserName, Plugin plugin );

    /**
     * Loads the icon representing the favorite application
     *
     * @param nMyAppId int identifier of the MyApps to fetch
     * @param plugin the Plugin
     * @return the image resource
     */
    ImageResource getIconResource( int nMyAppId, Plugin plugin );

    /**
     * Get the list of my apps
     *
     * @param plugin {@link Plugin}
     * @return a list of {@link MyAppsDatabase}
     */
    ReferenceList getMyAppsList( Plugin plugin );

    /**
     * Check if the Myapps has an icon or not
     * 
     * @param nMyAppId the ID of the MyApp
     * @param plugin {@link Plugin}
     * @return true if it has an icon, false otherwise
     */
    boolean hasIcon( int nMyAppId, Plugin plugin );
}
