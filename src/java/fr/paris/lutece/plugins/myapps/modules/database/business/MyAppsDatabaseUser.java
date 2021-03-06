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

import fr.paris.lutece.plugins.myapps.business.MyAppsUser;

/**
 *
 * MyAppsDatabaseUser
 *
 */
public class MyAppsDatabaseUser extends MyAppsUser
{
    private String _strStoredUserName;
    private String _strStoredUserPassword;
    private String _strStoredUserData;
    private int _nApplicationOrder;

    /**
     * Returns the StoredUserName
     *
     * @return The StoredUserName
     */
    public String getStoredUserName( )
    {
        return _strStoredUserName;
    }

    /**
     * Sets the StoredUserName
     *
     * @param strStoredUserName
     *            The StoredUserName
     */
    public void setStoredUserName( String strStoredUserName )
    {
        _strStoredUserName = strStoredUserName;
    }

    /**
     * Returns the StoredUserPassword
     *
     * @return The StoredUserPassword
     */
    public String getStoredUserPassword( )
    {
        return _strStoredUserPassword;
    }

    /**
     * Sets the StoredUserPassword
     *
     * @param strStoredUserPassword
     *            The StoredUserPassword
     */
    public void setStoredUserPassword( String strStoredUserPassword )
    {
        _strStoredUserPassword = strStoredUserPassword;
    }

    /**
     * Returns the StoredUserData
     *
     * @return The StoredUserData
     */
    public String getStoredUserData( )
    {
        return _strStoredUserData;
    }

    /**
     * Sets the StoredUserData
     *
     * @param strStoredUserData
     *            The StoredUserData
     */
    public void setStoredUserData( String strStoredUserData )
    {
        _strStoredUserData = strStoredUserData;
    }

    /**
     * Returns the application order
     * 
     * @return The ApplicationOrder
     */
    public int getApplicationOrder( )
    {
        return _nApplicationOrder;
    }

    /**
     * Sets the application order
     * 
     * @param nApplicationOrder
     *            the nApplicationOrder to set
     */
    public void setApplicationOrder( int nApplicationOrder )
    {
        _nApplicationOrder = nApplicationOrder;
    }
}
