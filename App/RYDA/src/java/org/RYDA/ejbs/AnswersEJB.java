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
import org.RYDA.entities.Answer;

/**
 *
 * @author Kshav
 */
@Stateless
public class AnswersEJB {

    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    
    public Answer createAnswer(Answer answer){
        em.persist(answer);
        return answer;
    }
    
    public List<Answer> listAnswers(){
        TypedQuery<Answer> query = em.createNamedQuery("listAnswers", Answer.class);
        return query.getResultList();
    }
}
