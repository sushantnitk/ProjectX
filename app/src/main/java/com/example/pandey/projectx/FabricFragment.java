package com.example.pandey.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;


import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

/**
 * Created by Pandey on 10-11-2016.
 */
public class FabricFragment extends AppCompatActivity  {

    private TwitterLoginButton loginButton;
    private static final String TAG = MultiFragment.class.getSimpleName();
    private static final String TWITTER_KEY = "bJ0rwystJ7r2JxdnRigjWgl68";
    private static final String TWITTER_SECRET = " wGcwOhaWgtb7Yu76Szmx1xHN3BRIR46uQ1rfrCmSf4oxnEL4NT";
    TextView twitteruserId,twitteruserName;
    ImageView twitterimage;
    Button logout;
    boolean loggedIn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.fabric_login);
        twitterimage=(ImageView)findViewById(R.id.twitterpic);
        logout=(Button)findViewById(R.id.logoutTwitter);
        twitteruserId=(TextView)findViewById(R.id.twitter_user_id);
        twitteruserName=(TextView)findViewById(R.id.twitter_user_name);
        loginButton=(TwitterLoginButton)findViewById(R.id.twitter_login_button);
        updateUI(false);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookieSyncManager.createInstance(FabricFragment.this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
                Twitter.getSessionManager().clearActiveSession();
                Twitter.logOut();
                loggedIn=false;
                finish();
            }
        });
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession   session = result.data;
                Twitter          twitter = Twitter.getInstance();
                TwitterApiClient api     = twitter.core.getApiClient(session);
                AccountService service = api.getAccountService();
                Call<User>       user    = service.verifyCredentials(true, true);
                user.enqueue(new Callback<User>()
                {
                    @Override
                    public void success(Result<User> userResult)
                    {
                        loggedIn=true;
                        updateUI(true);
                        // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                        String photoUrlNormalSize   = userResult.data.profileImageUrl;
                        String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                        String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                        String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
                        Glide.with(getApplicationContext()).load(userResult.data.profileImageUrl).fitCenter().placeholder(R.drawable.tw__ic_logo_default).into(twitterimage);
                    }

                    @Override
                    public void failure(TwitterException exc)
                    {
                        Log.d("TwitterKit", "Verify Credentials Failure", exc);
                        loggedIn=false;
                    }
                });
                session = result.data;
                String username = session.getUserName();
                Long  userid = session.getUserId();
                twitteruserName.setText("Hi " + username);
                twitteruserId.setText(userid.toString());

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
                updateUI(false);
            }
        });
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.twitter_login_button).setVisibility(View.GONE);
            findViewById(R.id.tweetView).setVisibility(View.VISIBLE);
        } else {

            findViewById(R.id.twitter_login_button).setVisibility(View.VISIBLE);
            findViewById(R.id.tweetView).setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(loggedIn){
            updateUI(true);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
