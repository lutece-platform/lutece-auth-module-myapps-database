/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import java.util.Collection;

import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabasePlugin;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;


/**
 *
 * MyAppsDatabaseCategoryHome
 *
 */
public final class MyAppsDatabaseCategoryHome
{
    // Static variable pointed at the DAO instance
    private static IMyAppsDatabaseCategoryDAO _dao = (IMyAppsDatabaseCategoryDAO) SpringContextService.getPluginBean( MyAppsDatabasePlugin.PLUGIN_NAME,
            MyAppsDatabaseConstants.BEAN_MYAPPS_DATABASE_CATEGORY_DAO );

    /**
     * Private constructor
     */
    private MyAppsDatabaseCategoryHome(  )
    {
    }

    
    /**
     * Creation of an instance of myAppsCategory
     *
     * @param myAppsCategory The instance of the category which contains the informations to store
     * @param plugin the Plugin
     */
    public static void create( MyAppsDatabaseCategory myAppsCategory, Plugin plugin )
    {
        _dao.insert( myAppsCategory, plugin );
    }

    /**
     * Update of the myAppsCategory which is specified in parameter
     *
     * @param myAppsCategoryr The instance of the myAppsCategory which contains the data to store
     * @param plugin the Plugin
     */
    public static void update( MyAppsDatabaseCategory myAppsCategory, Plugin plugin )
    {
        _dao.store( myAppsCategory, plugin );
    }

    /**
     * Remove the category whose identifier is specified in parameter
     *
     * @param strCodeCategory the category code
     * @param plugin the Plugin
     */
    public static void remove( String strCodeCategory, Plugin plugin )
    {
        _dao.delete( strCodeCategory, plugin );
    }

    
    /**
     * Returns an instance of a myAppsCategory whose identifier is specified in parameter
     *
     * @param nMyAppsUserId The myAppsUser primary key
     * @param plugin the Plugin
     * @return an instance of MyAppsUser
     */
    public static MyAppsDatabaseCategory findByPrimaryKey( String strCodeCategory, Plugin plugin )
    {
        return _dao.load( strCodeCategory, plugin );
    }

    /**
     * Loads the data of all the myAppsCategory and returns them in form of a collection
     *
     * @param plugin the Plugin
     * @return the collection which contains the data of all the myAppsCategory
     */
    public static Collection<MyAppsDatabaseCategory> getMyAppsCategoryList( Plugin plugin )
    {
        return _dao.selectCategoryList( plugin );
    }
    
    
    public static ReferenceList getMyAppsCategoryRefList( Plugin plugin )
    {
    	
    	Collection<MyAppsDatabaseCategory> listCategory=getMyAppsCategoryList(plugin);
    	ReferenceList refList=new ReferenceList();
    	ReferenceItem referenceItem;
    	referenceItem=new ReferenceItem();
    	referenceItem.setCode("");
    	referenceItem.setName("");
    	refList.add(referenceItem);
    	for(MyAppsDatabaseCategory category:listCategory)
    	{
    		referenceItem=new ReferenceItem();
    		referenceItem.setCode(category.getCodeCategory());
    		referenceItem.setName(category.getLibelleCategory());
    		refList.add(referenceItem);
    	}
    	return refList;
    	
    }
    


}
