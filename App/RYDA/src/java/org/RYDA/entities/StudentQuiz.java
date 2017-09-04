/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "StudentQuiz")
@NamedQuery(name = "getAllStudentQuiz", query = "Select s from StudentQuiz s")
public class StudentQuiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STUDENTQUIZID")
    private Long id;
    
    @Column(name = "STUDENTID")
    private Long studentId;
    
    @Column(name = "QUIZID")
    private Long quizId;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)            
    @JoinColumn(name = "STUDENTID", referencedColumnName = "USERID", insertable = false, updatable = false)
    private Student student;
   
    @ManyToOne(optional = false, fetch = FetchType.EAGER)                        
    @JoinColumn(name = "QUIZID", referencedColumnName = "QUIZID", insertable = false, updatable = false)
    private Quiz quiz;
    private Integer noOfRightAnswers;
    private Integer noOfWrongAnswers;
    private Integer noOfQuestionsSkipped;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar attemptDate;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getNoOfRightAnswers() {
        return noOfRightAnswers;
    }

    public void setNoOfRightAnswers(Integer noOfRightAnswers) {
        this.noOfRightAnswers = noOfRightAnswers;
    }

    public Integer getNoOfWrongAnswers() {
        return noOfWrongAnswers;
    }

    public void setNoOfWrongAnswers(Integer noOfWrongAnswers) {
        this.noOfWrongAnswers = noOfWrongAnswers;
    }

    public Integer getNoOfQuestionsSkipped() {
        return noOfQuestionsSkipped;
    }

    public void setNoOfQuestionsSkipped(Integer noOfQuestionsSkipped) {
        this.noOfQuestionsSkipped = noOfQuestionsSkipped;
    }

    public Calendar getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Calendar attemptDate) {
        this.attemptDate = attemptDate;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentQuiz)) {
            return false;
        }
        StudentQuiz other = (StudentQuiz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.StudentQuiz[ id=" + id + " ]";
    }
    
}
