package com.example.collagefinalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.ActLoginBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.utils.InputValidation;
import com.google.android.material.snackbar.Snackbar;

public class ActLogin extends AppCompatActivity {
    private ActLoginBinding mBinding;
    private InputValidation inputValidation;
    private DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActLoginBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        initListeners();
        initObjects();
    }

    /**
     * This method is to click listeners
     */
    private void initListeners() {
        mBinding.appCompatButtonLogin.setOnClickListener(v -> verifyFromSQLite());
        mBinding.textViewLinkRegister.setOnClickListener(v -> {
            Intent intentRegister = new Intent(getApplicationContext(), ActRegister.class);
            startActivity(intentRegister);
        });
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        DBHelper = new DBHelper(this);
        inputValidation = new InputValidation(this);

//        mBinding.textInputEditTextEmail.setText("amish@yopmail.com");
//        mBinding.textInputEditTextPassword.setText("Amish");
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(mBinding.textInputEditTextEmail, mBinding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(mBinding.textInputEditTextEmail, mBinding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mBinding.textInputEditTextPassword, mBinding.textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        if (DBHelper.checkUser(mBinding.textInputEditTextEmail.getText().toString().trim()
                , mBinding.textInputEditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(this, ActHome.class);
            accountsIntent.putExtra("EMAIL", mBinding.textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
            finish();
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(mBinding.nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        mBinding.textInputEditTextEmail.setText(null);
        mBinding.textInputEditTextPassword.setText(null);
    }
}