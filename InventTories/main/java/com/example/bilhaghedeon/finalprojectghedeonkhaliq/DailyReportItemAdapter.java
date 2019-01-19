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
*  This adapter will load items into recycler view and display by item name and include item
*  quantity
* */


public class DailyReportItemAdapter extends RecyclerView.Adapter<DailyReportItemAdapter.MyViewHolder> {

    private List<Item> mItems;

    public DailyReportItemAdapter(List<Item> items) {
        this.mItems = items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtName, mTxtQuantity;

        public MyViewHolder(LinearLayout layout) {
            super(layout);

            mTxtName = layout.findViewById(R.id.txt_name);
            mTxtQuantity = layout.findViewById(R.id.txt_quantity);
        }

    }

    @NonNull
    @Override
    public DailyReportItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item, viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportItemAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mTxtName.setText(mItems.get(i).getName());
        myViewHolder.mTxtQuantity.setText(String.format("%d", mItems.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }


}
