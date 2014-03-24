package com.android.BookProject2;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        int crimeNumber = getIntent().getIntExtra(CrimeFragment.EXTRA_CRIME_NUMBER, 0);
        return CrimeFragment.newInstance(crimeId, crimeNumber);
    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment_land);
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
//        if (fragment == null) {
//            fragment = new CrimeFragment();
//            fm.beginTransaction()
//                    // container view id- tells fragmentManager where fragment's view should appear
//                    // and what id it is in the fragment list
//                    .add(R.id.fragmentContainer, fragment)
//                    .commit();
//        }
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragmentContainer, new CrimeFragment())
//                    .commit();
//        }
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CrimeFragment extends Fragment {
        public static final String EXTRA_CRIME_ID = "com.android.bookproject2.crime_id";
        public static final String EXTRA_CRIME_NUMBER = "com.android.bookproject2.crime_number";
        private Crime mCrime;
        private EditText mTitleField;
        private Button mDateButton;
        private CheckBox mSolvedCheckBox;
        //private int crimeNumber;

        @Override
        // fragment methods must be public because they will be called by other activities
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
            //crimeNumber = getArguments().getInt(EXTRA_CRIME_NUMBER);
            // direct retrieval, simple at the cost of encapsulation of fragment
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_crime, container, false);
            // call view.findbyid instead of activity.findbyid because there is no activity
            mTitleField = (EditText)rootView.findViewById(R.id.crime_title);
            mTitleField.setText(mCrime.getTitle());
            mTitleField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    mCrime.setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mDateButton = (Button)rootView.findViewById(R.id.crime_date);
            mDateButton.setText(mCrime.getDate());
            mDateButton.setEnabled(false);

            mSolvedCheckBox = (CheckBox)rootView.findViewById(R.id.crime_solved);
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    mCrime.setSolved(isChecked);
                }
            });
            return rootView;
        }

        public static CrimeFragment newInstance(UUID crimeId, int crimeNumber) {
            Bundle args = new Bundle();
            args.putSerializable(EXTRA_CRIME_ID, crimeId);
            args.putInt(EXTRA_CRIME_NUMBER, crimeNumber);
            CrimeFragment fragment = new CrimeFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

}
