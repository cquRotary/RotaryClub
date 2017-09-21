package org.RYDA.library;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Utility {

    public static boolean isValidSession() {
        if (getUsername().isEmpty()) {
            return false;
        }
        return true;
    }

    public static void checkSession() {
        try {
            if (!Utility.isValidSession()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RYDA/admin/login.xhtml");
            }
        } catch (Exception e) {

        }
    }

    public static String getUsername() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String username = (String) sessionMap.get("username");

        if (username == null) {
            username = "";
        }
        return username;
    }

    public static String getAccountName() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String accountName = (String) sessionMap.get("accountName");

        if (accountName == null) {
            accountName = "";
        }
        return accountName;
    }

    public static String getLoginTime() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String loginTime = (String) sessionMap.get("loginTime");

        if (loginTime == null) {
            loginTime = "";
        }
        return loginTime;
    }

    public static void RedirectUrl(String url) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {

        }
    }
}
