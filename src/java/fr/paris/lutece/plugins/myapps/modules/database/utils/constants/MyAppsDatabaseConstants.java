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
package fr.paris.lutece.plugins.myapps.modules.database.utils.constants;


/**
 *
 * MyAppsDatabaseConstants
 *
 */
public final class MyAppsDatabaseConstants
{
    // BEANS
    public static final String BEAN_MYAPPS_DATABASE_DAO = "myapps-database.myAppsDatabaseDAO";
    public static final String BEAN_MYAPPS_DATABASE_USER_DAO = "myapps-database.myAppsDatabaseUserDAO";

    // MARKS
    public static final String MARK_MYAPP = "myapp";
    public static final String MARK_MYAPP_USER = "myapp_user";
    public static final String MARK_PAGINATOR = "paginator";
    public static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    public static final String MARK_MYAPPS_LIST = "myapps_list";
    public static final String MARK_ENABLED_MYAPPS_LIST = "enabled_myapps_list";
    public static final String MARK_DISABLED_MYAPPS_LIST = "disabled_myapps_list";
    public static final String MARK_PERMISSION_CREATE_MYAPP = "permission_create_myapp";
    public static final String MARK_PERMISSION_MODIFY_MYAPP = "permission_modify_myapp";
    public static final String MARK_PERMISSION_DELETE_MYAPP = "permission_delete_myapp";
    public static final String MARK_PERMISSIONS_LIST = "permissions_list";

    // PARAMETERS
    public static final String PARAMETER_PAGE = "page";
    public static final String PARAMETER_ACTION = "action";
    public static final String PARAMETER_MYAPP_ID = "myapp_id";
    public static final String PARAMETER_USER_LOGIN = "user_login";
    public static final String PARAMETER_USER_PASSWORD = "user_password";
    public static final String PARAMETER_USER_EXTRA_DATA = "user_extra_data";
    public static final String PARAMETER_PAGE_INDEX = "page_index";
    public static final String PARAMETER_MYAPP_NAME = "myapp_name";
    public static final String PARAMETER_MYAPP_URL = "myapp_url";
    public static final String PARAMETER_MYAPP_ICON = "myapp_icon";
    public static final String PARAMETER_MYAPP_DESCRIPTION = "myapp_description";
    public static final String PARAMETER_USER_NAME = "user_name";
    public static final String PARAMETER_CODE_HEADING = "code_heading";
    public static final String PARAMETER_USER_FIELD = "user_field";
    public static final String PARAMETER_USER_FIELD_HEADING = "user_field_heading";
    public static final String PARAMETER_UPDATE_FILE = "update_file";

    // ACTIONS
    public static final String ACTION_MANAGE = "manage";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_MODIFY = "modify";
    public static final String ACTION_DO_INSERT = "do_insert";
    public static final String ACTION_DO_MODIFY = "do_modify";
    public static final String ACTION_DO_REMOVE = "do_remove";

    // PROPERTIES
    public static final String PROPERTY_PAGE_TITLE = "module.myapps.database.page_myapps.pageTitle";
    public static final String PROPERTY_PAGE_PATH = "module.myapps.database.page_myapps.pagePathLabel";
    public static final String PROPERTY_LABEL_MANAGE_MYAPPS = "module.myapps.database.page_myapps.label.manageMyApps";
    public static final String PROPERTY_MANAGE_PAGE_TITLE = "module.myapps.database.page_app_manage.pageTitle";
    public static final String PROPERTY_MANAGE_PAGE_PATH = "module.myapps.database.page_app_manage.pagePathLabel";
    public static final String PROPERTY_INSERT_PAGE_TITLE = "module.myapps.database.page_app_insert.pageTitle";
    public static final String PROPERTY_INSERT_PAGE_PATH = "module.myapps.database.page_app_insert.pagePathLabel";
    public static final String PROPERTY_MODIFY_PAGE_TITLE = "module.myapps.database.page_app_modify.pageTitle";
    public static final String PROPERTY_MODIFY_PAGE_PATH = "module.myapps.database.page_app_modify.pagePathLabel";
    public static final String PROPERTY_PAGE_TITLE_MYAPPS = "module.myapps.database.manage_myapps.pageTitle";
    public static final String PROPERTY_PAGE_TITLE_CREATE = "module.myapps.database.create_application.pageTitle";
    public static final String PROPERTY_PAGE_TITLE_MODIFY = "module.myapps.database.modify_application.pageTitle";
    public static final String PROPERTY_DEFAULT_ITEMS_PER_PAGE = "myapps-database.itemsPerPage";

    // MESSAGES
    public static final String MESSAGE_ERROR = "module.myapps.database.message.error";
    public static final String MESSAGE_CONFIRM_REMOVE_MYAPP = "module.myapps.database.message.confirmRemoveMyApp";

    /**
     * Private constructor
     */
    private MyAppsDatabaseConstants(  )
    {
    }
}
