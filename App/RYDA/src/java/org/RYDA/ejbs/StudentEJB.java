package org.RYDA.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.RYDA.entities.AppUser;
import org.RYDA.entities.Student;

@Stateless
public class StudentEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;
    private Student student = new Student();
    
    public StudentEJB() {
    }
    
    public Student createStudent(Student student){              
        em.persist(student);
        return student;
    }
}
