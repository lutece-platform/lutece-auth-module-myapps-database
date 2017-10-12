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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;

/**
 *
 * MyAppsDatabaseUserTest
 *
 */
public class MyAppsDatabaseUserTest extends LuteceTestCase
{
    private final static int IDAPPLICATION1 = 1;
    private final static int IDAPPLICATION2 = 2;
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String STOREDUSERDATA1 = "StoredUserData1";
    private final static String STOREDUSERDATA2 = "StoredUserData2";
    private final static String STOREDUSERNAME1 = "StoredUserName1";
    private final static String STOREDUSERNAME2 = "StoredUserName2";
    private final static String STOREDUSERPASSWORD1 = "StoredUserPassword1";
    private final static String STOREDUSERPASSWORD2 = "StoredUserPassword2";
    private final Plugin _plugin = PluginService.getPlugin( "myapps-database" );

    /**
     * Test business of class fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseUser
     */
    public void testBusiness( )
    {
        // Initialize an object
        MyAppsDatabaseUser myAppUser = new MyAppsDatabaseUser( );
        myAppUser.setMyAppsUserId( MyAppsDatabaseUserHome.newPrimaryKey( _plugin ) );
        myAppUser.setIdApplication( IDAPPLICATION1 );
        myAppUser.setName( NAME1 );
        myAppUser.setStoredUserData( STOREDUSERDATA1 );
        myAppUser.setStoredUserName( STOREDUSERNAME1 );
        myAppUser.setStoredUserPassword( STOREDUSERPASSWORD1 );

        // Create test
        MyAppsDatabaseUserHome.create( myAppUser, _plugin );

        MyAppsDatabaseUser myAppUserStored = (MyAppsDatabaseUser) MyAppsDatabaseUserHome.getCredentials( myAppUser.getMyAppsUserId( ), _plugin );
        assertEquals( myAppUserStored.getMyAppsUserId( ), myAppUser.getMyAppsUserId( ) );
        assertEquals( myAppUserStored.getIdApplication( ), myAppUser.getIdApplication( ) );
        assertEquals( myAppUserStored.getName( ), myAppUser.getName( ) );
        assertEquals( myAppUserStored.getStoredUserData( ), myAppUser.getStoredUserData( ) );
        System.out.println( "stored : " + myAppUserStored.getStoredUserName( ) );
        System.out.println( "actual : " + myAppUser.getStoredUserName( ) );
        assertEquals( myAppUserStored.getStoredUserName( ), myAppUser.getStoredUserName( ) );
        assertEquals( myAppUserStored.getStoredUserPassword( ), myAppUser.getStoredUserPassword( ) );

        // Update test
        myAppUser.setIdApplication( IDAPPLICATION2 );
        myAppUser.setName( NAME2 );
        myAppUser.setStoredUserData( STOREDUSERDATA2 );
        myAppUser.setStoredUserName( STOREDUSERNAME2 );
        myAppUser.setStoredUserPassword( STOREDUSERPASSWORD2 );
        MyAppsDatabaseUserHome.update( myAppUser, _plugin );
        myAppUserStored = (MyAppsDatabaseUser) MyAppsDatabaseUserHome.getCredentials( myAppUser.getMyAppsUserId( ), _plugin );
        assertEquals( myAppUserStored.getMyAppsUserId( ), myAppUser.getMyAppsUserId( ) );
        assertEquals( myAppUserStored.getIdApplication( ), myAppUser.getIdApplication( ) );
        assertEquals( myAppUserStored.getName( ), myAppUser.getName( ) );
        assertEquals( myAppUserStored.getStoredUserData( ), myAppUser.getStoredUserData( ) );
        assertEquals( myAppUserStored.getStoredUserName( ), myAppUser.getStoredUserName( ) );
        assertEquals( myAppUserStored.getStoredUserPassword( ), myAppUser.getStoredUserPassword( ) );

        // List test
        MyAppsDatabaseUserHome.getMyAppsUsersList( _plugin );

        // Delete test
        MyAppsDatabaseUserHome.remove( myAppUser.getMyAppsUserId( ), _plugin );
        myAppUserStored = (MyAppsDatabaseUser) MyAppsDatabaseUserHome.getCredentials( myAppUser.getMyAppsUserId( ), _plugin );
        assertNull( myAppUserStored );
    }
}
