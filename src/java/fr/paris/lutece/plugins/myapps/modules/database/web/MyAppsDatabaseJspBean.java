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
package fr.paris.lutece.plugins.myapps.modules.database.web;

import fr.paris.lutece.plugins.myapps.business.MyApps;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabase;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseCategory;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseCategoryHome;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseFilter;
import fr.paris.lutece.plugins.myapps.modules.database.business.MyAppsDatabaseHome;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseResourceIdService;
import fr.paris.lutece.plugins.myapps.modules.database.service.MyAppsDatabaseService;
import fr.paris.lutece.plugins.myapps.modules.database.utils.constants.MyAppsDatabaseConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
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

    // Variables
    private int _nItemsPerPage;
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( MyAppsDatabaseConstants.PROPERTY_DEFAULT_ITEMS_PER_PAGE, 50 );
    private String _strCurrentPageIndex;
    private String _strCategoryFilter;

    /**
     * Constructor
     */
    public MyAppsDatabaseJspBean( )
    {
    }

    /**
     * Returns the list of myapps
     * 
     * @param request
     *            The Http request
     * @return the myapps list
     */
    public String getManageMyApps( HttpServletRequest request )
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MYAPPS );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage, _nDefaultItemsPerPage );

        _strCategoryFilter = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY ) != null ? request
                .getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY ) : _strCategoryFilter;
        MyAppsDatabaseFilter filter = new MyAppsDatabaseFilter( );
        filter.setCategory( _strCategoryFilter );
        List<MyApps> listMyApps = MyAppsDatabaseService.getInstance( ).selectMyAppsList( filter, getPlugin( ) );
        LocalizedPaginator paginator = new LocalizedPaginator( listMyApps, _nItemsPerPage, getHomeUrl( request ), MyAppsDatabaseConstants.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndex, getLocale( ) );
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MyAppsDatabaseConstants.MARK_PAGINATOR, paginator );
        model.put( MyAppsDatabaseConstants.MARK_NB_ITEMS_PER_PAGE, String.valueOf( _nItemsPerPage ) );
        model.put( MyAppsDatabaseConstants.MARK_MYAPPS_LIST, paginator.getPageItems( ) );
        model.put( MyAppsDatabaseConstants.MARK_MYAPPS_CATEGORY_LIST, MyAppsDatabaseCategoryHome.getMyAppsCategoryRefList( getPlugin( ) ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_CREATE_MYAPP, RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE, getUser( ) ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSIONS_LIST, MyAppsDatabaseService.getInstance( ).getMyAppsPermissions( listMyApps, getUser( ) ) );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_MODIFY_MYAPP, MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE );
        model.put( MyAppsDatabaseConstants.MARK_PERMISSION_DELETE_MYAPP, MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE );
        model.put( MyAppsDatabaseConstants.MARK_MYAPP_CODE_CATEGORY_FILTER, _strCategoryFilter );
        HtmlTemplate templateList = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_MYAPPS, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Returns the list of myapps
     * 
     * @param request
     *            The Http request
     * @return the myapps list
     */
    public String getManageMyAppsCategory( HttpServletRequest request )
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MYAPPS_CATEGORY );
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MyAppsDatabaseConstants.MARK_MYAPPS_CATEGORY_LIST, MyAppsDatabaseCategoryHome.getMyAppsCategoryList( getPlugin( ) ) );
        HtmlTemplate templateList = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_MYAPPS_CATEGORY, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Process the confirmation of the removal of an application
     * 
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    public String getConfirmRemoveMyApp( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            UrlItem url = new UrlItem( MyAppsDatabaseConstants.JSP_DO_REMOVE_MYAPP );
            url.addParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID, request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID ) );

            strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_CONFIRM_REMOVE_MYAPP, url.getUrl( ),
                    AdminMessage.TYPE_CONFIRMATION );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    public String getConfirmRemoveMyAppCategory( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strMyAppCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        if ( StringUtils.isNotBlank( strMyAppCode ) )
        {

            MyAppsDatabaseFilter filter = new MyAppsDatabaseFilter( );
            filter.setCategory( strMyAppCode );
            List<MyApps> listMyApps = MyAppsDatabaseHome.selectMyAppsList( filter, getPlugin( ) );
            if ( listMyApps != null && listMyApps.size( ) > 0 )
            {

                strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR_CAN_NOT_REMOVE_MYAPP_CATEGORY,
                        AdminMessage.TYPE_STOP );
            }
            else
            {

                UrlItem url = new UrlItem( MyAppsDatabaseConstants.JSP_DO_REMOVE_MYAPP_CATEGORY );
                url.addParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY,
                        request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY ) );
                strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_CONFIRM_REMOVE_MYAPP_CATEGORY, url.getUrl( ),
                        AdminMessage.TYPE_CONFIRMATION );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Handles the removal form of a myapp
     * 
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage applications
     * @throws AccessDeniedException
     *             if the current user has not the permission to remove
     */
    public String doRemoveMyApp( HttpServletRequest request ) throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, strMyAppId, MyAppsDatabaseResourceIdService.PERMISSION_DELETE_MYAPPS_DATABASE, getUser( ) ) )
            {
                throw new AccessDeniedException( );
            }

            int nAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabaseService.getInstance( ).remove( nAppId, getPlugin( ) );

            return getHomeUrl( request );
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    public String doRemoveMyAppCategory( HttpServletRequest request ) throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        if ( StringUtils.isNotBlank( strMyAppCode ) )
        {

            MyAppsDatabaseCategoryHome.remove( strMyAppCode, getPlugin( ) );
            return AppPathService.getBaseUrl( request ) + MyAppsDatabaseConstants.JSP_MANAGE_MYAPP_CATEGORY;
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    /**
     * Returns the form to update a myapp
     * 
     * @param request
     *            The Http request
     * @return The HTML form to update info
     * @throws AccessDeniedException
     *             if the current user has not the permission to modify
     */
    public String getModifyMyApp( HttpServletRequest request ) throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MODIFY );

        String strHtml = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, strMyAppId, MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, getUser( ) ) )
            {
                throw new AccessDeniedException( );
            }

            int nMyAppId = Integer.parseInt( strMyAppId );
            MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance( ).findByPrimaryKey( nMyAppId, getPlugin( ) );

            Map<String, Object> model = new HashMap<String, Object>( );
            model.put( MyAppsDatabaseConstants.MARK_MYAPP, myApp );
            model.put( MyAppsDatabaseConstants.MARK_MYAPPS_CATEGORY_LIST, MyAppsDatabaseCategoryHome.getMyAppsCategoryRefList( getPlugin( ) ) );
            HtmlTemplate template = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_MODIFY_APPLICATION, getLocale( ), model );

            strHtml = getAdminPage( template.getHtml( ) );
        }
        else
        {
            getManageMyApps( request );
        }

        return strHtml;
    }

    public String getModifyMyAppCategory( HttpServletRequest request ) throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_MODIFY_CATEGORY );

        String strHtml = StringUtils.EMPTY;
        String strMyAppCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        if ( StringUtils.isNotBlank( strMyAppCode ) )
        {

            MyAppsDatabaseCategory myAppCategory = (MyAppsDatabaseCategory) MyAppsDatabaseCategoryHome.findByPrimaryKey( strMyAppCode, getPlugin( ) );

            Map<String, Object> model = new HashMap<String, Object>( );
            model.put( MyAppsDatabaseConstants.MARK_MYAPP_CATEGORY, myAppCategory );

            HtmlTemplate template = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_MODIFY_CATEGORY, getLocale( ), model );

            strHtml = getAdminPage( template.getHtml( ) );
        }
        else
        {
            getManageMyAppsCategory( request );
        }

        return strHtml;
    }

    public String doModifyMyAppCategory( HttpServletRequest request ) throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppCodeCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        if ( StringUtils.isNotBlank( strMyAppCodeCategory ) )
        {

            String strLibelleCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_LIBELLE_CATEGORY );

            if ( StringUtils.isBlank( strLibelleCategory ) )
            {

                return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );

            }

            MyAppsDatabaseCategory category = new MyAppsDatabaseCategory( );
            category.setCodeCategory( strMyAppCodeCategory );
            category.setLibelleCategory( strLibelleCategory );

            MyAppsDatabaseCategoryHome.update( category, getPlugin( ) );

            return AppPathService.getBaseUrl( request ) + MyAppsDatabaseConstants.JSP_MANAGE_MYAPP_CATEGORY;

        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    /**
     * Returns the form to create a myapp
     * 
     * @param request
     *            The Http request
     * @return The HTML form to update info
     * @throws AccessDeniedException
     *             if the current user has not the permission to create
     */
    public String getCreateMyApp( HttpServletRequest request ) throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_CREATE );

        if ( !RBACService.isAuthorized( MyApps.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID, MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE,
                getUser( ) ) )
        {
            throw new AccessDeniedException( );
        }
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MyAppsDatabaseConstants.MARK_MYAPPS_CATEGORY_LIST, MyAppsDatabaseCategoryHome.getMyAppsCategoryRefList( getPlugin( ) ) );
        HtmlTemplate template = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_CREATE_APPLICATION, getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    public String getCreateMyAppCategory( HttpServletRequest request ) throws AccessDeniedException
    {
        setPageTitleProperty( MyAppsDatabaseConstants.PROPERTY_PAGE_TITLE_CREATE_CATEGORY );

        HtmlTemplate template = AppTemplateService.getTemplate( MyAppsDatabaseConstants.TEMPLATE_CREATE_CATEGORY, getLocale( ), null );

        return getAdminPage( template.getHtml( ) );
    }

    public String doCreateMyAppCategory( HttpServletRequest request ) throws AccessDeniedException
    {

        String strMyAppCodeCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );
        String strLibelleCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_LIBELLE_CATEGORY );

        if ( StringUtils.isBlank( strMyAppCodeCategory ) && StringUtils.isBlank( strLibelleCategory ) )
        {

            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );

        }

        if ( MyAppsDatabaseCategoryHome.findByPrimaryKey( strMyAppCodeCategory, getPlugin( ) ) != null )
        {

            return AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_FIELD_CATEGORY_ALREADY_EXIST, AdminMessage.TYPE_STOP );
        }

        MyAppsDatabaseCategory category = new MyAppsDatabaseCategory( );
        category.setCodeCategory( strMyAppCodeCategory );
        category.setLibelleCategory( strLibelleCategory );

        MyAppsDatabaseCategoryHome.create( category, getPlugin( ) );

        return AppPathService.getBaseUrl( request ) + MyAppsDatabaseConstants.JSP_MANAGE_MYAPP_CATEGORY;

    }

    /**
     * Process the myapp creation
     * 
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     *             if the current user has not the permission to create
     */
    public String doCreateMyApp( HttpServletRequest request ) throws AccessDeniedException
    {
        if ( !RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                MyAppsDatabaseResourceIdService.PERMISSION_CREATE_MYAPPS_DATABASE, getUser( ) ) )
        {
            throw new AccessDeniedException( );
        }

        String strJspUrl = StringUtils.EMPTY;
        String strAppName = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_NAME );
        String strUrl = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_URL );
        String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_PASSWORD );
        String strCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE );
        String strCodeHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE_HEADING );
        String strData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA );
        String strDataHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA_HEADING );
        String strDescription = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_DESCRIPTION );
        String strCodeCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );

        String strError = verifyFields( request );

        if ( StringUtils.isBlank( strError ) )
        {
            // create the multipart request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MyAppsDatabase myApp = new MyAppsDatabase( );
            myApp.setName( strAppName );
            myApp.setUrl( strUrl );
            myApp.setPassword( strPassword );
            myApp.setCode( strCode );
            myApp.setCodeHeading( strCodeHeading );
            myApp.setData( strData );
            myApp.setDataHeading( strDataHeading );
            myApp.setDescription( strDescription );
            myApp.setCodeCategory( strCodeCategory );
            try
            {
                FileItem itemIcon = multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON );

                if ( itemIcon != null )
                {
                    byte [ ] bytes = itemIcon.get( );
                    String strMimeType = itemIcon.getContentType( );
                    myApp.setIconContent( bytes );
                    myApp.setIconMimeType( strMimeType );
                }
                else
                {
                    myApp.setIconContent( null );
                    myApp.setIconMimeType( null );
                }

                MyAppsDatabaseService.getInstance( ).create( myApp, getPlugin( ) );
            }
            catch( Exception e )
            {
                AppLogService.error( e.getMessage( ), e );
            }

            strJspUrl = getHomeUrl( request );
        }
        else
        {
            strJspUrl = strError;
        }

        return strJspUrl;
    }

    /**
     * Process the myapp creation
     * 
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     *             if the current user has not the permission to modify
     */
    public String doModifyMyApp( HttpServletRequest request ) throws AccessDeniedException
    {
        String strJspUrl = StringUtils.EMPTY;
        String strMyAppId = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_ID );

        if ( StringUtils.isNotBlank( strMyAppId ) && StringUtils.isNumeric( strMyAppId ) )
        {
            if ( !RBACService.isAuthorized( MyAppsDatabase.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    MyAppsDatabaseResourceIdService.PERMISSION_MODIFY_MYAPPS_DATABASE, getUser( ) ) )
            {
                throw new AccessDeniedException( );
            }

            String strAppName = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_NAME );
            String strUrl = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_URL );
            String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_PASSWORD );
            String strCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE );
            String strCodeHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE_HEADING );
            String strData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA );
            String strDataHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA_HEADING );
            String strDescription = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_DESCRIPTION );
            String strCodeCategory = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_CODE_CATEGORY );
            String strError = verifyFields( request );

            if ( StringUtils.isBlank( strError ) )
            {
                int nMyAppId = Integer.parseInt( strMyAppId );

                // create the multipart request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

                MyAppsDatabase myApp = (MyAppsDatabase) MyAppsDatabaseService.getInstance( ).findByPrimaryKey( nMyAppId, getPlugin( ) );
                myApp.setName( strAppName );
                myApp.setUrl( strUrl );
                myApp.setPassword( strPassword );
                myApp.setCode( strCode );
                myApp.setCodeHeading( strCodeHeading );
                myApp.setData( strData );
                myApp.setDataHeading( strDataHeading );
                myApp.setDescription( strDescription );
                myApp.setCodeCategory( strCodeCategory );
                boolean bUpdateImage = ( multiRequest.getParameter( MyAppsDatabaseConstants.PARAMETER_UPDATE_FILE ) != null );

                if ( bUpdateImage && ( multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON ) != null ) )
                {
                    try
                    {
                        FileItem itemIcon = multiRequest.getFile( MyAppsDatabaseConstants.PARAMETER_MYAPP_ICON );

                        if ( itemIcon != null )
                        {
                            byte [ ] bytes = itemIcon.get( );
                            String strMimeType = itemIcon.getContentType( );
                            myApp.setIconContent( bytes );
                            myApp.setIconMimeType( strMimeType );
                        }
                    }
                    catch( Exception e )
                    {
                        AppLogService.error( e.getMessage( ), e );
                    }
                }

                MyAppsDatabaseService.getInstance( ).update( myApp, bUpdateImage, getPlugin( ) );

                strJspUrl = getHomeUrl( request );
            }
            else
            {
                strJspUrl = strError;
            }
        }
        else
        {
            strJspUrl = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_ERROR, AdminMessage.TYPE_STOP );
        }

        return strJspUrl;
    }

    /**
     * Check the sizes of each field
     * 
     * @param request
     *            {@link HttpServletRequest}
     * @return an empty string if there are no error, the url of the error message otherwise
     */
    private String verifyFields( HttpServletRequest request )
    {
        String strError = StringUtils.EMPTY;
        String strAppName = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_NAME );
        String strUrl = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_URL );
        String strPassword = request.getParameter( MyAppsDatabaseConstants.PARAMETER_PASSWORD );
        String strCode = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE );
        String strCodeHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_CODE_HEADING );
        String strData = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA );
        String strDataHeading = request.getParameter( MyAppsDatabaseConstants.PARAMETER_DATA_HEADING );
        String strDescription = request.getParameter( MyAppsDatabaseConstants.PARAMETER_MYAPP_DESCRIPTION );

        if ( StringUtils.isBlank( strUrl ) || StringUtils.isBlank( strAppName ) )
        {
            strError = AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        if ( StringUtils.isBlank( strError ) )
        {
            if ( StringUtils.isNotBlank( strAppName ) && ( strAppName.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_NAME, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strUrl )
                    && ( strUrl.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_URL, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strPassword )
                    && ( strPassword.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_PASSWORD, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strCode )
                    && ( strCode.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_USER_NAME, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strCodeHeading )
                    && ( strCodeHeading.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_USER_HEADING, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strData )
                    && ( strData.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_USER_FIELD, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strDataHeading )
                    && ( strDataHeading.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_USER_FIELD_HEADING, getLocale( ) );
            }

            if ( StringUtils.isBlank( strError ) && StringUtils.isNotBlank( strDescription )
                    && ( strDescription.length( ) > MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT ) )
            {
                strError = I18nService.getLocalizedString( MyAppsDatabaseConstants.PROPERTY_LABEL_DESCRIPTION, getLocale( ) );
            }

            if ( StringUtils.isNotBlank( strError ) )
            {
                Object [ ] params = {
                        strError, MyAppsDatabaseConstants.PROPERTY_DEFAULT_FIELD_SIZE_INT
                };
                strError = AdminMessageService.getMessageUrl( request, MyAppsDatabaseConstants.MESSAGE_FIELD_TOO_LONG, params, AdminMessage.TYPE_STOP );
            }
        }

        return strError;
    }
}
