<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="myAppsDatabase" scope="session" class="fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean" />

<%
	myAppsDatabase.init( request, myAppsDatabase.RIGHT_MYAPPS_DATABASE_MANAGEMENT );
    response.sendRedirect( myAppsDatabase.doModifyMyApp( request ) );
%>