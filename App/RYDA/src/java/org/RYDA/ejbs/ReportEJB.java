package org.RYDA.ejbs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.RYDA.entities.QuizAttemptResponse;

@Stateless
public class ReportEJB {

    @PersistenceContext(unitName = "RYDAPU")
    private EntityManager em;

    public List<QuizAttemptResponse> getQuizAttemptCount() {
        List<QuizAttemptResponse> quizAttemptResponseList = new ArrayList<>();
        TypedQuery<QuizAttemptResponse> query = em.createNamedQuery("getQuizAttemptCount", QuizAttemptResponse.class);

        Iterator itr = query.getResultList().iterator();
        while (itr.hasNext()) {
            QuizAttemptResponse quizAttemptResponse = new QuizAttemptResponse();
            Object[] obj = (Object[]) itr.next();

            quizAttemptResponse.setQuizId(Long.parseLong(String.valueOf(obj[0])));
            quizAttemptResponse.setQuizTitle(String.valueOf(obj[1]));
            quizAttemptResponse.setAttemptCount(Long.parseLong(String.valueOf(obj[2])));

            quizAttemptResponseList.add(quizAttemptResponse);
        }

        return quizAttemptResponseList;
    }
}
