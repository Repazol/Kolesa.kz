package com.repa.kolesakz;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static List<CarObject> Cars = new ArrayList();
    public static CarObject SelectedCar;

    MainFragment MainFrg;
    PartsFragment PartsFrg;

    String LOG_TAG = "MyLog";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static void ShowCarsInLog () {
        String n;
        String LOG_TAG = "MyLog";
        for (CarObject c:Cars) {
            Log.d (LOG_TAG,"----------------------");
            Log.d (LOG_TAG,c.getTitle()+" Images:"+c.getImagesCount());
            for (int i=0;i<c.getImagesCount();i++) {
                n="";
                if (c.getImageBitmap(i)!=null) {n="not";}
                Log.d (LOG_TAG,i+":"+c.getImage(i)+" is "+n+" null");

            }
        }
    }

    public static void ShowCarsInLogN (CarObject c) {
        String n;
        String LOG_TAG = "MyLog";

        Log.d (LOG_TAG,"----------------------");
        Log.d (LOG_TAG,c.getTitle()+" Images:"+c.getImagesCount());
        for (int i=0;i<c.getImagesCount();i++) {
                n="";
                if (c.getImageBitmap(i)!=null) {n="not";}
                Log.d (LOG_TAG,i+":"+c.getImage(i)+" is "+n+" null");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LOG_TAG = getString(R.string.log_tag);
        Log.d(LOG_TAG, "Start main activity");

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        Log.d(LOG_TAG, "Start main activity Done...");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }
    /*@Override
    public void onBackPressed() {
        // Write your code here
        Log.d(LOG_TAG, "onBackPressed");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            boolean done = getSupportFragmentManager().popBackStackImmediate();
            Log.d(LOG_TAG, " done="+done);
        }

        //super.onBackPressed();
    } */
    public void ShowCarsFragment()
    {
        if (MainFrg==null)
        {
            MainFrg = new MainFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainFrg,"MainFrg")
                .addToBackStack("MainFrag")
                .commit();
    }
    public void ShowPartsFragment()
    {
        if (PartsFrg==null)
        {
            PartsFrg = new PartsFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PartsFrg,"PartsFrg")
                .addToBackStack("PartsFrg")
                .commit();

        //fTrans = getFragmentManager().beginTransaction();
        //fTrans.replace(R.id.container, PartsFrg).addToBackStack("ParstFrag");
        //fTrans.commit();

    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                ShowCarsFragment();

                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                ShowPartsFragment();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
