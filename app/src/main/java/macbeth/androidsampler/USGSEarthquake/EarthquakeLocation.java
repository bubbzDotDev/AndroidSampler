package macbeth.androidsampler.USGSEarthquake;

import java.util.List;

import static java.lang.Math.round;

/**
 * Contains the coordinate information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeLocation {
    private List<Float> coordinates;

    private long depth;

    /**
     * Longitude of the earthquake.
     *
     * @return longitude
     */
    public float getLongitude() {
        return coordinates.get(0);
    }

    /**
     * Latitude of the earthquake
     *
     * @return latitude
     */
    public float getLatitude() {
        return coordinates.get(1);
    }

    /**
     * Depth of the earthquake
     *
     * @return - The depth
     */
    public long getDepth() {
        return round(coordinates.get(2));
        // return 0; // Need to fix this... https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
    }

}
