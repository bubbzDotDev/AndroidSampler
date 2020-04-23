package macbeth.androidsampler.JSONRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import macbeth.androidsampler.ArcGisMapDisplay.ArcGisMapDisplay;
import macbeth.androidsampler.R;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    private List<EarthquakeEvent> data;  // The adapter holds a reference to the data.
    private Context context;             // The data is usually created outside and then given
                                         // to the adapter.

    public EarthquakeAdapter(Context context) {
        this.data = new ArrayList<EarthquakeEvent>(); // Start out empty
        this.context = context;
    }

    public void setData(List<EarthquakeEvent> data) {
        this.data = data; // Start using the data that is given to us
    }

    public EarthquakeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTYpe) {
        // This inflates the layout that we defined for a single row in our RecyclerView
        // The ViewHolder will take that view and store values.  A seperate VIewHolder exists
        // for each row.
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.earthquake_list_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        // The onBindViewHolder will put data into the ViewHolder
        // The data is obtained by using the position to get data out of the List
        // This function can also be used to create a listener in case someone clicks a row
        holder.tvMagnitude.setText(String.format(
                Locale.US,"%.1f",data.get(position).getDetail().getMag()));
        holder.tvLocation.setText(data.get(position).getDetail().getPlace());
        holder.tvDateTime.setText(data.get(position).getDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will display a map for the location of the earthquake
                Intent intent = new Intent(context, ArcGisMapDisplay.class);
                intent.putExtra("description",data.get(position).toString());
                intent.putExtra("latitude",data.get(position).getPoint().getLatitude());
                intent.putExtra("longitude",data.get(position).getPoint().getLongitude());
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMagnitude;
        TextView tvLocation;
        TextView tvDateTime;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMagnitude = (TextView) itemView.findViewById(R.id.tv_magnitude);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
            tvDateTime = (TextView) itemView.findViewById(R.id.tv_datetime);
        }

    }
}
