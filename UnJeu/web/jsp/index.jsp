<%-- 
    Document   : index
    Created on : 30 janv. 2012, 22:43:34
    Author     : Yan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="fr">
<head>
    <title>Un Jeu - Anonymousse Team</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/script.js"></script>
    <script type="text/javascript" src="js/sliders.js"></script>
</head>
<body>

	<div id="wrapper">
    
	<header>
        	<h1><a title="Un Jeu" href="./"><img src="images/UnJeu.png" height="120px"/></a></h1>
        </header>
	
            <c:choose>
                <c:when test="${!empty joueur } ">
                     <jsp:include page="nav.jsp"/>
                </c:when>
                <c:otherwise>
                     <jsp:include page="nav_log.jsp"/>
                </c:otherwise> 
            </c:choose>
           
      
            
        <section id="main" class="clearfix">
            
            <c:choose>
                <c:when test="${param.action == 'jeu'}">
                    <%@ include file="jeu.jsp" %>
                </c:when>
                <c:when test="${param.action == 'inscription'}">
                    <%@ include file="inscription.jsp" %>
                </c:when>
                <c:when test="${param.action == 'connexion'}">
                    <%@ include file="connexion.jsp" %>
                </c:when>
                <c:when test="${param.action == 'classement'}">
                    <%@ include file="classement.jsp" %>
                </c:when>
                <c:when test="${param.action == 'screen'}">
                    <%@ include file="screen.jsp" %>
                </c:when>
                <c:when test="${param.action == 'apropos'}">
                    <%@ include file="apropos.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@ include file="home.jsp" %>
                </c:otherwise> 
            </c:choose>	
                
        </section>

        <footer>
            
            <p><em>&copy; 2012 IUT Anonymousse Team</em><br /><a title="Un Jeu" href="view.do">Un Jeu</a> est un projet étudiant strictement expérimental</p>
			
        </footer>
      
    </div>
<script type="text/javascript" src="js/interaction.js"></script>
</body>
</html>
