package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.RYDA.entities.AppUser;
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
}
