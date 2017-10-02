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
import org.RYDA.entities.Question;
import org.RYDA.entities.Quiz;

/**
 *
 * @author Kshav
 */
@Stateless
public class QuizEJB {

    // ======================================
    // =             Attribute             =
    // ======================================
    
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private Quiz quiz = new Quiz();
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public Quiz createQuiz(Quiz quiz){
        if (quiz.getId() != null)
        {
            em.merge(quiz);
        }
        else{
            em.persist(quiz);
        }
        return quiz;
    }
    
    public void createAllQuizzes()
    {
        if (!listQuiz().isEmpty())
            return;
        List<Quiz> quizList = new ArrayList<>();
        quiz = new Quiz("Quiz 1");
        quizList.add(quiz);
        
        quiz = new Quiz("Quiz 2");
        quizList.add(quiz);
        
        quiz = new Quiz("Quiz 3");
        quizList.add(quiz);
        
        for (Quiz q : quizList)
        {
            createQuiz(q);
        }
    }
    
    public List<Quiz> listQuiz(){
        TypedQuery<Quiz> query = em.createNamedQuery("getAllQuizzes", Quiz.class);
        return query.getResultList();
    }
    
    //methods to delete quiz by id
    public boolean delete(long id) {
        quiz = em.find(Quiz.class, id);
        em.remove(quiz);
        return true;
    }
    
    //methods quering the quiz by quiz id
    public Quiz getQuizById(long id) {
        TypedQuery<Quiz> query = em.createNamedQuery("getQuizById", Quiz.class).setParameter("quizId", id);
        return query.getSingleResult();
    }
    
    public List<Quiz> searchQuiz(String title){
        TypedQuery<Quiz> query = em.createNamedQuery("searchQuizByTitle", Quiz.class).setParameter("title", "%" + title + "%");
        return query.getResultList();
    }
}
