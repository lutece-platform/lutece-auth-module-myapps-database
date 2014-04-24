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
package fr.paris.lutece.plugins.myapps.modules.database.web;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.business.MyAppsUser;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseFilter;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseUser;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseService;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.plugins.myapps.service.MyAppsPlugin;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * MyAppsDatabaseApp
 *
 */
public class MyAppsDatabaseApp implements XPageApplication
{
    // TEMPLATES
    private static final String TEMPLATE_MYAPPS_MANAGE = "skin/plugins/myapps/modules/database/page_app_manage.html";
    private static final String TEMPLATE_MYAPPS_INSERT = "skin/plugins/myapps/modules/database/page_app_insert.html";
    private static final String TEMPLATE_MYAPPS_MODIFY = "skin/plugins/myapps/modules/database/page_app_modify.html";

    /**
     * Front Office application to manage myapps application
     *
     * @param request The request
     * @param nMode The mode
     * @param plugin The plugin
     * @return The Xpage
     * @throws SiteMessageException exception if some parameters are not correctly filled
     * @throws UserNotSignedException exception if the current user is not connected
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException, UserNotSignedException
    {
        LuteceUser user = getUser( request );
        XPage page = new XPage(  );
        page.setTitle( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE,
                request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_PAGE_PATH,
                request.getLocale(  ) ) );

        String strAction = request.getParameter( MyAppsDatabaseConstants.PARAMETER_ACTION );

        if ( StringUtils.isNotBlank( strAction ) )
        {
            if ( MyAppsDatabaseConstants.ACTION_MANAGE.equals( strAction ) )
            {
                page = getManageMyAppsPage( page, request, user, plugin );
            }
            else if ( MyAppsDatabaseConstants.ACTION_INSERT.equals( strAction ) )
            {
                page = getInsertMyAppsPage( page, request, plugin );
            }
            else if ( MyAppsDatabaseConstants.ACTION_MODIFY.equals( strAction ) )
            {
                page = getModifyMyAppsPage( page, request, user, plugin );
            }
            else if ( MyAppsDatabaseConstants.ACTION_DO_INSERT.equals( strAction ) )
            {
                doInsertMyApp( request, user, plugin );
                page = getManageMyAppsPage( page, request, user, plugin );
            }
            else if ( MyAppsDatabaseConstants.ACTION_DO_MODIFY.equals( strAction ) )
            {
                doModifyMyApp( request, user, plugin );
                page = getManageMyAppsPage( page, request, user, plugin );
            }
            else if ( MyAppsDatabaseConstants.ACTION_DO_REMOVE.equals( strAction ) )
            {
                doRemoveMyApp( request, user, plugin );
                page = getManageMyAppsPage( page, request, user, plugin );
            }
        }

        return page;
    }

    /**
     * Get the manage myApps interface
     *
     * @param page the {@link XPage}
     * @param request {@link HttpServletRequest}
     * @param user the current {@link LuteceUser}
     * @param plugin {@link Plugin}
     * @return a {@link XPage}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     */
    private XPage getManageMyAppsPage( XPage page, HttpServletRequest request, LuteceUser user, Plugin plugin )
        throws SiteMessageException
    {
    	
    	String strMyAppCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );
    	 
    	MyAppsDatabaseFilter filter=new MyAppsDatabaseFilter();
    	filter.setUserName(user.getName(  ));
    	filter.setCategory(strMyAppCategory);
    	List<MyApps> listEnabledMyApps = MyAppsDatabaseService.getInstance(  )
                                                              .getMyAppsListByFilter( filter, true, plugin );
        List<MyApps> listDisabledMyApps = MyAppsDatabaseService.getInstance(  ).selectMyAppsList( filter,plugin );
        listDisabledMyApps.removeAll( listEnabledMyApps );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MyAppsDatabaseConstants.MARK_ENABLED_MYAPPS_LIST, listEnabledMyApps );
        model.put( MyAppsDatabaseConstants.MARK_DISABLED_MYAPPS_LIST, listDisabledMyApps );
        model.put(MyAppsDatabaseConstants.MARK_MYAPP_CATEGORY, strMyAppCategory);
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYAPPS_MANAGE, request.getLocale(  ), model );
        page.setContent( template.getHtml(  ) );
        page.setTitle( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_MANAGE_PAGE_TITLE,
                request.getLocale(  ) ) );
        page.setPathLabel( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_MANAGE_PAGE_PATH,
                request.getLocale(  ) ) );

        return page;
    }

    /**
     * Get the insert myApps interface
     *
     * @param page the {@link XPage}
     * @param request {@link HttpServletRequest}
     * @param plugin {@link Plugin}
     * @return a {@link XPage}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     * @throws UserNotSignedException 
     */
    private XPage getInsertMyAppsPage( XPage page, HttpServletRequest request, Plugin plugin )
        throws SiteMessageException, UserNotSignedException
    {
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );
        String strMyAppCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );
    	
        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance(  ).findByPrimaryKey( nMyAppId, plugin );
            if ( myApp != null )
            {
            	// Check if the application does not need any login
            	if ( StringUtils.isBlank( myApp.getCode(  ) ) && StringUtils.isBlank( myApp.getPassword(  ) ) &&
            			( StringUtils.isBlank( myApp.getDataHeading(  ) ) || StringUtils.isBlank( myApp.getData(  ) ) && 
            					StringUtils.isNotBlank( myApp.getDataHeading(  ) ) ) )
                {
            		/*
            		 * 2 cases in which the application does not require any login 
            		 * 1) 	- The application code is empty
            		 * 		- The application password is empty
            		 * 		- The application data is empty
            		 * 2)	- The application code is empty
            		 * 		- The application password is empty
            		 * 		- The application code is not empty
            		 * 		- The application code heading is empty 
            		 * In this case, the application is directly inserted, and the user
            		 * is redirected to the myapps management page.
            		 */
                	LuteceUser user = getUser( request );
                	doInsertMyApp( request, user, plugin );
                	page = getManageMyAppsPage( page, request, user, plugin );
                }
                else
                {
                	/*
                	 * This case needs either a login, either a password, either a complementary 
                	 * data or a combination of those 3.
                	 */
                	Map<String, Object> model = new HashMap<String, Object>(  );
                    model.put( MyAppsDatabaseConstants.MARK_MYAPP, myApp );
                    model.put(MyAppsDatabaseConstants.MARK_MYAPP_CATEGORY, strMyAppCategory);
                    HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYAPPS_INSERT, request.getLocale(  ), model );
                    page.setContent( template.getHtml(  ) );
                    page.setTitle( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_INSERT_PAGE_TITLE,
                            request.getLocale(  ) ) );
                    page.setPathLabel( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_INSERT_PAGE_PATH,
                            request.getLocale(  ) ) );
                }
            }
            else
            {
            	SiteMessageService.setMessage( request, MyAppsDatabaseConstants.MESSAGE_ERROR, SiteMessage.TYPE_STOP );
            }
        }
        else
        {
            SiteMessageService.setMessage( request, MyAppsDatabaseConstants.MESSAGE_ERROR, SiteMessage.TYPE_STOP );
        }

        return page;
    }

    /**
     * Get the modify myApps interface
     *
     * @param page the {@link XPage}
     * @param request {@link HttpServletRequest}
     * @param user the current {@link LuteceUser}
     * @param plugin {@link Plugin}
     * @return a {@link XPage}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     */
    private XPage getModifyMyAppsPage( XPage page, HttpServletRequest request, LuteceUser user, Plugin plugin )
        throws SiteMessageException
    {
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );
        String strMyAppCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );
    	
        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppId = Integer.parseInt( strMyAppId );
            MyApps myApp = MyAppsDatabaseService.getInstance(  ).findByPrimaryKey( nMyAppId, plugin );
            MyAppsUser myAppUser = MyAppsDatabaseService.getInstance(  )
                                                        .getCredential( nMyAppId, user.getName(  ), plugin );

            if ( ( myApp != null ) && ( myAppUser != null ) )
            {
                Map<String, Object> model = new HashMap<String, Object>(  );
                model.put( MyAppsDatabaseConstants.MARK_MYAPP, myApp );
                model.put( MyAppsDatabaseConstants.MARK_MYAPP_USER, myAppUser );
                model.put(MyAppsDatabaseConstants.MARK_MYAPP_CATEGORY, strMyAppCategory);
                HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MYAPPS_MODIFY, request.getLocale(  ),
                        model );
                page.setContent( template.getHtml(  ) );
                page.setTitle( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_MODIFY_PAGE_TITLE,
                        request.getLocale(  ) ) );
                page.setPathLabel( I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_MODIFY_PAGE_PATH,
                        request.getLocale(  ) ) );
            }
            else
            {
                SiteMessageService.setMessage( request, MyAppsDatabaseConstants.MESSAGE_ERROR, SiteMessage.TYPE_STOP );
            }
        }
        else
        {
            SiteMessageService.setMessage( request, MyAppsDatabaseConstants.MESSAGE_ERROR, SiteMessage.TYPE_STOP );
        }

        return page;
    }

    /**
     * Do insert a myApps
     *
     * @param request {@link HttpServletRequest}
     * @param user the current {@link LuteceUser}
     * @param plugin {@link Plugin}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     */
    private void doInsertMyApp( HttpServletRequest request, LuteceUser user, Plugin plugin )
        throws SiteMessageException
    {
        MyAppsDatabaseUser myAppsUser = getMyAppsDatabaseUserInfo( request, user, plugin );
        if( myAppsUser != null )
        {
        	MyAppsDatabaseService.getInstance(  ).createMyAppUser( myAppsUser, plugin );
        }
    }

    /**
     * Do modify a myApps
     *
     * @param request {@link HttpServletRequest}
     * @param user the current {@link LuteceUser}
     * @param plugin {@link Plugin}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     */
    private void doModifyMyApp( HttpServletRequest request, LuteceUser user, Plugin plugin )
        throws SiteMessageException
    {
    	MyAppsDatabaseUser myAppsUser = getMyAppsDatabaseUserInfo( request, user, plugin );
    	if( myAppsUser != null )
        {
    		MyAppsDatabaseService.getInstance(  ).updateMyAppUser( myAppsUser, plugin );
        }
    }

    /**
     * Get MyAppsDatabaseUser
     * @param request {@link HttpServletRequest}
     * @param user the {@link LuteceUser}
     * @param plugin {@link Plugin}
     * @return a {@link MyAppsDatabaseUser}
     * @throws SiteMessageException exception if some parameters are not correctly filled
     */
    private MyAppsDatabaseUser getMyAppsDatabaseUserInfo( HttpServletRequest request, LuteceUser user, Plugin plugin )
    	throws SiteMessageException
    {
    	MyAppsDatabaseUser myAppsUser = null;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );
        String strUserLogin = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_LOGIN );
        String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_PASSWORD );
        String strExtraData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_EXTRA_DATA );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance(  )
                                                                         .findByPrimaryKey( nMyAppId, plugin );

            // Check mandatory fields
            if ( myApp != null && !( StringUtils.isNotBlank( myApp.getCode(  ) ) && StringUtils.isBlank( strUserLogin ) || 
            		StringUtils.isNotBlank( myApp.getPassword(  ) ) && StringUtils.isBlank( strPassword ) ) && 
            		( StringUtils.isNotBlank( myApp.getData(  ) ) && StringUtils.isNotBlank( strExtraData ) ) ||
                    StringUtils.isBlank( myApp.getData(  ) ) || StringUtils.isBlank( myApp.getDataHeading(  ) ) )
            {
                String strUserName = user.getName(  );
                myAppsUser = (MyAppsDatabaseUser) MyAppsDatabaseService.getInstance(  )
                                                                                          .getCredential( nMyAppId,
                        strUserName, plugin );

                if ( myAppsUser == null )
                {
                	myAppsUser = new MyAppsDatabaseUser(  );
                }
            	myAppsUser.setName( strUserName );
                myAppsUser.setIdApplication( nMyAppId );
                myAppsUser.setStoredUserName( ( strUserLogin != null ) ? strUserLogin : StringUtils.EMPTY );
                myAppsUser.setStoredUserPassword( ( strPassword != null ) ? strPassword : StringUtils.EMPTY );
                myAppsUser.setStoredUserData( ( strExtraData != null ) ? strExtraData : StringUtils.EMPTY );
            }
            else
            {
            	SiteMessageService.setMessage( request, Messages.MANDATORY_FIELDS, SiteMessage.TYPE_STOP );
            }
        }
        else
        {
            SiteMessageService.setMessage( request, Messages.MANDATORY_FIELDS, SiteMessage.TYPE_STOP );
        }
        
    	return myAppsUser;
    }

    /**
     * Do delete a myApps
     *
     * @param request {@link HttpServletRequest}
     * @param user the current {@link LuteceUser}
     * @param plugin {@link Plugin}
     */
    private void doRemoveMyApp( HttpServletRequest request, LuteceUser user, Plugin plugin )
    {
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );
        String strMyAppCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            int nMyAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabaseService.getInstance(  ).removeMyAppUser( nMyAppId, user.getName(  ), plugin );

            UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) );
            url.addParameter( MyAppsDatabaseConstants.PARAMETER_PAGE, MyAppsPlugin.PLUGIN_NAME );
            url.addParameter(MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY, strMyAppCategory);
        }
    }

    /**
     * Get the current user
     *
     * @param request {@link HttpServletRequest}
     * @return the current {@link LuteceUser}
     * @throws UserNotSignedException exception if the current user is not connected
     */
    private LuteceUser getUser( HttpServletRequest request )
        throws UserNotSignedException
    {
        LuteceUser user = SecurityService.getInstance(  ).getRemoteUser( request );

        if ( user == null )
        {
            throw new UserNotSignedException(  );
        }

        return user;
    }
}
