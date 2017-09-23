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
import org.RYDA.ejbs.CorrectAnswerEJB;
import org.RYDA.entities.CorrectAnswer;

@Named(value = "correctAnswerController")
@RequestScoped
public class CorrectAnswerController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private CorrectAnswerEJB correctAnswerEJB;        
    private CorrectAnswer correctAnswer = new CorrectAnswer();     
    private List<CorrectAnswer> correctAnswersList = new ArrayList<CorrectAnswer>();        
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public String addCorrectAnswer(){                
        correctAnswer = correctAnswerEJB.createCorrectAnswer(correctAnswer);            
        correctAnswersList = correctAnswerEJB.listCorrectAnswer();
        return "correctAnswerList.xhtml";            
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public CorrectAnswerEJB getCorrectAnswerEJB() {
        return correctAnswerEJB;
    }

    public void setCorrectAnswerEJB(CorrectAnswerEJB correctAnswerEJB) {
        this.correctAnswerEJB = correctAnswerEJB;
    }

    public CorrectAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(CorrectAnswer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<CorrectAnswer> getCorrectAnswersList() {
        return correctAnswersList;
    }

    public void setCorrectAnswersList(List<CorrectAnswer> correctAnswersList) {
        this.correctAnswersList = correctAnswersList;
    }
}
