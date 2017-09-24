package org.RYDA.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getStudentsByQuizId", query ="SELECT a FROM Student a WHERE a.id IN (SELECT sq.studentId FROM StudentQuiz sq WHERE sq.quizId = :quizId)"),
    @NamedQuery(name = "getQuizAttemptCount", query = "SELECT s.quizId, q.title quizTitle, COUNT(1) attemptCount FROM StudentQuiz s, Quiz q WHERE s.quizId = q.id GROUP BY s.quizId, q.title")
})
public class StudentQuiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long studentId;
    private Long quizId;

    public StudentQuiz() {
    }

    public StudentQuiz(Long studentId, Long quizId) {
        this.studentId = studentId;
        this.quizId = quizId;
    }

    
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

    /**
     * @return the studentId
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the quizId
     */
    public Long getQuizId() {
        return quizId;
    }
    
    public void setQuizId(long quizId)
    {
        this.quizId = quizId;
    }
}
