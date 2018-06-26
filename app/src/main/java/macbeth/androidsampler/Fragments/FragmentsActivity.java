package macbeth.androidsampler.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import macbeth.androidsampler.R;

public class FragmentsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CollectionPagerAdapter adapter;
    private FragmentsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        setTitle("Fragments");

        presenter = new FragmentsPresenter();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_page1:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.nav_page2:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.nav_page3:
                viewPager.setCurrentItem(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private class CollectionPagerAdapter extends FragmentPagerAdapter {
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            switch(i) {
                case 0:
                    fragment = new Fragment1();
                    break;
                case 1:
                    fragment = new Fragment2();
                    break;
                case 2:
                    fragment = new Fragment3();
                    break;
                default:
                    fragment = null;
            }
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString("SystemTime", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                args.putSerializable("Presenter", presenter);
                fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
