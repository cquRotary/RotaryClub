/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.AppUser;

/**
 *
 * @author riadmin
 */
@Stateless
public class LoginEJB {

    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private AppUser user = new AppUser();
    
    public AppUser authenticateAppUser(AppUser user)
    {
        try
        {
        TypedQuery<AppUser> query = em.createNamedQuery("authenticateUser", AppUser.class).setParameter("username", user.getUsername()).setParameter("password", user.getPassword());
        return query.getSingleResult();
        }
        catch (Exception e)
        {
            return new AppUser();
        }
    }
}
