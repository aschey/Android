package com.android.BookProject2;

import android.support.v4.app.Fragment;

/**
 * Created by aschey on 2/10/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
