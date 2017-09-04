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
import org.RYDA.entities.Quiz;

/**
 *
 * @author Kshav
 */
@Stateless
public class QuizEJB {

    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    
    public Quiz createQuiz(Quiz quiz){
        em.persist(quiz);
        return quiz;
    }
    
    public List<Quiz> listQuiz(){
        TypedQuery<Quiz> query = em.createNamedQuery("listQuiz", Quiz.class);
        return query.getResultList();
    }
}
