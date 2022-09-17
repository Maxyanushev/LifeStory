package com.example.nana.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nana.R;
import com.example.nana.models.UserModel;
import com.example.nana.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    EditText username, email, password;
    TextView loginTitle, changeTypeOfAuth, registerTypes;
    Button buttonLogin;
    ImageView google, facebook, twitter;

    private boolean isSigningUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        init();
        initListeners();
    }

    private void init() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = binding.editUsername;
        email = binding.editEmail;
        password = binding.editPassword;

        loginTitle = binding.textLoginTitle;
        changeTypeOfAuth = binding.textChangeTypeOfAuth;
        registerTypes = binding.textRegisterTypes;

        buttonLogin = binding.btnLogin;

        google = binding.google;
        facebook = binding.facebook;
        twitter = binding.twitter;
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(v -> {
            if (isSigningUp) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Что-то не ввели...", Toast.LENGTH_LONG).show();
                } else {
                    handleRegister();
                }
            } else {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Что-то не ввели...", Toast.LENGTH_LONG).show();
                } else {
                    handleLogin();
                }
            }
        });

        binding.textChangeTypeOfAuth.setOnClickListener(v -> {
            if (isSigningUp) {
                isSigningUp = false;

                binding.editUsername.setVisibility(View.GONE);

                loginTitle.setText(R.string.login);
                buttonLogin.setText(R.string.login);
                changeTypeOfAuth.setText(R.string.do_not_have_an_account);
                registerTypes.setText(R.string.or_login_with);
            } else {
                isSigningUp = true;

                binding.editUsername.setVisibility(View.VISIBLE);

                loginTitle.setText(R.string.registration);
                buttonLogin.setText(R.string.register);
                changeTypeOfAuth.setText(R.string.already_have_an_account);
                registerTypes.setText(R.string.or_register_with);
            }
        });
    }

    private void handleRegister() {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseDatabase.getInstance().getReference("user/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(new UserModel(username.getText().toString(), email.getText().toString(), ""));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(LoginActivity.this, "Регистрация прошла успешно!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleLogin() {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(LoginActivity.this, "Вход прошёл успешно!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}