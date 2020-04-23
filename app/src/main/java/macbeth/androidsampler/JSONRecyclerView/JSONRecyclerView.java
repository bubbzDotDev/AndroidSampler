package macbeth.androidsampler.JSONRecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import macbeth.androidsampler.R;

public class JSONRecyclerView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    private RecyclerView.LayoutManager layoutMannager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_recycler_view);
        setTitle("JSON Recycler View");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.jsonRecyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new EarthquakeAdapter(this);
        recyclerView.setAdapter(adapter);
        layoutMannager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutMannager);
        EarthquakeLoader loader = new EarthquakeLoader(this);
        loader.execute();
    }

    public void displayEarthquakes(List<EarthquakeEvent> earthquakeList) {
        adapter.setData(earthquakeList);
        adapter.notifyDataSetChanged();
    }

    public void enableProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void disableProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
