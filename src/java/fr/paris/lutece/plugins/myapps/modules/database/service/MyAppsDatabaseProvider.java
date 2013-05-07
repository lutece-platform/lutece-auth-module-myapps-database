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
package fr.paris.lutece.plugins.myapps.modules.database.service;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseFilter;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseUser;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.plugins.myapps.service.MyAppsManager;
import fr.paris.lutece.plugins.myapps.service.MyAppsProvider;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * 
 * MyAppsDatabaseProvider
 * 
 */
public final class MyAppsDatabaseProvider implements MyAppsProvider {
	private static final String PROPERTY_PROVIDER_NAME = "module.myapps.database.page_myapps.provider.name";
	private static final String PROPERTY_DEFAULT_PROVIDER_NAME = "myapps-database.provider.defaultName";
	private static MyAppsDatabaseProvider _singleton;

	/**
	 * Constructor
	 */
	private MyAppsDatabaseProvider() {
	}

	/**
	 * Get the instance of {@link MyAppsDatabaseProvider}
	 * 
	 * @return an instance of {@link MyAppsDatabaseProvider}
	 */
	public static synchronized MyAppsDatabaseProvider getInstance() {
		if (_singleton == null) {
			_singleton = new MyAppsDatabaseProvider();
		}

		return _singleton;
	}

	/**
	 * Init the provider
	 */
	public void init() {
		getInstance().register();
	}

	/**
	 * Register the provider to the manager
	 */
	public void register() {
		MyAppsManager.getInstance().registerProvider(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPluginName() {
		return MyAppsDatabasePlugin.PLUGIN_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getProviderName() {
		return AppPropertiesService.getProperty(PROPERTY_DEFAULT_PROVIDER_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getProviderName(Locale locale) {
		return I18nService.getLocalizedString(PROPERTY_PROVIDER_NAME, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getResourceImage(String strMyAppId) {
		Plugin plugin = PluginService
				.getPlugin(MyAppsDatabasePlugin.PLUGIN_NAME);

		return MyAppsDatabaseService.getInstance().getResourceImageUrl(
				strMyAppId, plugin);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MyApps> getMyAppsListByUserName(String strUserName,
			boolean isAscSort) {
		Plugin plugin = PluginService
				.getPlugin(MyAppsDatabasePlugin.PLUGIN_NAME);
		MyAppsDatabaseFilter filter = new MyAppsDatabaseFilter();
		filter.setUserName(strUserName);
		return MyAppsDatabaseService.getInstance().getMyAppsListByFilter(
				filter, isAscSort, plugin);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MyApps> getMyAppsListByCategory(String strCodeCategory,
			boolean bIsAscSort) {
		// TODO Auto-generated method stub
    	List<MyApps> myApps=null;
        if( !MyAppsDatabaseCacheService.getInstance().isCacheEnable() || MyAppsDatabaseCacheService.getInstance().getFromCache(strCodeCategory)== null)
        {	
        	Plugin plugin = PluginService.getPlugin( MyAppsDatabasePlugin.PLUGIN_NAME );
            MyAppsDatabaseFilter filter=new MyAppsDatabaseFilter();
            filter.setCategory( strCodeCategory );
            myApps=MyAppsDatabaseService.getInstance(  ).getMyAppsListByFilter( filter, bIsAscSort, plugin );
            if( MyAppsDatabaseCacheService.getInstance().isCacheEnable() && myApps !=null && myApps.size()>0 )
            {
            	MyAppsDatabaseCacheService.getInstance().putInCache(strCodeCategory, myApps);	
            }
        }
        else
        {
          myApps=(List<MyApps>)MyAppsDatabaseCacheService.getInstance().getFromCache(strCodeCategory);	
        }
        return myApps;
        
        
        
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MyApps> getMyAppsListByUserNameAndCategory(String strUserName,
			String strCodeCategory, boolean bIsAscSort) {
		// TODO Auto-generated method stub
		Plugin plugin = PluginService
				.getPlugin(MyAppsDatabasePlugin.PLUGIN_NAME);
		MyAppsDatabaseFilter filter = new MyAppsDatabaseFilter();
		filter.setUserName(strUserName);
		filter.setCategory(strCodeCategory);
		return MyAppsDatabaseService.getInstance().getMyAppsListByFilter(
				filter, bIsAscSort, plugin);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLabelManageMyApps(Locale locale) {
		return I18nService.getLocalizedString(
				MyAppsDatabaseConstants.PROPERTY_LABEL_MANAGE_MYAPPS, locale);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUrlOpenMyApps(int nMyAppId, LuteceUser user) {
		Plugin plugin = PluginService
				.getPlugin(MyAppsDatabasePlugin.PLUGIN_NAME);
	
		MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService
				.getInstance().findByPrimaryKey(nMyAppId, plugin);
		String strUrl = StringUtils.EMPTY;

		if (  myApp != null ) {
			// If the label of data is blank, then the value of data has to
			// concatenated to the url
			// This way, the AdminUser can pass hidden parameters
			if (StringUtils.isBlank(myApp.getDataHeading())
					&& StringUtils.isNotBlank(myApp.getData())) {
				StringBuilder sbUrl = new StringBuilder(myApp.getUrl());

				if (myApp.getUrl().indexOf(
						MyAppsDatabaseConstants.QUESTION_MARK) == -1) {
					sbUrl.append(MyAppsDatabaseConstants.QUESTION_MARK);
				} else {
					sbUrl.append(MyAppsDatabaseConstants.AMPERSAND);
				}

				sbUrl.append(myApp.getData());
				strUrl = sbUrl.toString();
			} else {
				strUrl = myApp.getUrl();
			}
			UrlItem url = new UrlItem(strUrl);
			if( user != null )
			{
				
				String strUserName = user.getName();
				MyAppsDatabaseUser myAppsUser = (MyAppsDatabaseUser) MyAppsDatabaseService
						.getInstance().getCredential(nMyAppId, strUserName, plugin);
				
				//an myApp can be not associated to a user
				if(myAppsUser !=null)
				{
					
				// The login
					String strLoginFieldName = myApp.getCode();
					String strUserLogin = myAppsUser.getStoredUserName();
		
					// Password
					String strPasswordField = myApp.getPassword();
					String strUserPassword = myAppsUser.getStoredUserPassword();
		
					// Extra Field
					String strExtraField = myApp.getData();
					String strExtraFieldValue = myAppsUser.getStoredUserData();
		
					
					if (StringUtils.isNotBlank(myApp.getCode())) {
						url.addParameter(strLoginFieldName, strUserLogin);
					}
					if (StringUtils.isNotBlank(myApp.getPassword())) {
						url.addParameter(strPasswordField, strUserPassword);
					}
		
					// If the label of data is not blank, then it is an extra field the
					// LuteceUser has to fill
					if (StringUtils.isNotBlank(strExtraField)
							&& StringUtils.isNotBlank(myApp.getDataHeading())) {
						url.addParameter(strExtraField, strExtraFieldValue);
					}
				}
			}

			strUrl = url.getUrl();
		}

		return strUrl;
	}

}
