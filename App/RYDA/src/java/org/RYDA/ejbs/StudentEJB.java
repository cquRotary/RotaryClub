package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.Quiz;
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
    
    public List<Student> getAllStudents()
    {
        TypedQuery<Student> query = em.createNamedQuery("getAllStudents", Student.class);
        return query.getResultList();
    }
    
    //methods quering the quiz by quiz id
    public Student getStudentById(long id) {
        TypedQuery<Student> query = em.createNamedQuery("getStudentById", Student.class).setParameter("studentId", id);
        return query.getSingleResult();
    }
    
    //methods quering the quiz by quiz id
    public List<Student> getStudentsByQuizId(long quizId) {
        TypedQuery<Student> query = em.createNamedQuery("getStudentsByQuizId", Student.class).setParameter("quizId", quizId);
        return query.getResultList();
    }
    
    public List<Student> searchStudent(String name){
        TypedQuery<Student> query = em.createNamedQuery("searchStudentByName", Student.class).setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}