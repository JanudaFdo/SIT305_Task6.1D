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

public class RegisterFragment extends Fragment {

    private EditText etUsername, etEmail, etConfirmEmail, etPassword, etConfirmPassword, etPhone;
    private Button btnCreate;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etUsername = view.findViewById(R.id.et_reg_username);
        etEmail = view.findViewById(R.id.et_reg_email);
        etConfirmEmail = view.findViewById(R.id.et_reg_confirm_email);
        etPassword = view.findViewById(R.id.et_reg_password);
        etConfirmPassword = view.findViewById(R.id.et_reg_confirm_password);
        etPhone = view.findViewById(R.id.et_reg_phone);
        btnCreate = view.findViewById(R.id.btn_create_account);

        btnCreate.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String confirmEmail = etConfirmEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || confirmEmail.isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!email.equals(confirmEmail)) {
                Toast.makeText(getContext(), "Emails do not match", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences prefs = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                prefs.edit()
                        .putString("username", username)
                        .putString("password", password)
                        .putString("email", email)
                        .putBoolean("interestsSaved", false)
                        .apply();

                Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }
}
