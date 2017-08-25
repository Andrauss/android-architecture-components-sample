package com.daniloprado.tasklist.task;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.daniloprado.tasklist.R;
import com.daniloprado.tasklist.base.AppCompatLifecycleActivity;

import java.util.List;

public class TaskListActivity extends AppCompatLifecycleActivity implements TaskDetailDialogFragment.TaskDialogListener {

    RecyclerView recyclerView;
    TaskListAdapter taskListAdapter;
    TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_tasks);
        setupRecyclerView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> newTask());

        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.getTasks().observe(this, this::updateRecyclerView);
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        taskListAdapter = new TaskListAdapter(new TaskListAdapter.OnTaskClickedListener() {
            @Override
            public void onClick(Task task) {
                editTask(task);
            }

            @Override
            public void onLongClick(Task task) {
                viewModel.deleteTask(task);
            }
        });
        recyclerView.setAdapter(taskListAdapter);
    }

    private void updateRecyclerView(List<Task> tasks) {
        taskListAdapter.replaceItems(tasks);
        taskListAdapter.notifyDataSetChanged();
    }

    private void newTask() {
        TaskDetailDialogFragment dialog = TaskDetailDialogFragment.newInstance(new Task("", ""));
        dialog.show(getFragmentManager(), "TaskDetailDialogFragment");
    }

    private void editTask(Task task) {
        TaskDetailDialogFragment dialog = TaskDetailDialogFragment.newInstance(task);
        dialog.show(getFragmentManager(), "TaskDetailDialogFragment");
    }

    @Override
    public void onDialogSaveClick(Task task) {
        viewModel.saveTask(task);
    }
}
