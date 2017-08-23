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

/**
 *
 * @author Kshav
 */
@Stateless
public class AnswersEJB {

    @PersistenceUnit(unitName = "RYDAPU")
    private EntityManager em;
    
    public Answers createAnswer(Answers answer){
        em.persist(answer);
        return answer;
    }
    
    public List<Answers> listAnswers(){
        TypedQuery<Answers> query = em.createNamedQuery("listAnswers", Answers.class);
        return query.getResultList();
    }
}
