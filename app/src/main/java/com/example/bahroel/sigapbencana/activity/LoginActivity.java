package com.example.bahroel.sigapbencana.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.network.ApiClient;
import com.example.bahroel.sigapbencana.network.ApiService;
import com.example.bahroel.sigapbencana.network.model.LoginResponse;
import com.example.bahroel.sigapbencana.network.model.User;
import com.example.bahroel.sigapbencana.utils.PrefUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    ApiService apiService;
    CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.activity_main_buttonlogin)
    Button btnLogin;
    @BindView(R.id.activity_login_relative)
    RelativeLayout relativeBtnLogin;
    @BindView(R.id.et_email_user)
    EditText emailUser;
    @BindView(R.id.et_password_user)
    EditText passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (isNetworkAvailable()){
            setup();
        }
    }

    private void setup(){
        ButterKnife.bind(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        if (!PrefUtils.isLoggedIn(getApplicationContext())){
            showMessage("Belum login");
        }else {
            startActivity(new Intent(this, HomeActivity.class).
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailUser.getText().toString().trim().isEmpty() || passwordUser.getText().toString().trim().isEmpty()){
                    showMessage("Email atau password kosong");
                }else {
                    loginUser();
                }
            }
        });

        btnLogin.setBackgroundResource(R.drawable.rounded_cornersprimarydark);
        relativeBtnLogin.setBackgroundResource(R.drawable.rounded_cornerslight);
    }

    private void loginUser(){
        String email = emailUser.getText().toString().trim();
        String password = passwordUser.getText().toString().trim();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login ...");
        // show it
        progressDialog.show();
        disposable.add(
                apiService
                        .login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                try{
                                    if (loginResponse.isSuccess()){
                                        progressDialog.dismiss();
                                        Log.d("TOKEN", "onSuccess: " + loginResponse.getData().get(0).getApiToken());
                                        Log.d("USERID", "onSuccess: " + loginResponse.getData().get(0).getId());
                                        Log.d("EMAIL", "onSuccess: " + loginResponse.getData().get(0).getEmail());
                                        Log.d("NAME", "onSuccess: " + loginResponse.getData().get(0).getLurah().getNama());
                                        Log.d("PASSWORD", "onSuccess: " + password);
                                        PrefUtils.setApiKey(getApplicationContext(), loginResponse.getData().get(0).getApiToken());
                                        PrefUtils.setLoginSession(getApplicationContext(), loginResponse.getData().get(0).getId(), loginResponse.getData().get(0).getEmail(),
                                                password, loginResponse.getData().get(0).getLurah().getNama());
                                        showMessage(loginResponse.getMessage());
                                        openDasboard();
                                    }else {
                                        progressDialog.dismiss();
                                        showMessage(loginResponse.getMessage());
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                                showMessage("Tidak terhubung server");
                                Log.d("ERROR", "onError: " + e.getMessage());
                                showError(e);
                            }
                        }));
    }

    private void openDasboard(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading(String message) {
        super.showLoading(message);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
    }

    @Override
    protected void onDestroy() {
        this.disposable.dispose();
        super.onDestroy();
    }


}
