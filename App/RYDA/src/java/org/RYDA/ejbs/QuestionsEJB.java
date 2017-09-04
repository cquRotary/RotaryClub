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
import org.RYDA.entities.CorrectAnswer;
import org.RYDA.entities.Questions;

/**
 *
 * @author Kshav
 */
@Stateless
public class QuestionsEJB {

    @PersistenceUnit(unitName = "RYDAPU")
    private EntityManager em;
    
    public Questions createQuestion(Questions question){
        em.persist(question);
        return question;
    }
    
    public List<Questions> listQuestions(){
        TypedQuery<Questions> query = em.createNamedQuery("listQuestions", Questions.class);
        return query.getResultList();
    }
}
