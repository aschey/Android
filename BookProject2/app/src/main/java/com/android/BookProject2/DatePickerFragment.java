package com.android.BookProject2;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by aschey on 3/24/14.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.android.bookproject2.date";
    private String mDate;

    public static DatePickerFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return new DatePickerDialog.Builder(getActivity())
//                .setTitle(R.string.date_picker_title)
//                .setPositiveButton(android.R.string.ok, null)
//                .create();
        mDate = getArguments().getString(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                mDate =
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        }, year, month, day);

    }
}
