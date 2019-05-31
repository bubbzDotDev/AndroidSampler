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
            URL urlObj = new URL("https://apis.justwatch.com/content/titles/en_US/popular");
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");  // We need these 2 set functions for a POST
            connection.setDoOutput(true);
            // Sometimes the API wants us to specify these properties
            connection.setRequestProperty("User-Agent", "Java client");
            connection.setRequestProperty("Content-Type", "application/json");

            JSONData data = new JSONData();

            // With a Post, we have to write data to the server.  For a Get, the data is part of
            // the URL so we didn't have to write.

            // Hopefully find "Gone with the Wind" for free on Amazon Prime ... Good for family
            data.setQuery("wind");

            List<String> providers = new ArrayList<String>();
            providers.add("amp"); // Amazon Prime
            data.setProviders(providers);

            List<String> certifications = new ArrayList<String>();
            certifications.add("G");
            data.setAge_certifications(certifications);


            Gson gson = new Gson();
            String body = gson.toJson(data);

            // We created the body above by creating a JSONData object that contained our request
            // and then we converted it to JSON.  In other words, the API expects that we request
            // information via POST using JSON format.

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
        // Let the activity now that we are done.
        if (activityRef.get() != null && result != null) {
            activityRef.get().displayDump(result);
            activityRef.get().disableProgressBar();
        }
    }
}
