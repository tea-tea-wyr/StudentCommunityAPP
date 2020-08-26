package com.example.studentcommunityapp.ui.settings;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.User;
import com.example.studentcommunityapp.util.CircleImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SettingFragment extends Fragment {
    private String TAG = "SettingFragment";
    private ImageView imageView;
    private SettingViewModel mViewModel;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.setting_fragment,container,false);
        //getUserData(view);
        imageView =view.findViewById(R.id.setting_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_navigation_user);
            }
        });
        final LinearLayout linearLayout = view.findViewById(R.id.li1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_account);
            }
        });
        final LinearLayout quit = view.findViewById(R.id.l9);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_loginFragment);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getUserData(final View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://81.70.27.208:8000/api/get_user_info?op=1&userid=000006").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, "onResponse: " + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")){
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("user_info")));
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        final User user = new User(object);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final TextView name = view.findViewById(R.id.account_name);
                                final TextView school = view.findViewById(R.id.account_school);
                                final TextView college = view.findViewById(R.id.account_college);
                                final TextView year = view.findViewById(R.id.account_year);
                                final TextView Class = view.findViewById(R.id.account_class);
                                final TextView wechat = view.findViewById(R.id.account_wechat);
                                final TextView email = view.findViewById(R.id.account_email);
                                final TextView phone = view.findViewById(R.id.account_phone);
                                name.setText(user.getName());
                                school.setText(user.getUniversity());
                                college.setText(user.getCollege());
                                year.setText(user.getLevel());
                                Class.setText(user.getaClass());
                                wechat.setText(user.getWechat());
                                email.setText(user.getEmail());
                                phone.setText(user.getPhone());
                            }
                        });
                    }
                    else {
                        Log.d(TAG, "null");
                    }
                } catch (JSONException e){
                    Log.e(TAG, "onResponse_catch: ", e);
//                    e.printStackTrace();
                }

            }
        });
    }


}
