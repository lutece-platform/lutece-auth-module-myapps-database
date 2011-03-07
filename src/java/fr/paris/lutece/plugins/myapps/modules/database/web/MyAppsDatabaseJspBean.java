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
package fr.paris.lutece.plugins.myapps.modules.database.web;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseResourceIdService;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseService;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * MyAppsDatabaseJspBean
 *
 */
public class MyAppsDatabaseJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_MYAPPS_DATABASE_MANAGEMENT = "MYAPPS_DATABASE_MANAGEMENT";

    // TEMPLATES
    private static final String TEMPLATE_MYAPPS = "admin/plugins/myapps/modules/database/manage_myapps.html";
    private static final String TEMPLATE_CREATE_APPLICATION = "admin/plugins/myapps/modules/database/create_myapp.html";
    private static final String TEMPLATE_MODIFY_APPLICATION = "admin/plugins/myapps/modules/database/modify_myapp.html";

    // JSP
    private static final String JSP_DO_REMOVE_MYAPP = "jsp/admin/plugins/myapps/modules/database/DoRemoveMyApp.jsp";

    //Variables
    private int _nItemsPerPage;
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( MyAppsDatabaseConstants.PROPERTY_DEFAULT_ITEMS_PER_PAGE,
            50 );
    private String _strCurrentPageIndex;

    /**
     * Constructor
     */
    public MyAppsDatabaseJspBean(  )
    {
    }

    /**
     * Returns the list of myapps
     *
     * @param request The Http request
     * @return the myapps list
     */
    public String getManageMyApps( HttpServletRequest request )
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MYAPPS );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        List<MyApps> listMyApps = MyAppsDatabaseService.getInstance(  ).selectMyAppsList( getPlugin(  ) );
        LocalizedPaginator paginator = new LocalizedPaginator( listMyApps, _nItemsPerPage, getHomeUrl( request ),
                MyAppsDatabaseConstants.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MyAppsDatabaseConstants.MARK_PAGINATOR, paginator );
        model.put( MyAppsDatabaseConstants.MARK_NB_ITEMS_PER_PAGE, String.valueOf( _nItemsPerPage ) );
        model.put( MyAppsDatabaseConstants.MARK_MYAPPS_LIST, paginator.getPageItems(  ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_CREATE_MYAPP,
            RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE, getUser(  ) ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSIONS_LIST,
            MyAppsDatabaseService.getInstance(  ).getMyAppsPermissions( listMyApps, getUser(  ) ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_MODIFY_MYAPP,
            MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_DELETE_MYAPP,
            MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MYAPPS, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Process the confirmation of the removal of an application
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String getConfirmRemoveMyApp( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            UrlItem url = new UrlItem( JSP_DO_REMOVE_MYAPP );
            url.addParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID,
                request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID ) );

            strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_CONFIRM_REMOVE_MYAPP,
                    url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR,
                    AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Handles the removal form of a myapp
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage applications
     * @throws AccessDeniedException if the current user has not the permission to remove
     */
    public String doRemoveMyApp( HttpServletRequest request )
        throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, strMyAppId,
                        MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE, getUser(  ) ) )
            {
                throw new AccessDeniedException(  );
            }

            int nAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabaseService.getInstance(  ).remove( nAppId, getPlugin(  ) );

            return getHomeUrl( request );
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR,
                    AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    /**
     * Returns the form to update a myapp
     *
     * @param request The Http request
     * @return The HTML form to update info
     * @throws AccessDeniedException if the current user has not the permission to modify
     */
    public String getModifyMyApp( HttpServletRequest request )
        throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MODIFY );

        String strHtml = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, strMyAppId,
                        MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, getUser(  ) ) )
            {
                throw new AccessDeniedException(  );
            }

            int nMyAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance(  )
                                                                         .findByPrimaryKey( nMyAppId, getPlugin(  ) );

            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( MyAppsDatabaseConstants.MARK_MYAPP, myApp );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_APPLICATION, getLocale(  ), model );

            strHtml = getAdminPage( template.getHtml(  ) );
        }
        else
        {
            getManageMyApps( request );
        }

        return strHtml;
    }

    /**
     * Returns the form to create a myapp
     *
     * @param request The Http request
     * @return The HTML form to update info
     * @throws AccessDeniedException if the current user has not the permission to create
     */
    public String getCreateMyApp( HttpServletRequest request )
        throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_CREATE );

        if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE, getUser(  ) ) )
        {
            throw new AccessDeniedException(  );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_APPLICATION, getLocale(  ), null );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the myapp creation
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException if the current user has not the permission to create
     */
    public String doCreateMyApp( HttpServletRequest request )
        throws AccessDeniedException
    {
        if ( !RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE, getUser(  ) ) )
        {
            throw new AccessDeniedException(  );
        }

        String strJspUrl = StringUtils.EMPTY;
        String strAppName = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_NAME );
        String strUrl = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_URL );
        String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_PASSWORD );
        String strCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_NAME );
        String strCodeHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE_HEADING );
        String strData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_FIELD );
        String strDataHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_FIELD_HEADING );
        String strDescription = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_DESCRIPTION );

        // Mandatory fields
        if ( StringUtils.isNotBlank( strCode ) && StringUtils.isNotBlank( strPassword ) &&
                StringUtils.isNotBlank( strUrl ) && StringUtils.isNotBlank( strAppName ) )
        {
            // create the multipart request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MyAppsDatabase myApp = new MyAppsDatabase(  );
            myApp.setName( strAppName );
            myApp.setUrl( strUrl );
            myApp.setPassword( strPassword );
            myApp.setCode( strCode );
            myApp.setCodeHeading( strCodeHeading );
            myApp.setData( strData );
            myApp.setDataHeading( strDataHeading );
            myApp.setDescription( strDescription );

            try
            {
                FileItem itemIcon = multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON );

                if ( itemIcon != null )
                {
                    byte[] bytes = itemIcon.get(  );
                    String strMimeType = itemIcon.getContentType(  );
                    myApp.setIconContent( bytes );
                    myApp.setIconMimeType( strMimeType );
                }
                else
                {
                    myApp.setIconContent( null );
                    myApp.setIconMimeType( null );
                }

                MyAppsDatabaseService.getInstance(  ).create( myApp, getPlugin(  ) );
            }
            catch ( Exception e )
            {
                AppLogService.error( e.getMessage(  ), e );
            }

            strJspUrl = getHomeUrl( request );
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    /**
     * Process the myapp creation
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException if the current user has not the permission to modify
     */
    public String doModifyMyApp( HttpServletRequest request )
        throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                        MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, getUser(  ) ) )
            {
                throw new AccessDeniedException(  );
            }

            String strAppName = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_NAME );
            String strUrl = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_URL );
            String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_PASSWORD );
            String strCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_NAME );
            String strCodeHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE_HEADING );
            String strData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_FIELD );
            String strDataHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_USER_FIELD_HEADING );
            String strDescription = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_DESCRIPTION );

            if ( StringUtils.isNotBlank( strCode ) && StringUtils.isNotBlank( strPassword ) &&
                    StringUtils.isNotBlank( strUrl ) && StringUtils.isNotBlank( strAppName ) )
            {
                int nMyAppId = Integer.parseInt( strMyAppId );

                // create the multipart request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

                MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance(  )
                                                                             .findByPrimaryKey( nMyAppId, getPlugin(  ) );
                myApp.setName( strAppName );
                myApp.setUrl( strUrl );
                myApp.setPassword( strPassword );
                myApp.setCode( strCode );
                myApp.setCodeHeading( strCodeHeading );
                myApp.setData( strData );
                myApp.setDataHeading( strDataHeading );
                myApp.setDescription( strDescription );

                boolean bUpdateImage = ( multiRequest.getParameter( MyAppsDatabaseConstants.PARAMETER_UPDATE_FILE ) != null );

                if ( bUpdateImage && ( multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON ) != null ) )
                {
                    try
                    {
                        FileItem itemIcon = multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON );

                        if ( itemIcon != null )
                        {
                            byte[] bytes = itemIcon.get(  );
                            String strMimeType = itemIcon.getContentType(  );
                            myApp.setIconContent( bytes );
                            myApp.setIconMimeType( strMimeType );
                        }
                    }
                    catch ( Exception e )
                    {
                        AppLogService.error( e.getMessage(  ), e );
                    }
                }

                MyAppsDatabaseService.getInstance(  ).update( myApp, bUpdateImage, getPlugin(  ) );

                return getHomeUrl( request );
            }
            else
            {
                strJspUrl = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS,
                        AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR,
                    AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }
}
