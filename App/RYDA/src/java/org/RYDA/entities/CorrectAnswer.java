/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Kshav
 */
@Entity
@Table(name = "CorrectAnswer")
@NamedQuery(name = "getAllCorrectAnswers", query = "SELECT c FROM CorrectAnswer c where c.questionId = :qId")
public class CorrectAnswer implements Serializable {

    // ======================================
    // =             Attribute             =
    // ======================================
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CORRECTANSWERID")
    private Long id;
    @Column(name = "QUESTIONID")
    private Long questionId;
    @Column(name = "ANSWERID")
    private Long answerId;
    private String description;
    @OneToOne(optional = false, fetch = FetchType.EAGER)                        //one to one relationship between customer order and car entity
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "QUESTIONID", insertable = false, updatable = false)
    private Question question;
    
    @OneToOne(optional = false, fetch = FetchType.EAGER)                        //one to one relationship between customer order and car entity
    @JoinColumn(name = "ANSWERID", referencedColumnName = "ANSWERID", insertable = false, updatable = false)
    private Answer answer;

    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CorrectAnswer)) {
            return false;
        }
        CorrectAnswer other = (CorrectAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.CorrectAnswer[ id=" + id + " ]";
    }
    
}
