package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    RecyclerView TaskRV;
    TextView NotificationTV, NameTV, EmailTV;
    List<Task> taskList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Retrieve the User object from intent
        user = (User) getIntent().getSerializableExtra("User");

        // Bind view elements
        TaskRV = findViewById(R.id.TaskRecyclerView);
        NotificationTV = findViewById(R.id.Notification);
        NameTV = findViewById(R.id.YourName);
        EmailTV = findViewById(R.id.YourEmail);  // Added binding for email TextView

        // Set user info
        NameTV.setText(user.getUsername());
        EmailTV.setText(user.getEmail());  // Set user email

        // Generate tasks if empty
        if (user.getTasks().isEmpty()) GenerateTask();
        taskList = user.getTasks();

        // Logging and notification update
        Log.d("TaskActivity", "Number of tasks: " + taskList.size());
        int incompleteCount = countIncompleteTasks(taskList);
        EmailTV.setText(user.getEmail());
        NotificationTV.setText("You have " + incompleteCount + " task" + (incompleteCount != 1 ? "s" : "") + " due");

        // Set up RecyclerView

        TaskRV.setLayoutManager(new LinearLayoutManager(this));

    }

    private void GenerateTask() {
        List<Task> taskList = new ArrayList<>();
        for (Interest interest : user.getInterests()) {
            taskList.add(new Task(interest.getTopic(), "Llama generated task for topic: " + interest.getTopic(), null, false));
        }
        user.setTasks(taskList);
    }

    private int countIncompleteTasks(List<Task> tasks) {
        int count = 0;
        for (Task task : tasks) {
            if (!task.getCompleted()) {
                count++;
            }
        }
        return count;
    }
}
