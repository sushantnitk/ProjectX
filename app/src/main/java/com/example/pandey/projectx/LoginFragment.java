package com.example.pandey.projectx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pandey.projectx.Utility.AlertDialogManager;
import com.example.pandey.projectx.Utility.Session;

/**
 * Created by Pandey on 10-11-2016.
 */
public class LoginFragment extends AppCompatActivity {
    Session session;
    EditText username,password;
    Button login;
    TextView newUser,forgetUser;
    SharedPreferences preferences;
    AlertDialogManager alertDialogManager=new AlertDialogManager();
    SharedPreferences.Editor editor;
    public LoginFragment() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        session= new Session(this);
        username=(EditText)findViewById(R.id.login_name);
        password=(EditText)findViewById(R.id.login_paswd);
        newUser=(TextView)findViewById(R.id.newUser);
        forgetUser=(TextView)findViewById(R.id.forgetPswd);
        login=(Button)findViewById(R.id.signIn);
        preferences=this.getSharedPreferences("PREFS",0);
        editor=preferences.edit();
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFragment.this,SignUpFragment.class);
                startActivity(intent);
            }
        });
        forgetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFragment.this,ForgetUser.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString();
                String paswd = password.getText().toString();
                if(name.trim().length() > 0 && paswd.trim().length() > 0){

                    if(preferences.getString("Name","").equals(name) && preferences.getString("mobile","").equals(paswd)){
                        session.createUserLoginSession(name, preferences.getString("email",""));
                        Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d("Success","Login Successfull");
                        startActivity(i);
                        finish();


                    }else{
                        alertDialogManager.showAlertDialog(LoginFragment.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }else{
                    alertDialogManager.showAlertDialog(LoginFragment.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }


}
