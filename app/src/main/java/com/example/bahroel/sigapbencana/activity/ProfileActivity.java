package com.example.bahroel.sigapbencana.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.network.ApiClient;
import com.example.bahroel.sigapbencana.network.ApiService;
import com.example.bahroel.sigapbencana.network.model.Berita;
import com.example.bahroel.sigapbencana.network.model.LoginResponse;
import com.example.bahroel.sigapbencana.network.model.Lurah;
import com.example.bahroel.sigapbencana.network.model.User;
import com.example.bahroel.sigapbencana.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends BaseActivity {

    CompositeDisposable disposable = new CompositeDisposable();
    ApiService apiService;
    List<User> userList;

    @BindView(R.id.activity_profile_bio)
    RelativeLayout relativeBio;
    @BindView(R.id.activity_profile_rechange)
    RelativeLayout relativeChange;
    @BindView(R.id.activity_profile_layoutrechange1)
    LinearLayout linearChange1;
    @BindView(R.id.activity_profile_layoutrechange2)
    LinearLayout linearChange2;

    @BindView(R.id.activity_profile_address)
    TextView tvAddress;
    @BindView(R.id.activity_profile_name)
    TextView tvName;
    @BindView(R.id.activity_profile_servedyear)
    TextView tvServedyear;
    @BindView(R.id.et_new_password)
    EditText passwordBaru;
    @BindView(R.id.et_confirm_new_password)
    EditText connfirmPasswordBaru;
    @BindView(R.id.activity_profile_buttonchange)
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (isNetworkAvailable()){
            setup();
        }
    }

    private void setup() {
        ButterKnife.bind(this);
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        relativeBio.setBackgroundResource(R.drawable.rounded_cornerslight);
        relativeChange.setBackgroundResource((int)R.drawable.rounded_cornersprimary);
        linearChange1.setBackgroundResource((int)R.drawable.rounded_cornerslight);
        linearChange2.setBackgroundResource((int)R.drawable.rounded_cornerslight);
        btnChangePassword.setBackgroundResource((int)R.drawable.rounded_cornersprimarydark);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordBaru.getText().toString().trim().isEmpty() |
                        connfirmPasswordBaru.getText().toString().trim().isEmpty()){
                    showMessage("Ada field yang kosong");
                }else if (!connfirmPasswordBaru.getText().toString().trim().equals(passwordBaru.getText().toString().trim())){
                    showMessage("Konfirm password salah");

                }else {
                    setPassword();
                }
            }
        });

        getLurah();
    }

    private void setPassword() {
        String newPassword = passwordBaru.getText().toString().trim();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengirim data ke server ...");
        // show it
        progressDialog.show();
        disposable.add(
                apiService.updatePassword(PrefUtils.getPasswordUser(getApplicationContext()), newPassword, PrefUtils.getApiKey(getApplicationContext()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                                @Override
                                public void onSuccess(LoginResponse loginResponse) {
                                    progressDialog.dismiss();
                                    PrefUtils.setNewPassword(getApplicationContext(), newPassword);
                                    showMessage(loginResponse.getMessage());
                                    openDasboard();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    progressDialog.dismiss();
                                    showMessage("Tidak terhubung server");
                                    Log.d("ERROR", "onError: " + e.getMessage());
                                    showError(e);
                                }
                            })
        );
    }

    private void openDasboard(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void getLurah(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat data profile ...");
        // show it
        progressDialog.show();
        disposable.add(
                apiService.getLurah(PrefUtils.getUserId(getApplicationContext()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                            @Override
                            public void onSuccess(LoginResponse loginResponse) {
                                progressDialog.dismiss();
                                tvName.setText(loginResponse.getData().get(0).getLurah().getNama());
                                tvServedyear.setText(loginResponse.getData().get(0).getLurah().getPeriode());
                                tvAddress.setText(loginResponse.getData().get(0).getLurah().getAlamat());
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                                showMessage("Tidak terhubung server");
                                Log.d("ERROR", "onError: " + e.getMessage());
                                showError(e);
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
