package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.demoapp.Model.History;
import com.example.demoapp.Model.NotificationDTO;
import com.example.demoapp.View.AccountFragment;
import com.example.demoapp.View.HistoryFragment;
import com.example.demoapp.View.HomeFragment;
import com.example.demoapp.View.NotificationFragment;
import com.example.demoapp.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private String Uid;

    private DatabaseReference mDatabase;
    private List<History> histories = new ArrayList<>();
    private NotificationDTO notify;
    private Snackbar notifySnackBar ;

    private HistoryFragment historiesFragment =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intentNof=new Intent(this,NotificationService.class);
        intentNof.putExtra("userurl", "url");
        startService(intentNof);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String value1 = bundle.getString("Key_1", "");
            Toast.makeText(HomeActivity.this,value1,Toast.LENGTH_LONG).show();
        }
        notifySnackBar = Snackbar.make(view, "New notification", Snackbar.LENGTH_INDEFINITE)
                .setAction("View", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new NotificationFragment(notify);
                        loadFragment(fragment);
                    }
                });

        //enable offline capabilities
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("UsersData/iH0qIYQfyzWzX5kQQRftJ1y422o2");

        //read new data every time database change
        ValueEventListener postListener =
                mDatabase.child("histories").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Toast.makeText(HomeActivity.this, "test", Toast.LENGTH_LONG).show();
                        histories.clear();
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            History h = item.getValue(History.class);
                            histories.add(h);
                        }
                        Collections.reverse(histories);
                        if(historiesFragment !=null){
                            historiesFragment.updateData((ArrayList<History>) histories);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("DEBUG", "loadPost:onCancelled", databaseError.toException());
                    }
                });

        ValueEventListener notifyListener = mDatabase.child("notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    notify = item.getValue(NotificationDTO.class);
                }
                Log.d("DEBUG", notify.toString());
                if(!notify.isVerified) notifySnackBar.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBUG", error.getMessage());
            }
        });

        loadFragment(new NotificationFragment(null));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_history:
                    fragment = new HistoryFragment();
                    historiesFragment = (HistoryFragment) fragment;
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("histories", (Serializable) histories);
                    fragment.setArguments(mBundle);
                    loadFragment(fragment);
                    return true;
                case R.id.nav_notification:
                    fragment = new NotificationFragment((!notify.isVerified)?notify:null);
                    loadFragment(fragment);
                    return true;
                case R.id.nav_account:
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}