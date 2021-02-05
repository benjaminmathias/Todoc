package com.cleanup.todoc;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // For DATA
    private TodocDatabase dataBase;

    // DATA FOR TEST
    private static long TASK_ID = 1;
    private static Task TASK_DEMO = new Task(TASK_ID, 1L, "Test", 1);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.dataBase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        dataBase.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // Adding a new user
        this.dataBase.taskDao().insertTask(TASK_DEMO);
        // Test
        Task task = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTask(TASK_ID));
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_ID);
    }

    public void getAllTasks() throws InterruptedException {
        this.dataBase.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTasks());
        Task task = LiveDataTestUtil.getValue(this.dataBase.taskDao().getTask(TASK_ID));
        assertTrue(tasks.contains(task));
    }
}
