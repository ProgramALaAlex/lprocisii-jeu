<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Inscription!</h3>

<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>
    
<form method="post" action="inscription.do">
    <table id="inscription">
        <tr>
            <td class="label">Pseudo</td>
            <td><input type="text" name="pseudo" value="<%=request.getParameter("pseudo") != null ? request.getParameter("pseudo") : ""%>" /></td>
        </tr>
        <tr>
            <td class="label">Mot de passe</td>
            <td><input type="password" name="pass1" /></td>
        </tr>
        <tr>
            <td class="label">Retaper le mot de passe</td>
            <td><input type="password" name="pass2" /></td>
        </tr>
        <tr>
            <td class="label">Email</td>
            <td><input type="text" name="mail" value="<%=request.getParameter("mail") != null ? request.getParameter("mail") : ""%>" /></td>
        </tr>
        <tr>
            <td class="label">Statistiques du personnage</td>
            <td>
                <%
                    int pv = (request.getParameter("pv")!=null ? Integer.parseInt(request.getParameter("pv"))+100 : 100);
                    int att = (request.getParameter("attaque")!=null ? Integer.parseInt(request.getParameter("attaque"))+100 : 100);
                    int vit = (request.getParameter("vitesse")!=null ? Integer.parseInt(request.getParameter("vitesse"))+100 : 100);
                    int distrib = 400 - pv - att - vit;
                %>
                
                <em>Points restants à distribuer : <strong><span id="distrib"><%= distrib %></span></strong></em><br />
                Points de vie : <strong><span id="vpv"><%= pv %></span></strong><br />
                <input type="range" name="pv" id="pv" value="<%= pv - 100 %>" min="0" max="100" step="1" onChange="checkSliders($(this));" /><br />
                Attaque : <strong><span id="vattaque"><%= att %></span></strong><br />
                <input type="range" name="attaque" id="attaque" value="<%= att - 100 %>" min="0" max="100" step="1" onChange="checkSliders($(this));" /><br />
                Vitesse : <strong><span id="vvitesse"><%= vit %></span></strong><br />
                <input type="range" name="vitesse" id="vitesse" value="<%= vit - 100 %>" min="0" max="100" step="1" onChange="checkSliders($(this));" />
            </td>
        </tr>
        <tr>
            <td></td><td><input type="submit" name="submit" value="Je m'inscris!" /></td>
        </tr>
    </table>
</form>