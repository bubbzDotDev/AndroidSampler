package macbeth.androidsampler.JSONRecyclerView;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public final class JSONHelper    {

    public final static String log = "JSONHelper";

    public static Object loadJsonData(String url, HashMap<String, String> properties, Type className) {
        Object object = null;
        String data = JSONHelper.loadUrl(url, properties);
        Gson gson = new Gson();
        object = gson.fromJson(data, className);
        return object;
    }

    public static Object[] loadJsonDataArray(String url, HashMap<String, String> properties, Type classNameArray) {
        Object[] objects;
        String data = JSONHelper.loadUrl(url, properties);
        Gson gson = new Gson();
        objects = gson.fromJson(data, classNameArray);
        return objects;
    }

    public static String loadUrl(String url, HashMap<String,String> properties) {
        StringBuilder data = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            if (properties != null) {
                for (String key : properties.keySet()) {
                    connection.setRequestProperty(key, properties.get(key));
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read all data from the website into a single string
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);

        } catch (IOException ioe) {
            Log.e(JSONHelper.log, "IOException Received.");
            Log.d(JSONHelper.log, ioe.getMessage());
            return null;
        }
        Log.i(JSONHelper.log, "Received JSON Data");
        return data.toString();
    }

}
