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
package fr.paris.lutece.plugins.myapps.modules.database.business;

import org.apache.commons.lang.StringUtils;

/**
 * Filter MyAppsDatabaseFilter
 * 
 * @author merlinfe
 *
 */
public class MyAppsDatabaseFilter
{

    // category
    private String _strCategory;
    // User name
    private String _strUserName;

    /**
     * 
     * @return true of the filter contains a category
     */
    public boolean containsCategory( )
    {
        return StringUtils.isNotBlank( _strCategory );
    }

    /**
     * set the category
     * 
     * @param _strCategory
     *            set the category
     */
    public void setCategory( String _strCategory )
    {
        this._strCategory = _strCategory;
    }

    /**
     * return the category
     * 
     * @return the category
     */
    public String getCategory( )
    {
        return _strCategory;
    }

    /**
     * set the user name
     * 
     * @param _strUserName
     *            _strUserName
     */
    public void setUserName( String _strUserName )
    {
        this._strUserName = _strUserName;
    }

    /**
     * 
     * @return the user name
     */
    public String getUserName( )
    {
        return _strUserName;
    }

    /**
     * 
     * @return true of the filter contains the user name
     */
    public boolean containsUserName( )
    {
        return StringUtils.isNotBlank( _strUserName );
    }

}
