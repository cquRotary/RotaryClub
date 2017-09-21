package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.AppUserEJB;
import org.RYDA.entities.AppUser;

@ManagedBean
@RequestScoped
public class AppUserController {

    @EJB
    private AppUserEJB appUserEJB;      
    private AppUser appUser;
    private List<AppUser> appUserList;
    
    public AppUserController() {
        appUser = new AppUser();
        appUserList = new ArrayList<AppUser>();
    }
         
    @PostConstruct
    public void init() 
    {
        appUserList = appUserEJB.listAppUsers();
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

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

    public void setAppUserList(List<AppUser> appUserList) {
        this.appUserList = appUserList;
    }
    
    //method to create AppUser
    public String createAppUser() {
        appUser = appUserEJB.createAppUser(appUser);
        appUserList = appUserEJB.listAppUsers();
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "user-list.xhtml";
    }
    
    // delete appUser
    public String deleteAction(long id) {
        boolean success = appUserEJB.delete(id);
        appUserList = appUserEJB.listAppUsers();
        if (success){
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "user-list.xhtml";
    }
    
    /// view details of appUser
    public String viewAction(long id) {
        appUser = appUserEJB.getAppUserById(id);
        return "user-details.xhtml";
    }
}