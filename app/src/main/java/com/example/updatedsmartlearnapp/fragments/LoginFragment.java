package com.example.updatedsmartlearnapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.*;
import androidx.navigation.Navigation;
import com.example.updatedsmartlearnapp.R;

public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etUsername = view.findViewById(R.id.et_login_username);
        etPassword = view.findViewById(R.id.et_login_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvRegister = view.findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(v -> {
            String enteredUsername = etUsername.getText().toString().trim();
            String enteredPassword = etPassword.getText().toString().trim();

            SharedPreferences prefs = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
            String savedUsername = prefs.getString("username", "");
            String savedPassword = prefs.getString("password", "");
            boolean interestsSaved = prefs.getBoolean("interestsSaved", false);

            if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();

                if (interestsSaved) {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dashboardFragment);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_interestSelectionFragment);
                }
            } else {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        );
    }
}
