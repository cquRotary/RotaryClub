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
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.QuestionEJB;
import org.RYDA.entities.Question;
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class AttemptQuizController {

    @EJB
    private QuestionEJB questionEJB;
    private Question question;
    private List<Question> questionList;
    
    public AttemptQuizController() {
        question = new Question();
        questionList = new ArrayList<Question>();
    }
    
    @PostConstruct
    public void init() {
        long quizId = getQuizId();
        if (quizId > 0) {
            questionList = questionEJB.getQuestionsByQuizId(quizId);
        } else {
            questionList = questionEJB.listQuestions();
        }
    }
    
    public QuestionEJB getQuestionsEJB() {
        return questionEJB;
    }

    public void setQuestionsEJB(QuestionEJB questionEJB) {
        this.questionEJB = questionEJB;
    }
    
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
    
    public long getQuizId() {
        String quizIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quizId");
        if (quizIdStr != null) {
            return Long.parseLong(quizIdStr);
        }
        return 0;
    }
}
