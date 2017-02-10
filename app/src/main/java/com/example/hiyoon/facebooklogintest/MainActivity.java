package com.example.hiyoon.facebooklogintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    // CallbackManager
    private CallbackManager callbackManager;

    // Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CallBackManager 생성
        callbackManager = CallbackManager.Factory.create();

        // 로그인 버튼 로직 추가
        LoginButton btnLogin = (LoginButton) findViewById(R.id.btn_login);
        btnLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }


            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });
    }
}
