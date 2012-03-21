<%-- 
    Document   : nav
    Created on : 20 mars 2012, 16:23:41
    Author     : Loic
--%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <nav>
            <ul class="menu">
                <li><a title="Jouer!" href="./?action=jeu">Jouer!</a></li>
                <li><a title="Actualité" href="./">Actualité</a></li>
                <li><a title="Se déconnecter" href="deconnexion.do">Se déconnecter</a></li>
                <li><a title="Classement du mois" href="./?action=classement">Classement du mois</a></li>
                <li><a title="Screenshots" href="./?action=screen">Screenshots</a></li>
		<li><a title="A propos" href="./?action=apropos">A propos</a></li>
                <li><a title="Armurerie" href="./?action=armu">Armurerie</a></li>
                <c:if test="${sessionScope['groupe'] == 2}">
                    <li><a title="Administration" href="./?action=admin">Admin</a></li>
                </c:if>
            </ul>
        </nav>