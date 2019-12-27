package com.example.loginphone.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.loginphone.BaseActivity;
import com.example.loginphone.MainActivity;
import com.example.loginphone.R;
import com.example.loginphone.databinding.ActivityLoginBinding;
import com.example.loginphone.login.bean.Sendbean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends BaseActivity {
    private int countSeconds = 60;//倒计时秒数

    private Context mContext;
    String flag;
    String data;
    String msg;
    String phone;
    String ok;

    LoginViewModel loginViewModel;

    ActivityLoginBinding binding;

    @BindView(R.id.et_phone)
    ExtendedEditText et_phone;
    @BindView(R.id.btn_login)
    ImageButton btn_login;
    @BindView(R.id.tv_ver)
    TextView tv_ver;

//延迟消息机制，接受消息
    public Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                binding.tvVer.setText("(" + countSeconds + ")后获取验证码");
                Log.e(TAG, "handleMessage: "+ binding.tvVer.getText().toString() );
                mCountHandler.sendEmptyMessageDelayed(0, 1000);  //
            } else {
                countSeconds = 60;
                binding.tvVer.setText("请重新获取验证码");
                Log.e(TAG,"hand" + binding.tvVer.getText().toString());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login); //databinding绑定控件
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        this.binding.setLogin(loginViewModel);

        binding.setLifecycleOwner(this);

    }


    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
    }
//    public void login(View view){
//        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//        startActivity(intent);
//    }
    //发送短信验证码
    public void send(View view){
        if (countSeconds == 60) {
            String mobile = this.binding.etPhone.getText().toString();
//            Log.e("tag", "mobile==" + mobile);
            Log.e("tag", "zhucemobile=="+mobile);
            getMobile(mobile); //获取验证码信息，判断是否有手机号码
        } else {
            Toast.makeText(LoginActivity.this, "不能重复发送验证码", Toast.LENGTH_SHORT).show();
        }
    }

    //获取验证码信息，判断是否有手机号码
    public void getMobile(String mobile) {

        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
        Toast.makeText(LoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
        } else if (isMobileNO(mobile) == false) {
            Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("tag", "输入了正确的手机号");
            requestVerifyCode(mobile);
        }
    }

    //获取信息进行登录
    public void loginPhone(View view) {

        Sendbean sendbean = new Sendbean(); //实例化对象 ，你要发给后台接口的json参数
        String mobile = this.binding.etPhone.getText().toString(); //获取输入手机号
        String verifyCode = this.binding.etVer.getText().toString(); //获取输入验证码
        sendbean.setTelephone(mobile);  //对象添加手机号码
        sendbean.setValidateCode(verifyCode);  //验证码
        Log.d(TAG, "loginPhone: " + mobile + verifyCode);  //测试log日志手机号验证码是否正确
        String jsonString=new Gson().toJson(sendbean);
        Log.d(TAG, "loginPhone: " + jsonString);  //测试log日志jsonString是否这个你却

        RequestParams params = new RequestParams("后台给你的登录接口");

        //必须有这两句，才可以传给后台，否则会报错
        params.setAsJsonContent(true);
        params.setBodyContent(jsonString);//传递json数据给后天


        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {

            }
            @Override
            public void onStarted() {

            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Log.e("tag", "登陆的jsonObject=" + jsonObject);

                    flag = jsonObject.getString("flag");
//                    Log.e("登录tag", "登陆的jsonObject=" + flag + data);
                    if ("true".equals(flag)) {
                        JSONObject json = new JSONObject(data);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //我这里按照我的要求写的，你们也可以适当改动
                        //获取用户信息的状态
//                            getUserInfo();
                    }else{
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("tag登录","登陆的data="+data + e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag错误","登陆的data="+ex);
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });
    }



    //        获取验证码信息，进行验证码请求
    public void requestVerifyCode(String mobile) {

        String  url = "这里是你请求的验证码接口，让后台给你，参数什么的加在后面";
        RequestParams requestParams = new RequestParams(url+mobile);

        x.http().post(requestParams, new Callback.ProgressCallback<String>()
        {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }

            @Override
            public void onSuccess(String result) {
                System.out.println("获取验证码成功");
                try {
                    JSONObject jsonObject2 = new JSONObject(result);
                    Log.e("tag", "jsonObject2" + jsonObject2);
                    String flag = jsonObject2.getString("flag");

//                    Log.e("tag", "jsonObject2" + flag );
//                    Log.e("tag", "获取验证码==" );
                    if ("true".equals(flag)) {
                        Log.e("tagone", "获取验证码==" );

                        startCountBack();//这里是用来进行请求参数的
                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();

                System.out.println("获取验证码失败"+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //获取验证码信息,进行计时操作
    public void startCountBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                binding.tvVer.setText(countSeconds + "");
                Log.e(TAG, "run: 0"+binding.tvVer.getText().toString() );
                mCountHandler.sendEmptyMessage(0); //发送给信息给handler
            }
        });
    }

    //使用正则表达式判断电话号码

    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8,]|18[0-9]|19[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
}

//    @BindView(R.id.imb_qq)
//    ImageButton imb_qq;
//    @BindView(R.id.imb_wc)
//    ImageButton imb_wc;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

//        //传入参数APPID和全局Context上下文
//        mTencent = Tencent.createInstance(APP_ID, LoginActivity.this.getApplicationContext());



//        imb_qq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTencent.isQQInstalled(LoginActivity.this)) { // 判断QQ是否安装了
//                    goQQLogin();
//                } else {
//                    Toast.makeText(LoginActivity.this, "请先安装QQ", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            private void goQQLogin() {
//                mTencent.login(LoginActivity.this, "all", (IUiListener) new QQLoginCallBack());
//            }
//
//        });
//    }



    /**
     * QQ登录之后的返回
     */
//    private class QQLoginCallBack implements IUiListener {
//
//        @Override
//        public void onComplete(Object o) {
//            Toast.makeText(LoginActivity.this, "登录成功：", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
//
//        @Override
//        public void onError(UiError uiError) {
//            Toast.makeText(LoginActivity.this, "登录失败:", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel() {
//            Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mTencent.onActivityResultData(requestCode, resultCode,
//                data, new QQLoginCallBack());
//
//        if (requestCode == Constants.REQUEST_API) {
//            if (resultCode == Constants.REQUEST_LOGIN) {
//                Tencent.handleResultData(data, new QQLoginCallBack());
//
//
//            }
//        }
//    }
//}
