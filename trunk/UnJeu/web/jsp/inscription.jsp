<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Inscription!</h3>

<form method="post" action="inscription.do">
    <table id="formulaire">
        <tr>
            <td class="label">Pseudo</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td class="label">Mot de passe</td>
            <td><input type="password" name="pass1"/></td>
        </tr>
        <tr>
            <td class="label">Retaper le mot de passe</td>
            <td><input type="password" name="pass2"/></td>
        </tr>
        <tr>
            <td class="label">Email</td>
            <td><input type="text" name="mail"/></td>
        </tr>
        <tr>
            <td class="label">Statistiques du personnage</td>
            <td>
                <em>Points restants à distribuer : <strong><span id="distrib">100</span></strong></em><br />
                Points de vie : <strong><span id="vpv">100</span></strong><br />
                <input type="range" name="pv" id="pv" value="0" min="0" max="100" step="1" onChange="checkSliders($(this));" /><br />
                Attaque : <strong><span id="vattaque">100</span></strong><br />
                <input type="range" name="attaque" id="attaque" value="0" min="0" max="100" step="1" onChange="checkSliders($(this));" /><br />
                Vitesse : <strong><span id="vvitesse">100</span></strong><br />
                <input type="range" name="vitesse" id="vitesse" value="0" min="0" max="100" step="1" onChange="checkSliders($(this));" />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" name="submit" value="Je m'inscris!" /></td>
        </tr>
    </table>
</form>