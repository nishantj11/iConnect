package com.example.priyank.final_project;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;

public class insert extends AppCompatActivity {

    EditText name, contact, email;
    FirebaseDatabase database;
    DrawerLayout drawerLayout;
    DatabaseReference myRef;
    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        name= findViewById(R.id.name_ins);
        contact = findViewById(R.id.contact_ins);
        drawerLayout = findViewById(R.id.my_drawer);
        email = findViewById(R.id.email_ins);
        navigationView = findViewById(R.id.nav_view);
        save = (Button) findViewById(R.id.save);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("User");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().toLowerCase();
                Long Contact = Long.valueOf(contact.getText().toString());
                String Email = email.getText().toString();

                String key = myRef.push().getKey();
                Userdetails userdetails = new Userdetails();
                userdetails.setName(Name);
                userdetails.setEmail(Email);
                userdetails.setContact(Contact);

                myRef.child(key).setValue(userdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(insert.this, "Write was successful!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(insert.this, "Write failed",
                                Toast.LENGTH_SHORT).show();
                        Log.d("ErrorMsg",e.getMessage());
                    }
                });
                name.setText("");
                email.setText("");
                contact.setText("");

            }




        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_out:
                        goToHome();
                        break;
                    case R.id.search:
                        Intent intent = new Intent(insert.this,UserDisplayActivity.class);
                        startActivity(intent);
                }
//                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
    void goToHome() {
        Intent intent = new Intent(insert.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            goToHome();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to SignOut", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
