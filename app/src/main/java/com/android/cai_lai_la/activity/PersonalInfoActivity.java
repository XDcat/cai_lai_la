package com.android.cai_lai_la.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.cai_lai_la.MainActivity;
import com.android.cai_lai_la.R;
import com.android.cai_lai_la.controller.UserController;
import com.android.cai_lai_la.model.User;
import com.android.cai_lai_la.widget.ItemGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonalInfoActivity extends AppCompatActivity {
    @BindView(R.id.bt_logout)
    Button logoutButton;
    @BindView(R.id.ig_id)
    ItemGroup id;
    @BindView(R.id.ig_name)
    ItemGroup name;
    @BindView (R.id.ig_email)
    ItemGroup email;
    private ArrayList<String> optionsItems_gender = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_info);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        initDate();
        initView();
        if (UserController.isLog(this)){
            User user = UserController.loadUser(this);
            id.getContentEdt().setText(String.valueOf(user.getUid()));
            name.getContentEdt().setText(user.getNickname());
            email.getContentEdt().setText(user.getEmail());
        }
    }

    private void initView(){
        // 退出登录按钮
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出登录并跳转到主页面
                UserController.logout(PersonalInfoActivity.this);
                Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);
            }
        });
    }
    private void initDate(){

    }
}
