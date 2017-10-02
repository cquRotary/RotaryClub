package org.RYDA.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.RYDA.ejbs.StudentQuizEJB;
import org.RYDA.entities.Answer;
import org.RYDA.entities.Question;
import org.RYDA.entities.Quiz;
import org.RYDA.entities.Student;
import org.RYDA.entities.StudentAnswer;
import org.RYDA.entities.StudentQuestionAnswer;
import org.RYDA.entities.StudentQuiz;
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
    
    private List<StudentQuestionAnswer> studentQuestionAnswerList;
    
    private Map<StudentQuestionAnswer, Long> studentAnswers = new HashMap<>();
    
    @EJB
    private StudentQuizEJB studentQuizEJB;
    
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
        
        studentQuestionAnswerList = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        quizList = quizEJB.listQuiz();
        long quizId = getQuizIdFromQueryString();
        
        if (Utility.readSession("quizId") != null)
        {
            quizId = Long.parseLong((String) Utility.readSession("quizId"));
        }

        if (quizId > 0) {
            quiz = quizEJB.getQuizById(quizId);
            questionList = questionEJB.getQuestionsByQuizId(quizId);
            for(Question q : questionList)
            {
                answerList = answerEJB.listAnswers(q.getId());
                q.setAnswers(answerList);
                
                StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
                studentQuestionAnswer.setId(q.getId());
                studentQuestionAnswer.setQuestion(q.getQuestion());
                studentQuestionAnswer.setHint(q.getHint());
                studentQuestionAnswer.setStudentAnswerId(0);
                studentQuestionAnswer.setAnswers(answerList);
                for (Answer a : answerList)
                {
                    if (a.getIsCorrect())
                    {
                        studentQuestionAnswer.setCorrectAnswer(a.getAnswerOption());
                    }
                }
                studentQuestionAnswerList.add(studentQuestionAnswer);
            }
        } else {
            questionList = questionEJB.listQuestions();
            for(Question q : questionList)
            {
                answerList = answerEJB.listAnswers(q.getId());
                q.setAnswers(answerList);
                
                StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
                studentQuestionAnswer.setId(q.getId());
                studentQuestionAnswer.setQuestion(q.getQuestion());
                studentQuestionAnswer.setHint(q.getHint());
                studentQuestionAnswer.setStudentAnswerId(0);
                studentQuestionAnswer.setAnswers(answerList);
                studentQuestionAnswerList.add(studentQuestionAnswer);
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
    
    public List<StudentQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    public void setStudentQuestionAnswerList(List<StudentQuestionAnswer> studentQuestionAnswerList) {
        this.studentQuestionAnswerList = studentQuestionAnswerList;
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
        Utility.writeSession("quizId", Utility.readQueryString("quizId"));

        Student studentFromSession = (Student) Utility.readSession("student");
        String studentName = studentFromSession.getName();

        String url = "quizOutcome.xhtml";
        //return "attemptQuiz.xhtml?quizId" + quizId;

        Utility.RedirectUrl(url);
    }
    
    public void proceedToAttemptQuiz()
    {
        Date d = new Date();
        Utility.writeSession("quizStartDate", d);
        Utility.RedirectUrl("attemptQuiz.xhtml");
    }
    
    public void submitStudentAnswerList() {
        Student studentFromSession = (Student) Utility.readSession("student");
        student = studentEJB.createStudent(studentFromSession);
        String sessionQuizId = (String) Utility.readSession("quizId");
        Date quizStartDate = (Date) Utility.readSession("quizStartDate");
        Date quizCompletedDate = new Date();
        StudentQuiz studentQuiz = new StudentQuiz(studentFromSession.getId(), Long.parseLong(sessionQuizId), quizStartDate, quizCompletedDate);
        studentQuizEJB.createStudentQuiz(studentQuiz);
//        for (Map.Entry<StudentQuestionAnswer, Long> entry : studentAnswers.entrySet())
//        {
//            StudentQuestionAnswer q = entry.getKey();
//            long answerId = entry.getValue();
//            studentAnswer.setStudentId(student.getId());
//            studentAnswer.setAnswerId(answerId);
//        }

        for (StudentQuestionAnswer q : studentQuestionAnswerList)
        {
            studentAnswer = new StudentAnswer();
            studentAnswer.setStudentId(student.getId());
            studentAnswer.setAnswerId(q.getStudentAnswerId());
            studentAnswerEJB.createStudentAnswer(studentAnswer);
        }
        
        String url = "attemptResult.xhtml?quizId=" + sessionQuizId;
        Utility.RedirectUrl(url);
    }

    /**
     * @return the studentAnswers
     */
    public Map<StudentQuestionAnswer, Long> getStudentAnswers() {
        return studentAnswers;
    }

    /**
     * @param studentAnswers the studentAnswers to set
     */
    public void setStudentAnswers(Map<StudentQuestionAnswer, Long> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
}
