package org.RYDA.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.ReportEJB;
import org.RYDA.ejbs.StudentEJB;
import org.RYDA.entities.QuizAttemptResponse;
import org.RYDA.entities.Student;
import org.RYDA.library.Utility;

@ManagedBean
@RequestScoped
public class ReportController {
    @EJB
    private ReportEJB reportEJB;    
    private List<QuizAttemptResponse> quizAttemptResponseList;
    
    @EJB
    private StudentEJB studentEJB;
    private List<Student> studentList;
    
    public ReportController() {
    }

    /**
     * @return the reportEJB
     */
    public ReportEJB getReportEJB() {
        return reportEJB;
    }

    /**
     * @param reportEJB the reportEJB to set
     */
    public void setReportEJB(ReportEJB reportEJB) {
        this.reportEJB = reportEJB;
    }

    /**
     * @return the quizAttemptResponseList
     */
    public List<QuizAttemptResponse> getQuizAttemptResponseList() {
        return reportEJB.getQuizAttemptCount();
    }

    /**
     * @return the studentEJB
     */
    public StudentEJB getStudentEJB() {
        return studentEJB;
    }

    /**
     * @param studentEJB the studentEJB to set
     */
    public void setStudentEJB(StudentEJB studentEJB) {
        this.studentEJB = studentEJB;
    }

    /**
     * @return the studentList
     */
    public List<Student> getStudentList() {
         return studentEJB.getStudentsByQuizId(getQuizId());
    }
    
    public long getQuizId() {
        String quizIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quizId");
        if (quizIdStr != null) {
            return Long.parseLong(quizIdStr);
        }
        return 0;
    }
    
    public void viewQuizAttempt(long studentId)
    {
        String url = "student-result.xhtml?quizId=" + getQuizId() + "&studentId=" + studentId;
        Utility.RedirectUrl(url);
    }
}
