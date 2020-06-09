package com.android.cai_lai_la.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.cai_lai_la.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavMeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavMeFragment extends Fragment {
    public NavMeFragment() {
        // Required empty public constructor
    }

    public static NavMeFragment newInstance(){
        NavMeFragment fragment = new NavMeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_me, container, false);


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button touxiang = (Button)view.findViewById(R.id.personalInfo_button_first);
        Drawable drawable1 = getResources().getDrawable(R.drawable.headsculp);
        drawable1.setBounds(0, 0, 120, 120);
        touxiang.setCompoundDrawables(null, drawable1, null, null);
        view.findViewById(R.id.personalInfo_button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Button wallet = (Button)view.findViewById(R.id.personalInfo_button_second);
        Drawable drawable2 = getResources().getDrawable(R.drawable.wallet);
        drawable2.setBounds(0, 0, 60, 60);
        wallet.setCompoundDrawables(null, drawable2, null, null);
        view.findViewById(R.id.personalInfo_button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button shipped = (Button)view.findViewById(R.id.personalInfo_button_third);
        Drawable drawable3 = getResources().getDrawable(R.drawable.shipped);
        drawable3.setBounds(0, 0, 60, 60);
        shipped.setCompoundDrawables(null, drawable3, null, null);
        view.findViewById(R.id.personalInfo_button_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button comment = (Button)view.findViewById(R.id.personalInfo_button_fourth);
        Drawable drawable4 = getResources().getDrawable(R.drawable.comment);
        drawable4.setBounds(0, 0, 60, 60);
        comment.setCompoundDrawables(null, drawable4, null, null);
        view.findViewById(R.id.personalInfo_button_fourth).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
        });
        Button aftersale_service = (Button)view.findViewById(R.id.personalInfo_button_fifth);
        Drawable drawable5 = getResources().getDrawable(R.drawable.afs);
        drawable5.setBounds(0, 0, 60, 60);
        aftersale_service.setCompoundDrawables(null, drawable5, null, null);
        view.findViewById(R.id.personalInfo_button_fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button order = (Button)view.findViewById(R.id.personalInfo_button_sixth);
        view.findViewById(R.id.personalInfo_button_sixth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button address = (Button)view.findViewById(R.id.personalInfo_button_seventh);
        view.findViewById(R.id.personalInfo_button_seventh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        TextView jiantou1 = (TextView) view.findViewById(R.id.personalInfo_textView_first);
        Drawable drawable6 = getResources().getDrawable(R.drawable.jiantou);
        drawable6.setBounds(0, 0, 40, 40);
        jiantou1.setCompoundDrawables(null, null, drawable6, null);

        TextView jiantou2 = (TextView) view.findViewById(R.id.personalInfo_textView_second);
        Drawable drawable7 = getResources().getDrawable(R.drawable.jiantou);
        drawable7.setBounds(0, 0, 40, 40);
        jiantou2.setCompoundDrawables(null, null, drawable7, null);
    }


}
