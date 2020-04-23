package macbeth.androidsampler.Fragments;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

        presenter = new FragmentsPresenter();  // Something to manage the logic of all of the fragments

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);  // Used to transition between fragments
        setSupportActionBar(myToolbar);

        // The CollecitonPagerAdapter will manage all of the fragments.  When I call setCurrentItem,
        // it will display the appropriate fragment.
        adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0); // Display first fragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Based on the menu selection, select a fragment
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

    public FragmentsPresenter getPresenter() {
        return presenter;
    }

    // The CollectionpagerAdapter defines what Fragment to create
    private class CollectionPagerAdapter extends FragmentPagerAdapter {
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {
            // Based on the id, create a fragment.  This will only happen once (unless the fragment
            // was destroyed in which case it will be re-created.  In normal circumstances, the
            // fragment will remain even if I navigate away from it.
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
                // We can pass information to the Fragment using the Bundle (or the
                // constructors above)
                Bundle args = new Bundle();
                args.putString("SystemTime", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Need to tell it how many fragments there are.
            return 3;
        }

    }
}
