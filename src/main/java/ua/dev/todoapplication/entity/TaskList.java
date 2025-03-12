package ua.dev.todoapplication.entity;

import java.util.List;

public abstract class TaskList {

     public List<Task> completed;
     public List<Task> uncompleted;

    public List<Task> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Task> completed) {
        this.completed = completed;
    }

    public List<Task> getUncompleted() {
        return uncompleted;
    }

    public void setUncompleted(List<Task> uncompleted) {
        this.uncompleted = uncompleted;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                completed +
                uncompleted +
                '}';
    }
}
