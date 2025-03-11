package ua.dev.todoapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.dev.todoapplication.entity.Root;
import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.entity.Today;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileService {

    private final String FILE_NAME = "taskStorage.txt";
    private final String JSON_FILE_NAME = "jsonStorage.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private Root root;

    public List<Task> readFile(){
        List<Task> taskList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = reader.readLine()) != null){
                Task task = new Task(
                        extractValue(line, "id"),
                        extractValue(line, "title"),
                        extractValue(line, "description"),
                        extractValue(line, "creationDate"),
                        extractValue(line, "date"),
                        extractValue(line, "priority"),
                        extractValue(line, "timeStart"),
                        extractValue(line, "timeEnd"));
                System.out.println(task);
                taskList.add(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }

    public void readJsonFile(){
        try{
            Path filePath = Paths.get(JSON_FILE_NAME);
            String json = Files.readString(filePath);
            if(json.isEmpty()){
                createEmptyRoot();
            }else {
                root = mapper.readValue(json, Root.class);
                System.out.println(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in " + FileService.class + " | " + e);
        }
    }

    private void createEmptyRoot() {
        Today today = new Today();
        today.setUncompleted(Collections.emptyList());
        today.setCompleted(Collections.emptyList());
        root = new Root();
        root.setToday(today);
    }

    public Root getRoot(){
        return Objects.requireNonNullElseGet(root, Root::new);
    }

    private String extractValue(String line, String key) {
        String regex = key + "='([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "Null";
    }

//    public void writeNewTask(Task task){
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
//            writer.append(String.valueOf(task));
//            writer.newLine();
//            writer.flush();
//        } catch (IOException e) {
//            throw new RuntimeException("Error in " + FileService.class + " | " + e);
//        }
//    }

    public void writeNewTask(Task task){
        List<Task> todayUncompletedTaskList = new ArrayList<>(getRoot().getToday().getUncompleted());
        todayUncompletedTaskList.add(task);
        root.getToday().setUncompleted(todayUncompletedTaskList);
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
