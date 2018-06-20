package macbeth.androidsampler.JSONRecyclerView;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

public class EarthquakeLoader extends AsyncTask<Void, Void, List<EarthquakeEvent>> {

    private WeakReference<JSONRecyclerView> activityRef;

    public EarthquakeLoader(JSONRecyclerView activity) {
        activityRef = new WeakReference<JSONRecyclerView>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activityRef.get() != null) {
            activityRef.get().enableProgressBar();
        }
    }

    protected List<EarthquakeEvent> doInBackground(Void... voids) {
        EarthquakeList earthquakeList = (EarthquakeList) JSONHelper.loadJsonData("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson", null, EarthquakeList.class);
        return earthquakeList.getEarthquakes();
    }

    protected void onPostExecute(List<EarthquakeEvent> earthquakeList) {
        earthquakeList.sort((e1,e2)->(Float.compare(e2.getDetail().getMag(),e1.getDetail().getMag())));

        if (activityRef.get() != null) {
            activityRef.get().disableProgressBar();
            activityRef.get().displayEarthquakes(earthquakeList);
        }
    }
}
