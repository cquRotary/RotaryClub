/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.RYDA.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.RYDA.ejbs.StudentEJB;
import org.RYDA.entities.Student;

@ManagedBean
@RequestScoped
public class StudentController {

    // ======================================
    // =             Attributes             =
    // ======================================
    
    @EJB
    private StudentEJB studentEJB;        
    private Student student;     
    private List<Student> studentList;
    
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public StudentController()
    {
        student = new Student();
        studentList = new ArrayList<Student>();
        studentEJB = new StudentEJB();
    }

    @PostConstruct
    public void init() 
    {
        studentList = studentEJB.getAllStudents();
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public StudentEJB getStudentEJB() {
        return studentEJB;
    }

    public void setStudentEJB(StudentEJB studentEJB) {
        this.studentEJB = studentEJB;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    
    //method to view student detail
    public String viewQuizAttempt(long id) {
        student = studentEJB.getStudentById(id);
        return "student-details.xhtml";
    }
    
    public String searchStudent(String name) {
        studentList = studentEJB.searchStudent(name);
        if (studentList.size() <= 0) {
            FacesContext.getCurrentInstance().addMessage("successForm:errorInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No results Found"));
        } else {
            FacesContext.getCurrentInstance().addMessage("successForm:successInput", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", " " + studentList.size() + " record(s) found"));
        }
        return "student-list.xhtml";
    }
}
