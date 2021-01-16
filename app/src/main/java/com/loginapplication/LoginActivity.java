package com.loginapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loginapplication.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final Boolean CHECK_IF_VERIFIED = false;
    JSONObject json;
    HashMap<String, String> params = new HashMap<>();



    private Context mContext;
    ActivityLoginBinding binding;
    String email="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mContext = LoginActivity.this;
        binding.tvSignUp.setPaintFlags(binding.tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        init();
    }


    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    //initialize the buttons
    private void init() {







        binding.llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to register screen");
            }
        });

        binding.tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in.");


                String password = binding.passwordInput.getText().toString();


                try {
                    json = new JSONObject();




                    email= binding.etEmail.getText().toString();
                        if (!ProjectUtils.isEmailValid(email)) {
                            Toast.makeText(mContext, R.string.val_email, Toast.LENGTH_SHORT).show();
                        } else if (isStringNull(password)) {
                            Toast.makeText(mContext, R.string.ent_password, Toast.LENGTH_SHORT).show();
                        } else if (!ProjectUtils.isPasswordValid(password)) {
                            Toast.makeText(mContext, R.string.val_password, Toast.LENGTH_SHORT).show();
                        } else {
                        if(ProjectUtils.isNetworkConnected(mContext)){loginIn();}else {
                            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                            


                        }

                } catch (Exception e) {

                }

            }
        });


    }

    private void loginIn() {
        try {
            json.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(binding.passwordInput));
            json.put(Consts.EMAIL,email);
            Log.e(TAG, "loginIn: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new HttpsRequest(Consts.LOGIN_API, json, mContext).stringPostJson(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) throws JSONException {


                String token="";
                token=new Gson().toJson(response.get("token"));
                Toast.makeText(mContext, token, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onBackPressed() {

    }

}