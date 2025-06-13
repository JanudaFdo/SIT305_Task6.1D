package com.example.updatedsmartlearnapp.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.updatedsmartlearnapp.R;
import com.example.updatedsmartlearnapp.models.QuizQuestion;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.QuestionViewHolder> {

    private final List<QuizQuestion> questionList;

    public HistoryAdapter(List<QuizQuestion> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuizQuestion q = questionList.get(position);

        holder.tvTitle.setText((position + 1) + ". " + trimText(q.getQuestion(), 6));
        holder.tvQuestion.setText(q.getQuestion());


        int correctIndex = convertAnswerToIndex(q.getCorrectAnswer());
        int userIndex = 0;

        List<String> opts = q.getOptions();
        String[] optionLabels = {"A", "B", "C", "D"};
        TextView[] optionViews = {holder.tvOpt1, holder.tvOpt2, holder.tvOpt3, holder.tvOpt4};

        for (int i = 0; i < 4; i++) {
            String prefix = "⚪ ";
            int color = Color.WHITE;
            if (i == userIndex) {
                prefix = "🔴 ";
                color = Color.RED;
            }
            if (i == correctIndex) {
                prefix = "🟢 ";
                color = Color.GREEN;
            }
            String tag = (i == correctIndex && i == userIndex) ? "(You & Correct)" :
                    (i == correctIndex) ? "(Correct Answer)" :
                            (i == userIndex) ? "(Your Answer)" : "";

            optionViews[i].setText(prefix + opts.get(i) + " " + tag);
            optionViews[i].setTextColor(color);
        }

        // Toggle expand/collapse
        holder.headerRow.setOnClickListener(v -> {
            if (holder.expandSection.getVisibility() == View.VISIBLE) {
                holder.expandSection.setVisibility(View.GONE);
                holder.toggleIcon.setRotation(0f);
            } else {
                holder.expandSection.setVisibility(View.VISIBLE);
                holder.toggleIcon.setRotation(180f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout expandSection, headerRow;
        TextView tvTitle, tvQuestion, tvOpt1, tvOpt2, tvOpt3, tvOpt4;
        ImageView toggleIcon;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            expandSection = itemView.findViewById(R.id.expand_section);
            headerRow = itemView.findViewById(R.id.header_row);
            tvTitle = itemView.findViewById(R.id.tv_hist_title);
            tvQuestion = itemView.findViewById(R.id.tv_hist_question);
            tvOpt1 = itemView.findViewById(R.id.tv_opt1);
            tvOpt2 = itemView.findViewById(R.id.tv_opt2);
            tvOpt3 = itemView.findViewById(R.id.tv_opt3);
            tvOpt4 = itemView.findViewById(R.id.tv_opt4);
            toggleIcon = itemView.findViewById(R.id.iv_expand_toggle);
        }
    }

    private int convertAnswerToIndex(String letter) {
        if (letter == null) return -1;
        switch (letter.toUpperCase()) {
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            default: return -1;
        }
    }

    private String trimText(String input, int wordLimit) {
        String[] words = input.split("\\s+");
        if (words.length <= wordLimit) return input;
        StringBuilder trimmed = new StringBuilder();
        for (int i = 0; i < wordLimit; i++) trimmed.append(words[i]).append(" ");
        return trimmed.toString().trim() + "...";
    }
}
