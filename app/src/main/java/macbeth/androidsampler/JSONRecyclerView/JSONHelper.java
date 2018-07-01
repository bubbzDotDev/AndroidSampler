package macbeth.androidsampler.JSONRecyclerView;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public final class JSONHelper    {

    public final static String log = "JSONHelper";
    public final static int LOAD_GET = 0;
    public final static int LOAD_POST = 1;

    public static Object loadJsonData(String url, HashMap<String, String> properties, Type className, int method) {
        Object object = null;
        String data = JSONHelper.loadUrl(url, properties, method);
        Gson gson = new Gson();
        object = gson.fromJson(data, className);
        return object;
    }

    public static Object[] loadJsonDataArray(String url, HashMap<String, String> properties, Type classNameArray, int method) {
        Object[] objects;
        String data = JSONHelper.loadUrl(url, properties, method);
        Gson gson = new Gson();
        objects = gson.fromJson(data, classNameArray);
        return objects;
    }

    public static String loadUrl(String url, HashMap<String,String> properties, int method) {
        try {
            // Create a stream to the URL
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            if (properties != null) {
                for (String key : properties.keySet()) {
                    connection.setRequestProperty(key, properties.get(key));
                }
            }

            if (method == LOAD_GET) {
                connection.setRequestMethod("GET");
            }
            else {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestProperty("User-Agent", "Java client");
                connection.setRequestProperty("Content-Type", "application/json");
                Gson gson = new Gson();
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
                String body = gson.toJson(params);
                DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
                wr.writeBytes(body);
                wr.flush ();
                wr.close ();
            }
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
            Log.i(JSONHelper.log,"Received JSON Data");
            return allLines;
        } catch (MalformedURLException murle) {
            Log.e(JSONHelper.log, "MalformedURLException Received.");
            Log.d(JSONHelper.log, murle.getMessage());
            return null;
        } catch (IOException ioe) {
            Log.e(JSONHelper.log, "IOException Received.");
            Log.d(JSONHelper.log, ioe.getMessage());
            return null;
        }
    }
}
