package macbeth.androidsampler.USGSEarthquake;

/**
 * Contains the detailed earthquake information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeDetail {

    private String place;
    private float mag;
    private long time;
    private String alert;

    public EarthquakeDetail() {
    }

    /**
     * Place or location of the earthquake
     *
     * @return place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Magnitude of the earthquake
     *
     * @return magniuted
     */
    public float getMag() {
        return mag;
    }

    /**
     * Time of the earthquake (ms since epoch)
     *
     * @return time
     */
    public long getTime() {
        return time;
    }

    /**
     * Alert (green, yellow, orange, or red) assigned for the earthquake
     *
     * @return alert
     */
    public String getAlert() {
        return alert;
    }

//    public EarthquakeLocation getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(EarthquakeLocation coordinates) {
//        this.coordinates = coordinates;
//    }
}
