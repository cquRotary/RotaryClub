package org.RYDA.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = "getStudentsByQuizId", query ="SELECT a FROM Student a WHERE a.id IN (SELECT sq.studentId FROM StudentQuiz sq WHERE sq.quizId = :quizId)"),
    @NamedQuery(name = "getQuizAttemptCount", query = "SELECT s.quizId, q.title quizTitle, COUNT(1) attemptCount FROM StudentQuiz s, Quiz q WHERE s.quizId = q.id GROUP BY s.quizId, q.title"),
    @NamedQuery(name = "getStudentQuiz", query = "SELECT a FROM StudentQuiz a WHERE a.studentId = :studentId AND a.quizId = :quizId"),
    @NamedQuery(name = "getStudentQuizByStudentId", query = "SELECT a FROM StudentQuiz a WHERE a.studentId = :studentId")
})
public class StudentQuiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long studentId;
    private Long quizId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date quizStartDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date quizCompletedDate;

    public StudentQuiz() {
    }

    public StudentQuiz(Long studentId, Long quizId, Date quizStartDate, Date quizCompletedDate) {
        this.studentId = studentId;
        this.quizId = quizId;
        this.quizStartDate = quizStartDate;
        this.quizCompletedDate = quizCompletedDate;
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

    /**
     * @return the quizStartDate
     */
    public Date getQuizStartDate() {
        return quizStartDate;
    }

    /**
     * @param quizStartDate the quizStartDate to set
     */
    public void setQuizStartDate(Date quizStartDate) {
        this.quizStartDate = quizStartDate;
    }

    /**
     * @return the quizCompletedDate
     */
    public Date getQuizCompletedDate() {
        return quizCompletedDate;
    }

    /**
     * @param quizCompletedDate the quizCompletedDate to set
     */
    public void setQuizCompletedDate(Date quizCompletedDate) {
        this.quizCompletedDate = quizCompletedDate;
    }
}
