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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.ContactUsEJB;
import org.RYDA.entities.ContactUs;

@ManagedBean
@RequestScoped
public class ContactUsController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private ContactUsEJB contactUsEJB;        
    private ContactUs contactUs;     
    private List<ContactUs> contactUsList;
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public ContactUsController()
    {
        contactUs = new ContactUs();
        contactUsList = new ArrayList<ContactUs>();
        contactUsEJB = new ContactUsEJB();
    }

    @PostConstruct
    public void init() 
    {
        //contactUsList = contactUsEJB.listContactUs();
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public ContactUsEJB getContactUsEJB() {
        return contactUsEJB;
    }

    public void setContactUsEJB(ContactUsEJB contactUsEJB) {
        this.contactUsEJB = contactUsEJB;
    }

    public ContactUs getContactUs() {
        return contactUs;
    }

    public void setContactUs(ContactUs contactUs) {
        this.contactUs = contactUs;
    }

    public List<ContactUs> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(List<ContactUs> contactUsList) {
        this.contactUsList = contactUsList;
    }
    
    //method to create ContactUs
    public String createContactUs() {
        contactUs = contactUsEJB.createContactUs(contactUs);
        //contactUsList = contactUsEJB.listContactUs();
        //FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "contactUs.xhtml";
    }
    
    //method to view ContactUs detail
    public String viewAction(long id) {
        contactUs = contactUsEJB.getContactUsById(id);
        return "contactUs-details.xhtml";
    }
}
