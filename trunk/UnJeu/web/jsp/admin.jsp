<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Administration</h3>

<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>
    
<fieldset class="adminField">
    <legend>Chercher un joueur</legend>
    <form method="post" action="">
        <table>
            <tr><td>Pseudo</td><td><input type="text" name="pseudo" /></td></tr>
            <tr><td></td><td><input type="submit" name="submit" value="Chercher" /></td></tr>
        </table>
    </form>
</fieldset>