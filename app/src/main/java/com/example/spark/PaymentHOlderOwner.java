package com.example.spark;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentHOlderOwner extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView username,paidmoney,upiid;
    public ItemClickListner listner;

    public PaymentHOlderOwner(@NonNull View itemView) {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.userNameHistory);
        paidmoney = (TextView) itemView.findViewById(R.id.paidMoneyByUser);
        upiid = (TextView) itemView.findViewById(R.id.upiIdOfUser);
    }

    public void setItemClickListner(ItemClickListner listner){
         this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
