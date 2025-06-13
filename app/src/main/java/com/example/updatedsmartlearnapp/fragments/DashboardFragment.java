package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import com.example.updatedsmartlearnapp.R;

public class DashboardFragment extends Fragment {

    private TextView tvGreeting;
    private ImageView ivAvatar;
    private CardView taskCard;
    private CardView profileCard;
    private CardView upgradeCard;


    public DashboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvGreeting = view.findViewById(R.id.tv_greeting);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        taskCard = view.findViewById(R.id.card_task);
        profileCard = view.findViewById(R.id.card_profile);
        upgradeCard = view.findViewById(R.id.card_upgrade);
        CardView historyCard = view.findViewById(R.id.card_history);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "Student");

        tvGreeting.setText("Hello,\n" + username);
        ivAvatar.setImageResource(R.drawable.avatar);

        taskCard.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_taskFragment);
        });

        profileCard.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_profileFragment);
        });
        upgradeCard.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_upgradeFragment);
        });
        historyCard.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_historyFragment)
        );
    }
}
