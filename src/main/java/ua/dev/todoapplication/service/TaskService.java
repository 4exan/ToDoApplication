package ua.dev.todoapplication.service;

import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.entity.Unsorted;

import java.util.Collections;
import java.util.List;

public class TaskService {

    private final FileService fileService;

    public TaskService(FileService fileService) {
        this.fileService = fileService;
    }

    private Unsorted getUnsorted(){
        return fileService.getRoot().getUnsorted();
    }

    public List<Task> getUnsortedCompletedTasks(){
        Unsorted unsortedTasks = getUnsorted();
        List<Task> completed = unsortedTasks.getCompleted();
        if(completed.isEmpty()){
            return Collections.emptyList();
        }else{
            return completed;
        }
    }

    public List<Task> getUnsortedUncompletedTasks(){
        Unsorted unsortedTasks = getUnsorted();
        List<Task> uncompleted = unsortedTasks.getUncompleted();
        if(uncompleted.isEmpty()){
            return Collections.emptyList();
        }else{
            return uncompleted;
        }
    }

}
