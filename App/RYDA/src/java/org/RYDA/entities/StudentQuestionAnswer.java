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
    
}
