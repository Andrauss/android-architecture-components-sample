package com.daniloprado.tasklist.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daniloprado.tasklist.R;

import java.util.ArrayList;
import java.util.List;

class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private final OnTaskClickedListener listener;

    TaskListAdapter(OnTaskClickedListener onTaskClickedListener) {
        this.listener = onTaskClickedListener;
    }

    public void replaceItems(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());
        holder.view.setOnClickListener(view -> listener.onClick(task));
        holder.view.setOnLongClickListener(view -> {
            listener.onLongClick(task);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    interface OnTaskClickedListener {
        void onClick(Task task);
        void onLongClick(Task task);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView textViewTitle;
        TextView textViewDescription;

        public ViewHolder(View v) {
            super(v);
            view = v;
            textViewTitle = (TextView) v.findViewById(R.id.textview_task_title);
            textViewDescription = (TextView) v.findViewById(R.id.textview_task_description);
        }

    }
}
