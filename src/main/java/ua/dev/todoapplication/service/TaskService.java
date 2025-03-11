package ua.dev.todoapplication.service;

import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.entity.Today;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TaskService {

    private final FileService fileService;

    public TaskService(FileService fileService) {
        this.fileService = fileService;
    }

    private Today getToday(){
        return fileService.getRoot().getToday();
    }

    public List<Task> getTodayCompletedTasks(){
        Today today = getToday();
        List<Task> completed = today.getCompleted();
        if(completed.isEmpty()){
            return Collections.emptyList();
        }else{
            return completed;
        }
    }

    public List<Task> getTodayUncompletedTasks(){
        Today today = getToday();
        List<Task> uncompleted = today.getUncompleted();
        if(uncompleted.isEmpty()){
            return Collections.emptyList();
        }else{
            return uncompleted;
        }
    }

}
