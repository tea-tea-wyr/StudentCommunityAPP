package com.example.studentcommunityapp.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentcommunityapp.R;

public class LoginFragment extends Fragment {
    public static final String TAG ="Login" ;
    private SharedPreferences infos;
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        final EditText name = getView().findViewById(R.id.editText_account);
        final EditText password = getView().findViewById(R.id.editText_password);
        infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        final String islogin=infos.getString("user","");
        Button button_login =getView().findViewById(R.id.login_button);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String names = name.getText().toString();
                String psw = password.getText().toString();
                mViewModel.getState(names,psw,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        System.out.println("观察到"+s);
                        if(s.equals("0")){
                            Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(islogin.equals("")){
                                infos.edit().putString("user",names).apply();
                            }
                            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_navigation_home);
                        }
                    }
                });
            }
        });
        // TODO: Use the ViewModel
    }

}
