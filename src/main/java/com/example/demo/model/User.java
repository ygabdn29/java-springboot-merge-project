package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_m_user")

public class User{
  @Column(name ="password")
  private String password;

  @Column(name ="username")
  private String username;

  @Column(name ="is_verified")
  private Boolean isVerified;

  @Column(name ="guid")
  private String guid;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  @Id
  @Column
  private Integer id;

  @Column
  private Boolean isVerified;

  @Column
  private String guid;

  @OneToOne
  @MapsId
  @JsonIgnore
  private Employee employee;

  public User() {
  }


  public User(String password, String username, Role role, Boolean isVerified, Integer id, Employee employee) {
    this.password = password;
    this.username = username;
    this.role = role;
    this.isVerified = isVerified;
    this.id = id;
    this.employee = employee;
  }
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public Boolean getIsVerified() {
    return isVerified;
  }

  public void setIsVerified(Boolean isVerified) {
    this.isVerified = isVerified;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

}