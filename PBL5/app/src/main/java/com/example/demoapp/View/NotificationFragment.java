package com.example.demoapp.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.demoapp.Model.History;
import com.example.demoapp.Model.NotificationDTO;
import com.example.demoapp.Model.Notify;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class NotificationFragment extends Fragment {

    private Toolbar mToolBar;
    private boolean hasData;
    private NotificationDTO data;

    public NotificationFragment() {

    }

    public NotificationFragment(@Nullable NotificationDTO notification){
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
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("UsersData").child("iH0qIYQfyzWzX5kQQRftJ1y422o2");
            View hasDataLayout = view.findViewById(R.id.has_data_layout);
            View noDataLayout = view.findViewById(R.id.no_data_layout);
            hasDataLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            //assign view
            ImageView image = view.findViewById(R.id.iv_notify_image);
            TextView name = view.findViewById(R.id.tv_notify);
            TextView time = view.findViewById(R.id.tv_notify_time);
            Button acceptBtn = view.findViewById(R.id.accept_button);
            Button denyBtn = view.findViewById(R.id.deny_button);
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.isVerified = true;
                    //update value
                    History newHistory = new History();
                    newHistory.name = data.name;
                    newHistory.time = data.time;
                    newHistory.isAccepted = true;
                    newHistory.url = data.image;
                    mDatabase.child("notification").child("inf").setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "S.O.S something went wrong :((", Toast.LENGTH_LONG).show();
                                }
                            });
                    mDatabase.child("histories").push().setValue(newHistory)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    view.findViewById(R.id.has_data_layout).setVisibility(View.GONE);
//                                    view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                                    hasDataLayout.setVisibility(View.GONE);
                                    noDataLayout.setVisibility(View.VISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"S.O.S something went wrong :((",  Toast.LENGTH_LONG).show();
                                }
                            });
                    mDatabase.child("DoorStatus").setValue(1);

                }
            });

            denyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.isVerified = true;
                    //update value
                    History newHistory = new History();
                    newHistory.name = data.name;
                    newHistory.time = data.time;
                    newHistory.isAccepted = false;
                    newHistory.url = data.image;
                    mDatabase.child("notification").child("inf").setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "S.O.S something went wrong :((", Toast.LENGTH_LONG).show();
                                }
                            });
                    mDatabase.child("histories").push().setValue(newHistory)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    view.findViewById(R.id.has_data_layout).setVisibility(View.GONE);
//                                    view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                                    hasDataLayout.setVisibility(View.GONE);
                                    noDataLayout.setVisibility(View.VISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"S.O.S something went wrong :((",  Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
            //bind data to view
            Picasso.get().load(data.image)
                    .resize(200,200)
                    .centerCrop()
                    .into(image);

            name.setText(data.name);
            time.setText(data.time);
        }
        else{
            view.findViewById(R.id.has_data_layout).setVisibility(View.GONE);
            view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
        }
    }
}