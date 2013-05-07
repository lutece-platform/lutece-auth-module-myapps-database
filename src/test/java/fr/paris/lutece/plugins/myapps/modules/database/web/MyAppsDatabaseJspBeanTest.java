/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.myapps.modules.database.web;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseHome;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.MokeHttpServletRequest;

import java.util.List;


/**
 *
 * MyAppsDatabaseJspBeanTest
 *
 */
public class MyAppsDatabaseJspBeanTest extends LuteceTestCase
{
    private final Plugin _plugin = PluginService.getPlugin( "myapps-database" );

    /**
     * Test of getManageMyApps method of class fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetManageMyApps(  ) throws AccessDeniedException
    {
        System.out.println( "getManageMyApps" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        MyAppsDatabaseJspBean instance = new MyAppsDatabaseJspBean(  );
        instance.init( request, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        String result = instance.getManageMyApps( request );

        assertNotNull( result );
    }

    /**
     * Test of getConfirmRemoveMyApp method of class fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetConfirmRemoveMyApp(  ) throws AccessDeniedException
    {
        System.out.println( "getConfirmRemoveMyApp" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        MyAppsDatabaseJspBean instance = new MyAppsDatabaseJspBean(  );
        instance.init( request, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        String result = instance.getConfirmRemoveMyApp( request );

        assertNotNull( result );
    }

    /**
     * Test of getModifyMyApp method of class fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetModifyMyApp(  ) throws AccessDeniedException
    {
        System.out.println( "getModifyMyApp" );

        List<MyApps> listMyApps = (List<MyApps>) MyAppsDatabaseHome.selectMyAppsList(null, _plugin );

        if ( listMyApps.size(  ) > 0 )
        {
            MyApps myApp = listMyApps.get( 0 );
            MokeHttpServletRequest request = new MokeHttpServletRequest(  );
            request.addMokeParameters( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID,
                String.valueOf( myApp.getIdApplication(  ) ) );

            AdminUser user = AdminUserHome.findUserByLogin( "admin" );
            user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
            request.registerAdminUserWithRigth( user, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

            MyAppsDatabaseJspBean instance = new MyAppsDatabaseJspBean(  );
            instance.init( request, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

            String result = instance.getModifyMyApp( request );

            assertNotNull( result );
        }
    }

    /**
     * Test of getCreateMyApp method of class fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean
     * @throws AccessDeniedException if the user has not the right
     */
    public void testGetCreateMyApp(  ) throws AccessDeniedException
    {
        System.out.println( "getCreateMyApp" );

        MokeHttpServletRequest request = new MokeHttpServletRequest(  );

        AdminUser user = AdminUserHome.findUserByLogin( "admin" );
        user.setRoles( AdminUserHome.getRolesListForUser( user.getUserId(  ) ) );
        request.registerAdminUserWithRigth( user, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        MyAppsDatabaseJspBean instance = new MyAppsDatabaseJspBean(  );
        instance.init( request, MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT );

        String result = instance.getCreateMyApp( request );

        assertNotNull( result );
    }
}
