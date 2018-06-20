package macbeth.androidsampler.JSONRecyclerView;

import java.util.Date;

/**
 * Contains the all earthquake event information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeEvent {
    private EarthquakeDetail properties;
    private EarthquakeLocation geometry;

    /**
     * Detail of the earthquake
     *
     * @return EarthquakeDetail
     */
    public EarthquakeDetail getDetail() {
        return properties;
    }

    /**
     * Location of the earthquake
     *
     * @return EarthquakeLocation
     */
    public EarthquakeLocation getPoint() {
        return geometry;
    }

    public String getDateTime() {
        Date date = new Date(properties.getTime());
        return date.toString();
    }

    /**
     * Create a string that contains a summary detail of the earthquake
     *
     * @return String
     */
    public String toString() {
        try {
            String output = "Location: " + properties.getPlace() + "\n";
            Date date = new Date(properties.getTime());
            output += "Time: " + date.toString() + "\n";
            output += "Magnitude: " + properties.getMag() + "\n";
            output += "Latitude: " + geometry.getLatitude() + "\n";
            output += "Longitude: " + geometry.getLongitude() + "\n";
            output += "Depth: " + geometry.getDepth() + " km\n";
            output += "Alert: " + properties.getAlert() + "\n";
            return output;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

}
