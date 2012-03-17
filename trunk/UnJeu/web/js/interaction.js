/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var lastSelected = null;

function show ( value )
{
    if( lastSelected == value)
    {
        document.getElementById('inventaire').style.display = "none";
        document.getElementById('perso').style.display = "none";
        lastSelected = null;
    }
    else if( value == "sac" )
    {
        document.getElementById('inventaire').style.display = "";
        document.getElementById('perso').style.display = "none";
        lastSelected = value;
    }
    else if ( value == "equipement" ) 
    {
        document.getElementById('inventaire').style.display = "";
        document.getElementById('perso').style.display = "";
        lastSelected = value;
    }
}


$( init );

	var inventaire;
	var objet;
	
	function init(){
		inventaire = new Array();
		for(var i = 0; i<17; i++){
			inventaire[i] = -1;
		}
		
		// test
		objet = new Array();
		objet[0] = new Array();
		objet[0]['url'] = "images/icone/attaque.gif";
		objet[1] = new Array();
		objet[1]['url'] = "images/icone/force.gif";
		
		inventaire[0] = 0;
		inventaire[1] = 1;
		
		//creation de l'inventaire
		for(var i = 0; i < 4; i++){
			var tr = document.createElement('tr');
			for(var j = 0; j < 4; j++){
				var td = document.createElement('td');
				td.className = "elt_inventaire";
				td.id = "inv_"+(i*4+j);
				tr.appendChild(td);
			}
			document.getElementById('inventaire').appendChild(tr);
		}
		
		
		//creation des items
		for(var i = 0; i<17; i++){
			if(inventaire[i] != -1){
				var img = document.createElement('img');
				img.src = objet[inventaire[i]]['url'];
				img.className = "item";
				img.id = "obj"+inventaire[i];
				document.getElementById('inv_'+i).appendChild(img);
			}
		}
		
		$('.item').draggable( {
			helper: myHelper
		} );
		
		$('.elt_inventaire').droppable( {
			drop: handleDropEvent
		} );
		
		$('.equip').droppable( {
			drop: dropInventaire
		} );
	}
	
	function dropInventaire( event, ui ) {
		var draggable = ui.draggable;
		
		if(this.getElementsByTagName('img').length > 0){
			var old = this.getElementsByTagName('img')[0];
			this.getElementsByTagName('img')[0].parentNode.removeChild(this.getElementsByTagName('img')[0]);
			var img1 = document.getElementById(draggable.attr('id')).cloneNode(true);
			img1.className = "item2";
			this.appendChild(img1);
			
			for(var i = 0; i < inventaire.length ;i++){
				if(inventaire[i] == draggable.attr('id').replace('obj', '')){
					inventaire[i] = old.id.replace('obj', '');
					document.getElementById(draggable.attr('id')).parentNode.appendChild(old);
					document.getElementById(draggable.attr('id')).parentNode.removeChild(document.getElementById(draggable.attr('id')));
					break;
				}
			}
			
			return null;
		}
			
		
		var img = document.getElementById(draggable.attr('id')).cloneNode(true);
		img.className = "item2";
		this.appendChild(img);	
		for(var i = 0; i < inventaire.length ;i++){
			if(inventaire[i] == draggable.attr('id').replace('obj', '')){
				inventaire[i] = -1;
				document.getElementById(draggable.attr('id')).parentNode.removeChild(document.getElementById(draggable.attr('id')));
				break;
			}
		}
		
		$('.item2').draggable( {
			helper: myHelper
		} );
	}
	
	function handleDropEvent( event, ui ) {
		var draggable = ui.draggable;

		//On teste si on drop depuis l'inventaire
		if(draggable.attr('className') == "item2 ui-draggable"){
			var img = document.getElementById(draggable.attr('id')).cloneNode(true);
			img.className = "item";
			
			document.getElementById("obj"+draggable.attr('id').replace('obj', '')).parentNode.removeChild(document.getElementById("obj"+draggable.attr('id').replace('obj', '')));
			
			inventaire[this.id.replace("inv_", "")] = draggable.attr('id').replace('obj', '');
			this.appendChild(img);	
			$('.item').draggable( {
				helper: myHelper
			} );
		
		
			return null;
		}
			
		//On test si on drop dans la meme case
		for(var i = 0; i < inventaire.length ;i++){
			if(inventaire[i] == draggable.attr('id').replace('obj', '')){
				if(this.id.replace("inv_", "") == i)
					return null;
			}
		}
	
		//On test si on drop dans une case deja occupé
		if(this.getElementsByTagName('img').length > 0){
			var precedant;
			
			for(var i = 0; i < 16; i++){
				if( inventaire[i] == draggable.attr('id').replace('obj', ''))
					precedant = i;
			}
			var img1 = document.getElementById(draggable.attr('id')).cloneNode(true);
			var img2 = this.getElementsByTagName('img')[0].cloneNode(true);
			document.getElementById(draggable.attr('id')).parentNode.removeChild(document.getElementById(draggable.attr('id')));
			this.getElementsByTagName('img')[0].parentNode.removeChild(this.getElementsByTagName('img')[0]);
			this.appendChild(img1);
			document.getElementById('inv_'+precedant).appendChild(img2);
			
			//on met a jour le tableau
			inventaire[precedant] = img2.id.replace('obj', '');
			inventaire[this.id.replace('inv_', '')] = img1.id.replace('obj', '');
			
			
			$('.item').draggable( {
				helper: myHelper
			} );
			
			return null;
		}
		

		//On deplace l'element
		var img = document.getElementById(draggable.attr('id')).cloneNode(true);
		for(var i = 0; i < inventaire.length ;i++){
			if(inventaire[i] == draggable.attr('id').replace('obj', '')){
				inventaire[i] = -1;
				document.getElementById("obj"+draggable.attr('id').replace('obj', '')).parentNode.removeChild(document.getElementById("obj"+draggable.attr('id').replace('obj', '')));
				break;
			}
		}
		inventaire[this.id.replace("inv_", "")] = draggable.attr('id').replace('obj', '');
		this.appendChild(img);	
		$('.item').draggable( {
			helper: myHelper
		} );
		
	}
	
	function myHelper( event ) {
		return '<img src="'+objet[this.id.replace('obj', '')]['url']+'"/>';
	}