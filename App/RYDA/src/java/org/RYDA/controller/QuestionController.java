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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.CorrectAnswerEJB;
import org.RYDA.ejbs.QuestionEJB;
import org.RYDA.entities.CorrectAnswer;
import org.RYDA.entities.Question;

@ManagedBean
@javax.faces.bean.RequestScoped
public class QuestionController {
    @EJB
    private QuestionEJB questionEJB;        
    private Question question;     
    private List<Question> questionList;        
    
    public QuestionController () {
        question = new Question();
        questionList = new ArrayList<Question>();
    }
    
    @PostConstruct
    public void init() {
        questionList = questionEJB.listQuestions();
    }
    
    public String addQuestion(){                
        question = questionEJB.createQuestion(question);            
        questionList = questionEJB.listQuestions();
        return "question-list.xhtml";            
    }

    public QuestionEJB getQuestionsEJB() {
        return questionEJB;
    }

    public void setQuestionsEJB(QuestionEJB questionEJB) {
        this.questionEJB = questionEJB;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
    
    //method to create Question
    public String createQuestion() {
        question = questionEJB.createQuestion(question);
        questionList = questionEJB.listQuestions();
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "question-list.xhtml";
    }
    
    // delete customer
    public String deleteAction(long id) {
        boolean success = questionEJB.delete(id);
        questionList = questionEJB.listQuestions();
        if (success){
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "question-list.xhtml";
    }
    
    /// view products on customer
    public String viewAction(long id) {
        question = questionEJB.getQuestionById(id);
        return "question-details.xhtml";
    }
}
