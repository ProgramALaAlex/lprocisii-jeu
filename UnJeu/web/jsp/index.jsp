<%-- 
    Document   : index
    Created on : 30 janv. 2012, 22:43:34
    Author     : Yan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="fr">
<head>
    <title>Un Jeu - Anonymousse Team</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <!--[if IE]><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/script.js"></script>
</head>
<body>

	<div id="wrapper">
    
		<header>
        	<h1><a title="Un Jeu" href="view.do"><img src="images/UnJeu.png" height="120px"/></a></h1>
        </header>
	
        <nav>
            <ul class="menu">
                <li><a title="Actualité" href="#">Actualité</a></li>
                <li><a title="S'inscrire" href="#">S'inscrire</a></li>
                <li><a title="Se connecter" href="#">Se connecter</a></li>
                <li><a title="Forum" href="#">Forum</a></li>
                <li><a title="Classement du mois" href="#">Classement du mois</a></li>
                <li><a title="Screenshots" href="#">Screenshots</a></li>
				<li><a title="A propos" href="#">A propos</a></li>
            </ul>
        </nav>
      
        <section id="main" class="clearfix">
          
            <section id="content">
    
              <applet mayscript="true"
			code="org.lwjgl.util.applet.AppletLoader"
			id = "unJeu"
                        archive="lwjgl_util_applet.jar, lzma.jar, plugin.jar"
                        codebase="./librairies/"
                        width="640" height="480">
 
              <!-- The following tags are mandatory -->

              <!-- Name of Applet, will be used as name of directory it is saved in, and will uniquely identify it in cache -->
              <param name="al_title"
                     value="MainGame">

              <!-- Main Applet Class -->
              <param name="al_main"
                     value="game.AppletGameContainer">
              <param name="game"
                     value="game.MainGame">

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
                <section id="iconeMenu">
                    <img onclick="show('sac');" src="images/sac.jpg" height="40px"/>
                    <img onclick="show('equipement');" src="images/equipement.jpg" height="40px"/>
                </section>
                <aside id="fenetresInteraction">
                    <table id="inventaire" style="display: none;"></table>
                    <div id="perso" style="display: none;">
                            <div id="armure" class="equip"></div>
                            <div id="arme" class="equip"></div>
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
                 -->
            </section>
			
        </section>

        <footer>
            
            <p><em>&copy; 2012 IUT Anonymousse Team</em><br /><a title="Un Jeu" href="view.do">Un Jeu</a> est un projet étudiant strictement expérimental</p>
			
        </footer>
      
    </div>
<script type="text/javascript" src="js/interaction.js"></script>
</body>
</html>

