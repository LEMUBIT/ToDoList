package com.example.lemuel.todolist.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//todo (4)
public class Task extends RealmObject {

    @PrimaryKey
    int id;

   private String task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
