package edu.gatech.cs2340.youngmoney.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import edu.gatech.cs2340.youngmoney.R;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class SocialLoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        LoginButton fbLogin = findViewById(R.id.login_button);
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });



                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                System.out.print("Facebook Success: " + isLoggedIn);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0){
            startActivity(new Intent(SocialLoginActivity.this, HomeActivity.class));
        }
    }
}
