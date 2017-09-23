package org.RYDA.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.AnswersEJB;
import org.RYDA.ejbs.QuestionEJB;
import org.RYDA.ejbs.QuizEJB;
import org.RYDA.ejbs.StudentAnswerEJB;
import org.RYDA.ejbs.StudentEJB;
import org.RYDA.entities.Answer;
import org.RYDA.entities.Question;
import org.RYDA.entities.Quiz;
import org.RYDA.entities.Student;
import org.RYDA.entities.StudentAnswer;
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class AttemptQuizController {

    @EJB
    private QuestionEJB questionEJB;
    private Question question;
    private List<Question> questionList;

    @EJB
    private AnswersEJB answerEJB;
    private Answer answer;
    private List<Answer> answerList;
    
    @EJB
    private StudentEJB studentEJB;
    private Student student;

    @EJB
    private QuizEJB quizEJB;
    private Quiz quiz;
    private List<Quiz> quizList;

    @EJB
    private StudentAnswerEJB studentAnswerEJB;
    private StudentAnswer studentAnswer;
    private List<StudentAnswer> studentAnswerList;
    
    private long quizId;

    public AttemptQuizController() {
        question = new Question();
        questionList = new ArrayList<Question>();

        answer = new Answer();
        answerList = new ArrayList<Answer>();
        
        student = new Student();

        quiz = new Quiz();
        quizList = new ArrayList<Quiz>();
        quizEJB = new QuizEJB();
    }

    @PostConstruct
    public void init() {
        quizList = quizEJB.listQuiz();
        long quizId = getQuizIdFromQueryString();
        if (quizId > 0) {
            questionList = questionEJB.getQuestionsByQuizId(quizId);
            for(Question q : questionList)
            {
                answerList = answerEJB.listAnswers(q.getId());
                q.setAnswers(answerList);
            }
        } else {
            questionList = questionEJB.listQuestions();
            for(Question q : questionList)
            {
                answerList = answerEJB.listAnswers(q.getId());
                q.setAnswers(answerList);
            }
        }
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

    public QuestionEJB getQuestionEJB() {
        return questionEJB;
    }

    public void setQuestionEJB(QuestionEJB questionEJB) {
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
    
    public AnswersEJB getAnswersEJB() {
        return answerEJB;
    }

    public void setAnswersEJB(AnswersEJB answerEJB) {
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
    
    public StudentEJB getStudentEJB() {
        return studentEJB;
    }

    public void setStudentEJB(StudentEJB studentEJB) {
        this.studentEJB = studentEJB;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public StudentAnswerEJB getStudentAnswerEJB() {
        return studentAnswerEJB;
    }

    public void setStudentAnswerEJB(StudentAnswerEJB studentAnswerEJB) {
        this.studentAnswerEJB = studentAnswerEJB;
    }

    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(StudentAnswer studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public List<StudentAnswer> getStudentAnswerList() {
        return studentAnswerList;
    }

    public void setStudentAnswerList(List<StudentAnswer> studentAnswerList) {
        this.studentAnswerList = studentAnswerList;
    }
    
    public long getQuizIdFromQueryString() {
        String quizIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quizId");
        if (quizIdStr != null) {
            return Long.parseLong(quizIdStr);
        }
        return 0;
    }

    public void createStudent() {    
        Utility.writeSession("student", student);

        Student studentFromSession = (Student) Utility.readSession("student");
        String studentName = studentFromSession.getName();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String quizId = params.get("quizId");

        String url = "attemptQuiz.xhtml?quizId" + quizId;
        //return "attemptQuiz.xhtml?quizId" + quizId;

        Utility.RedirectUrl(url);
    }
    
    public void submitStudentAnswerList() {
        Student studentFromSession = (Student) Utility.readSession("student");
        student = studentEJB.createStudent(studentFromSession);
        for (Question q : questionList)
        {
            for (Answer a : q.getAnswers())
            {
                studentAnswer.setStudentId(student.getId());
                studentAnswer.setAnswerId(a.getId());
            }
            studentAnswerList.add(studentAnswer);
        }
        studentAnswerEJB.submitStudentAnswerList(studentAnswerList);
    }
}
