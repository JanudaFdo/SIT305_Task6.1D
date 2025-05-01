package com.example.learningapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskDetailsActivity extends AppCompatActivity {

    private RecyclerView QuizRV;
    private String topic;
    private String description;
    private String[] quizSelection;
    private User user;
    private TextView TitleTV, DescriptionTV;
    private Button SubmitBTN;
    private ManagerDB managerDB;
    private VerticalAdapter<Quiz> quizAdapter;
    private List<Quiz> quizList = new ArrayList<>();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        managerDB = new ManagerDB(this);

        topic = getIntent().getStringExtra("Topic");
        description = getIntent().getStringExtra("Description");
        user = (User) getIntent().getSerializableExtra("User");

        QuizRV = findViewById(R.id.QuizRecyclerView);
        TitleTV = findViewById(R.id.GeneratedTask);
        DescriptionTV = findViewById(R.id.SmallDescriptionTextView);
        SubmitBTN = findViewById(R.id.submitButton);

        SubmitBTN.setEnabled(false);
        QuizRV.setLayoutManager(new LinearLayoutManager(this));
        TitleTV.setText(topic);
        DescriptionTV.setText(description);
        quizSelection = new String[3]; // assuming 3 questions always

        pd = new ProgressDialog(this);
        pd.setMessage("Loading quiz...");
        pd.setCancelable(false);
        pd.show();

        fetchQuizData();
        setupSubmitButton();
    }

    private void fetchQuizData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.141.25.75:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .readTimeout(10, java.util.concurrent.TimeUnit.MINUTES)
                        .build())
                .build();

        QuizApiService apiService = retrofit.create(QuizApiService.class);
        Call<TaskResponse> call = apiService.getQuiz(topic);

        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                pd.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    quizList = response.body().getQuizList();

                    if (quizList == null || quizList.isEmpty()) {
                        Toast.makeText(TaskDetailsActivity.this, "No quiz questions found.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Log.d("QuizList", "Quiz fetched: " + new Gson().toJson(quizList));
                    initQuizAdapter(quizList);
                    SubmitBTN.setEnabled(true);
                } else {
                    Log.e("QuizFetch", "Response code: " + response.code());
                    Toast.makeText(TaskDetailsActivity.this, "Failed to load quiz. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                pd.dismiss();
                Log.e("QuizFetch", "Network failure", t);
                Toast.makeText(TaskDetailsActivity.this, "Error fetching quiz: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initQuizAdapter(List<Quiz> quizzes) {
        quizAdapter = new VerticalAdapter<>(quizzes, (item, selection) -> {
            int position = quizzes.indexOf(item);
            if (position != -1) {
                quizSelection[position] = selection;
                Log.d("QuizSelection", "Position: " + position + " Answer: " + selection);
            }
        });

        QuizRV.setAdapter(quizAdapter);
    }

    private void setupSubmitButton() {
        SubmitBTN.setOnClickListener(v -> {
            for (String selection : quizSelection) {
                if (selection == null || selection.isEmpty()) {
                    Toast.makeText(TaskDetailsActivity.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            List<Result> resultList = new ArrayList<>();
            for (int i = 0; i < quizList.size(); i++) {
                resultList.add(new Result(quizList.get(i), quizSelection[i]));
            }

            managerDB.deleteUserInterest(user.getId(), topic);

            Intent intent = new Intent(TaskDetailsActivity.this, ResultActivity.class);
            intent.putExtra("ResultList", (Serializable) resultList);
            intent.putExtra("User", user);
            startActivity(intent);
        });
    }
}
