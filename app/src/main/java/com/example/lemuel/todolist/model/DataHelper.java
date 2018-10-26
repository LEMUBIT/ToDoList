package com.example.lemuel.todolist.model;

import com.example.lemuel.todolist.model.Task;

import io.realm.Realm;

//todo(5)
public class DataHelper {
    private static final String FIELD_ID = "id";

    public static void newTask(Realm realm, int taskID, String task){
        realm.beginTransaction();
        //because is has a primary ket hence use 'createObject(Class<E>, Object)' instead
        Task taskn = realm.createObject(Task.class, taskID);
        taskn.setTask(task);
        realm.commitTransaction();
    }

   public static void delete(Realm realm, final long id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task item = realm.where(Task.class).equalTo(FIELD_ID, id).findFirst();
                // Otherwise it has been deleted already.
                if (item != null) {
                    item.deleteFromRealm();
                }
            }
        });

    }
}
