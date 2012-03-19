/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.util.regex.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import command.*;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Yan
 */
public class FrontController extends HttpServlet {

    private static final long serialVersionUID = -2L;

    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
       
        ActionFlow flow;
        String operation;
        Command cmd;
        
        flow = new ActionFlow("Erreur",req.getContextPath()+"/erreur.html",true);

        operation = getOperation(req.getRequestURI());
        
        cmd = CommandManager.getCommand(operation);
        
        if (cmd != null) {
            flow = cmd.actionPerform(req);
        }

        if (flow.isRedirect() == true) {            
            res.sendRedirect(flow.getPath());
        } else {
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/jsp/" + flow.getPath());
            rd.forward(req, res);
        } // doPost
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException,
            IOException {
        doPost(req, res);
    } // doGet

    private String getOperation(String requestURI) {
        Pattern p = Pattern.compile(".*/(.*).do");
        Matcher m = p.matcher(requestURI);
        if (m.find() && m.groupCount() == 1) {
            return m.group(1);
        } else {
            return null;
        }
    }
} // class