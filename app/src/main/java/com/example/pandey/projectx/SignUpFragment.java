package com.example.pandey.projectx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

/**
 * Created by Pandey on 10-11-2016.
 */
public class SignUpFragment extends AppCompatActivity{
    AutoCompleteTextView username,email,phone;
    Button signup,clear;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    CoordinatorLayout coordinatorLayout;
    private String PREF ="SignUp";
    public SignUpFragment(){
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        username=(AutoCompleteTextView)findViewById(R.id.txtname);
        email=(AutoCompleteTextView)findViewById(R.id.txtemail);
        phone=(AutoCompleteTextView)findViewById(R.id.txtmobileno);
        signup =(Button)findViewById(R.id.btnSignUp);
        clear=(Button)findViewById(R.id.btnClear);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        preferences=this.getSharedPreferences(PREF,0);
        editor=preferences.edit();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail =email.getText().toString();
                String mobileno = phone.getText().toString();
                String name = username.getText().toString();
                if (name.trim().equals(""))
                    username.setError("Please enter your name");
                else if (mail.trim().equals(""))
                    email.setError("Please enter email Address");
                else if (!mail.contains("@") || !mail.contains("."))
                    email.setError("Please enter a valid email Address");
                else if (mobileno.trim().length() < 10)
                    phone.setError("Not a valid mobile no"); // I'm finding solution to validate mobile no
                else {
                    editor.putString("Name",name);
                    editor.putString("email",mail);
                    editor.putString("mobile",mobileno);
                    editor.commit();
                    Intent intent = new Intent(SignUpFragment.this,LoginFragment.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                phone.setText("");
                username.setText("");
                username.setError(null);
                email.setError(null);
                phone.setError(null);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
