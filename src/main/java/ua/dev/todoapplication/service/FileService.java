package ua.dev.todoapplication.service;

import ua.dev.todoapplication.entity.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileService {

    private final String FILE_NAME = "taskStorage.txt";

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

    private String extractValue(String line, String key) {
        String regex = key + "='([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "Null";
    }

    public void writeNewTask(Task task){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.append(String.valueOf(task));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
