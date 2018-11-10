package com.example.andresavendano.analytica;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Intent i = getIntent();
        int ii =i.getIntExtra("cual",0);
        if(ii==0) {
            mViewPager.setCurrentItem(0);
        }
        else if(ii==1) {
            mViewPager.setCurrentItem(1);
        }
        else if(ii==2) {
            mViewPager.setCurrentItem(2);
        }
        else if(ii==3) {
            mViewPager.setCurrentItem(3);
        }
        else if(ii==4) {
            mViewPager.setCurrentItem(4);
        }
        else if(ii==5) {
            mViewPager.setCurrentItem(5);
        }
        else if(ii==6) {
            mViewPager.setCurrentItem(6);
        }
        else if(ii==7) {
            mViewPager.setCurrentItem(7);
        }
        else if(ii==8) {
            mViewPager.setCurrentItem(8);
        }

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Ingresa una funcion de la forma\n" +
                    "ln(x) = log(x)");
            alertDialog.setTitle("Help");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position){
                case 0:
                    fragment=new FragSimpleGaussian();
                    break;
                case 1:
                    fragment=new FragPartialPivoting();
                    break;
                case 2:
                    fragment=new FragTotalPivoting();
                    break;
                case 3:
                    fragment=new FragLUSimpleGaussian();
                    break;
                case 4:
                    fragment=new FragLUPartial();
                    break;
                case 5:
                    fragment=new FragDirectFactorization();
                    break;
                case 6:
                    fragment=new FragJacobi();
                    break;
                case 7:
                    fragment=new FragGaussSeidel();
                    break;
                case 8:
                    fragment=new FragSorGaussSeidel();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 9;
        }
    }
}
