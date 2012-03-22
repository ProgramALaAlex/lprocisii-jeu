<%@page import="beans.InventaireBean"%>
<%@page import="beans.InventaireDB"%>
inventaire = new Array();
		for(var i = 0; i<17; i++){
			inventaire[i] = -1;
		}
                
                <%
                    InventaireDB ddb = new InventaireDB();
                    InventaireBean ivv = ddb.jsonInventaire(session.getAttribute("id")+"");
                   
                %>
                <%= ivv.getJs() %>
		
                document.getElementById('inventaire').innerHTML = "";
                
                
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
		
		$('.equip2').droppable( {
			drop: dropArme
		} );
                
                $('.equip1').droppable( {
			drop: dropArmure
		} );