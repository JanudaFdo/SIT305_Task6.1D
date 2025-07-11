package com.example.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private List<Result> resultList;
    RecyclerView ResultRV;
    Button ContinueBTN;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        user = (User) getIntent().getSerializableExtra("User");

        // Initialize view components
        ResultRV = (RecyclerView) findViewById(R.id.ResultRecyclerView);
        ContinueBTN = (Button) findViewById(R.id.ContinueButton);

        // Get data from previous activity
        resultList = (List<Result>) getIntent().getSerializableExtra("ResultList");

        // Setup recyclerview
        ResultRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Setup button
        ContinueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TaskActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }
}