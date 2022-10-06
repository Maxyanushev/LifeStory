package com.example.nana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nana.R;
import com.example.nana.core.BaseActivity;
import com.example.nana.models.UserModel;
import com.example.nana.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends BaseActivity {

    public ActivityLoginBinding binding;

    private EditText username, email, password;
    private TextView loginTitle, changeTypeOfAuth, registerTypes;
    private Button buttonLogin;
    public ImageView google, facebook, twitter;

    private boolean isSigningUp = true, isPasswordVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkEntered();

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
                if (email.getText().toString().isEmpty()) {
                    email.setError("Ой-Ой! Похоже Email обязательно нужно ввести!");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(this, "Проверьте правильность написания Email!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Если хотите остаться без аккаунта, то пароль можно и не вводить :)", Toast.LENGTH_LONG).show();
                } else if (username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "А как же имя пользователя? Его стоит ввести!", Toast.LENGTH_LONG).show();
                } else { handleRegister(); }
            } else {
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Ой-Ой! Похоже электронную почту обязательно нужно ввести!", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Эй! А пароль? Без пароля не впущу!", Toast.LENGTH_LONG).show();
                } else { handleLogin(); }
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

        binding.buttonShowHide.setOnClickListener(v -> {
            if (isPasswordVisible) {
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.buttonShowHide.setImageResource(R.drawable.eye_hide);
                isPasswordVisible = false;
            } else {
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.buttonShowHide.setImageResource(R.drawable.eye_show);
                isPasswordVisible = true;
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

    public void checkEntered() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}