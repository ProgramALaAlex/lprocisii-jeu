<h3>Connnexion!</h3>

<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>

<form method="post" action="connexion.do">
    <table id="formulaire">
        <tr>
            <td class="label">Pseudo</td>
            <td><input type="text" name="login" value="<%=request.getParameter("login") != null ? request.getParameter("login") : ""%>"  /></td>
        </tr>
        <tr>
            <td class="label">Mot de passe</td>
            <td><input type="password" name="pass"/></td>
        </tr>
        <tr>
            <td></td><td><input type="submit" name="submit" value="Connexion"/></td>
        </tr>
    </table>
</form>