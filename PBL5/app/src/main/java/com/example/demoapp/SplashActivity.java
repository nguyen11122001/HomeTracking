package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demoapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferencesCheckBox=getSharedPreferences("remember",MODE_PRIVATE);
        String checkox=preferencesCheckBox.getString("checkbox","");
        if(checkox.equals("true"))
        {
            String Email=preferencesCheckBox.getString("name","");
            String Pass=preferencesCheckBox.getString("pass","");
            Login(Email,Pass);

        }else
        {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    void Login(String Email , String pass)
    {
        mAuth.signInWithEmailAndPassword(Email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Key_1", user.getUid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                        } else
                        {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(SplashActivity.this,"Login",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}