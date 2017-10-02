/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.QuizEJB;
import org.RYDA.entities.Quiz;

@ManagedBean
@RequestScoped
public class QuizController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private QuizEJB quizEJB;        
    private Quiz quiz;     
    private List<Quiz> quizList;
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public QuizController()
    {
        quiz = new Quiz();
        quizList = new ArrayList<Quiz>();
        quizEJB = new QuizEJB();
    }

    @PostConstruct
    public void init() 
    {
        quizList = quizEJB.listQuiz();
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
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
    
    //method to delete Quiz
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
    
    //method to view Quiz detail
    public String viewAction(long id) {
        quiz = quizEJB.getQuizById(id);
        return "quiz-details.xhtml";
    }
    
    public String searchQuiz(String title) {
        quizList = quizEJB.searchQuiz(title);
        if (quizList.size() <= 0) {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No results Found"));
        } else {
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", " " + quizList.size() + " record(s) found"));
        }
        return "quiz-list.xhtml";
    }
}
