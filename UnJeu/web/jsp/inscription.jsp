<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Inscription!</h3>

<form method="post" action="inscription.do">
    <table id="formulaire">
        <tr>
            <td>Pseudo</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td>Mot de passe</td>
            <td><input type="password" name="pass1"/></td>
        </tr>
        <tr>
            <td>Retaper le mot de passe</td><td><input type="password" name="pass2"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="mail"/></td>
        </tr>
        <tr>
            <td>Statistiques du personnage</td>
            <td></td>
        <tr>
            <td>Attaque</td>
            <td><input type="range" name="attaque" value="100" min="100" max="200" step="1" onChange="" />100</td>
        </tr>
        <tr>
            <td>Vitesse</td>
            <td><input type="range" name="vitesse" value="100" min="100" max="200" step="1" /></td>
        </tr>
        <tr>
            <td><input type="submit" name="submit" value="Je m'inscris!" /></td>
            <td></td></tr>
    </table>
</form>