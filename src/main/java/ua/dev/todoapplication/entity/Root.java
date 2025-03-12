package ua.dev.todoapplication.entity;

import java.util.List;

public class Root {

    private Unsorted unsorted;
    private List<Project> projects;

    public Unsorted getUnsorted() {
        return unsorted;
    }

    public void setUnsorted(Unsorted unsorted) {
        this.unsorted = unsorted;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Root{" +
                unsorted +
                projects +
                '}';
    }
}
