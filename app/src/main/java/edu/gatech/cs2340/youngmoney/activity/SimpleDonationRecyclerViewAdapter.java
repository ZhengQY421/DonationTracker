package edu.gatech.cs2340.youngmoney.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.cs2340.youngmoney.R;
import edu.gatech.cs2340.youngmoney.model.Donation;
import edu.gatech.cs2340.youngmoney.model.ModelDonation;

public class SimpleDonationRecyclerViewAdapter extends RecyclerView.Adapter<SimpleDonationRecyclerViewAdapter.ViewHolder> {


    private final List<Donation> donations;
    private Context context;
    private boolean white;

    public SimpleDonationRecyclerViewAdapter(Context c, List<Donation> d, boolean w) {
        context = c;
        donations = d;
        white = w;
    }

    @NonNull
    @Override
    public SimpleDonationRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {

        View view;

        if (white){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item_format_white, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item_format, parent, false);
        }

        return new SimpleDonationRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int index) {

        holder.loc = donations.get(index);
        holder.locView.setText(""+holder.loc.getItem());

        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ModelDonation model = ModelDonation.get_instance();
                model.set_current(holder.loc);

                Intent intent = new Intent(v.getContext(), DonationDetailActivity.class);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView locView;
        public Donation loc;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            if(white){
                locView = view.findViewById(R.id.donation2);
            } else {
                locView = (TextView) view.findViewById(R.id.donation);
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + locView.getText() + "'";
        }
    }
}




