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
package fr.paris.lutece.plugins.myapps.modules.database.service;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;

import java.util.List;
import java.util.Map;


/**
 *
 * MyAppsDatabaseServiceTest
 *
 */
public class MyAppsDatabaseServiceTest extends LuteceTestCase
{
    private final Plugin _plugin = PluginService.getPlugin( "myapps-database" );

    /**
     * Test of getMyAppsPermissions method of class fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseService
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetMyAppsPermissions(  ) throws AccessDeniedException
    {
        System.out.println( "getMyAppsPermissions" );

        List<MyApps> listMyApps = (List<MyApps>) MyAppsDatabaseHome.selectMyAppsList(null, _plugin );
        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );

        MyAppsDatabaseService instance = new MyAppsDatabaseService(  );

        Map<String, Map<String, Boolean>> result = instance.getMyAppsPermissions( listMyApps, user );

        assertNotNull( result );
    }

    /**
     * Test method getResourceImageUrl of class fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseService
     */
    public void testGetResourceImageUrl(  )
    {
        System.out.println( "getImageResource" );

        List<MyApps> listMyApps = (List<MyApps>) MyAppsDatabaseHome.selectMyAppsList( null,_plugin );

        if ( listMyApps.size(  ) > 0 )
        {
            int nIndex = 0;
            boolean bBreak = false;

            while ( ( nIndex < listMyApps.size(  ) ) && !bBreak )
            {
                MyApps myApp = listMyApps.get( nIndex );

                if ( myApp.hasIcon(  ) )
                {
                    MyAppsDatabaseService instance = new MyAppsDatabaseService(  );

                    String result = instance.getResourceImageUrl( String.valueOf( myApp.getIdApplication(  ) ), _plugin );

                    assertNotNull( result );
                    bBreak = true;
                }

                nIndex++;
            }
        }
    }
}
