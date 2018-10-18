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
import edu.gatech.cs2340.youngmoney.model.Location;
import edu.gatech.cs2340.youngmoney.model.Model;

public class SimpleLocationRecyclerViewAdapter  extends RecyclerView.Adapter<SimpleLocationRecyclerViewAdapter.ViewHolder> {


    private final List<Location> locations;
    private Context context;

    public SimpleLocationRecyclerViewAdapter(Context c, List<Location> locs) {
        context = c;
        locations = locs;
    }

    @NonNull
    @Override
    public SimpleLocationRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_format, parent, false);

        return new SimpleLocationRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int index) {

        holder.loc = locations.get(index);
        holder.locView.setText(""+holder.loc.getName());

        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Model model = Model.get_instance();
                model.set_current(holder.loc);

                Intent intent = new Intent(v.getContext(), LocationDetailActivity.class);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView locView;
        public Location loc;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            locView = (TextView) view.findViewById(R.id.location);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + locView.getText() + "'";
        }
    }
}




