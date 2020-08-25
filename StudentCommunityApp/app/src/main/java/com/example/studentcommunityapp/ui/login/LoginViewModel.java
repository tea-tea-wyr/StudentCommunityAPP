package com.example.studentcommunityapp.ui.login;

import android.content.Context;
import android.icu.text.UnicodeSetIterator;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studentcommunityapp.util.WebHelper;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    public LoginViewModel(){
        //EventBus.getDefault().register(this);
    }
    private MutableLiveData<String> liveData;
    public void GetLoginState(String name, String password, final Context activity, final View view){
        liveData=new MutableLiveData<>();
        MediaType mediaType = MediaType.parse("application/json");
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",name);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient=WebHelper.getInstance().client;//这里最好是写死的，将情况按参数匹配
        if(jsonObject.length()>0){
            String body=jsonObject.toString();
            final Request request=new Request.Builder()
                    .url("http://81.70.27.208:8000/api/login")
                    .post(RequestBody.create(body,mediaType)).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(LoginFragment.TAG, "onFailure: ",e );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        String res=response.body().string();
                        Headers header=response.headers();
                        List<String> cookies = header.values("Set-Cookie");
                        Gson gson=new Gson();
                        JSONObject jsonObject1=new JSONObject(res);
                        String state=String.valueOf(jsonObject1.get("status"));//根据status判断是否可用
                        if(state.equals("1")){//截获cookie
                            String session=header.values("Set-Cookie").get(0);
                            String sessionID = session.substring(0, session.indexOf(";"));
                            System.out.println("登录接受的cookie是 "+sessionID);
                            WebHelper.setCookie(activity,sessionID);
                        }
                        else{
                            System.out.println("null");
                        }
                        liveData.postValue(state);
                    }catch (Exception e){
                        Log.e(LoginFragment.TAG, "onResponse: ", e);
                    }
                }
            });
        }
    }
    public LiveData<String> getState(String name, String password, final Context activity, final View view){
        GetLoginState( name,password,activity,view);
        return liveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //EventBus.getDefault().unregister(this);
    }
}
