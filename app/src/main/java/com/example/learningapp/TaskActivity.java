package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    RecyclerView TaskRV;
    TextView NotificationTV, NameTV;
    VerticalAdapter<Task> taskAdapter;
    List<Task> taskList;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        user = (User) getIntent().getSerializableExtra("User");

        // Initialize view components
        TaskRV = (RecyclerView) findViewById(R.id.TaskRecyclerView);
        NotificationTV = (TextView) findViewById(R.id.Notification);
        NameTV = (TextView) findViewById(R.id.YourName);

        // Generate task based on user's interests and update notification
        if (user.getTasks().isEmpty()) GenerateTask();
        taskList = user.getTasks();
        Log.d("TaskActivity", "Number of tasks: " + taskList.size());
        NotificationTV.setText("You have " + countIncompleteTasks(taskList) +" task due");
        NameTV.setText(user.getUsername());

        // Setup recyclerview
        taskAdapter = new VerticalAdapter<Task>(taskList, (item, selection) -> {
            Intent intent = new Intent(TaskActivity.this, TaskDetailsActivity.class);
            intent.putExtra("Topic", item.getTitle());
            intent.putExtra("Description", item.getDescription());
            intent.putExtra("User", user);
            startActivity(intent);
        });
        TaskRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TaskRV.setAdapter(taskAdapter);
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