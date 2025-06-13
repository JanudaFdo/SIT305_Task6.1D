package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.example.updatedsmartlearnapp.R;
import com.example.updatedsmartlearnapp.adapters.HistoryAdapter;
import com.example.updatedsmartlearnapp.models.QuizQuestion;
import com.example.updatedsmartlearnapp.models.QuizResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecycler;
    private TextView emptyNotice;

    public HistoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        historyRecycler = view.findViewById(R.id.recycler_history);
        emptyNotice = view.findViewById(R.id.tv_no_history);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String lastQuizJson = prefs.getString("last_quiz", null);

        if (lastQuizJson == null || lastQuizJson.trim().isEmpty()) {
            emptyNotice.setVisibility(View.VISIBLE);
            historyRecycler.setVisibility(View.GONE);
            return;
        }

        QuizResponse quizData = new Gson().fromJson(lastQuizJson, QuizResponse.class);
        List<QuizQuestion> questionList = (quizData != null && quizData.getQuiz() != null)
                ? quizData.getQuiz()
                : new ArrayList<>();

        if (questionList.isEmpty()) {
            emptyNotice.setVisibility(View.VISIBLE);
            historyRecycler.setVisibility(View.GONE);
        } else {
            emptyNotice.setVisibility(View.GONE);
            historyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            historyRecycler.setAdapter(new HistoryAdapter(questionList));
        }
    }
}
