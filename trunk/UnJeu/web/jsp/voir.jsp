<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean jvoir = jdb.getById(request.getParameter("id"));
%>
<table>
    <tr><td class="label">Pseudo</td><td><%= jvoir.getPseudo() %></td></tr>
    <tr><td class="label">Pv</td><td><%= jvoir.getPvMax() %></td></tr>
    <tr><td class="label">Nombre combat</td><td><%= jvoir.getTotalCombats() %></td></tr>
    <tr><td class="label">nombre monstre tué</td><td><%= jvoir.getTotalMonstres() %></td></tr>
</table>