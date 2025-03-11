package ua.dev.todoapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.dev.todoapplication.entity.Task;

public class TaskService {

    private final FileService fileService = new FileService();

    public void addNewTask(Task newTask) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonTask = mapper.writeValueAsString(newTask);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error at" + TaskService.class + "| " + e);
        }
    }

}
