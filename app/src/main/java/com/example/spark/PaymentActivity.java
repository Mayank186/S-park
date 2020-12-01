package com.example.spark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class PaymentActivity extends AppCompatActivity {

    public EditText editTextOwnerName,editTextTotalAmount,editTextExtraMinute,editTextExtraTotal,editTextTotal;
    private TextView textViewPayment;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String UserName,userUPIID;
    private ProgressDialog progressDialog;
    private int accept,accept_1,firebaseTime;
    private String PaymentGooglePayID,PaymentName,GOOGLE_PAY_PACKAGE_NAME,OwnerName;
    private int GOOGLE_PAY_REQUEST_CODE;
    final int UPI_PAYMENT = 0;
    public int Amount,time,minute,extraTime,extraTotal,finalTotal;
    public String id,amount;
    public int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Objects.requireNonNull(getSupportActionBar()).hide();

        GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        GOOGLE_PAY_REQUEST_CODE = 123;

        editTextOwnerName = (EditText) findViewById(R.id.editTextOwnerName);
        editTextTotalAmount = (EditText) findViewById(R.id.editTextAmount);
        textViewPayment = (TextView) findViewById(R.id.textViewPaymentButton);
        editTextExtraMinute = (EditText) findViewById(R.id.editTextExtraMinute);
        editTextExtraTotal = (EditText) findViewById(R.id.editTextExtraTotal);
        editTextTotal = (EditText) findViewById(R.id.editTextTotal);
        PaymentName = "Parking Spot";

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        Amount= bundle.getInt("Amount");
        time = bundle.getInt("time");
        position = bundle.getInt("position");
        progressDialog = new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("car_standing").child(String.valueOf(position));
        final DatabaseReference databaseReference2 = firebaseDatabase.getReference("data").child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // owner details
                OwnerProfile ownerProfile = dataSnapshot.getValue(OwnerProfile.class);
                editTextOwnerName.setText(ownerProfile.getName());
                OwnerName = ownerProfile.getName();
                PaymentGooglePayID = ownerProfile.getGid();


                // PaymentGooglePayID owner gid
                // OwnerName owner name
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getTimeForPayment getTimeForPayment = dataSnapshot.getValue(getTimeForPayment.class);
                firebaseTime = getTimeForPayment.getTime();
                minute = firebaseTime/60;
                extraTime = minute - time;
                extraTotal=extraTime*2;
                editTextTotalAmount.setText(Integer.toString(Amount));
                editTextExtraMinute.setText(Integer.toString(extraTime));
                editTextExtraTotal.setText(Integer.toString(extraTotal));
                finalTotal = Amount + extraTotal;
                editTextTotal.setText(Integer.toString(finalTotal));
                amount = String.valueOf(finalTotal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // user details
                PaymentProfile paymentProfile = dataSnapshot.getValue(PaymentProfile.class);
                PaymentProfile paymentProfile1 = dataSnapshot.child("googleid").getValue(PaymentProfile.class);
                UserName = paymentProfile.getName();
                userUPIID = paymentProfile1.getId();
                // userUPIID user gid
                // UserName user name
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        textViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("abc","transaction"+OwnerName+"//"+PaymentGooglePayID+"//"+PaymentName+"//"+amount);
                PayUsingUPI( OwnerName,PaymentGooglePayID,PaymentName,amount);
//                progressDialog.setMessage("Transaction Pending....");
//                Log.v("abc","progress bar...");
//                progressDialog.show();
//                Log.v("abc","end");
                // PaymentGooglePayID owner gid
                // OwnerName owner name

            }

            private void PayUsingUPI(String name, String upid, String note, String amount) {
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", upid)
                                .appendQueryParameter("pn", name)
//                                .appendQueryParameter("mc", "your-merchant-code")
//                                .appendQueryParameter("tr", "your-transaction-ref-id")
                                .appendQueryParameter("tn", note)
                                .appendQueryParameter("am", amount)
                                .appendQueryParameter("cu", "INR")
//                                .appendQueryParameter("url", "your-transaction-url")
                                .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                Intent chooser = Intent.createChooser(intent,"Pay with");

                if(chooser.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(chooser,UPI_PAYMENT);
                } else {
                    Toast.makeText(PaymentActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
                }

//                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//                startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }

    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                UserPayment userPayment = new UserPayment(
                        OwnerName,PaymentGooglePayID,Integer.parseInt(amount)
                );
                // userUPIID user gid
                // UserName user name
                OwnerPayment ownerPayment = new OwnerPayment(
                        UserName,userUPIID,Integer.parseInt(amount)
                );
                FirebaseDatabase.getInstance().getReference("data")
                        .child("QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3").child("history").push()
                        .setValue(ownerPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            accept=1;
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("AccountDetails")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("history").push()
                        .setValue(userPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            accept_1 =1;
                        }
                    }
                });
                if(accept == accept_1){
                    Toast.makeText(PaymentActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                    Log.e("UPI", "payment successful: "+approvalRefNo);
                    //progressDialog.dismiss();
                    Intent intent = new Intent(PaymentActivity.this, carBooking.class);
                    startActivity(intent);
                }
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
