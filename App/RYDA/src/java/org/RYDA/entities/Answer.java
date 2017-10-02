package org.RYDA.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAnswerByAnswerId", query ="SELECT a FROM Answer a WHERE a.id = :answerId"),
    @NamedQuery(name = "getAnswersByQuestionId", query = "SELECT a FROM Answer a WHERE a.questionId = :questionId"),
})

public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "answerId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "questionId")
    private Long questionId;
    private String answerOption;
    private boolean isCorrect;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "answerId", referencedColumnName = "answerId")
    private List<StudentAnswer> studentAnswers;
    
    public Answer()
    {
        
    }
    
    public Answer(String answerOption)
    {
        this.answerOption = answerOption;
    }
    
    public Answer(long questionId, String answerOption, boolean isCorrect)
    {
        this.questionId = questionId;
        this.answerOption = answerOption;
        this.isCorrect = isCorrect;
    }
    
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

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }    
    
    public List<StudentAnswer> getStudentAnswers() {
        return this.studentAnswers;
    }

    public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
        this.studentAnswers = studentAnswers;
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
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.Answers[ id=" + id + " ]";
    }
    
}
