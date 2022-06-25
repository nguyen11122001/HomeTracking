package com.example.demoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    private EditText edtName,edtPhone;
    private Users user ;
    ArrayList<Users> listUser = new ArrayList<Users>();
    private static ArrayList<String> listName = new ArrayList<String>();
    private String name,phone;
    private Button btnAdd;
    Map<String, Object> data;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName=findViewById(R.id.txt_name);
        edtPhone=findViewById(R.id.txt_phone);
        btnAdd=findViewById(R.id.btn_add);
        db = FirebaseFirestore.getInstance();
        data = new HashMap<>();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(Users.class);
                                listUser.add(user);
                                listName.add(user.getName());
                                Log.d("TAG", " Name: " + user.getName());

                            }


                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=edtName.getText().toString();
                phone = edtPhone.getText().toString();
                Integer a =checkUser(name);
                if((!(name.equals("")) || !(phone.equals(""))) && (a==3 )){
                    data.put("name", name);
                    data.put("phone", phone);

                    db.collection("Users").document(UUID.randomUUID().toString())
                            .set(data, SetOptions.merge());
                    listName.add(name);
                    new MaterialAlertDialogBuilder(view.getContext())
                            .setTitle("Success")
                            .setMessage("Created "+name+" !!")
                            .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                else{
                    if(a==1){
                        new MaterialAlertDialogBuilder(view.getContext())
                                .setTitle("Error")
                                .setMessage("Please wait a second !!")
                                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }
                    else{
                        new MaterialAlertDialogBuilder(view.getContext())
                                .setTitle("User is exist!!")
                                .setMessage("Please re-enter name !!")
                                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }

                }

                
//                Intent intent = new Intent(getBaseContext(), AddActivity.class);
//
//                startActivity(intent);
            }
        });

    }

    private Integer checkUser(String name){
        Log.d("TAG", "check " + listName);
        if (listName==null){
            return 1;
        }

        for (String name2:listName) {
            if(name.equals(name2)) return 2;
        }
        return 3;
    }
}