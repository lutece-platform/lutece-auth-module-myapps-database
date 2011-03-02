<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="myAppsDatabase" scope="session" class="fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean" />

<% myAppsDatabase.init( request, fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean.RIGHT_MYAPPS_DATABASE_MANAGEMENT ); %>
<%= myAppsDatabase.getModifyMyApp ( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>

