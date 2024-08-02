package com.example.demo5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import javax.persistence.Table;

@Entity
@Table(name = "tb_m_user")

public class User{
  @Column(name = "password")
  private String password;


  @Column(name = "username")
  private String username;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;


    @Id
    @Column
    private Integer id;
    
    @OneToOne
    @MapsId
    private Employee employee;

    public User() {
    }

    public User(Role role, String password) {
    this.role = role;
    this.password = password;
    }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

    public Role getRole() {
      return role;
    }

    public void setRole(Role role) {
      this.role = role;
    }

    public Employee getEmployee() {
      return employee;
    }

    public void setEmployee(Employee employee) {
      this.employee = employee;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

}
