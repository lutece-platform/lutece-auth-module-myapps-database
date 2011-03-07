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
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;


/**
 *
* IMyAppsDatabaseUserDAO Interface
*
*/
public interface IMyAppsDatabaseUserDAO
{
    /**
     * Get a new primary key
     *
     * @param plugin {@link Plugin}
     * @return a new primary key
     */
    int newPrimaryKey( Plugin plugin );

    /**
     * Insert a new record in the table.
     *
     * @param myAppsUser instance of the MyAppsUser object to insert
     * @param plugin the Plugin
     */
    void insert( MyAppsDatabaseUser myAppsUser, Plugin plugin );

    /**
    * Update the record in the table
    *
    * @param myAppsUser the reference of the MyAppsUser
    * @param plugin the Plugin
    */
    void store( MyAppsDatabaseUser myAppsUser, Plugin plugin );

    /**
     * Delete a record from the table
     *
     * @param nMyAppsUserId int identifier of the MyAppsUser to delete
     * @param plugin the Plugin
     */
    void delete( int nMyAppsUserId, Plugin plugin );

    /**
     * Delete a record from the table
     *
     * @param nMyAppId the id of the MyApp
     * @param strUserName the user name
     * @param plugin the Plugin
     */
    void delete( int nMyAppId, String strUserName, Plugin plugin );

    /**
     * load the data of the right from the table
     *
     * @param nMyAppsUserId The identifier of the myAppsUser
     * @param plugin the Plugin
     * @return The instance of the myAppsUser
     */
    MyAppsUser load( int nMyAppsUserId, Plugin plugin );

    /**
    * Loads the data of all the myAppsUsers and returns them in form of a collection
    *
    * @param plugin the Plugin
    * @return the collection which contains the data of all the myAppsUsers
    */
    Collection<MyAppsUser> selectMyAppsUsersList( Plugin plugin );

    /**
     * Get the credentials information
     *
     * @param nMyAppId the application ID
     * @param strUserName the user name
     * @param plugin {@link Plugin}
     * @return a {@link MyAppsDatabaseUser}
     */
    MyAppsUser getCredentials( int nMyAppId, String strUserName, Plugin plugin );

    /**
     * Get the credentials information
     *
     * @param nMyAppsUserId the application user ID
     * @param plugin {@link Plugin}
     * @return a {@link MyAppsDatabaseUser}
     */
    MyAppsDatabaseUser getCredentials( int nMyAppsUserId, Plugin plugin );
}
