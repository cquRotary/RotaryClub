package org.RYDA.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.AppUserEJB;
import org.RYDA.ejbs.LoginEJB;
import org.RYDA.entities.AppUser;
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class LoginController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private LoginEJB loginEJB;
    private AppUser appUser;
    private SessionBean sessionBean;

    @EJB
    private AppUserEJB appUserEJB;

    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public LoginController() {
        appUser = new AppUser();
    }

    @PostConstruct
    public void init() {
        createDefaultUser();
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public LoginEJB getLoginEJB() {
        return loginEJB;
    }

    public void setLoginEJB(LoginEJB loginEJB) {
        this.loginEJB = loginEJB;
    }

    public AppUserEJB getAppUserEJB() {
        return appUserEJB;
    }

    public void setAppUserEJB(AppUserEJB appUserEJB) {
        this.appUserEJB = appUserEJB;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser quiz) {
        this.appUser = appUser;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    //method to set default user
    private void createDefaultUser() {
        appUser = appUserEJB.getAppUserByUsername("admin");
        if (appUser.getId() == null) {
            appUser.setUsername("admin");
            appUser.setPassword("admin+123");
            appUser.setFirstName("Administrator");
            appUserEJB.createAppUser(appUser);
        }
        appUser = new AppUser();
    }

    //method to authenticate user with provided credential
    public String authenticateUser() {
        appUser = loginEJB.authenticateAppUser(appUser);
        long userId = appUser.getId();
        if (appUser.getId() != null) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("username", appUser.getUsername());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            sessionMap.put("loginTime", dateFormat.format(date).toString());
            String username = (String) sessionMap.get("username");

            String accountName = appUser.getFirstName();
            if (!appUser.getMiddleName().isEmpty()) {
                accountName += " ";
            }
            accountName += appUser.getMiddleName();
            if (!appUser.getLastName().isEmpty()) {
                accountName += " ";
            }
            accountName += appUser.getLastName();
            sessionMap.put("accountName", accountName);

            FacesContext.getCurrentInstance().addMessage("successForm:successInput",
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Welcome to Administrator portal "
                            + (String) sessionMap.get("accountName")));
            Utility.RedirectUrl("index.xhtml");
            return "index.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Invalid username or password"));
        return "login.xhtml";
    }
}
