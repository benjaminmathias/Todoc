package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // Repo
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    // Data
    @Nullable
    private LiveData<Task> currentTask;

    public TaskViewModel(TaskDataRepository taskDataSource, Executor executor){
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init(long taskId){
        if(this.currentTask != null){
            return;
        }
        currentTask = taskDataSource.getTask(taskId);
    }

    // For Task
    public LiveData<Task> getTask(long taskId){
        return this.currentTask;
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getTasks();
    }

    public void createTask(final Task task){
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }


}
