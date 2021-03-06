package com.madhouseapps.financialcalculator;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.view.WindowManager;
import android.widget.TextView;

public class Calculations extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BottomNavigationView navigationView;
    private ViewPager mViewPager;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        Intent i = getIntent();
        int choice = i.getIntExtra("Choice", 1);



        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed},
                new int[] { android.R.attr.state_window_focused}
                // pressed
        };

        /*
        Nav color fix
         */

        int[] EMIcolors = new int[] {
                this.getResources().getColor(R.color.primary_emi),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                this.getResources().getColor(R.color.primary_emi)
        };

        int[] FDcolors = new int[] {
                this.getResources().getColor(R.color.primary_fd),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                this.getResources().getColor(R.color.primary_fd)
        };

        int[] RDcolors = new int[] {
                this.getResources().getColor(R.color.primary_rd),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                this.getResources().getColor(R.color.primary_rd)
        };

        int[] SIPcolors = new int[] {
                this.getResources().getColor(R.color.primary_sip),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                this.getResources().getColor(R.color.primary_sip)
        };

        int[] RPcolors = new int[] {
                this.getResources().getColor(R.color.primary_rp),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                Color.parseColor("#383838"),
                this.getResources().getColor(R.color.primary_rp)
        };

        final ColorStateList emiList = new ColorStateList(states, EMIcolors);
        final ColorStateList fdList = new ColorStateList(states, FDcolors);
        final ColorStateList rdList = new ColorStateList(states, RDcolors);
        final ColorStateList sipList = new ColorStateList(states, SIPcolors);
        final ColorStateList rpList = new ColorStateList(states, RPcolors);

        /*
        Nav color fix ENDS
         */



        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setItemIconTintList(emiList);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        switch (choice) {
            case 1:
                mViewPager.setCurrentItem(0);
                navigationView.setItemIconTintList(emiList);
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case 2:
                mViewPager.setCurrentItem(1);
                navigationView.setItemIconTintList(fdList);
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case 3:
                mViewPager.setCurrentItem(2);
                navigationView.setItemIconTintList(rdList);
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case 4:
                mViewPager.setCurrentItem(3);
                navigationView.setItemIconTintList(sipList);
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(3).setChecked(true);
                break;
            case 5:
                mViewPager.setCurrentItem(4);
                navigationView.setItemIconTintList(sipList);
                navigationView.getMenu().getItem(0).setChecked(false);
                navigationView.getMenu().getItem(4).setChecked(true);
                break;
        }


        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_emi:
                                mViewPager.setCurrentItem(0);
                                navigationView.setItemIconTintList(emiList);

                                break;
                            case R.id.action_fd:
                                mViewPager.setCurrentItem(1);
                                navigationView.setItemIconTintList(fdList);

                                break;
                            case R.id.action_rd:
                                mViewPager.setCurrentItem(2);
                                navigationView.setItemIconTintList(rdList);

                                break;
                            case R.id.action_sip:
                                mViewPager.setCurrentItem(3);
                                navigationView.setItemIconTintList(sipList);
                                break;
                            case R.id.action_retirement:
                                mViewPager.setCurrentItem(4);
                                navigationView.setItemIconTintList(rpList);
                                break;
                        }
                        return false;
                    }
                });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        navigationView.setItemIconTintList(emiList);
                        break;
                    case 1:
                        navigationView.setItemIconTintList(fdList);
                        break;
                    case 2:
                        navigationView.setItemIconTintList(rdList);
                        break;
                    case 3:
                        navigationView.setItemIconTintList(sipList);
                        break;
                    case 4:
                        navigationView.setItemIconTintList(rpList);
                        break;
                    default:
                        navigationView.setItemIconTintList(rdList);
                        break;
                }

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);

                }
                else
                {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }

                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: return new EMICalculation();
                case 1: return new FDCalculation();
                case 2: return new RDCalculation();
                case 3: return new SIPCalculation();
                case 4: return new RetirementCalculation();
                default: return new EMICalculation();
            }

        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EMI";
                case 1:
                    return "FD";
                case 2:
                    return "RD";
                case 3:
                    return "SIP";
                case 4:
                    return "Retirement";
            }
            return null;
        }
    }
}
