package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class carBooking extends AppCompatActivity {
    private RecyclerView recyclerView;
    public List<BookTheVehicle> list;
    private DatabaseReference databaseReference;
    //private MyBookAdapter myBookAdapter;
    public String id;
    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseDatabase.getInstance().getReference("AccountDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("car_standing")){
//                    carStanding = dataSnapshot.child("car_standing").getValue(String.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Query query = FirebaseDatabase.getInstance().getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("car_standing");
        //databaseReference = FirebaseDatabase.getInstance().getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3");
//        databaseReference = FirebaseDatabase.getInst;



        FirebaseRecyclerOptions<BookTheVehicle> options =
                new FirebaseRecyclerOptions.Builder<BookTheVehicle>()
                .setQuery(query, BookTheVehicle.class)
                .build();

        FirebaseRecyclerAdapter<BookTheVehicle,bookViewHolder> adapter =
                new FirebaseRecyclerAdapter<BookTheVehicle, bookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull bookViewHolder holder, int position, @NonNull BookTheVehicle model) {
                final bookViewHolder h2 = holder;
                holder.mCardView.setTag(position);
                String spot = String.valueOf(model.getSpot());
//                String parkingSpot = ((HashMap<String, String>)model).get("spot");
//                Log.v("axx", parkingSpot);
//                Log.v("abc", "ENTERED FUNCTION"+model.toString());
//                Log.v("abc", "cs: "+parkingSpot);

                //String parkingSpot = ((ParkingSpot)model).getCar_standing();
                if(spot.equals("Yes")){
                    holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#ff0000"));
                    holder.textViewBookSpot.setEnabled(false);
                } else {
                    holder.textViewChangeSpot.setBackgroundColor(Color.parseColor("#00ff00"));
                    holder.textViewBookSpot.setEnabled(true);
                    holder.textViewBookSpot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(carBooking.this, carBookingBytime.class);
//                            View view = v;
//                            View parent = (View) v.getParent();
//                            while (!(parent instanceof RecyclerView)){
//                                view=parent;
//                                parent = (View) parent.getParent();
//                            }
//                            int position = recyclerView.getChildAdapterPosition(view);
                            int position = h2.getAdapterPosition()+1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("position",position);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            }

            @NonNull
            @Override
            public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_view, parent, false);
                bookViewHolder holder = new bookViewHolder(view);
                return holder;
            }
        };
        //Log.w("fd","gdfghhb");
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
//        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        FirebaseDatabase.getInstance().getReference().child("path")
//        .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list = new ArrayList<BookTheVehicle>();
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    BookTheVehicle bookTheVehicle = dataSnapshot1.getValue(BookTheVehicle.class);
//                    list.add(bookTheVehicle);
//                }
//                myBookAdapter = new MyBookAdapter(list, carBooking.this);
//                recyclerView.setAdapter(myBookAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(carBooking.this,"Something Wrong",Toast.LENGTH_LONG).show();
//            }
//        });


    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("a12", "BACK");
        Intent i = new Intent(carBooking.this, VehicleBooking.class);
        startActivity(i);
    }
}
