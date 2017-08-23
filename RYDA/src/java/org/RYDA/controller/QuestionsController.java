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
import org.RYDA.ejbs.QuestionsEJB;
import org.RYDA.entities.CorrectAnswer;
import org.RYDA.entities.Questions;

/**
 *
 * @author Kshav
 */
@Named(value = "questionsController")
@RequestScoped
public class QuestionsController {

    @EJB
    private QuestionsEJB questionsEJB;        
    private Questions question = new Questions();     
    private List<Questions> questionList = new ArrayList<Questions>();        
    
    public String addQuestion(){                
        question = questionsEJB.createQuestion(question);            
        questionList = questionsEJB.listQuestions();
        return "questionsrList.xhtml";            
    }

    public QuestionsEJB getQuestionsEJB() {
        return questionsEJB;
    }

    public void setQuestionsEJB(QuestionsEJB questionsEJB) {
        this.questionsEJB = questionsEJB;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public List<Questions> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Questions> questionList) {
        this.questionList = questionList;
    }
    
    

    
}
