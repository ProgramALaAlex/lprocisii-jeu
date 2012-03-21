<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%@page import="java.util.ArrayList"%>
<%
    String pseudo = request.getParameter("pseudo");
%>
<h3>Rechercher un joueur</h3>

<c:if test="${not empty requestScope['info']}">
    <p class="infoMessage"><c:out value="${requestScope['info']}" /></p>
</c:if>

<form methode="get" action="./">
    <input type="hidden" name="action" value="armu"/>
    <input type="text" name="pseudo"/>
    <input type="submit" value="Chercher"/>
</form>

<% if(pseudo != null)
if(pseudo.compareTo("") != 0){ 
    JoueurDB j_db = new JoueurDB();
    ArrayList<JoueurBean> list = j_db.getList(pseudo);
    for(int i = 0; i < list.size(); i++){
    %>
    <a href="./?action=voir&id=<%= list.get(i).getIdJoueur() %>"><%= list.get(i).getPseudo() %></a></br>
<% }} %>
                