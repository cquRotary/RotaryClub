package org.RYDA.entities;

import java.io.Serializable;
import java.util.Calendar;
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
    @NamedQuery(name = "listQuestions", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "getQuestionsByQuizId", query = "SELECT q FROM Question q WHERE q.quizId = :quizId"),
    @NamedQuery(name = "getQuestionById", query = "SELECT q FROM Question q WHERE q.id = :questionId")
})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "questionId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "quizId")
    private Long quizId;
    private String question;
    private String hint;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreated;
    private Long userId;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId", referencedColumnName = "questionId")
    private List<Answer> answers;
    
    //accessors and mutators
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }
    
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.Questions[ id=" + id + " ]";
    }
    
}
