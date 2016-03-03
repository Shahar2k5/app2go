package com.example.android.app2go;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shaharbarsheshet on 03/03/2016.
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {
    private ArrayList<LocationPoint> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mStartPoint;
        public TextView mEndPoint;
        public ImageView mStartPin;
        public ImageView mEndPin;
        public TextView mTotalTime;

        public ViewHolder(View v) {
            super(v);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationsAdapter(ArrayList<LocationPoint> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_points_cell, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        vh.mStartPin = (ImageView) v.findViewById(R.id.iconStart);
        vh.mEndPin = (ImageView) v.findViewById(R.id.iconEnd);
        vh.mStartPoint = (TextView) v.findViewById(R.id.topLineStart);
        vh.mTotalTime = (TextView) v.findViewById(R.id.totalTime);
        vh.mEndPoint = (TextView) v.findViewById(R.id.topLineEnd);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mStartPoint.setText(mDataset.get(position).getSource());
        holder.mEndPoint.setText(mDataset.get(position).getDestination());
        holder.mEndPin.setColorFilter(mContext.getResources().getColor(R.color.mdc_blue_400));
        holder.mStartPin.setColorFilter(mContext.getResources().getColor(R.color.mdc_deep_orange_500));
        holder.mTotalTime.setText(mDataset.get(position).getDurationText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
