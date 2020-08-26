package com.example.studentcommunityapp.ui.settings.account;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.studentcommunityapp.R;

public class Account extends Fragment {

    private AccountViewModel mViewModel;

    public static Account newInstance() {
        return new Account();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.account_fragment,container,false);
        final ImageView imageView = view.findViewById(R.id.account_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_settingFragment);
            }
        });
        final LinearLayout name = view.findViewById(R.id.li1);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_nameFragment);
            }
        });
        final LinearLayout password = view.findViewById(R.id.l2);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_passwordFragment);
            }
        });
        final LinearLayout school = view.findViewById(R.id.l3);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_universityFragment);
            }
        });
        final LinearLayout college = view.findViewById(R.id.l4);
        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_collegeFragment);
            }
        });
        final LinearLayout Class = view.findViewById(R.id.l5);
        Class .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_classFragment);
            }
        });
        final LinearLayout year = view.findViewById(R.id.l6);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_yearFragment);
            }
        });
        final LinearLayout wechat = view.findViewById(R.id.l7);
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_wechatFragment);
            }
        });
        final LinearLayout phone = view.findViewById(R.id.l8);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_phoneFragment);
            }
        });
        final LinearLayout email = view.findViewById(R.id.l9);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_account_to_emailFragment);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }

}
