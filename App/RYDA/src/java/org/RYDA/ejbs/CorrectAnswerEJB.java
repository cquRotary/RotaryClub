/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import org.RYDA.entities.Answers;
import org.RYDA.entities.CorrectAnswer;

/**
 *
 * @author Kshav
 */
@Stateless
public class CorrectAnswerEJB {

    @PersistenceUnit(unitName = "RYDAPU")
    private EntityManager em;
    
    public CorrectAnswer createCorrectAnswer(CorrectAnswer correctAnswer){
        em.persist(correctAnswer);
        return correctAnswer;
    }
    
    public List<CorrectAnswer> listCorrectAnswer(){
        TypedQuery<CorrectAnswer> query = em.createNamedQuery("correctAnswer", CorrectAnswer.class);
        return query.getResultList();
    }
}
