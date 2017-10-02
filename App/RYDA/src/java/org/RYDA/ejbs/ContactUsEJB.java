/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.ContactUs;

@Stateless
public class ContactUsEJB {

    // ======================================
    // =             Attribute             =
    // ======================================
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private ContactUs contactUs = new ContactUs();

    // ======================================
    // =           Public Methods           =
    // ======================================
    public ContactUs createContactUs(ContactUs contactUs) {
        em.persist(contactUs);
        return contactUs;
    }

    //method to query and list the answer by questionId
    public List<ContactUs> listContactUs() {
        TypedQuery<ContactUs> query = em.createNamedQuery("listQueries", ContactUs.class);
        return query.getResultList();
    }

    //methods quering the question by question id
    public ContactUs getContactUsById(long id) {
        TypedQuery<ContactUs> query = em.createNamedQuery("getContactUsById", ContactUs.class).setParameter("contactUsId", id);
        return query.getSingleResult();
    }
}
