package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.updatedsmartlearnapp.R;
import com.example.updatedsmartlearnapp.models.QuizQuestion;
import com.example.updatedsmartlearnapp.models.QuizResponse;
import com.google.gson.Gson;
import java.util.List;
import android.graphics.Typeface;

public class ResultFragment extends Fragment {

    private LinearLayout resultContainer;
    private Button btnContinue;

    public ResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        resultContainer = view.findViewById(R.id.quiz_container);
        btnContinue = view.findViewById(R.id.btn_continue);


        TextView label = new TextView(getContext());
        label.setText("✨ Answered by AI");
        label.setTextColor(Color.CYAN);
        label.setTextSize(14);
        label.setTypeface(null, Typeface.ITALIC);
        label.setPadding(0, 0, 0, 8);
        resultContainer.addView(label);


        TextView heading = new TextView(getContext());
        heading.setText("Your Results");
        heading.setTextColor(Color.BLACK);
        heading.setTextSize(24);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(0, 0, 0, 24);
        resultContainer.addView(heading);

        // Load and show answers
        SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String quizJson = prefs.getString("last_quiz", null);

        if (quizJson != null && !quizJson.trim().isEmpty()) {
            try {
                QuizResponse quizResponse = new Gson().fromJson(quizJson, QuizResponse.class);
                if (quizResponse != null && quizResponse.getQuiz() != null) {
                    showResults(quizResponse.getQuiz());
                } else {
                    showError("Quiz data is missing or invalid.");
                }
            } catch (Exception e) {
                showError("Failed to parse quiz data.");
                e.printStackTrace();
            }
        } else {
            showError("No previous quiz data found.");
        }

        btnContinue.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_dashboardFragment)
        );
    }



    private void showResults(List<QuizQuestion> questions) {
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            int index = convertAnswerToIndex(q.getCorrectAnswer());

            if (index < 0 || index >= q.getOptions().size()) {
                Log.w("ResultFragment", "Invalid correct answer index for question: " + q.getQuestion());
                continue;
            }

            String correctOption = q.getOptions().get(index);

            CardView cardView = new CardView(requireContext());
            cardView.setCardElevation(4);
            cardView.setRadius(12);
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            cardView.setUseCompatPadding(true);

            TextView content = new TextView(requireContext());
            content.setText((i + 1) + ". " + q.getQuestion() + "\n✅ " + correctOption);
            content.setTextColor(Color.WHITE);
            content.setTextSize(16);
            content.setPadding(20, 20, 20, 20);

            cardView.addView(content);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            cardView.setLayoutParams(params);

            resultContainer.addView(cardView);
        }
    }

    private int convertAnswerToIndex(String letter) {
        if (letter == null) return 0;
        switch (letter.toUpperCase()) {
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return -1;
        }
    }

    private void showError(String message) {
        TextView error = new TextView(requireContext());
        error.setText(message);
        error.setTextColor(Color.RED);
        error.setTextSize(16);
        error.setPadding(0, 20, 0, 20);
        resultContainer.addView(error);

        // Also fade in error message smoothly
        resultContainer.animate().alpha(1f).setDuration(500).start();
    }
}
