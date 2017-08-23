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
import org.RYDA.ejbs.UsersEJB;
import org.RYDA.entities.Administrator;
import org.RYDA.entities.Student;
import org.RYDA.entities.Users;

/**
 *
 * @author Kshav
 */
@Named(value = "usersController")
@RequestScoped
public class UsersController {

    @EJB
    private UsersEJB userEJB;      
    
    private Administrator admin = new Administrator();      
    private Student student = new Student();       
    private Users user = new Users();               
    private List<Users> userList = new ArrayList<Users>();      
    private List<Administrator> adminList = new ArrayList<Administrator>();  
    private List<Student> studentList = new ArrayList<Student>();      
   
        
    public String addAdmin(){                  
        admin = userEJB.createAdministrator(admin);
        adminList = userEJB.listAdministrator();
        return "usersList.xhtml";                 
    }
    
    public String addStudent(){                      
        student = userEJB.createStudent(student);
        studentList = userEJB.listStudent();
        return "userList.xhtml";                     
    }

    public UsersEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UsersEJB userEJB) {
        this.userEJB = userEJB;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public List<Administrator> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Administrator> adminList) {
        this.adminList = adminList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    
    

    
}
