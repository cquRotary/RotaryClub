package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.RYDA.ejbs.AnswersEJB;
import org.RYDA.ejbs.QuestionEJB;
import org.RYDA.ejbs.QuizEJB;
import org.RYDA.entities.Answer;
import org.RYDA.entities.Question;
import org.RYDA.entities.Quiz;

@ManagedBean
@RequestScoped
public class QuestionController {
    @EJB
    private QuestionEJB questionEJB;        
    private Question question;     
    private List<Question> questionList; 
    
    @EJB
    private AnswersEJB answerEJB;
    private Answer answer;
    private List<Answer> answerList;
    
    @EJB
    private QuizEJB quizEJB;
    private Quiz quiz;
    private List<Quiz> quizList;
    
    public QuestionController () {
        question = new Question();
        questionList = new ArrayList<Question>();
        answer = new Answer();
        answerList = new ArrayList<Answer>();
        quiz = new Quiz();
        quizList = new ArrayList<Quiz>();
    }
    
    @PostConstruct
    public void init() 
    {
        long quizId = getQuizId();
        if (quizId > 0)
        {
            questionList = questionEJB.getQuestionsByQuizId(quizId);
        }
        else
        {
            questionList = questionEJB.listQuestions();
        }
        
        quizList = quizEJB.listQuiz();
    }
    
    public long getQuizId()
    {
        String quizIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quizId");
        if (quizIdStr != null)
        {
            return Long.parseLong(quizIdStr);
        }
        return 0;
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
    
    //method to create Question
    public String createQuestion(long quizId) {
        question = questionEJB.createQuestion(question);
        questionList = questionEJB.listQuestions();
        answerList = answerEJB.listAnswers(question.getId());
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully"));
        return "question.xhtml";
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
    public String addAction(long quizId) {
        question.setQuizId(quizId);
        return "question.xhtml?quizId" + quizId;
    }
    
    /// view products on customer
    public String viewAction(long id) {
        question = questionEJB.getQuestionById(id);
        answerList = answerEJB.listAnswers(id);
        return "question.xhtml";
    }
    
    //method to create Answer
    public String createAnswer(long questionId) {
        System.out.println("QuestionId = " + questionId);
        answer.setQuestionId(questionId);
        answer = answerEJB.createAnswer(answer);
        answerList = answerEJB.listAnswers(questionId);
        FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New record added successfully" + questionId));
        question = questionEJB.getQuestionById(questionId);
        return "question.xhtml";
    }
    
    // delete customer
    public String deleteAnswer(long id, long questionId) {
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
    
    /// view products on customer
    public String viewAnswer(long id) {
        answer = answerEJB.getAnswerById(id);
        return "answer-details.xhtml";
    }
}
