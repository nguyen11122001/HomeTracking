package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.demoapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail=binding.txtEmail.getText().toString();
                String txtPass=binding.txtPass.getText().toString();

                if(txtEmail.length() !=0 && txtPass.length()!=0) {
                    Login(txtEmail,txtPass);

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please entern Email or Passwork",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    void Login(String Email , String pass)
    {
        mAuth.signInWithEmailAndPassword(Email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(binding.checkboxIsRemember.isChecked())
                            {
                                SharedPreferences preferences=getSharedPreferences("remember",MODE_PRIVATE);
                                SharedPreferences.Editor editor =preferences.edit();
                                editor.putString("checkbox","true");
                                editor.putString("name",Email);
                                editor.putString("pass",pass);
                                editor.apply();
                            }
                            else
                            {
                                SharedPreferences preferences=getSharedPreferences("remember",MODE_PRIVATE);
                                SharedPreferences.Editor editor =preferences.edit();
                                editor.putString("checkbox","false");
                                editor.putString("name","");
                                editor.putString("pass","");
                                editor.apply();
                            }
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Key_1", user.getUid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                        } else
                        {
                            Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}