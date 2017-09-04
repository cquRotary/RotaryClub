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
import org.RYDA.entities.Question;

@Stateless
public class QuestionEJB {
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private Question question = new Question();
    
    //Public methods    
    public Question createQuestion(Question question){
        em.persist(question);
        return question;
    }
    
    //method to query all the questions
    public List<Question> listQuestions(){
        TypedQuery<Question> query = em.createNamedQuery("listQuestions", Question.class);
        return query.getResultList();
    }
    
    //methods to delete question by id
    public boolean delete(long id) {
        question = em.find(Question.class, id);
        em.remove(question);
        return true;
    }
    
    //methods quering the question by question id
    public Question getQuestionById(long id) {
        TypedQuery<Question> query = em.createNamedQuery("getQuestionById", Question.class).setParameter("questionId", id);
        return query.getSingleResult();
    }
}
