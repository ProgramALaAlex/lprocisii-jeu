<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean joueur = jdb.getById(request.getParameter("id"));
    boolean admin = JoueurBean.estAdmin((Integer)session.getAttribute("groupe"));
%>
<c:set var="joueur" value="${jvoir}"/>
<h3>Profil</h3>
<% if(admin) {%>
<a href="javascript: void(0);" onClick="$('#adminPanel').slideToggle();">Admin</a>
<fieldset id="adminPanel">
    <form method="post" action="supprimer.do">
        <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
        <input type="image" title="Supprimer le joueur" src="images/supprimer.png" onClick="if (!confirm('Supprimer le joueur?')) return false;" />
    </form>
    <input type="image" title="Modifier le joueur" src="images/modifier.png" onClick="this.form.submit();" />
</fieldset>
<% } %>
<table>
    <tr>
        <td class="label">Pseudo :</td>
        <td><%= joueur.getPseudo() %></td>
    </tr>
    <tr>
        <td class="label">Pv :</td>
        <td><%= joueur.getPvMax() %></td>
    </tr>
    <tr>
        <td class="label">Nombre de combats :</td>
        <td><%= joueur.getTotalCombats() %></td>
    </tr>
    <tr>
        <td class="label">Nombre de monstres tués :</td>
        <td><%= joueur.getTotalMonstres() %></td>
    </tr>
</table>