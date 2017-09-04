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
import javax.persistence.Query;
import org.RYDA.entities.Administrator;
import org.RYDA.entities.Student;
import org.RYDA.entities.User;

/**
 *
 * @author Kshav
 */
@Stateless
public class UsersEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;               
        
    private List<Administrator> adminList = new ArrayList<Administrator>();          
    private List<Student> studentList = new ArrayList<Student>();          
    
    public List<User> listUsers(){                    
        Query query = em.createNamedQuery("listUsers");
        return query.getResultList();
    }
    
    public List<Administrator> listAdministrator(){           
        Query query = em.createNamedQuery("listAdmin");       
        return  query.getResultList();
    }  
    
    public List<Student> listStudent(){               
        Query query = em.createNamedQuery("listStudent");
        return query.getResultList();
        
    }  
    
    public Administrator createAdministrator(Administrator admin){              
        em.persist(admin);
        return admin;
    }
    
    public Student createStudent(Student student){              
        em.persist(student);
        return student;
    }
}
