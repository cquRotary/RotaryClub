package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.AppUser;
import org.RYDA.entities.Quiz;
import org.RYDA.entities.Student;
import org.RYDA.entities.StudentAnswer;
import org.RYDA.entities.StudentQuiz;

@Stateless
public class StudentQuizEJB {

    @PersistenceContext(unitName = "RYDAPU")           
    private EntityManager em;
    private StudentQuiz studentQuiz = new StudentQuiz();
    
    public StudentQuizEJB() {
    }
    
    public StudentQuiz createStudentQuiz(StudentQuiz studentQuiz){              
        em.persist(studentQuiz);
        return studentQuiz;
    }
    
    public List<Student> searchStudent(String name){
        TypedQuery<Student> query = em.createNamedQuery("searchStudentByName", Student.class).setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
