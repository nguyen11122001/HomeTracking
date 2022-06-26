package com.example.demoapp.View;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.demoapp.AddActivity;
import com.example.demoapp.R;
import com.example.demoapp.UploadActivity;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {

    private Button btnLogout,btnAdd,btnPush,btnList,btnTrain;
    Context thiscontext;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext=container.getContext();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnAdd = view.findViewById(R.id.btn_addUser);
        btnPush = view.findViewById(R.id.btn_pushImage);
        btnList = view.findViewById(R.id.btn_listUser);
        btnTrain = view.findViewById(R.id.btn_train);


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment  fragment2 = new UsersFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(thiscontext, AddActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Key_2", user.getUid());
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(thiscontext, UploadActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Key_2", user.getUid());
//                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(thiscontext, AddActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Key_2", user.getUid());
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("checkbox","false");
                editor.putString("name","");
                editor.putString("pass","");
                editor.apply();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}