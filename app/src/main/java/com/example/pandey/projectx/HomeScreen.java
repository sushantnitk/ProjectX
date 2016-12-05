package com.example.pandey.projectx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandey.projectx.Utility.AlertDialogManager;
import com.example.pandey.projectx.Utility.Session;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Pandey on 11-11-2016.
 */
public class HomeScreen extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    Session session;

    // Button Logout
    Button btnLogout;
    TextView name,email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        session= new Session(getApplicationContext());
        name=(TextView)findViewById(R.id.lblName);
        email=(TextView)findViewById(R.id.lblEmail);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String sname = user.get(Session.KEY_NAME);

        // email
        String semail = user.get(Session.KEY_EMAIL);

        // displaying user data
        name.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        email.setText(Html.fromHtml("Email: <b>" + email + "</b>"));


        /**
         * Logout button click event
         * */
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
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
}
