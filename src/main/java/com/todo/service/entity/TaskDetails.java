package com.todo.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Class Name : TaskDetails.
 *              entity classes used by java ORM framework to persist the data into database.
 *
 */
@Entity
@Table(name = "task_details")
public class TaskDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "task_name", unique = true)
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "modified_time")
    private Instant modifiedTime;

    @JsonIgnore
    @ManyToOne
    @JsonIgnoreProperties("taskDetails")
    private UserDetails userDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskDetails taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public TaskDetails taskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        return this;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public TaskDetails estimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public TaskDetails createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public TaskDetails modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public TaskDetails userDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        return this;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
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
        TaskDetails taskDetails = (TaskDetails) o;
        if (taskDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskDetails{" +
            "id=" + getId() +
            ", taskName='" + getTaskName() + "'" +
            ", taskDescription='" + getTaskDescription() + "'" +
            ", estimatedTime=" + getEstimatedTime() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", modifiedTime='" + getModifiedTime() + "'" +
            "}";
    }
}
