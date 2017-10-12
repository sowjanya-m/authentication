package com.telstra.webauth.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "password_history")
public class Password {
    private Long id;
    private Long userId;
    private String password;
    @Column(name = "Password_date")
    private Date passwordDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordDate() {
        return passwordDate;
    }

    public void setPasswordDate(Date passwordDate) {
        this.passwordDate = passwordDate;
    }
}
