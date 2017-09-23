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
import org.RYDA.ejbs.AnswersEJB;
import org.RYDA.ejbs.QuestionEJB;
import org.RYDA.entities.Answer;
import org.RYDA.entities.Question;

@ManagedBean
@RequestScoped
public class AnswerController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private AnswersEJB answerEJB;
    private Answer answer;     
    private List<Answer> answerList;
    private Question question;
    
    @EJB
    private QuestionEJB questionEJB;
    private long questionId;

    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public AnswerController()
    {
        question = new Question();
        answer = new Answer();
        answerList = new ArrayList<Answer>();
    }
    
    @PostConstruct
    public void init() {
        answerList = answerEJB.listAnswers(questionId);
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public AnswersEJB getAnswerEJB() {
        return answerEJB;
    }

    public void setAnswerEJB(AnswersEJB answerEJB) {
        this.answerEJB = answerEJB;
    }
    
    public QuestionEJB getQuestionEJB() {
        return questionEJB;
    }

    public void setQuestionEJB(QuestionEJB questionEJB) {
        this.questionEJB = questionEJB;
    }
    
    public long getQuestionId()
    {
        return this.questionId;
    }
    
    public void setQuestionId(long questionId)
    {
        this.questionId = questionId;
    }
    
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
    
    //method to create Answer
    public String createAnswer(long questionId) {
        System.out.println("QuestionId = " + questionId);
        setQuestionId(questionId);
        answer.setQuestionId(questionId);
        answer = answerEJB.createAnswer(answer);
        answerList = answerEJB.listAnswers(questionId);
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Save record added successfully" + questionId));
        question = questionEJB.getQuestionById(questionId);
        return "question.xhtml";
    }
    
    // Method to delete answer and redirect to question setup page
    public String deleteAction(long id, long questionId) {
        setQuestionId(questionId);
        answer.setQuestionId(questionId);
        boolean success = answerEJB.delete(id);
        answerList = answerEJB.listAnswers(questionId);
        if (success){
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "question.xhtml";
    }
    
    /// view answer detail
    public String viewAction(long id) {
        answer = answerEJB.getAnswerById(id);
        return "answer-details.xhtml";
    }
}
