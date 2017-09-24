/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.Answer;
import org.RYDA.entities.Quiz;

@Stateless
public class AnswersEJB {

    // ======================================
    // =             Attribute             =
    // ======================================
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private Answer answer = new Answer();

    // ======================================
    // =           Public Methods           =
    // ======================================
    public Answer createAnswer(Answer answer) {
        em.persist(answer);
        return answer;
    }

    //method to query and list the answer by questionId
    public List<Answer> listAnswers(long questionId) {
        TypedQuery<Answer> query = em.createNamedQuery("getAnswersByQuestionId", Answer.class).setParameter("questionId", questionId);
        return query.getResultList();
    }

    //methods to delete question by id
    public boolean delete(long id) {
        answer = em.find(Answer.class, id);
        em.remove(answer);
        return true;
    }

    //methods quering the question by question id
    public Answer getAnswerById(long id) {
        TypedQuery<Answer> query = em.createNamedQuery("getAnswerById", Answer.class).setParameter("answerId", id);
        return query.getSingleResult();
    }
}
