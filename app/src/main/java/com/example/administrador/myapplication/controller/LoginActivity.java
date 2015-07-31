package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.util.FormHelper;

public class LoginActivity extends AppCompatActivity{

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

        bindLoginButton();
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        User user = new User();
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

        if(FormHelper.requireValidate(LoginActivity.this, editTextUsername, editTextPassword)){
            {
                if(User.getUser().equals(user)){
                    Intent goToMainActivity = new Intent(LoginActivity.this, ClientListActivity.class);
                    startActivity(goToMainActivity);
                }
                else{
                    Toast.makeText(LoginActivity.this, getString(R.string.invalidLogin), Toast.LENGTH_LONG).show();
                }
            }
        }

            }
        });

    }
}
