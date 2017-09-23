package org.RYDA.library;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Utility {

    // ======================================
    // =           Public Methods           =
    // ======================================
    
    //method to check valid session
    public static boolean isValidSession() {
        if (getUsername().isEmpty()) {
            return false;
        }
        return true;
    }

    //method to check session
    public static void checkSession() {
        try {
            if (!Utility.isValidSession()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RYDA/admin/login.xhtml");
            }
        } catch (Exception e) {

        }
    }

    //method to get Username
    public static String getUsername() {
        String username = (String) readSession("username");

        if (username == null) {
            username = "";
        }
        return username;
    }

    //method to get Account name
    public static String getAccountName() {
        String accountName = (String) readSession("accountName");

        if (accountName == null) {
            accountName = "";
        }
        return accountName;
    }

    //method to get Login time
    public static String getLoginTime() {
        String loginTime = (String) readSession("loginTime");

        if (loginTime == null) {
            loginTime = "";
        }
        return loginTime;
    }

    //method to redirect to next page
    public static void RedirectUrl(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {

        }
    }
    
    //method to write session
    public static void writeSession(String objectName, Object object)
    {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put(objectName, object);
    }
    
    //method to read session
    public static Object readSession(String objectName)
    {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return sessionMap.get(objectName);
    }
}
