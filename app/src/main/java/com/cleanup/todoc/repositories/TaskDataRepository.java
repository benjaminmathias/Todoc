package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    // GET
    // Return list of Task
    public LiveData<List<Task>> getTasks(){
        return this.taskDao.getTasks();
    }

    // Return a single task
    public LiveData<Task> getTask(long taskId){
        return this.taskDao.getTask(taskId);
    }

    // CREATE
    public void createTask(Task task){
        taskDao.insertTask(task);
    }

    // DELETE
    public void deleteTask(long taskId){
        taskDao.deleteTask(taskId);
    }


}
