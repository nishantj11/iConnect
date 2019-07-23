package com.example.priyank.final_project;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDisplayActivity extends AppCompatActivity {

    NavigationView navigationView;
    RecyclerView recyclerview;
    FirebaseAuth mAuth;
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    Button Submit;
    EditText text;
    List<Listdata> list;
    private static final String TAG = "FragmentActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        text=findViewById(R.id.autoCompleteTextView1);
        Submit = findViewById(R.id.submit);
        drawerLayout = findViewById(R.id.my_drawer);
        navigationView = findViewById(R.id.nav_view);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);


        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("edittext",text.getText().toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("User");
                Query query = FirebaseDatabase.getInstance().getReference().child("User")
                        .orderByChild("name").startAt(text.getText().toString().toLowerCase())
                        .endAt(text.getText().toString().toLowerCase()+"\uf8ff");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list = new ArrayList<>();
                        // StringBuffer stringbuffer = new StringBuffer();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                            Userdetails userdetails = dataSnapshot1.getValue(Userdetails.class);
                            Listdata listdata = new Listdata();
                            String name=userdetails.getName();
                            Log.d("err_l",name);
                            String email=userdetails.getEmail();
                            Long contact=userdetails.getContact();
                            listdata.setAname(name);
                            listdata.setAemail(email);
                            listdata.setAcontact(contact);
                            list.add(listdata);
                            // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                        }

                        RecyclerViewAdapter recycler = new RecyclerViewAdapter(list, getApplicationContext());
                        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(UserDisplayActivity.this);
                        recyclerview.setLayoutManager(layoutmanager);
                        recyclerview.setItemAnimator( new DefaultItemAnimator());
                        recyclerview.setAdapter(recycler);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                        Toast.makeText(UserDisplayActivity.this, "Sorry no such data available",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_out:
                        goToHome();
                        break;

                    case R.id.insert:
                        Intent intent = new Intent(UserDisplayActivity.this, insert.class);
                        startActivity(intent);
                }

                drawerLayout.closeDrawers();
                return false;
            }
        });


        }
        void goToHome() {
        Intent intent = new Intent(UserDisplayActivity.this, MainActivity.class);
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