var name = "Test";
var ongletCourant = "general";


function ajoutMsg( user, msg, onglet){
    var div = document.createElement('div');
    var span1 = document.createElement('span');
    var span2 = document.createElement('span');
    span1.innerHTML = user+" :";
    span2.innerHTML = msg;
    span1.className = "user";
    div.appendChild(span1);
    div.appendChild(span2);
    document.getElementById(onglet).appendChild(div);
}

function envoi(){
    var msg = document.getElementById('msg').value;
    document.getElementById('msg').value = "";
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    applet.goMsg(msg);
    return false;
}

function switchOnglet ( ong ){
    switch ( ong ){
	case "general" :
	    document.getElementById('general').style.display = "";
	    document.getElementById('guild').style.display = "none";
	    document.getElementById('whisp').style.display = "none";
	    break;

	case "whisp" :
	    document.getElementById('general').style.display = "none";
	    document.getElementById('guild').style.display = "none";
	    document.getElementById('whisp').style.display = "";
	    break;

	case "guild" :
	    document.getElementById('general').style.display = "none";
	    document.getElementById('guild').style.display = "";
	    document.getElementById('whisp').style.display = "none";
	    break;
    }
}



function sysout(){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    applet.testJavaScript();
}
function equiperArmure(a){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    applet.equiperArmure(a);
}
function desequiperArmure(){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    applet.desequiperArmure();
}
function voirInventaire(){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    var inv = applet.voirInventaire();
    $("#butInv").hide();
    $("#inventaire").html(inv);
}
function desactiverBoutons() {
    $("input").attr("disabled", "disabled");
}
function activerBoutons() {
    $("input").removeAttr("disabled");
}
function voirListeJoueurs(){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    var liste = applet.afficherListeJoueurMapHTML();
    $("#listeJoueur").html(liste);
}

function inviter(id){
    var appletloader = document.getElementById('unJeu');
    var applet = appletloader.getApplet().getGame();
    applet.inviterAuGroupeByID(id);
    var liste = applet.afficherListeJoueurMapHTML();
    $("#listeJoueur").html(liste);
}