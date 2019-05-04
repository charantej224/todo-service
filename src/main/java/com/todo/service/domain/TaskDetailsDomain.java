package com.todo.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDetailsDomain {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("estimatedTime")
    private Integer estimatedTime;

    @JsonProperty("taskname")
    private String taskName;

    @JsonProperty("taskdescription")
    private String taskDescription;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}