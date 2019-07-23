package com.example.priyank.final_project;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

        EditText Email,Password;
        Button Signin, Signup;
        Boolean doubleBackToExitPressedOnce = false;
        FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setTheme(R.style.mainAppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Email = findViewById(R.id.email);
            Password = findViewById(R.id.password);
            mAuth = FirebaseAuth.getInstance();
            Signin = findViewById(R.id.signin);
            Signup = findViewById(R.id.signup);

            Signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailText = Email.getText().toString();
                    String passwordText = Password.getText().toString();
                    if(emailText.compareTo("")==0 || passwordText.compareTo("")==0)
                    {
                        Toast.makeText(MainActivity.this, "Enter valid email and password",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        perform(emailText, passwordText);
                    }
                }
            });

        }

        private void perform(String emailText, String passwordText) {
            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, UserDisplayActivity.class);
                                Toast.makeText(MainActivity.this, "Signin Successfull.",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(intent);

                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        @Override
        public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            } else {

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to SignOut", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
}
