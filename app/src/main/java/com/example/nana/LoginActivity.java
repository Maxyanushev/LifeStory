package com.example.nana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nana.databinding.ActivityLoginBinding;
import com.example.nana.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    EditText editUserName, editEmail, editPassword;
    Button btnLogIn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editUserName = binding.editUserName;
        editEmail = binding.editEmail;
        editPassword = binding.editPassword;

        textView = binding.textView32;

        btnLogIn = binding.button;
    }
}