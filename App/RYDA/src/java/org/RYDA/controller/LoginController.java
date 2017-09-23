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

    @EJB
    private LoginEJB loginEJB;
    private AppUser appUser;
    private SessionBean sessionBean;

    @EJB
    private AppUserEJB appUserEJB;

    public LoginController() {
        appUser = new AppUser();
    }

    @PostConstruct
    public void init() {
        createDefaultUser();
    }

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

    //method to create Quiz
    public String authenticateUser() {
        appUser = loginEJB.authenticateAppUser(appUser);
        long userId = appUser.getId();
        if (appUser.getId() != null) {
//            sessionBean.setUsername(appUser.getUsername());
//            String appUsername = sessionBean.getUsername();

            Utility.writeSession("username", appUser.getUsername());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            Utility.writeSession("loginTime", dateFormat.format(date).toString());

            String username = (String) Utility.readSession("username");

            String accountName = appUser.getFirstName();
            if (!appUser.getMiddleName().isEmpty()) {
                accountName += " ";
            }
            accountName += appUser.getMiddleName();
            if (!appUser.getLastName().isEmpty()) {
                accountName += " ";
            }
            accountName += appUser.getLastName();
            Utility.writeSession("accountName", accountName);

            FacesContext.getCurrentInstance().addMessage("successForm:successInput",
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Welcome to Administrator portal "
                            + (String) Utility.readSession("accountName")));
            Utility.RedirectUrl("index.xhtml");
            return "index.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Invalid username or password"));
        return "login.xhtml";
    }
}
