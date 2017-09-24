package org.RYDA.entities;

public class QuizAttemptResponse {
    private long quizId;
    private String quizTitle;
    private long attemptCount;

    public QuizAttemptResponse() {
        this.quizId = 0;
        this.quizTitle = "";
        this.attemptCount = 0;
    }

    /**
     * @return the quizId
     */
    public long getQuizId() {
        return quizId;
    }

    /**
     * @param quizId the quizId to set
     */
    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    /**
     * @return the attemptCount
     */
    public long getAttemptCount() {
        return attemptCount;
    }

    /**
     * @param attemptCount the attemptCount to set
     */
    public void setAttemptCount(long attemptCount) {
        this.attemptCount = attemptCount;
    }

    /**
     * @return the quizTitle
     */
    public String getQuizTitle() {
        return quizTitle;
    }

    /**
     * @param quizTitle the quizTitle to set
     */
    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
}
