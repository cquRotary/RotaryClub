/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.RYDA.ejbs.QuizEJB;
import org.RYDA.entities.Quiz;

/**
 *
 * @author Kshav
 */
@Named(value = "quizController")
@RequestScoped
public class QuizController {

    @EJB
    private QuizEJB quizEJB;        
    private Quiz quiz = new Quiz();     
    private List<Quiz> quizList = new ArrayList<Quiz>();        
    
    public String addQuestion(){                
        quiz = quizEJB.createQuiz(quiz);            
        quizList = quizEJB.listQuiz();
        return "quizList.xhtml";            
    }

    public QuizEJB getQuizEJB() {
        return quizEJB;
    }

    public void setQuizEJB(QuizEJB quizEJB) {
        this.quizEJB = quizEJB;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }
    
    
    
}
