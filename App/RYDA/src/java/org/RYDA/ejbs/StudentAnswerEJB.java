package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.RYDA.entities.AppUser;
import org.RYDA.entities.Student;
import org.RYDA.entities.StudentAnswer;

@Stateless
public class StudentAnswerEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;
    private Student student = new Student();
    
    public StudentAnswerEJB() {
    }
    
    public List<StudentAnswer> submitStudentAnswerList(List<StudentAnswer> studentAnswerList){              
        em.persist(studentAnswerList);
        return studentAnswerList;
    }
}
