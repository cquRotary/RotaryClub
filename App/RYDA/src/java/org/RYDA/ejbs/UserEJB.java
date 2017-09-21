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
import org.RYDA.entities.User;

/**
 *
 * @author Kshav
 */
@Stateless
public class UserEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;   
    private User user = new User();
    
    public User createUser(User user){              
        em.persist(user);
        return user;
    }
    
    public List<User> listUsers(){                    
        TypedQuery<User> query = em.createNamedQuery("getAllUsers", User.class);
        return query.getResultList();
    }
    
    //methods to delete user by id
    public boolean delete(long id) {
        user = em.find(User.class, id);
        em.remove(user);
        return true;
    }
    
    //methods quering the user by user id
    public User getUserById(long id) {
        TypedQuery<User> query = em.createNamedQuery("getUserById", User.class).setParameter("userId", id);
        return query.getSingleResult();
    }
       
    
}
