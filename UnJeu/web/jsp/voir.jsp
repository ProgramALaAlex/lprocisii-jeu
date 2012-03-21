<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean joueur = jdb.getById(request.getParameter("id"));
    boolean admin = session.getAttribute("groupe") != null ? JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) : false;
%>
<c:set var="joueur" value="${jvoir}"/>
<h3>Profil</h3>

<c:if test="${not empty requestScope['info']}">
    <p class="infoMessage"><c:out value="${requestScope['info']}" /></p>
</c:if>
    
<% if(admin) {%>
<a href="javascript: void(0);" onClick="$('#adminPanel').slideToggle();">Admin</a>
<fieldset id="adminPanel">
    <form method="post" action="supprimer.do">
        <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
        <input type="image" title="Supprimer le joueur" src="images/supprimer.png" onClick="if (!confirm('Supprimer le joueur?')) return false;" />
    </form>
    <input type="image" title="Modifier le joueur" src="images/modifier.png" onClick="$('#profilVoir, #profilModif').toggle();" />
</fieldset>
<% } %>

<table id="profilVoir">
    <tr>
        <td class="label">Pseudo :</td>
        <td><%= joueur.getPseudo() %></td>
    </tr>
    <tr>
        <td class="label">Mail :</td>
        <td><%= joueur.getMail() %></td>
    </tr>
    <tr>
        <td class="label">Pv :</td>
        <td><%= joueur.getPvMax() %></td>
    </tr>
    <tr>
        <td class="label">Attaque :</td>
        <td><%= joueur.getAttaque() %></td>
    </tr>
    <tr>
        <td class="label">Vitesse :</td>
        <td><%= joueur.getVitesse() %></td>
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
    
<% if(admin) {%>
<form id="profilModif" method="post" action="modifier.do">
    <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
<table>
    <tr>
        <td class="label">Pseudo :</td>
        <td><input type="text" name="pseudo" value="<%= joueur.getPseudo() %>" /></td>
    </tr>
    <tr>
        <td class="label">Mail :</td>
        <td><input type="text" name="mail" value="<%= joueur.getMail() %>" /></td>
    </tr>
    <tr>
        <td class="label">Pv :</td>
        <td><input type="number" min="0" name="pv" value="<%= joueur.getPvMax() %>" /></td>
    </tr>
    <tr>
        <td class="label">Attaque :</td>
        <td><input type="number" min="0" name="attaque" value="<%= joueur.getAttaque() %>" /></td>
    </tr>
    <tr>
        <td class="label">Vitesse :</td>
        <td><input type="number" min="0" name="vitesse" value="<%= joueur.getVitesse() %>" /></td>
    </tr>
    <tr>
        <td class="label">Nombre de combats :</td>
        <td><input type="number" min="0" name="totalCombats" value="<%= joueur.getTotalCombats() %>" /></td>
    </tr>
    <tr>
        <td class="label">Nombre de monstres tués :</td>
        <td><input type="number" min="0" name="totalMonstres" value="<%= joueur.getTotalMonstres() %>" /></td>
    </tr>
    <tr>
        <td align="right"><input type="submit" name="submit" value="Modifier le joueur" /></td>
        <td><input type="reset" name="reset" value="Annuler" onclick="$('#profilVoir, #profilModif').toggle();" /></td>
    </tr>
</table>
</form>
<% } %>