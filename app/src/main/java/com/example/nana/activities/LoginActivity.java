package com.example.nana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nana.R;
import com.example.nana.databinding.ActivityLoginBinding;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public ActivityLoginBinding binding;
    public PreferenceManager preferenceManager;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    private EditText username, email, password;
    private TextView loginTitle, changeTypeOfAuth;
    private Button buttonLogin;
    private ImageButton buttonShowHide;

    private boolean isSigningUp = true, isPasswordVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
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

        buttonLogin = binding.btnLogin;
        buttonShowHide = binding.buttonShowHide;
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(v -> {
            if (isSigningUp) {
                loading(true, isSigningUp);
                if (username.getText().toString().isEmpty()) {
                    username.setError("Обязательное для заполнения поле!");
                    loading(false, isSigningUp);
                } else if (username.getText().length() < 4 || username.getText().length() > 12) {
                    username.setError("Имя пользователя должно содержать от 4 до 12 символов");
                    loading(false, isSigningUp);
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Обязательное для заполнения поле!");
                    loading(false, isSigningUp);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Проверьте правильность написания Email!");
                    loading(false, isSigningUp);
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Обязательное для заполнения поле!");
                    buttonShowHide.setVisibility(View.GONE);
                    loading(false, isSigningUp);
                } else if (password.getText().length() < 6 || password.getText().length() > 15) {
                    password.setError("Пароль должен содержать от 6 до 15 символов");
                    buttonShowHide.setVisibility(View.GONE);
                    loading(false, isSigningUp);
                } else {
                    handleSignUp();
                }
            } else {
                loading(true, isSigningUp);
                if (email.getText().toString().isEmpty()) {
                    email.setError("Обязательное для заполнения поле!");
                    loading(false, isSigningUp);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Проверьте правильность написания Email!");
                    loading(false, isSigningUp);
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Обязательное для заполнения поле!");
                    loading(false, isSigningUp);
                } else { handleSignIn(); }
            }
        });

        binding.textChangeTypeOfAuth.setOnClickListener(v -> {
            if (isSigningUp) {
                isSigningUp = false;

                binding.editUsername.setVisibility(View.GONE);

                loginTitle.setText(R.string.login);
                buttonLogin.setText(R.string.login);
                changeTypeOfAuth.setText(R.string.do_not_have_an_account);
            } else {
                isSigningUp = true;

                binding.editUsername.setVisibility(View.VISIBLE);

                loginTitle.setText(R.string.registration);
                buttonLogin.setText(R.string.register);
                changeTypeOfAuth.setText(R.string.already_have_an_account);
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

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonShowHide.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handleSignUp() {
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.editUsername.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.editEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.editPassword.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener(documentReference -> {
                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                preferenceManager.putString(Constants.KEY_NAME, binding.editUsername.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            })
            .addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void handleSignIn() {
        loading(true, isSigningUp);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.editEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.editPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        loading(false, isSigningUp);
                        password.setError("Неправильный пароль!");
                    }
                });
    }

    public void checkEntered() {
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void loading(Boolean isLoading, Boolean isSignUp) {
        if (isLoading) {
            binding.btnLogin.setText("");
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            if (isSignUp) {
                binding.btnLogin.setText(R.string.registration);
            } else {
                binding.btnLogin.setText(R.string.login);
            }
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}