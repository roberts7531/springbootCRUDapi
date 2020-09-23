package com.roberts7531.spring.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Task name is mandatory")
    private String TaskName;

    @NotBlank(message = "Task content is mandatory")
    private String Task;

    public long getId() {
        return id;
    }

    public Task() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task(@NotEmpty(message = "Task name is mandatory") String taskName, @NotBlank(message = "Task content is mandatory") String task) {
        TaskName = taskName;
        Task = task;
    }

    public String getTaskName() {
        return TaskName;
    }
    public void setTaskName(String taskName) {
        TaskName = taskName;
    }
    public String getTask() {
        return Task;
    }
    public void setTask(String task) {
        Task = task;
    }


}
