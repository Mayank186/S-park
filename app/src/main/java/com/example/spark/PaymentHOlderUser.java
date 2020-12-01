package com.example.spark;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentHOlderUser extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListner listner;
    public TextView name,UpiID,amount;

    public PaymentHOlderUser(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.ownerNameHistory);
        UpiID =(TextView) itemView.findViewById(R.id.upiIdOfOwner);
        amount =(TextView) itemView.findViewById(R.id.payMoneyToOwner);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}