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
    @NamedQuery(name = "getAllQuizzes", query = "SELECT q FROM Quiz q ORDER BY q.title"),
    @NamedQuery(name = "getQuizById", query = "SELECT q FROM Quiz q WHERE q.id = :quizId"),
    @NamedQuery(name = "searchQuizByTitle", query = "SELECT q FROM Quiz q WHERE q.title LIKE :title")
})

public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizId")
    private Long id;
    private String title;
    private String quizOutcome;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreated;        

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "quizId", referencedColumnName = "quizId")
    private List<Question> questions;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "quizId", referencedColumnName = "quizId")
    private List<StudentQuiz> studentQuizList;

    public Quiz() {
    }

    
    public Quiz(String title) {
        this.title = title;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getQuizOutcome() {
        return quizOutcome;
    }

    public void setQuizOutcome(String quizOutcome) {
        this.quizOutcome = quizOutcome;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public List<Question> getQuestions() {
        return this.questions;
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    public List<StudentQuiz> getStudentQuizList() {
        return this.studentQuizList;
    }

    public void setStudentQuizList(List<StudentQuiz> studentQuizList) {
        this.studentQuizList = studentQuizList;
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
        if (!(object instanceof Quiz)) {
            return false;
        }
        Quiz other = (Quiz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.Quiz[ id=" + id + " ]";
    }
    
}
