package macbeth.androidsampler.ArcGisMapDisplay;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import macbeth.androidsampler.R;

public class ArcGisMapDisplay extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcgis_map_display);
        setTitle("ArcGis Map Display");

        TextView tv = findViewById(R.id.textView2);
        mMapView = findViewById(R.id.mapView);


        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        float latitude = intent.getFloatExtra("latitude",0.0f);
        float longitude = intent.getFloatExtra("longitude",0.0f);
        tv.setText(description);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, latitude, longitude, 8);
        mMapView.setMagnifierEnabled(true);
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(graphicsOverlay);
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
        SimpleMarkerSymbol dot;

        dot = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);

        Graphic graphic = new Graphic(point, dot);
        graphicsOverlay.getGraphics().add(graphic);
        mMapView.setMap(map);


    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }
}
