package ua.dev.todoapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.dev.todoapplication.entity.Root;
import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.entity.Unsorted;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileService {

    private final String JSON_FILE_NAME;
    private final ObjectMapper mapper = new ObjectMapper();
    private Root root;

    public FileService(String filePath){
        this.JSON_FILE_NAME = filePath;
        readJsonFile();
    }

    public void readJsonFile(){
        try{
            File storage = new File(JSON_FILE_NAME);
            if(storage.createNewFile()){
                System.out.println("Created new storage");
            }else{
                Path filePath = Paths.get(JSON_FILE_NAME);
                String json = Files.readString(filePath);
                if(json.isEmpty()){
                    createEmptyRoot();
                }else {
                    root = mapper.readValue(json, Root.class);
                    System.out.println(root);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in " + FileService.class + " | " + e);
        }
    }

    private void createEmptyRoot() {
        Unsorted unsortedTasks = new Unsorted();
        unsortedTasks.setCompleted(Collections.emptyList());
        unsortedTasks.setUncompleted(Collections.emptyList());
        root.setProjects(Collections.emptyList());
        root.setUnsorted(unsortedTasks);
    }

    public Root getRoot(){
        return root;
    }

    public void writeNewTask(Task task){
        List<Task> unsortedUncompletedTaskList = new ArrayList<>(getRoot().getUnsorted().getUncompleted());
        unsortedUncompletedTaskList.add(task);
        root.getUnsorted().setUncompleted(unsortedUncompletedTaskList);
        writeChangesToFile();
    }

    private void writeChangesToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_NAME))) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String json = mapper.writeValueAsString(root);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error in " + FileService.class + " | " + e);
        }
    }
}
