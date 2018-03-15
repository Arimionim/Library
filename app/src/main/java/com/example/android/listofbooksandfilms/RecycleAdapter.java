package com.example.android.listofbooksandfilms;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.android.listofbooksandfilms.ListActivity.setRate;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List myDataSet;
    private static ClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mainText, additionalText, rate;
        ImageView good;
        View view;

        ViewHolder(View v) {
            super(v);
            view = v;
            v.setOnClickListener(this);
            mainText = v.findViewById(R.id.main_text);
            additionalText = v.findViewById(R.id.additional_text);
            rate = v.findViewById(R.id.rate_view);
            good = v.findViewById(R.id.good_view);
        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecycleAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    RecycleAdapter(List myDataset) {
        myDataSet = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.e("sdfsdf", ContextCompat.getColor(myDataSet.getContext(), myDataSet.getColorCardBackground()) + "");
        holder.view.setBackgroundColor(ContextCompat.getColor(myDataSet.getContext(), myDataSet.getColorCardBackground()));
        Log.e("sdfsdf", holder.view.getBackground() + "");

        holder.mainText.setText(myDataSet.get(position).getMainText());
        holder.mainText.setTextColor(ContextCompat.getColor(myDataSet.getContext(), myDataSet.getColorMainText()));

        holder.additionalText.setText(myDataSet.get(position).getAdditionalText());
        holder.additionalText.setTextColor(ContextCompat.getColor(myDataSet.getContext(), myDataSet.getColorDark()));

        holder.rate.setText(setRate(myDataSet.get(position).getRate()));
        holder.rate.setTextColor(ContextCompat.getColor(myDataSet.getContext(), myDataSet.getColorPrimary()));

        if (myDataSet.get(position).getIsGood()) {
            holder.good.setVisibility(View.VISIBLE);
        }
        else{
            holder.good.setVisibility(View.INVISIBLE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myDataSet.getSize();
    }
}