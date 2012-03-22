var name = "Test";
var ongletCourant = "general";
var nOk = false;

function getApplet(){
    var appletloader = document.getElementById('unJeu');
    return appletloader.getApplet().getGame();
}

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
    getApplet().goMsg(msg);
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
    getApplet().testJavaScript();
}
function equiperArmure(a){
    getApplet().equiperArmure(a);
}
function desequiperArmure(){
    getApplet().desequiperArmure();
}
function voirInventaire(){
    var inv = getApplet().voirInventaire();
    $("#butInv").hide();
    $("#inventaire").html(inv);
}
function desactiverBoutons() {
    nOk = true;
    $("input").attr("disabled", "disabled");
}
function activerBoutons() {
    nOk = false;
    $("input").removeAttr("disabled");
}
function voirListeJoueurs(){
    var liste = getApplet().afficherListeJoueurMapHTML();
    $("#listeJoueur").html(liste);
}

function inviter(id){
    getApplet().inviterAuGroupeByID(id);
    voirListeJoueurs();
}

function creerGroupe(nomGroupe){
    getApplet().creerGroupe(nomGroupe);
    voirListeJoueurs();
}

function voirListeInvitations(){
    var liste = getApplet().afficherListeInvitationHTML();
    $("#listeInvitation").html(liste);
}

function rejoindre(id){
    getApplet().rejoindreGroupe(id);
    voirListeJoueurs();
    voirListeInvitations();
}

function nepasrejoindre(id){
    getApplet().refuserGroupe(id);
    voirListeJoueurs();
    voirListeInvitations();
}