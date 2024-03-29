<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="beans.JoueurBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.NewDB"%>
<%@page import="beans.NewBean"%>
<%
    NewDB ndb = new NewDB();
    pageContext.setAttribute("newsList", ndb.findAll());
    pageContext.setAttribute("admin", session.getAttribute("groupe") != null ? JoueurBean.estAdmin((Integer)session.getAttribute("groupe")) : false);
%>

<h3>News :</h3>
<c:if test="${not empty requestScope['info']}">
    <p class="infoMessage"><c:out value="${requestScope['info']}" /></p>
</c:if>
<c:if test="${not empty requestScope['erreur']}">
    <p class="errorMessage"><c:out value="${requestScope['erreur']}" /></p>
</c:if>
<c:if test="${admin}">
    <a href="javascript: void(0);" onClick="$('#adminNews').slideToggle();">Poster une news</a><br />
    <fieldset id="adminNews">
        <form method="post" action="ajouterNews.do">
            <table>
                <tr>
                    <td>Titre</td>
                    <td><input type="text" name="titre" /></td>
                </tr>
                <tr>
                    <td>Contenu</td>
                    <td><textarea name="contenu" /></textarea></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Poster" /></td>
                </tr>
            </table>
        </form>
    </fieldset>
</c:if>
<c:choose>
    <c:when test="${not empty newsList}">
        <c:forEach var="news" items="${newsList}">
            <div class="divNews">
                <c:if test="${admin}">
                    <form method="post" action="supprimerNews.do">
                        <input type="hidden" name="id" value="${news.id}" />
                        <input type="image" title="Supprimer la news" src="images/supprimer.png" onClick="if (!confirm('Supprimer la news?')) return false;" />
                    </form>
                </c:if>
                <h4>${news.titre}</h4>
                <p>${news.contenu}</p>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Aucune news n'est disponible pour le moment.
    </c:otherwise>
</c:choose>