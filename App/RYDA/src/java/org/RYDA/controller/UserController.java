/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.UserEJB;
import org.RYDA.entities.User;

/**
 *
 * @author Kshav
 */
@ManagedBean
@Named(value = "usersController")
@RequestScoped
public class UserController {

    @EJB
    private UserEJB userEJB;      
    private User user;
    private List<User> userList;

    public UserController() {
        user = new User();
        userList = new ArrayList<User>();
    }
    
        
    @PostConstruct
    public void init() 
    {
        userList = userEJB.listUsers();
    }
    
    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    //method to create User
    public String createUser() {
        user = userEJB.createUser(user);
        userList = userEJB.listUsers();
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "user-list.xhtml";
    }
    
    // delete user
    public String deleteAction(long id) {
        boolean success = userEJB.delete(id);
        userList = userEJB.listUsers();
        if (success){
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "user-list.xhtml";
    }
    
    /// view details of user
    public String viewAction(long id) {
        user = userEJB.getUserById(id);
        return "user-details.xhtml";
    }
    
    

    
}
