package com.example.demo.model;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "tb_m_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    
    @Column
    private String name;

    @Column
    private Integer level;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(){}

    public Role(Integer id, String name, Integer level, List<User> users) {
      this.id = id;
      this.name = name;
      this.level = level;
      this.users = users;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getLevel() {
      return level;
    }

    public void setLevel(Integer level) {
      this.level = level;
    }

    public List<User> getUsers() {
      return users;
    }

    public void setUsers(List<User> users) {
      this.users = users;
    }
}
