/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.entities;

/**
 *
 * @author riadmin
 */
public class StudentQuestionAnswer extends Question {
    private long studentAnswerId;
    private String correctAnswer;

    /**
     * @return the studentAnswerId
     */
    public long getStudentAnswerId() {
        return studentAnswerId;
    }

    /**
     * @param studentAnswerId the studentAnswerId to set
     */
    public void setStudentAnswerId(long studentAnswerId) {
        this.studentAnswerId = studentAnswerId;
    }

    /**
     * @return the correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
}
