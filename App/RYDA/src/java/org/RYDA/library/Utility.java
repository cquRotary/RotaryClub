package org.RYDA.library;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
        String username = (String) readSession("username");

        if (username == null) {
            username = "";
        }
        return username;
    }

    public static String getAccountName() {
        String accountName = (String) readSession("accountName");

        if (accountName == null) {
            accountName = "";
        }
        return accountName;
    }

    public static String getLoginTime() {
        String loginTime = (String) readSession("loginTime");

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

    public static void writeSession(String objectName, Object object) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put(objectName, object);
    }

    public static Object readSession(String objectName) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return sessionMap.get(objectName);
    }

    public static String readQueryString(String param) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String val = params.get(param);
        if (val == null) {
            return "0";
        }
        if (val.isEmpty()) {
            return "0";
        }
        return val;
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
