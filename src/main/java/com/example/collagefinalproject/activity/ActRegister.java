package com.example.collagefinalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.ActRegisterBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.User;
import com.example.utils.InputValidation;
import com.google.android.material.snackbar.Snackbar;

public class ActRegister extends AppCompatActivity {
    private ActRegisterBinding mBinding;
    private InputValidation inputValidation;
    private DBHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActRegisterBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        mBinding.appCompatButtonRegister.setOnClickListener(v -> postDataToSQLite());
        mBinding.appCompatTextViewLoginLink.setOnClickListener(v -> finish());
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(this);
        dbHelper = new DBHelper(this);
        user = new User();
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(mBinding.textInputEditTextName, mBinding.textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mBinding.textInputEditTextEmail, mBinding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(mBinding.textInputEditTextEmail, mBinding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mBinding.textInputEditTextPassword, mBinding.textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(mBinding.textInputEditTextPassword, mBinding.textInputEditTextConfirmPassword,
                mBinding.textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!dbHelper.checkUser(mBinding.textInputEditTextEmail.getText().toString().trim())) {
            user.setName(mBinding.textInputEditTextName.getText().toString().trim());
            user.setEmail(mBinding.textInputEditTextEmail.getText().toString().trim());
            user.setPassword(mBinding.textInputEditTextPassword.getText().toString().trim());
            new Handler(Looper.getMainLooper()).postDelayed(() -> dbHelper.addUser(user), 300);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(mBinding.nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent accountsIntent = new Intent(this, ActHome.class);
                startActivity(accountsIntent);
                finish();
                emptyInputEditText();
            }, 500);
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(mBinding.nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        mBinding.textInputEditTextName.setText(null);
        mBinding.textInputEditTextEmail.setText(null);
        mBinding.textInputEditTextPassword.setText(null);
        mBinding.textInputEditTextConfirmPassword.setText(null);
    }
}