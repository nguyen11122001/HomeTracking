package com.example.demoapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoapp.R;
import com.google.android.material.appbar.AppBarLayout;

public class HomeFragment extends Fragment {
    private Toolbar mToolbar;
    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.topAppBar);
        mToolbar.getMenu().clear();
        mToolbar.inflateMenu(R.menu.top_app_bar);
        mToolbar.setTitle("Home");
        MenuItem item = mToolbar.getMenu().findItem(R.id.app_bar_search);
        item.setVisible(false);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}