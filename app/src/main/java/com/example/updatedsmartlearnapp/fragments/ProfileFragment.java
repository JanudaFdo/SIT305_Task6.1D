package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.updatedsmartlearnapp.R;

public class ProfileFragment extends Fragment {

    private TextView nameDisplay, emailDisplay, totalCount, correctCount, incorrectCount, aiHint, notificationLine;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nameDisplay = view.findViewById(R.id.tv_profile_name);
        emailDisplay = view.findViewById(R.id.tv_profile_email);
        totalCount = view.findViewById(R.id.tv_total_questions);
        correctCount = view.findViewById(R.id.tv_correct_answers);
        incorrectCount = view.findViewById(R.id.tv_incorrect_answers);
        aiHint = view.findViewById(R.id.ai_summary_hint);
        notificationLine = view.findViewById(R.id.tv_notification_placeholder);
        Button btnShare = view.findViewById(R.id.btn_share);
        ImageView avatarImage = view.findViewById(R.id.iv_profile_avatar);
        avatarImage.setImageResource(R.drawable.avatar);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        String username = prefs.getString("username", "Student");
        String email = prefs.getString("email", "student@example.com");
        emailDisplay.setText(email);
        int total = prefs.getInt("total_questions", 10);
        int correct = prefs.getInt("correct_answers", 10);
        int incorrect = prefs.getInt("incorrect_answers", 10);

        nameDisplay.setText(username);
        emailDisplay.setText(email);

        totalCount.setText(String.valueOf(total));
        correctCount.setText(String.valueOf(correct));
        incorrectCount.setText(String.valueOf(incorrect));

        aiHint.setText("✨ Summarized by AI — ask the system to help clarify your mistakes.");
        notificationLine.setText("🔔 Display any important notifications here");

        btnShare.setOnClickListener(v -> {
            String summary = "📘 SmartLearn Stats for " + username + "\n\n"
                    + "• Total Questions: " + total + "\n"
                    + "• Correct Answers: " + correct + "\n"
                    + "• Incorrect Answers: " + incorrect + "\n"
                    + "✨ Powered by AI";

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "SmartLearn Profile Summary");
            intent.putExtra(Intent.EXTRA_TEXT, summary);

            startActivity(Intent.createChooser(intent, "Share"));
        });
    }
}