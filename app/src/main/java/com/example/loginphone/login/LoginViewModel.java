package com.example.loginphone.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> phone;
    private MutableLiveData<String> verifyCode;
    Context context;

    public MutableLiveData<String> getPhone() {
        if (phone ==null){
            phone = new MutableLiveData<>();
            phone.setValue("请输入手机号");
        }
        return phone;
    }


    public MutableLiveData<String> getVerifyCode() {
        if (verifyCode ==null){
            verifyCode = new MutableLiveData<>();
            verifyCode.setValue("请输入验证码");
        }
        return verifyCode;
    }

}
