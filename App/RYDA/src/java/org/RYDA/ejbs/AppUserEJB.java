/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.AppUser;

@Stateless
public class AppUserEJB {

    // ======================================
    // =             Attribute             =
    // ======================================
    
    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;   
    private AppUser appUser = new AppUser();
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public AppUser createAppUser(AppUser appUser){              
        em.persist(appUser);
        return appUser;
    }
    
    //method to query list of users
    public List<AppUser> listAppUsers(){                    
        TypedQuery<AppUser> query = em.createNamedQuery("getAllAppUsers", AppUser.class);
        return query.getResultList();
    }
    
    //methods to delete appUser by id
    public boolean delete(long id) {
        appUser = em.find(AppUser.class, id);
        em.remove(appUser);
        return true;
    }
    
    //methods quering the appUser by appUser id
    public AppUser getAppUserById(long id) {
        TypedQuery<AppUser> query = em.createNamedQuery("getAppUserById", AppUser.class).setParameter("userId", id);
        return query.getSingleResult();
    }
       
    //methods quering the appUser by username
    public AppUser getAppUserByUsername(String username) {
        try {
            TypedQuery<AppUser> query = em.createNamedQuery("getAppUserByUsername", AppUser.class).setParameter("username", username);
            return query.getSingleResult();
        }
        catch (Exception e) {
            return new AppUser();
        }
    }
}
