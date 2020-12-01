package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Objects;

public class HistoryUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //ArrayList<PaymentUser> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistoryUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  list = new ArrayList<PaymentUser>();
//
//        FirebaseDatabase.getInstance().getReference("AccountDetails")
//            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("history")
//            .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    String key = dataSnapshot1.getKey();
//                    PaymentUser p = dataSnapshot.child(key).getValue(PaymentUser.class);
//                    list.add(p);
//                }
//                myAdapterForUser = new MyAdapterForUser(HistoryUserActivity.this, list);
//                recyclerView.setAdapter(myAdapterForUser);
//                }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


        Query query = FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("history");

        FirebaseRecyclerOptions<PaymentUser> options = new FirebaseRecyclerOptions.Builder<PaymentUser>()
                .setQuery(query, PaymentUser.class).build();

        FirebaseRecyclerAdapter<PaymentUser, PaymentHOlderUser> ownerAdapter =
                new FirebaseRecyclerAdapter<PaymentUser, PaymentHOlderUser>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull PaymentHOlderUser holder, int i, @NonNull PaymentUser model) {
                        holder.name.setText(model.getOwnername());
                        //holder.name.setText("Aakash joshi");
                        Log.v("abc",model.getOwnername());
                        Log.v("abc",Integer.toString(model.getAmount()));
                        Log.v("abc",model.getGoogleid());
                        holder.amount.setText(Integer.toString(model.getAmount()));
                        //holder.amount.setText("10");
                        holder.UpiID.setText(model.getGoogleid());
                        //holder.UpiID.setText("akashjoshi61099@gc");
                    }
                    @NonNull
                    @Override
                    public PaymentHOlderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_history_user, parent, false);
                        PaymentHOlderUser paymentHOlderUser = new PaymentHOlderUser(view);
                        return paymentHOlderUser;
                    }
                };
        recyclerView.setAdapter(ownerAdapter);
        ownerAdapter.startListening();

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("a12", "BACK");
        Intent i = new Intent(HistoryUserActivity.this, MapActivity.class);
        startActivity(i);
    }
}
