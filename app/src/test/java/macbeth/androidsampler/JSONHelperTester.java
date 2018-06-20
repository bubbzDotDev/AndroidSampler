package macbeth.androidsampler;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;
import macbeth.androidsampler.JSONRecyclerView.JSONHelper;

public class JSONHelperTester {

    @Test
    public void test_JSONHelper_invalidURL() {
        String result = JSONHelper.loadUrl("http://nothing", null);
        Assert.assertEquals(result, null);
    }

    @Test
    public void test_JSONHelp_validURL() {
        String result = JSONHelper.loadUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson", null);
        Assert.assertEquals(Pattern.matches(".*earthquake.*",result), true);
    }

}
