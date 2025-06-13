package com.example.updatedsmartlearnapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizResponse {

    @SerializedName("quiz")
    private List<QuizQuestion> quiz;

    public List<QuizQuestion> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<QuizQuestion> quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "QuizResponse: " + quiz;
    }
}
