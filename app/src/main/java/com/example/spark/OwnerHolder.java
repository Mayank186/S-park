package com.example.spark;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OwnerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ItemClickListner listner;
    public TextView spot;

    public OwnerHolder(@NonNull View itemView) {
        super(itemView);
        spot = (TextView) itemView.findViewById(R.id.Owner_Spot);
    }
    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
