package com.example.demoapp.View;

import android.app.Notification;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Model.History;
import com.example.demoapp.Model.Notify;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationFragment extends Fragment {

    private Toolbar mToolBar;
    private boolean hasData;
    private Notify data;

    private ImageView image;
    private TextView name;
    private Button acceptBtn;
    private Button denyBtn;

    public NotificationFragment() {

    }

    public NotificationFragment(@Nullable Notify notification){
        if(notification != null){
            hasData = true;
            data = notification;
        }
        else{
            hasData = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get somthing
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //create top bar
        mToolBar = view.findViewById(R.id.topAppBar);
        mToolBar.getMenu().clear();
        mToolBar.inflateMenu(R.menu.top_app_bar);
        mToolBar.setTitle("Notification");

        //load layout
        if(hasData){
            view.findViewById(R.id.has_data_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.no_data_layout).setVisibility(View.GONE);
            //assign view
            image = view.findViewById(R.id.iv_notify_image);
            name = view.findViewById(R.id.tv_notify);
            acceptBtn = view.findViewById(R.id.accept_button);
            denyBtn = view.findViewById(R.id.deny_button);
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = data.id;
                    final History[] history = new History[1];
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("histories");
                    mDatabase.child(""+id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                history[0] = task.getResult().getValue(History.class);
                                Log.d("firebase", history[0].name);
                            }
                        }
                    });
                    history[0].isAccepted = true;
                    //update value
                    mDatabase.child("histories").child(history[0].id+"").setValue(history[0])
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    view.findViewById(R.id.has_data_layout).setVisibility(View.GONE);
                                    view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"S.O.S something went wrong :((",  Toast.LENGTH_LONG);
                                }
                            });
                }
            });

            //bind data to view
        }
        else{
            view.findViewById(R.id.has_data_layout).setVisibility(View.GONE);
            view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
        }
    }
}