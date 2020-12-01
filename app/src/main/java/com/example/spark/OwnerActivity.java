package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.Objects;

public class OwnerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        Objects.requireNonNull(getSupportActionBar()).hide();
        navigationView = findViewById(R.id.nav_view_owner);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance().getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("car_standing");
        FirebaseRecyclerOptions<BookTheVehicle> options =
                new FirebaseRecyclerOptions.Builder<BookTheVehicle>()
                        .setQuery(query, BookTheVehicle.class)
                        .build();

        FirebaseRecyclerAdapter<BookTheVehicle,OwnerHolder> adapter =
                new FirebaseRecyclerAdapter<BookTheVehicle, OwnerHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OwnerHolder holder, int position, @NonNull BookTheVehicle model) {
                        String spot = String.valueOf(model.getSpot());
//                        Log.v("abc", "ENTERED FUNCTION"+model.toString());
//                        Log.v("abc", "cs: "+parkingSpot);

                        if(spot.equals("Yes")){
                            holder.spot.setBackgroundColor(Color.parseColor("#ff0000"));
                        } else {
                            holder.spot.setBackgroundColor(Color.parseColor("#00ff00"));
                        }
                    }

                    @NonNull
                    @Override
                    public OwnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_owner, parent, false);
                        OwnerHolder holder = new OwnerHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_history:
                startActivity(new Intent(getApplicationContext(), HistoryOwnerActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                SharedPreferences sharedPreferences
                        = getSharedPreferences("MySharedPref",
                        MODE_PRIVATE);
                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();

                myEdit.remove("email");
                myEdit.remove("password");

                myEdit.commit();


                firebaseAuth = FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.signOut();
                }
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }
        return false;
    }
}
