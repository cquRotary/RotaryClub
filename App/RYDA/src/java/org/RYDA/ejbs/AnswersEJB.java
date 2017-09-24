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
import org.RYDA.entities.Answer;
import org.RYDA.entities.Quiz;

@Stateless
public class AnswersEJB {

    // ======================================
    // =             Attribute             =
    // ======================================
    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;
    private Answer answer = new Answer();

    // ======================================
    // =           Public Methods           =
    // ======================================
    public Answer createAnswer(Answer answer) {
        em.persist(answer);
        return answer;
    }

    public void createAllAnswers(long questionId, int questionIndex) {
        if (!listAnswers(questionId).isEmpty()) {
            return;
        }
        List<Answer> answerList = new ArrayList<>();

        switch (questionIndex) {
            case 1:
                answer = new Answer(questionId, "Much more aware", true);
                answerList.add(answer);

                answer = new Answer(questionId, "More aware", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "No change", false);
                answerList.add(answer);
                break;

            case 2:
                answer = new Answer(questionId, "Much aware", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Much more aware", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "No change", false);
                answerList.add(answer);
                break;
                
            case 3:
                answer = new Answer(questionId, "Nothing, there is no penalty", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Only the testing officer will be investigated", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Action will be taken against you. The penalties are severe and include fines and imprisonment.", true);
                answerList.add(answer);
                break;
                
            case 4:
                answer = new Answer(questionId, "Action will be taken against all involved. The penalties are severe and include fines and imprisonment.", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Nothing, there is no penalty", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Only the testing officer will be investigated.", false);
                answerList.add(answer);
                break;
                
            case 5:
                answer = new Answer(questionId, "Yes, provided the provisional driver has held a P2 licence for more than 6 months.", false);
                answerList.add(answer);

                answer = new Answer(questionId, "No.", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, provided L and P1 or P2 plates are displayed.", false);
                answerList.add(answer);
                break;
                
            case 6:
                answer = new Answer(questionId, "12 months", true);
                answerList.add(answer);

                answer = new Answer(questionId, "18 months", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "24 months", false);
                answerList.add(answer);
                break;
                
            case 7:
                answer = new Answer(questionId, "Increase your speed and drive back on the road", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Slow down quickly by braking hard", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Slow down gradually and ease back onto the road.", true);
                answerList.add(answer);
                break;
                
            case 8:
                answer = new Answer(questionId, "Near to the left-hand side of the road", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Anywhere on your side of the road", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Along the middle of the road", false);
                answerList.add(answer);
                break;
                
            case 9:
                answer = new Answer(questionId, "Sound your horn to warn other drivers", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Unbuckle your seat belt so you can reverse as quickly as possible.", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Take care and never reverse for a greater distance and time than is necessary.", true);
                answerList.add(answer);
                break;
                
            case 10:
                answer = new Answer(questionId, "You must leave at least 3 metres front and back.", false);
                answerList.add(answer);

                answer = new Answer(questionId, "You must leave at least 2 metres from the front only.", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "You must leave at least 1 metre front and back.", true);
                answerList.add(answer);
                break;
                
            case 11:
                answer = new Answer(questionId, "Yes, if delivering goods.", false);
                answerList.add(answer);

                answer = new Answer(questionId, "No, not at any time.", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, if not obstructing traffic.", false);
                answerList.add(answer);
                break;
                
            case 12:
                answer = new Answer(questionId, "No, there is no regulation to cover this situation", false);
                answerList.add(answer);

                answer = new Answer(questionId, "No, any following traffic must stop if the door interferes with its progress", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, you must not open a door if you are likely to cause danger to road users or impede traffic", true);
                answerList.add(answer);
                break;
                
            case 13:
                answer = new Answer(questionId, "No, not at any time", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Yes, in daylight hours", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, but for no more than 30 minutes", false);
                answerList.add(answer);
                break;
                
            case 14:
                answer = new Answer(questionId, "No, not at any time", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Yes, provided no taxis are using the area", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, if you are carrying two or more passengers.", false);
                answerList.add(answer);
                break;
                
            case 15:
                answer = new Answer(questionId, "Turn on your hazard warning lights.", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Keep your sunglasses on to cut down headlight glare", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Turn on your lights on low beam", true);
                answerList.add(answer);
                break;
                
            case 16:
                answer = new Answer(questionId, "When within 200 metres of the vehicle ahead or an oncoming one", true);
                answerList.add(answer);

                answer = new Answer(questionId, "When within 200 metres of an oncoming vehicle only", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Never, you are allowed to drive with your lights on high beam at all times", false);
                answerList.add(answer);
                break;
                
            case 17:
                answer = new Answer(questionId, "Slow down, and give way to the bus as it has priority", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Sound your horn to stop the bus from pulling out", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Continue at your normal speed as the bus does not have priority", false);
                answerList.add(answer);
                break;
                
            case 18:
                answer = new Answer(questionId, "Yes, but it is legal for a towbar or bicycle rack to cover the rear number plate", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Yes, at any time", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "No, you are allowed to cover your number plates if you want to", false);
                answerList.add(answer);
                break;
                
            case 19:
                answer = new Answer(questionId, "Take your street directory in case you get lost", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Make sure your vehicle has enough fuel, oil, water and the correct tyre pressure.", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Take something to calm your nerves before driving", false);
                answerList.add(answer);
                break;
                
            case 20:
                answer = new Answer(questionId, "Stop, and reverse back along the freeway to the exit you missed", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Continue until you reach the next appropriate exit", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Stop immediately and turn around", false);
                answerList.add(answer);
                break;
                
            case 21:
                answer = new Answer(questionId, "Your speed", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Fuel gauge", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Windscreen wipers", false);
                answerList.add(answer);
                break;
                
            case 22:
                answer = new Answer(questionId, "Only within 100 metres of making a turn", true);
                answerList.add(answer);

                answer = new Answer(questionId, "When carrying at least two passengers", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Only to overtake another vehicle", false);
                answerList.add(answer);
                break;
                
            case 23:
                answer = new Answer(questionId, "No, not at all", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Yes, if the combined length of vehicle and trailers does not exceed 15 metres", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Yes, provided you have held a licence for two years", false);
                answerList.add(answer);
                break;
                
            case 24:
                answer = new Answer(questionId, "Stop immediately and let the faster vehicle overtake", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Drive at least 25 km/h below the speed limit", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Keep at least 60 metres behind heavy vehicles or other vehicles towing caravans", true);
                answerList.add(answer);
                break;
                
            case 25:
                answer = new Answer(questionId, "Yes, if the person(s) in the caravan are over 12 years of age", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Yes, provided you do not exceed 60 km/h", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "No, not under any circumstances", true);
                answerList.add(answer);
                break;
                
            case 26:
                answer = new Answer(questionId, "Check your tyre pressure, and if necessary, increase it to what the manufacturer recommends", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Make sure you have a street directory, so you know where you are going", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Have a large meal and a cup of coffee", false);
                answerList.add(answer);
                break;
                
            case 27:
                answer = new Answer(questionId, "Brake, look for room to the left, sound your horn and flash your lights", true);
                answerList.add(answer);

                answer = new Answer(questionId, "Slow down and hope that the driver will turn away", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Drive onto the wrong side of the road and hope the other vehicle does not do the same", false);
                answerList.add(answer);
                break;
                
            case 28:
                answer = new Answer(questionId, "Do not need to report the accident to the Police", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Must report the accident to the Police Station nearest to where the accident happened within 24 hours", true);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Only need to report the accident to the Police if someone was injured", false);
                answerList.add(answer);
                break;
                
            case 29:
                answer = new Answer(questionId, "You must let them see your licence, take details, and give the name and address of the vehicle owner", true);
                answerList.add(answer);

                answer = new Answer(questionId, "No details at all until you have contacted your insurance company", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Only your name and address details if a policeman asks for them", false);
                answerList.add(answer);
                break;
                
            case 30:
                answer = new Answer(questionId, "Report the accident to Police within seven days", false);
                answerList.add(answer);

                answer = new Answer(questionId, "Only call the Police if the accident also resulted in over $500 worth of property damage", false);
                answerList.add(answer);
                
                answer = new Answer(questionId, "Render every assistance and take immediate steps to have an ambulance notified. Then call the police", true);
                answerList.add(answer);
                break;
        }

        for (Answer a : answerList) {
            createAnswer(a);
        }
    }

    //method to query and list the answer by questionId
    public List<Answer> listAnswers(long questionId) {
        TypedQuery<Answer> query = em.createNamedQuery("getAnswersByQuestionId", Answer.class).setParameter("questionId", questionId);
        return query.getResultList();
    }

    //methods to delete question by id
    public boolean delete(long id) {
        answer = em.find(Answer.class, id);
        em.remove(answer);
        return true;
    }

    //methods quering the question by question id
    public Answer getAnswerById(long id) {
        TypedQuery<Answer> query = em.createNamedQuery("getAnswerById", Answer.class).setParameter("answerId", id);
        return query.getSingleResult();
    }
}
