<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean joueur = jdb.getById(request.getParameter("id"));
%>
<c:set var="joueur" value="${jvoir}"/>
<h3>Profil</h3>
<% if(JoueurBean.estAdmin((String)session.getAttribute("groupe")))) {%>
    truc truc
<% } %>
<table>
    <tr><td class="label">Pseudo :</td><td><%= joueur.getPseudo() %></td></tr>
    <tr><td class="label">Pv :</td><td><%= joueur.getPvMax() %></td></tr>
    <tr><td class="label">Nombre de combats :</td><td><%= joueur.getTotalCombats() %></td></tr>
    <tr><td class="label">Nombre de monstres tués :</td><td><%= joueur.getTotalMonstres() %></td></tr>
</table>