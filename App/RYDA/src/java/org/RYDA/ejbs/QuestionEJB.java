/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.ejbs;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.Question;

@Stateless
public class QuestionEJB {
    
    // ======================================
    // =             Attribute             =
    // ======================================
    
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private Question question = new Question();
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    //Public methods    
    public Question createQuestion(Question question){
        em.persist(question);
        return question;
    }
    
    public void createAllQuestions(long quizId)
    {
        if (!listQuestions().isEmpty())
            return;
        List<Question> questionList = new ArrayList<>();
        question = new Question(quizId, "How to increase your own hazard perception skills?", "Awarness", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "How to reduce distractions in a car?", "Focus and Awarness", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "What will happen if you offer a testing officer a bribe to pass your driving test?", "Will have to face legal consequences.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "What will happen if a testing officer asks you for a bribe to pass your driving test and you give it to him or her?", "Legal consequences", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Can a P1 or P2 provisional driver legally instruct a learner driver?", "Legally No", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "To progress to a P2 provisional licence, a P1 provisional driver must hold a P1 licence for a minimum period of -", "Enough time to be a learn driving skills.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If one or two of your wheels run off the edge of the roadway, you should -", "What will stop the vehicle.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If there are no lanes marked on the road, you should drive -", "Standard driving hand side.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "When reversing, you should -", "Common sense is vital.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "How close can you park to another vehicle when parked parallel to the kerb?", "At least minimum.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Where there is parallel kerbside parking, are you allowed to double-park alongside a parked vehicle?", "Never", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Do you have any responsibilities when opening a vehicle door on a roadway?", "Always take care.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Are you permitted to park on a median strip or traffic island?", "Never", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Are you permitted to park in the direction of the arrow?", "Never", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "When driving at sunset or dawn on a dark day, what should you do?", "Make it visible.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "You are driving at night with your headlights on high beam. When should you dip your headlights?", "make it maximum", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "You are driving in a 60 km/h zone, with only one lane for traffic in your direction. You see a bus ahead (with this sign displayed on the rear) signalling its intention to pull out from a bus stop, you should -", "Give way.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Is it an offence to obstruct clear vision of your number plates?", "Always", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Before driving on a freeway, which of the following should you do?", "Enough essential stuffs in case of emergency.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "What must you do if you miss your exit on a freeway?", "Continue", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "As you leave a freeway, which of the following should you check?", "How fast you are driving.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "When can a private car travel in a lane marked by bus sign?", "Enough for bus to park.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Are you permitted to drive a car towing more than one trailer?", "Not a good idea.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "What must you do when you are towing a caravan to help other vehicles overtake?", "Enough distance for the towing caravan.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Are you permitted to tow a caravan with a person riding in the caravan?", "Not a good idea.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "Before driving a long distance at fast speed or carrying a full car load, you should -", "Tyre might be good idea.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If an oncoming vehicle crosses the centre line and is coming straight at you and you cannot stop, you should -", "Stopping might be helpful.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If you are involved in an accident where your vehicle needs to be towed away and the Police does not attend the crash scene, you -", "Report the accident.", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If your vehicle is involved in an accident (regardless of the damage), what details must you give, to the other driver(s), if asked?", "Vital information", Long.parseLong("1"));
        questionList.add(question);
        
        question = new Question(quizId, "If a vehicle you are driving is involved in an accident and a person is injured, what must you do after stopping?", "Help", Long.parseLong("1"));
        questionList.add(question);
        
        for (Question q : questionList)
        {
            createQuestion(q);
        }
    }
    
    //method to query all the questions
    public List<Question> listQuestions(){
        TypedQuery<Question> query = em.createNamedQuery("listQuestions", Question.class);
        return query.getResultList();
    }
    
    //method to query all the questions by quizId
    public List<Question> getQuestionsByQuizId(long quizId){
        TypedQuery<Question> query = em.createNamedQuery("getQuestionsByQuizId", Question.class).setParameter("quizId", quizId);
        return query.getResultList();
    }
    
    //methods to delete question by id
    public boolean delete(long id) {
        question = em.find(Question.class, id);
        em.remove(question);
        return true;
    }
    
    //methods quering the question by question id
    public Question getQuestionById(long id) {
        TypedQuery<Question> query = em.createNamedQuery("getQuestionById", Question.class).setParameter("questionId", id);
        return query.getSingleResult();
    }
}
