 <%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="myAppsDatabase" scope="session" class="fr.paris.lutece.plugins.myapps.modules.database.web.MyAppsDatabaseJspBean" />

<% myAppsDatabase.init( request, myAppsDatabase.RIGHT_MYAPPS_DATABASE_MANAGEMENT ); %>
<%= myAppsDatabase.getManageApplications( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>