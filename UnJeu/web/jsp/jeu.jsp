<%@page import="beans.InventaireDB"%>
<%@page import="beans.InventaireBean"%>
<%@page import="beans.ClefDB"%>
<%
    ClefDB cle = new ClefDB();
    String clef = cle.createInvenaire(session.getAttribute("id")+"");
    
    InventaireDB idb = new InventaireDB();
    String inventaire = idb.jsonInventaire(session.getAttribute("id")+"").getJs();
%>    
<script>
var inventaire = new Array();
	var objet = new Array();
        function rpl(){
<%= inventaire %>
}</script>
<section id="content">
    
              <applet mayscript="true"
			code="org.lwjgl.util.applet.AppletLoader"
			id = "unJeu"
                        archive="lwjgl_util_applet.jar, lzma.jar, plugin.jar"
                        codebase="./librairies/"
                        width="640" height="480">
 
              <!-- Name of Applet, will be used as name of directory it is saved in, and will uniquely identify it in cache -->
              <param name="al_title"
                     value="MainGame">

              <!-- Main Applet Class -->
              <param name="al_main"
                     value="game.AppletGameContainer">
              <param name="game"
                     value="game.MainGame">
              <param name="clef"
                     value="<%= clef %>">
	    <param name="pseudo"
		   value="<%= session.getAttribute("pseudo") %>">
              <!-- logo to paint while loading, will be centered -->
             <!-- <param name="al_logo"
                     value="appletlogo.png">
                                 -->

              <!-- progressbar to paint while loading. Will be painted on top of logo, width clipped to percentage done -->
        <!--     
                 <param name="al_progressbar"
                     value="appletprogress.gif">
         -->
              <!-- List of Jars to add to classpath -->
              <param name="al_jars"
                     value="slick.jar, jeu.jar, lwjgl.jar.pack.lzma, jinput.jar.pack.lzma, lwjgl_util.jar.pack.lzma, jorbis-0.0.15.jar, jogg-0.0.7.jar">

              <!-- signed windows natives jar in a jar -->
              <param name="al_windows"
                     value="windows_natives.jar.lzma">

              <!-- signed linux natives jar in a jar -->
              <param name="al_linux"
                     value="linux_natives.jar.lzma">

              <!-- signed mac osx natives jar in a jar -->
              <param name="al_mac"
                     value="macosx_natives.jar.lzma">

              <!-- signed solaris natives jar in a jar -->
              <param name="al_solaris"
                     value="solaris_natives.jar.lzma">

              <!-- Tags under here are optional -->

              <!-- Version of Applet, secondary cache if used applet will start faster, version change will update applet, must be int or float -->
              <!-- <param name="al_version" value="0.1">
         -->

              <!-- background color to paint with, defaults to white -->
              <!-- <param name="boxbgcolor" value="#000000">
         -->

              <!-- foreground color to paint with, defaults to black -->
              <!-- <param name="boxfgcolor" value="#ffffff">
         -->

              <!-- whether to run in debug mode -->
              <!-- <param name="al_debug" value="true">
         -->

              <!-- whether to prepend host to cache path - defaults to true -->
              <!-- <param name="al_prepend_host" value="true">
         -->
              </applet>
            
            </section>
            
            <aside id="sidebar">
            
		<div id="listeJoueur"><i>Attente de communication avec l'applet...</i></div>
		<div id="listeInvitation"></div>
		
                <a class="chatSection" onclick="switchOnglet('general');">Général</a> - 
                <a class="chatSection" onclick="switchOnglet('guild');">Guild</a> - 
                <a class="chatSection" onclick="switchOnglet('whisp');">Whisp</a>
                <div id="general" class="chatbox"><span class="hello">Canal général</span></div>
                <div id="guild" class="chatbox" style="display: none;"><span class="hello">Canal guilde</span></div>
                <div id="whisp" class="chatbox" style="display: none;"><span class="hello">Messages privés</span></div>
                <form id="chatForm" action="javascript: void(0)" onsubmit="envoi();" >
                        <input type="text" id="msg" placeholder="Votre message" />
                        <input type="submit" value="Envoi" />
                </form>
                
            </aside>
            
            <section id="action">
		<input type="button" value="Voir Inventaire local" id="butInv" onClick="voirInventaire();"/>
		<div id="inventaireTxt"></div>
		
                <section id="iconeMenu">
                    <img onclick="show('sac');" src="images/sac.jpg" height="40px"/>
                    <img onclick="show('equipement');" src="images/equipement.jpg" height="40px"/>
                </section>
                <aside id="fenetresInteraction">
                    <table id="inventaire" style="display: none;"></table>
                    <div id="perso" style="display: none;">
                           
                            <div id="armure" class="equip1"></div>
                            <div id="arme" class="equip2"></div>
                    </div>
                </aside>
                <!--    
                <em>Section inventaire et actions</em>
                
                <br /><br />Quelques tests<br />
                <input type="button" value="Faire un test de sysout !" onClick="sysout();"/>
                <input type="button" value="Equiper armure 1" onClick="equiperArmure(1);"/>
                <input type="button" value="Déséquiper armure" onClick="desequiperArmure();"/>
                <hr />
                <input type="button" value="Voir Inventaire" id="butInv" onClick="voirInventaire();"/>
                <div id="inventaire"></div>
                --><input type="button" value="actualise" onclick="actualise();">
            </section>