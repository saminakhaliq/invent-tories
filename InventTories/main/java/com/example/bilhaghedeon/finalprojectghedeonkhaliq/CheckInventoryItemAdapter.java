package com.example.bilhaghedeon.finalprojectghedeonkhaliq;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

import java.util.List;

/*
*  This adapter will load items into recycler view and display by item name
*
* */
/**
 * Implemented by Samina Khaliq
 */


public class CheckInventoryItemAdapter extends RecyclerView.Adapter<CheckInventoryItemAdapter.MyViewHolder> {

    private List<Item> mItems;

    public CheckInventoryItemAdapter(List<Item> items) {
        this.mItems = items;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtName;

        public MyViewHolder(LinearLayout layout) {
            super(layout);

            mTxtName = layout.findViewById(R.id.txt_name);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item, viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.mTxtName.setText(mItems.get(i).getName());


    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

}
