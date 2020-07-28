package com.app.tosstra.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tosstra.R;
import com.app.tosstra.models.SIgnUp;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_signup, tvForgotPass;
    private Button bt_sigin;
    private ImageView iv_back;
    private Intent intent;
    private EditText et_email, et_pass;
    private String email, password;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        init();
    }

    private void init() {
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        tv_signup = findViewById(R.id.tv_signup);
        bt_sigin = findViewById(R.id.bt_sigin);
        iv_back = findViewById(R.id.iv_back);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tv_signup.setOnClickListener(this);
        bt_sigin.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signup:
                intent = new Intent(SigninActivity.this, SignupActivity.class);
                intent.putExtra("user_type", "dispatcher");
                startActivity(intent);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;

            case R.id.bt_sigin:
                if (validation()) {
                    hitSignInAPI();
                }
                break;
            case R.id.tvForgotPass:
                intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void hitSignInAPI() {
        String token =PreferenceHandler.readString(SigninActivity.this,"firebase_token","");
        String tokenToServer;
        if(token.isEmpty() ){
            tokenToServer="2";
        }else {
            tokenToServer=PreferenceHandler.readString(SigninActivity.this,"firebase_token","");
        }
        final Dialog dialog = AppUtil.showProgress(SigninActivity.this);
        Interface service = CommonUtils.retroInit();
        Call<SIgnUp> call = service.signIn(email, password, tokenToServer, "1");
        call.enqueue(new Callback<SIgnUp>() {
            @Override
            public void onResponse(Call<SIgnUp> call, Response<SIgnUp> response) {
                SIgnUp data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                  //  CommonUtils.showLongToast(SigninActivity.this, data.getData().get(0).getId());
                    intent = new Intent(SigninActivity.this, MainActivity.class);
                    type=data.getData().get(0).getUserType();
                    intent.putExtra("user_type", type);
                    CommonUtils.showLongToast(SigninActivity.this, data.getData().get(0).getId());
                    PreferenceHandler.writeString(SigninActivity.this, PreferenceHandler.USER_ID, data.getData().get(0).getId());
                    PreferenceHandler.writeString(SigninActivity.this,"online_status",data.getData().get(0).getOnlineStatus());
                    startActivity(intent);
                    finish();
                } else {
                    dialog.dismiss();
                    CommonUtils.showLongToast(SigninActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SIgnUp> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(SigninActivity.this, t.getMessage());
            }
        });
    }


    private boolean validation() {
        email = et_email.getText().toString().trim();
        password = et_pass.getText().toString().trim();
        return true;
    }
}