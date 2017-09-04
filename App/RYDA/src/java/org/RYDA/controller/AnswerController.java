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
import org.RYDA.ejbs.AnswersEJB;
import org.RYDA.entities.Answer;

/**
 *
 * @author Kshav
 */
@Named(value = "answersController")
@RequestScoped
public class AnswerController {

    @EJB
    private AnswersEJB answerEJB;        
    private Answer answer = new Answer();     
    private List<Answer> answerList = new ArrayList<Answer>();        
    
    public String addAnswer(){                
        answer = answerEJB.createAnswer(answer);            
        answerList = answerEJB.listAnswers();
        return "answerList.xhtml";            
    }

    public AnswersEJB getAnswerEJB() {
        return answerEJB;
    }

    public void setAnswerEJB(AnswersEJB answerEJB) {
        this.answerEJB = answerEJB;
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
}
