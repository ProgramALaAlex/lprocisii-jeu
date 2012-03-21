<h3>Connnexion!</h3>

<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>

<form method="post" action="connexion.do">
    <table id="formulaire">
        <tr>
            <td class="label">Login</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td class="label">Password</td>
            <td><input type="password" name="pass"/></td>
        </tr>
        <tr>
            <td></td><td><input type="submit" name="submit" value="Connexion"/></td>
        </tr>
    </table>
</form>