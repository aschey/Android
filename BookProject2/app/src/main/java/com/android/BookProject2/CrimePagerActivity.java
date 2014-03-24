package com.android.BookProject2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by aschey on 3/5/14.
 */
public class CrimePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requires PagerAdapter to provide views
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();
        // adapter needs to be able to add fragments to activity
        // helps viewpager identify fragments' views
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Log.e("position", Integer.toString(position));
                return CrimeActivity.CrimeFragment.newInstance(crime.getId(), position);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        int crimeNumber = getIntent()
                .getIntExtra(CrimeActivity.CrimeFragment.EXTRA_CRIME_NUMBER, 0);
        //Log.e("crimeNumber", Integer.toString(crimeNumber));
        //mViewPager.setCurrentItem(crimeNumber);
        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeActivity.CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++)
        {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
