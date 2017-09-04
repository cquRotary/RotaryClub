/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.RYDA.ejbs.StudentQuizEJB;
import org.RYDA.entities.StudentQuiz;

/**
 *
 * @author Kshav
 */
@Named(value = "studentQuizController")
@RequestScoped
public class StudentQuizController {

    @EJB
    private StudentQuizEJB studentQuizEJB;          
    private StudentQuiz studentQuiz = new StudentQuiz();          
    private List<StudentQuiz> studentQuizList = new ArrayList<StudentQuiz>();     
    
    
    public String addStudentQuizList(){           
        studentQuiz = studentQuizEJB.createStudentQuiz(studentQuiz);
        studentQuizList = studentQuizEJB.listStudentQuiz();
        return "studentQuizList.xhtml";
    }  

    public StudentQuizEJB getStudentQuizEJB() {
        return studentQuizEJB;
    }

    public void setStudentQuizEJB(StudentQuizEJB studentQuizEJB) {
        this.studentQuizEJB = studentQuizEJB;
    }

    public StudentQuiz getStudentQuiz() {
        return studentQuiz;
    }

    public void setStudentQuiz(StudentQuiz studentQuiz) {
        this.studentQuiz = studentQuiz;
    }

    public List<StudentQuiz> getStudentQuizList() {
        return studentQuizList;
    }

    public void setStudentQuizList(List<StudentQuiz> studentQuizList) {
        this.studentQuizList = studentQuizList;
    }
    
    
    
}
