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
import org.RYDA.entities.StudentQuiz;

/**
 *
 * @author Kshav
 */
@Stateless
public class StudentQuizEJB {

    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    
    public StudentQuiz createStudentQuiz(StudentQuiz studentQuiz){
        em.persist(studentQuiz);
        return studentQuiz;
    }
    
    public List<StudentQuiz> listStudentQuiz(){
        TypedQuery<StudentQuiz> query = em.createNamedQuery("listStudentQuiz", StudentQuiz.class);
        return query.getResultList();
    }
}
