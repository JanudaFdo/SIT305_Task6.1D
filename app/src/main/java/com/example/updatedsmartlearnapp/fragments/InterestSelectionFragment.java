package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;
import com.example.updatedsmartlearnapp.R;
import com.example.updatedsmartlearnapp.adapters.InterestAdapter;
import com.example.updatedsmartlearnapp.models.Topic;
import java.util.*;

public class InterestSelectionFragment extends Fragment {

    private InterestAdapter adapter;
    private Button btnNext;

    public InterestSelectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interest_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler_topics);
        btnNext = view.findViewById(R.id.btn_next);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        boolean alreadySaved = prefs.getBoolean("interestsSaved", false);


        if (alreadySaved) {
            Navigation.findNavController(view).navigate(R.id.action_interestSelectionFragment_to_dashboardFragment);
            return;
        }

        List<Topic> dummyTopics = Arrays.asList(
                new Topic("Algorithms"),
                new Topic("Data Structures"),
                new Topic("Web Development"),
                new Topic("Testing"),
                new Topic("AI Basics"),
                new Topic("Mobile Apps"),
                new Topic("Backend Dev"),
                new Topic("DevOps"),
                new Topic("Cloud"),
                new Topic("UI/UX"),
                new Topic("Security"),
                new Topic("Databases")
        );

        adapter = new InterestAdapter(getContext(), dummyTopics);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycler.setAdapter(adapter);

        btnNext.setOnClickListener(v -> {
            List<String> selected = adapter.getSelectedTopics();
            if (selected.isEmpty()) {
                Toast.makeText(getContext(), "Please select at least 1 topic", Toast.LENGTH_SHORT).show();
            } else {
                String joined = String.join(",", selected);
                prefs.edit()
                        .putString("interests", joined)
                        .putBoolean("interestsSaved", true)
                        .apply();

                Navigation.findNavController(view).navigate(R.id.action_interestSelectionFragment_to_dashboardFragment);
            }
        });
    }
}
