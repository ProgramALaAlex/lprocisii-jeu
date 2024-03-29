<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean joueur = jdb.getById(request.getParameter("id"));
    boolean admin = session.getAttribute("groupe") != null ? JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) : false;
    String[] ban = jdb.estBanni(joueur.getIdJoueur());
%>
<c:set var="joueur" value="${jvoir}"/>
<h3>Profil</h3>
<c:if test="${not empty requestScope['info']}">
    <p class="infoMessage"><c:out value="${requestScope['info']}" /></p>
</c:if>
<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>
    
<% if(admin) {%>
<a href="javascript: void(0);" onClick="$('#adminPanel').slideToggle();">Admin</a>
<fieldset id="adminPanel">
    <form method="post" action="supprimer.do">
        <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
        <input type="image" title="Supprimer le joueur" src="images/supprimer.png" onClick="if (!confirm('Supprimer le joueur?')) return false;" />
    </form>
    <input type="image" title="Modifier le joueur" src="images/modifier.png" onClick="$('#profilVoir, #profilModif').toggle();" />
    <input type="image" title="Bannir le joueur" src="images/bannir.png" onClick="$('#bannir').slideToggle();" />
    <% if (ban != null) { %>
        <form method="post" action="debannir.do">
            <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
            <input type="image" title="R�int�grer le joueur" src="images/debannir.png" onClick="if (!confirm('R�int�grer le joueur?')) return false;" />
        </form>
    <% } %>
</fieldset>
<fieldset id="bannir">
    <form method="post" action="bannir.do">
        Bannir pour :<br />
        <input type="hidden" name="id" value="<%= joueur.getIdJoueur() %>" />
        <input type="number" value="0" min="0" name="semaines"/> semaines<br />
        <input type="number" value="0" min="0" name="jours"/> jours<br />
        <input type="checkbox" name="definitif" value="1" /> d�finitivement<br />
        Raison <input type="text" name="raison" /><br />
        <input type="submit" name="submit" value="Bannir" onClick="if (!confirm('Bannir le joueur?')) return false;" />
    </form>
</fieldset>
<% } %>
<% if (ban != null) { %>
<fieldset id="banField">
    <p><strong>Joueur banni</strong><br />
    <%= !ban[0].equals("") ? "<strong>Raison:</strong> "+ban[0]+"<br />" : "" %>
    <strong>Ech�ance:</strong> <%= ban[1] %></p>
</fieldset>
<%} %>
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
        <td class="label">Apparence</td>
        <td>
            <%=joueur.getIdApparance() == 0 ? "Homme" : "Femme" %>
        </td>
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
        <td class="label">Nombre de monstres tu�s :</td>
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
        <td class="label">Genre</td>
        <td>
            <input type="radio" name="apparence" value="0" <%=joueur.getIdApparance() == 0 ? "Checked" : "" %>/>Homme
            <input type="radio" name="apparence" value="1" <%=joueur.getIdApparance() == 1 ? "Checked" : "" %>/>Femme
        </td>
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
        <td class="label">Nombre de monstres tu�s :</td>
        <td><input type="number" min="0" name="totalMonstres" value="<%= joueur.getTotalMonstres() %>" /></td>
    </tr>
    <tr>
        <td align="right"><input type="submit" name="submit" value="Modifier le joueur" /></td>
        <td><input type="reset" name="reset" value="Annuler" onclick="$('#profilVoir, #profilModif').toggle();" /></td>
    </tr>
</table>
</form>
<% } %>