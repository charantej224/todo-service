package com.todo.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserDetails.
 */
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "jhi_password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;

    @UpdateTimestamp
    @Column(name = "modified_time")
    private Instant modifiedTime;

    /**
     * one department can be associated with multiple employees.
     */
    @ApiModelProperty(value = "one department can be associated with multiple employees.")
    @OneToMany(mappedBy = "userDetails")
    private Set<TaskDetails> taskDetails = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public UserDetails userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public UserDetails password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public UserDetails createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public UserDetails modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Set<TaskDetails> getTaskDetails() {
        return taskDetails;
    }

    public UserDetails taskDetails(Set<TaskDetails> taskDetails) {
        this.taskDetails = taskDetails;
        return this;
    }

    public UserDetails addTaskDetails(TaskDetails taskDetails) {
        this.taskDetails.add(taskDetails);
        taskDetails.setUserDetails(this);
        return this;
    }

    public UserDetails removeTaskDetails(TaskDetails taskDetails) {
        this.taskDetails.remove(taskDetails);
        taskDetails.setUserDetails(null);
        return this;
    }

    public void setTaskDetails(Set<TaskDetails> taskDetails) {
        this.taskDetails = taskDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetails userDetails = (UserDetails) o;
        if (userDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDetails{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
