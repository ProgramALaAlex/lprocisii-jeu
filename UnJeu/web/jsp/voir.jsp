<%@page import="beans.JoueurDB"%>
<%@page import="beans.JoueurBean"%>
<%
    JoueurDB jdb = new JoueurDB();
    JoueurBean jvoir = jdb.getById(request.getParameter("id"));
%>
<table>
    <tr><td>Pseudo</td><td><%= jvoir.getPseudo() %></td></tr>
    <tr><td>Pv</td><td><%= jvoir.getPvMax() %></td></tr>
    <tr><td>Nombre combat</td><td><%= jvoir.getTotalCombats() %></td></tr>
    <tr><td>nombre monstre tué</td><td><%= jvoir.getTotalMonstres() %></td></tr>
</table>