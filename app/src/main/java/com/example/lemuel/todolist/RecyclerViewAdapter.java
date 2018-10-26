package com.example.lemuel.todolist;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lemuel.todolist.model.Task;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

//todo(9) (Start from here)
public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<Task, RecyclerViewAdapter.MyViewHolder> {

    /**
     * autoUpdate when it is {@code false}, the adapter won't be automatically updated when collection data
     * changes.
     **/
    public RecyclerViewAdapter(@Nullable OrderedRealmCollection<Task> data) {
        super(data, true);
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        final Task taskObj = getItem(i);
        myViewHolder.taskTxt.setText(taskObj.getTask());
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskTxt;

        MyViewHolder(View view) {
            super(view);
            taskTxt = view.findViewById(R.id.txt_row_task);
        }
    }
}
