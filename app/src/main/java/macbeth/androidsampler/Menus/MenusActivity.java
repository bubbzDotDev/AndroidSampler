package macbeth.androidsampler.Menus;

import android.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import macbeth.androidsampler.R;

public class MenusActivity extends AppCompatActivity {

    private TextView tvMenuSelected;
    private PopupMenu popup;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        setTitle("Menus");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        tvMenuSelected = findViewById(R.id.textView9);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // https://android--code.blogspot.com/2016/01/android-popup-window-example.html
        popupWindow = new PopupWindow(inflater.inflate(R.layout.menu_popup_window, null), RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        popup = new PopupMenu(this, findViewById(R.id.button15));
        popup.inflate(R.menu.menu_list);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                updateUI(menuItem);
                return true;
            }
        });


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        updateUI(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (updateUI(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void popupMenu(View view) {
        popup.show();
    }

    private boolean updateUI(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_page1:
                tvMenuSelected.setText("Page 1");
                return true;
            case R.id.nav_page2:
                tvMenuSelected.setText("Page 2");
                return true;
            case R.id.nav_page3:
                tvMenuSelected.setText("Page 3");
                popupWindow.showAtLocation(findViewById(R.id.drawer_layout), Gravity.CENTER, 0, 0);
                return true;
            default:
                tvMenuSelected.setText("Nothing");
                return false;
        }
    }
}
