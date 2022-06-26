package com.example.demoapp.View;


import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoapp.HomeActivity;
import com.example.demoapp.Model.History;
import com.example.demoapp.R;
import com.example.demoapp.ViewModel.HistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class HistoryFragment extends Fragment {
    private ArrayList<History> histories = new ArrayList<>();
    private HistoryAdapter adapter;
    private Toolbar mToolBar;
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get history list sent from activity
        Bundle bundle = this.getArguments();
        if(bundle != null){
            histories = (ArrayList<History>) bundle.getSerializable("histories");
            for (History i:
                    histories) {
                Log.d("DEBUG","fragment "+ i.name);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //define recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = view.findViewById(R.id.rv_history);
        recyclerView.setLayoutManager(layoutManager);
        //set adapter
        adapter = new HistoryAdapter(histories, getContext());
        recyclerView.setAdapter(adapter);
        mToolBar = view.findViewById(R.id.topAppBar);
        mToolBar.getMenu().clear();
        mToolBar.inflateMenu(R.menu.top_app_bar);
        mToolBar.setTitle("History");
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.app_bar_search:{
                        Toast.makeText(getContext(),"click", Toast.LENGTH_LONG);
                    }
                    return true;
                }
                return false;
            }
        });

        SearchManager searchManager =
                (SearchManager)getActivity(). getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) mToolBar.getMenu().findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<History> currentData = histories;
                ArrayList<History> newData = new ArrayList<>();
                if(query.length() > 0){
                    for(History h : currentData){
                        if (h.name.toLowerCase().contains(query.toLowerCase())) newData.add(h);
                    }
                    adapter.setData(newData);
                    for(History h : newData){
                        Log.d("DEBUG", h.name);
                    }
                    return false;
                }
                adapter.setData(histories);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("DEBUG", "onSubmit");
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
//        SearchManager searchManager =
//                (SearchManager)getActivity(). getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) mToolBar.getMenu().findItem(R.id.app_bar_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextChange(String query) {
//                ArrayList<History> currentData = adapter.getData();
//                ArrayList<History> newData = (ArrayList<History>) currentData.stream().filter(h -> h.name.contains(query)).collect(Collectors.toList());
//                adapter.setData(newData);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.d("DEBUG", "onSubmit");
//                return true;
//            }
//        };
//        searchView.setOnQueryTextListener(queryTextListener);
    }
}