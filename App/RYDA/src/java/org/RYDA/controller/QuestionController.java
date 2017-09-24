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
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class QuestionController {

    // ======================================
    // =             Attributes             =
    // ======================================
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

    // ======================================
    // =           Public Methods           =
    // ======================================
    public QuestionController() {
        question = new Question();
        questionList = new ArrayList<Question>();
        answer = new Answer();
        answerList = new ArrayList<Answer>();
        quiz = new Quiz();
        quizList = new ArrayList<Quiz>();
    }

    @PostConstruct
    public void init() {
        if (!quizEJB.listQuiz().isEmpty())
        {
            quizList = quizEJB.listQuiz();
            for (Quiz q : quizList)
            {
                quizEJB.delete(q.getId());
            }
        }
        
        if (!questionEJB.listQuestions().isEmpty())
        {
            questionList = questionEJB.listQuestions();
            for (Question q : questionList)
            {
                questionEJB.delete(q.getId());
            }
        }
        
        if (quizEJB.listQuiz().isEmpty()) {
            quizEJB.createAllQuizzes();
            quizList = quizEJB.listQuiz();
            for (Quiz q : quizList) {
                question = new Question(q.getId(), "How to increase your own hazard perception skills?", "Awarness", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Much more aware", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "More aware", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No change", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "How to reduce distractions in a car?", "Focus and Awarness", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Much aware", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Much more aware", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No change", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "What will happen if you offer a testing officer a bribe to pass your driving test?", "Will have to face legal consequences.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Nothing, there is no penalty", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only the testing officer will be investigated", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Action will be taken against you. The penalties are severe and include fines and imprisonment.", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "What will happen if a testing officer asks you for a bribe to pass your driving test and you give it to him or her?", "Legal consequences", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Action will be taken against all involved. The penalties are severe and include fines and imprisonment.", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Nothing, there is no penalty", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only the testing officer will be investigated.", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Can a P1 or P2 provisional driver legally instruct a learner driver?", "Legally No", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Yes, provided the provisional driver has held a P2 licence for more than 6 months.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No.", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, provided L and P1 or P2 plates are displayed.", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "To progress to a P2 provisional licence, a P1 provisional driver must hold a P1 licence for a minimum period of -", "Enough time to be a learn driving skills.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "12 months", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "18 months", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "24 months", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If one or two of your wheels run off the edge of the roadway, you should -", "What will stop the vehicle.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Increase your speed and drive back on the road", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Slow down quickly by braking hard", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Slow down gradually and ease back onto the road.", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If there are no lanes marked on the road, you should drive -", "Standard driving hand side.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Near to the left-hand side of the road", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Anywhere on your side of the road", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Along the middle of the road", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "When reversing, you should -", "Common sense is vital.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Sound your horn to warn other drivers", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Unbuckle your seat belt so you can reverse as quickly as possible.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Take care and never reverse for a greater distance and time than is necessary.", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "How close can you park to another vehicle when parked parallel to the kerb?", "At least minimum.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "You must leave at least 3 metres front and back.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "You must leave at least 2 metres from the front only.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "You must leave at least 1 metre front and back.", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Where there is parallel kerbside parking, are you allowed to double-park alongside a parked vehicle?", "Never", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Yes, if delivering goods.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No, not at any time.", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, if not obstructing traffic.", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Do you have any responsibilities when opening a vehicle door on a roadway?", "Always take care.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "No, there is no regulation to cover this situation", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No, any following traffic must stop if the door interferes with its progress", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, you must not open a door if you are likely to cause danger to road users or impede traffic", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Are you permitted to park on a median strip or traffic island?", "Never", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "No, not at any time", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, in daylight hours", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, but for no more than 30 minutes", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Are you permitted to park in the direction of the arrow?", "Never", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "No, not at any time", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, provided no taxis are using the area", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, if you are carrying two or more passengers.", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "When driving at sunset or dawn on a dark day, what should you do?", "Make it visible.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Turn on your hazard warning lights.", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Keep your sunglasses on to cut down headlight glare", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Turn on your lights on low beam", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "You are driving at night with your headlights on high beam. When should you dip your headlights?", "make it maximum", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "When within 200 metres of the vehicle ahead or an oncoming one", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "When within 200 metres of an oncoming vehicle only", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Never, you are allowed to drive with your lights on high beam at all times", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "You are driving in a 60 km/h zone, with only one lane for traffic in your direction. You see a bus ahead (with this sign displayed on the rear) signalling its intention to pull out from a bus stop, you should -", "Give way.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Slow down, and give way to the bus as it has priority", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Sound your horn to stop the bus from pulling out", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Continue at your normal speed as the bus does not have priority", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Is it an offence to obstruct clear vision of your number plates?", "Always", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Yes, but it is legal for a towbar or bicycle rack to cover the rear number plate", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, at any time", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No, you are allowed to cover your number plates if you want to", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Before driving on a freeway, which of the following should you do?", "Enough essential stuffs in case of emergency.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Take your street directory in case you get lost", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Make sure your vehicle has enough fuel, oil, water and the correct tyre pressure.", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Take something to calm your nerves before driving", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "What must you do if you miss your exit on a freeway?", "Continue", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Stop, and reverse back along the freeway to the exit you missed", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Continue until you reach the next appropriate exit", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Stop immediately and turn around", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "As you leave a freeway, which of the following should you check?", "How fast you are driving.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Your speed", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Fuel gauge", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Windscreen wipers", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "When can a private car travel in a lane marked by bus sign?", "Enough for bus to park.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Only within 100 metres of making a turn", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "When carrying at least two passengers", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only to overtake another vehicle", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Are you permitted to drive a car towing more than one trailer?", "Not a good idea.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "No, not at all", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, if the combined length of vehicle and trailers does not exceed 15 metres", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, provided you have held a licence for two years", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "What must you do when you are towing a caravan to help other vehicles overtake?", "Enough distance for the towing caravan.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Stop immediately and let the faster vehicle overtake", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Drive at least 25 km/h below the speed limit", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Keep at least 60 metres behind heavy vehicles or other vehicles towing caravans", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Are you permitted to tow a caravan with a person riding in the caravan?", "Not a good idea.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Yes, if the person(s) in the caravan are over 12 years of age", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Yes, provided you do not exceed 60 km/h", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No, not under any circumstances", true);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "Before driving a long distance at fast speed or carrying a full car load, you should -", "Tyre might be good idea.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Check your tyre pressure, and if necessary, increase it to what the manufacturer recommends", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Make sure you have a street directory, so you know where you are going", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Have a large meal and a cup of coffee", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If an oncoming vehicle crosses the centre line and is coming straight at you and you cannot stop, you should -", "Stopping might be helpful.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Brake, look for room to the left, sound your horn and flash your lights", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Slow down and hope that the driver will turn away", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Drive onto the wrong side of the road and hope the other vehicle does not do the same", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If you are involved in an accident where your vehicle needs to be towed away and the Police does not attend the crash scene, you -", "Report the accident.", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Do not need to report the accident to the Police", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Must report the accident to the Police Station nearest to where the accident happened within 24 hours", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only need to report the accident to the Police if someone was injured", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If your vehicle is involved in an accident (regardless of the damage), what details must you give, to the other driver(s), if asked?", "Vital information", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "You must let them see your licence, take details, and give the name and address of the vehicle owner", true);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "No details at all until you have contacted your insurance company", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only your name and address details if a policeman asks for them", false);
                answerEJB.createAnswer(answer);

                question = new Question(q.getId(), "If a vehicle you are driving is involved in an accident and a person is injured, what must you do after stopping?", "Help", Long.parseLong("1"));
                questionEJB.createQuestion(question);

                answer = new Answer(question.getId(), "Report the accident to Police within seven days", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Only call the Police if the accident also resulted in over $500 worth of property damage", false);
                answerEJB.createAnswer(answer);

                answer = new Answer(question.getId(), "Render every assistance and take immediate steps to have an ambulance notified. Then call the police", true);
                answerEJB.createAnswer(answer);
            }
        }
        
        long quizId = getQuizId();
        if (quizId > 0) {
            questionList = questionEJB.getQuestionsByQuizId(quizId);
        } else {
            questionList = questionEJB.listQuestions();
        }

        quizList = quizEJB.listQuiz();
    }

    public long getQuizId() {
        String quizIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quizId");
        if (quizIdStr != null) {
            return Long.parseLong(quizIdStr);
        }
        return 0;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================
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

    //method to delete Question
    public String deleteAction(long id) {
        boolean success = questionEJB.delete(id);
        questionList = questionEJB.listQuestions();
        if (success) {
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Something went wrong. Please try again"));
        }
        return "question-list.xhtml";
    }

    //Method to view question detail
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
        if (success) {
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Record deleted successfully"));
        } else {
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
