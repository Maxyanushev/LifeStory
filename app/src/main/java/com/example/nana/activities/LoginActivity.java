package com.example.nana.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nana.R;
import com.example.nana.databinding.ActivityLoginBinding;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
//    private String encodedImage;

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
//                binding.layoutImageProfile.setVisibility(View.GONE);

                loginTitle.setText(R.string.login);
                buttonLogin.setText(R.string.login);
                changeTypeOfAuth.setText(R.string.do_not_have_an_account);
            } else {
                isSigningUp = true;

                binding.editUsername.setVisibility(View.VISIBLE);
//                binding.layoutImageProfile.setVisibility(View.VISIBLE);

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

//        binding.layoutImageProfile.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            pickImage.launch(intent);
//        });
    }

    private void handleSignUp() {
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.editUsername.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.editEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.editPassword.getText().toString());
//        user.put(Constants.KEY_IMAGE, encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener(documentReference -> {
                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                preferenceManager.putString(Constants.KEY_NAME, binding.editUsername.getText().toString());
//                preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
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
//                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));

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

    private String encodedImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

//    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    if (result.getData() != null) {
//                        Uri imageUri = result.getData().getData();
//                        try {
//                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
//                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                            binding.imageProfile.setImageBitmap(bitmap);
//                            binding.textAddImageProfile.setVisibility(View.GONE);
//                            encodedImage = encodedImage(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//    );
}