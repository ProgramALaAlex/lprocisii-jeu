package listener;

import javax.servlet.*;

/**
 * @author Yan
 */
public final class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        try {
            servletContext.setAttribute("IP_SERVEUR", "localhost");
            servletContext.setAttribute("REGISTRY_PORT", "25565");
            servletContext.setAttribute("REGISTRY_NAME", "RMI_JEU");
        } catch (Exception e) {
            servletContext.log("Pb attribut liste: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String IP_SERVEUR = (String) servletContext.getAttribute("IP_SERVEUR");
        int REGISTRY_PORT = (Integer) servletContext.getAttribute("REGISTRY_PORT");
        String REGISTRY_NAME = (String) servletContext.getAttribute("REGISTRY_NAME");
        servletContext.removeAttribute("IP_SERVEUR");
        servletContext.removeAttribute("REGISTRY_PORT");
        servletContext.removeAttribute("REGISTRY_NAME");
    }
}
