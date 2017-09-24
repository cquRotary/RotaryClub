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
        @NamedQuery(name = "getAllAppUsers", query = "SELECT u FROM AppUser u"),
        @NamedQuery(name = "authenticateUser", query = "SELECT u FROM AppUser u WHERE u.username = :username AND u.password = :password"),
        @NamedQuery(name = "getAppUserById", query = "SELECT u FROM AppUser u WHERE u.id = :userId"),
        @NamedQuery(name = "getAppUserByUsername", query = "SELECT u FROM AppUser u WHERE u.username = :username")
})

public class AppUser implements Serializable {

    // ======================================
    // =             Attribute             =
    // ======================================
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
     
    // ======================================
    // =           Public Methods           =
    // ======================================
    
    public AppUser()
    {
        this.id = Long.parseLong("0");
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.username = "";
        this.password = "";
        this.email = "";
        this.phoneNumber = "";
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.RYDA.entities.Users[ id=" + id + " ]";
    }
    
}
