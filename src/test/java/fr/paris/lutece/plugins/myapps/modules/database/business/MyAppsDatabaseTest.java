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

import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;


/**
 *
 * MyAppsDatabaseTest
 *
 */
public class MyAppsDatabaseTest extends LuteceTestCase
{
    private final static String CODE1 = "Code1";
    private final static String CODE2 = "Code2";
    private final static String CODEHEADING1 = "CodeHeading1";
    private final static String CODEHEADING2 = "CodeHeading2";
    private final static String DATA1 = "Data1";
    private final static String DATA2 = "Data2";
    private final static String DATAHEADING1 = "DataHeading1";
    private final static String DATAHEADING2 = "DataHeading2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";
    private final static byte[] ICONCONTENT1 = { 1 };
    private final static byte[] ICONCONTENT2 = { 2 };
    private final static String ICONMIMETYPE1 = "IconMimeType1";
    private final static String ICONMIMETYPE2 = "IconMimeType2";
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String PASSWORD1 = "Password1";
    private final static String PASSWORD2 = "Password2";
    private final static String URL1 = "Url1";
    private final static String URL2 = "Url2";
    private final Plugin _plugin = PluginService.getPlugin( "myapps-database" );

    /**
     * Test business of class fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase
     */
    public void testBusiness(  )
    {
        // Initialize an object
        MyAppsDatabase myApp = new MyAppsDatabase(  );
        myApp.setIdApplication( MyAppsDatabaseHome.newPrimaryKey( _plugin ) );
        myApp.setCode( CODE1 );
        myApp.setCodeHeading( CODEHEADING1 );
        myApp.setData( DATA1 );
        myApp.setDataHeading( DATAHEADING1 );
        myApp.setDescription( DESCRIPTION1 );
        myApp.setIconContent( ICONCONTENT1 );
        myApp.setIconMimeType( ICONMIMETYPE1 );
        myApp.setName( NAME1 );
        myApp.setPassword( PASSWORD1 );
        myApp.setUrl( URL1 );

        // Create test
        MyAppsDatabaseHome.create( myApp, _plugin );

        MyAppsDatabase myAppStored = (MyAppsDatabase) MyAppsDatabaseHome.findByPrimaryKey( myApp.getIdApplication(  ),
                _plugin );
        ImageResource img = MyAppsDatabaseHome.getImageResource( myAppStored.getIdApplication(  ), _plugin );
        myAppStored.setIconContent( img.getImage(  ) );
        assertEquals( myAppStored.getIdApplication(  ), myApp.getIdApplication(  ) );
        assertEquals( myAppStored.getCode(  ), myApp.getCode(  ) );
        assertEquals( myAppStored.getCodeHeading(  ), myApp.getCodeHeading(  ) );
        assertEquals( myAppStored.getData(  ), myApp.getData(  ) );
        assertEquals( myAppStored.getDataHeading(  ), myApp.getDataHeading(  ) );
        assertEquals( myAppStored.getDescription(  ), myApp.getDescription(  ) );

        for ( int i = 0; i < myAppStored.getIconContent(  ).length; i++ )
        {
            assertEquals( myAppStored.getIconContent(  )[i], myApp.getIconContent(  )[i] );
        }

        assertEquals( myAppStored.getIconMimeType(  ), myApp.getIconMimeType(  ) );
        assertEquals( myAppStored.getName(  ), myApp.getName(  ) );
        assertEquals( myAppStored.getPassword(  ), myApp.getPassword(  ) );
        assertEquals( myAppStored.getUrl(  ), myApp.getUrl(  ) );

        // Update test
        myApp.setCode( CODE2 );
        myApp.setCodeHeading( CODEHEADING2 );
        myApp.setData( DATA2 );
        myApp.setDataHeading( DATAHEADING2 );
        myApp.setDescription( DESCRIPTION2 );
        myApp.setIconContent( ICONCONTENT2 );
        myApp.setIconMimeType( ICONMIMETYPE2 );
        myApp.setName( NAME2 );
        myApp.setPassword( PASSWORD2 );
        myApp.setUrl( URL2 );
        MyAppsDatabaseHome.update( myApp, true, _plugin );
        myAppStored = (MyAppsDatabase) MyAppsDatabaseHome.findByPrimaryKey( myApp.getIdApplication(  ), _plugin );
        img = MyAppsDatabaseHome.getImageResource( myAppStored.getIdApplication(  ), _plugin );
        myAppStored.setIconContent( img.getImage(  ) );
        assertEquals( myAppStored.getIdApplication(  ), myApp.getIdApplication(  ) );
        assertEquals( myAppStored.getCode(  ), myApp.getCode(  ) );
        assertEquals( myAppStored.getCodeHeading(  ), myApp.getCodeHeading(  ) );
        assertEquals( myAppStored.getData(  ), myApp.getData(  ) );
        assertEquals( myAppStored.getDataHeading(  ), myApp.getDataHeading(  ) );
        assertEquals( myAppStored.getDescription(  ), myApp.getDescription(  ) );

        for ( int i = 0; i < myAppStored.getIconContent(  ).length; i++ )
        {
            assertEquals( myAppStored.getIconContent(  )[i], myApp.getIconContent(  )[i] );
        }

        assertEquals( myAppStored.getIconMimeType(  ), myApp.getIconMimeType(  ) );
        assertEquals( myAppStored.getName(  ), myApp.getName(  ) );
        assertEquals( myAppStored.getPassword(  ), myApp.getPassword(  ) );
        assertEquals( myAppStored.getUrl(  ), myApp.getUrl(  ) );

        // List test
        MyAppsDatabaseHome.getMyAppsList( _plugin );

        // Delete test
        MyAppsDatabaseHome.remove( myApp.getIdApplication(  ), _plugin );
        myAppStored = (MyAppsDatabase) MyAppsDatabaseHome.findByPrimaryKey( myApp.getIdApplication(  ), _plugin );
        assertNull( myAppStored );
    }
}
