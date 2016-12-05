package com.example.pandey.projectx;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsAuthConfig;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.SessionListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Pandey on 13-11-2016.
 */
public class ForgetUser extends Activity {
    private AuthCallback callback;
    private static final String TWITTER_KEY = "bJ0rwystJ7r2JxdnRigjWgl68";
    private static final String TWITTER_SECRET =" wGcwOhaWgtb7Yu76Szmx1xHN3BRIR46uQ1rfrCmSf4oxnEL4NT";

    DigitsAuthButton digitsButton;
    private SessionListener sessionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetuser);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,TWITTER_SECRET);
        Fabric.with(getApplicationContext(),new Twitter(authConfig),new Digits());

        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        callback = new AuthCallback() {
            @Override
            public void success(DigitsSession session,String phoneNumber) {
                if (session.getAuthToken() instanceof TwitterAuthToken) {
                    final TwitterAuthToken authToken = (TwitterAuthToken) session.getAuthToken();
                }
            }

            @Override
            public void failure(DigitsException error) {
                Toast.makeText(ForgetUser.this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        };

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session,String phoneNumber) {
                DigitsAuthConfig.Builder digitsAuthConfigBuilder = new DigitsAuthConfig.Builder()
                        .withAuthCallBack(callback)
                        .withPhoneNumber("");

                Digits.authenticate(digitsAuthConfigBuilder.build());
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits","Sign in with Digits failure",exception);
            }
        });

        sessionListener = new SessionListener() {
            @Override
            public void changed(DigitsSession newSession) {
                Toast.makeText(ForgetUser.this, "Session phone was changed: " + newSession
                        .getPhoneNumber(), Toast.LENGTH_SHORT).show();
            }
        };
        Digits.getInstance().addSessionListener(sessionListener);

    }
    @Override
    protected void onStop() {
        Digits.getInstance().removeSessionListener(sessionListener);
        super.onStop();
    }
}

