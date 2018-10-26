package com.example.lemuel.todolist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;
import com.example.lemuel.todolist.model.DataHelper;
import com.example.lemuel.todolist.model.Task;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

//todo (10)
public class MainActivity extends AppCompatActivity implements NewTaskDialog.TaskDialogListener {
    private Realm realm;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAddTask = findViewById(R.id.fab_addTask);
        recyclerView = findViewById(R.id.my_recycler_view);
        //todo later
        final DialogFragment newFragment = new NewTaskDialog();
        realm = Realm.getDefaultInstance();
        setUpRecyclerView();
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getSupportFragmentManager(), "New Task");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }

    @Override
    public void onDialogPositiveClick(String task) {
        SecureRandom rand = new SecureRandom();

        // Generate random integers in range 0 to 999
        int taskID = rand.nextInt(100000);
        DataHelper.newTask(realm, taskID, task);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView() {
        adapter = new RecyclerViewAdapter(realm.where(Task.class).findAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            DataHelper.delete(realm, viewHolder.getItemId());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }

}
