/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import beans.InventaireBean;
import beans.InventaireDB;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Loic
 */
public class InventaireCommand implements Command{

    @Override
    public String getCommandName() {
        return "inventaireCommand";
    }

    @Override
    public ActionFlow actionPerform(HttpServletRequest request) {
        String vue = "index";
        
        InventaireDB idb = new InventaireDB();
        InventaireBean inventaire = idb.jsonInventaire(request.getSession().getAttribute("id")+"");
        inventaire.setSlot1_idObjet(new Integer(request.getParameter("inv[0]")));
        inventaire.setSlot1_qte(new Integer(request.getParameter("qte[0]")));
        
        inventaire.setSlot2_idObjet(new Integer(request.getParameter("inv[1]")));
        inventaire.setSlot2_qte(new Integer(request.getParameter("qte[1]")));
        
        inventaire.setSlot3_idObjet(new Integer(request.getParameter("inv[2]")));
        inventaire.setSlot3_qte(new Integer(request.getParameter("qte[2]")));
        
        inventaire.setSlot4_idObjet(new Integer(request.getParameter("inv[3]")));
        inventaire.setSlot4_qte(new Integer(request.getParameter("qte[3]")));
        
        inventaire.setSlot5_idObjet(new Integer(request.getParameter("inv[4]")));
        inventaire.setSlot5_qte(new Integer(request.getParameter("qte[4]")));
        
        inventaire.setSlot6_idObjet(new Integer(request.getParameter("inv[5]")));
        inventaire.setSlot6_qte(new Integer(request.getParameter("qte[5]")));
        
        inventaire.setSlot7_idObjet(new Integer(request.getParameter("inv[6]")));
        inventaire.setSlot7_qte(new Integer(request.getParameter("qte[6]")));
        
        inventaire.setSlot8_idObjet(new Integer(request.getParameter("inv[7]")));
        inventaire.setSlot8_qte(new Integer(request.getParameter("qte[7]")));
        
        inventaire.setSlot9_idObjet(new Integer(request.getParameter("inv[8]")));
        inventaire.setSlot9_qte(new Integer(request.getParameter("qte[8]")));
        
        inventaire.setSlot10_idObjet(new Integer(request.getParameter("inv[9]")));
        inventaire.setSlot10_qte(new Integer(request.getParameter("qte[9]")));
        
        inventaire.setSlot11_idObjet(new Integer(request.getParameter("inv[10]")));
        inventaire.setSlot11_qte(new Integer(request.getParameter("qte[10]")));
        
        inventaire.setSlot12_idObjet(new Integer(request.getParameter("inv[11]")));
        inventaire.setSlot12_qte(new Integer(request.getParameter("qte[11]")));
        
        inventaire.setSlot13_idObjet(new Integer(request.getParameter("inv[12]")));
        inventaire.setSlot13_qte(new Integer(request.getParameter("qte[12]")));
        
        inventaire.setSlot14_idObjet(new Integer(request.getParameter("inv[13]")));
        inventaire.setSlot14_qte(new Integer(request.getParameter("qte[13]")));
        
        inventaire.setSlot15_idObjet(new Integer(request.getParameter("inv[14]")));
        inventaire.setSlot15_qte(new Integer(request.getParameter("qte[14]")));
        
        inventaire.setSlot16_idObjet(new Integer(request.getParameter("inv[15]")));
        inventaire.setSlot16_qte(new Integer(request.getParameter("qte[15]")));
        
        idb.majInvenaire(inventaire);
        
        return new ActionFlow(vue, vue + ".jsp", false);
    }
    
}
