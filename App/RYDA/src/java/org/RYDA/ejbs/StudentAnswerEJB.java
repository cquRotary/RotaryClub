package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.AppUser;
import org.RYDA.entities.Student;
import org.RYDA.entities.StudentAnswer;

@Stateless
public class StudentAnswerEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;
    
    public StudentAnswerEJB() {
    }
    
    public StudentAnswer createStudentAnswer(StudentAnswer studentAnswer){              
        em.persist(studentAnswer);
        return studentAnswer;
    }
    
    public List<StudentAnswer> submitStudentAnswerList(List<StudentAnswer> studentAnswerList){              
        em.persist(studentAnswerList);
        return studentAnswerList;
    }
    
    public List<StudentAnswer> getAnswersByStudentId(long studentId) {
        TypedQuery<StudentAnswer> query = em.createNamedQuery("getAnswersByStudentId", StudentAnswer.class).setParameter("studentId", studentId);
        return query.getResultList();
    }
}
