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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.QuizEJB;
import org.RYDA.entities.Quiz;

@ManagedBean
@RequestScoped
public class QuizController {

    @EJB
    private QuizEJB quizEJB;        
    private Quiz quiz;     
    private List<Quiz> quizList;
    
    public QuizController()
    {
        quiz = new Quiz();
        quizList = new ArrayList<Quiz>();
    }
    
    public String addQuiz(){                
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
    
    //method to create Quiz
    public String createQuiz() {
        quiz = quizEJB.createQuiz(quiz);
        quizList = quizEJB.listQuiz();
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "quiz-list.xhtml";
    }
    
    // delete customer
    public String deleteAction(long id) {
        boolean success = quizEJB.delete(id);
        quizList = quizEJB.listQuiz();
        if (success){
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "quiz-list.xhtml";
    }
    
    /// view products on customer
    public String viewAction(long id) {
        quiz = quizEJB.getQuizById(id);
        return "quiz-details.xhtml";
    }
}
