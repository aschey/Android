package com.android.BookProject2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
        mDate = getArguments().getString(EXTRA_DATE);
        int day = Integer.parseInt(mDate.substring(4, 6));
        int year = Integer.parseInt(mDate.substring(7, mDate.length()));
        Calendar calendar = Calendar.getInstance();
        Date date = new SimpleDateFormat("MMM").parse(mDate, new ParsePosition(0));
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                int m = (i2 + 12) % 12;
                String month = new DateFormatSymbols().getMonths()[m].substring(0, 3);
                mDate = month + " " + i3 + " " + i;
                getArguments().putString(EXTRA_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() != null) {
            //Log.e("date", mDate);
            Intent i = new Intent();
            i.putExtra(EXTRA_DATE, mDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
