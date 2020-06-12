package com.android.cai_lai_la.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.cai_lai_la.R;

import androidx.fragment.app.Fragment;

/**
 */
public class NavClassFragment extends Fragment {
    public NavClassFragment() {
        // Required empty public constructor
    }

    public static NavClassFragment newInstance(){
        NavClassFragment fragment = new NavClassFragment();
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
        return inflater.inflate(R.layout.fragment_nav_class, container, false);
    }
}
