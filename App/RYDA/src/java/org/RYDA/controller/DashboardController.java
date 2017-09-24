package org.RYDA.controller;

import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class DashboardController {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String loginUsername;
    private String loginAccountName;
    private String loginTime;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public DashboardController() {
    }

    @PostConstruct
    public void init() {
        Utility.checkSession();
        this.loginUsername = Utility.getUsername();
        this.loginAccountName = Utility.getAccountName();
        this.loginTime = Utility.getLoginTime();
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================
    public String getLoginUsername() {
        return this.loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginAccountName() {
        return this.loginAccountName;
    }

    public void setLoginAccountName(String loginAccountName) {
        this.loginAccountName = loginUsername;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void logout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();

        Utility.RedirectUrl("login.xhtml");
    }
}
