/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package command;

/**
 *
 * @author Yan
 */
public class ActionFlow {
    private String name;
    private String path;
    private boolean redirect;

    public ActionFlow(String name, String path, boolean redirect) {
        this.name = name;
        this.path = path;
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    

}
