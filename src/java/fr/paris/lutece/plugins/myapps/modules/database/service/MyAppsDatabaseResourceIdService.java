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
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

/**
 *
 * MyAppsDatabaseResourceIdService
 *
 */
public class MyAppsDatabaseResourceIdService extends ResourceIdService
{
    public static final String PERMISSION_CREATE_MYAPPS_DATABASE = "CREATE_MYAPPS_DATABASE";
    public static final String PERMISSION_MODIFY_MYAPPS_DATABASE = "MODIFY_MYAPPS_DATABASE";
    public static final String PERMISSION_DELETE_MYAPPS_DATABASE = "DELETE_MYAPPS_DATABASE";
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "module.myapps.database.permission.ressourceType";
    private static final String PROPERTY_LABEL_CREATE = "module.myapps.database.permission.createMyAppsDatabase";
    private static final String PROPERTY_LABEL_MODIFY = "module.myapps.database.permission.modifyMyAppsDatabase";
    private static final String PROPERTY_LABEL_DELETE = "module.myapps.database.permission.deleteMyAppsDatabase";

    /**
     * Constructor
     */
    public MyAppsDatabaseResourceIdService( )
    {
        setPluginName( MyAppsDatabasePlugin.PLUGIN_NAME );
    }

    /**
     * Initializes the service
     */
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( MyAppsDatabaseResourceIdService.class.getName( ) );
        rt.setPluginName( MyAppsDatabasePlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( MyAppsDatabase.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission( );
        p.setPermissionKey( PERMISSION_CREATE_MYAPPS_DATABASE );
        p.setPermissionTitleKey( PROPERTY_LABEL_CREATE );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( PERMISSION_MODIFY_MYAPPS_DATABASE );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( PERMISSION_DELETE_MYAPPS_DATABASE );
        p.setPermissionTitleKey( PROPERTY_LABEL_DELETE );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Returns a list of resource ids
     *
     * @param locale
     *            The current locale
     * @return A list of resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        Plugin plugin = PluginService.getPlugin( MyAppsDatabasePlugin.PLUGIN_NAME );

        return MyAppsDatabaseService.getInstance( ).getMyApps( plugin );
    }

    /**
     * Returns the Title of a given resource
     *
     * @param strMyAppId
     *            The Id of the resource
     * @param locale
     *            The current locale
     * @return The Title of a given resource
     */
    public String getTitle( String strMyAppId, Locale locale )
    {
        String strTitle = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppsId = Integer.parseInt( strMyAppId );
            Plugin plugin = PluginService.getPlugin( getPluginName( ) );
            MyApps myApps = MyAppsDatabaseService.getInstance( ).findByPrimaryKey( nMyAppsId, plugin );

            if ( myApps != null )
            {
                strTitle = myApps.getName( );
            }
        }

        return strTitle;
    }
}
