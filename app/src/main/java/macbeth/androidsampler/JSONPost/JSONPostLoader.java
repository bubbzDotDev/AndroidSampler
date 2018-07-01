package macbeth.androidsampler.JSONPost;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JSONPostLoader extends AsyncTask<Void, Void, String> {

    private WeakReference<JSONPostActivity> activityRef;

    public JSONPostLoader(JSONPostActivity activity) {
        activityRef = new WeakReference<JSONPostActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activityRef.get() != null) {
            activityRef.get().enableProgressBar();
        }
    }

    protected String doInBackground(Void... dummy) {
        try {
            // Create a stream to the URL
            URL urlObj = new URL("https://api.justwatch.com/titles/en_US/popular");
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestProperty("User-Agent", "Java client");
            connection.setRequestProperty("Content-Type", "application/json");

            HashMap<String, List<String>> params = new HashMap<String,List<String>>();
            List<String> v0 = new ArrayList<String>();
            v0.add("G");
            params.put("age_certifications",v0);
            List<String> v1 = new ArrayList<String>();
            v1.add("movie");
            params.put("content_types",v1);
            params.put("presentation_types",null);
            List<String> v3 = new ArrayList<String>();
            v3.add("amp");
            params.put("providers",v3);
            params.put("genres",null);
            params.put("languages",null);
            params.put("release_year_from",null);
            params.put("release_year_until",null);
            List<String> v2 = new ArrayList<String>();
            v2.add("flatrate");
            params.put("monetization_types",v2);
            params.put("min_price",null);
            params.put("max_price",null);
            params.put("nationwide_cinema_releases_only",null);
            params.put("scoring_filter_types",null);
            params.put("cinema_release",null);
            params.put("query",null);
            params.put("page",null);
            params.put("page_size",null);
            params.put("timeline_type",null);

            Gson gson = new Gson();
            String body = gson.toJson(params);

            DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
            wr.writeBytes(body);
            wr.flush ();
            wr.close ();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read all data from the website into a single string
            String line = "";
            String allLines = "";
            do {
                line = reader.readLine();
                if (line != null) {
                    allLines += line;
                }
            }
            while (line != null);
            return allLines;
        } catch (MalformedURLException murle) {
            return null;
        } catch (IOException ioe) {
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if (activityRef.get() != null && result != null) {
            activityRef.get().displayDump(result);
            activityRef.get().disableProgressBar();
        }
    }
}
