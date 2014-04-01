package com.android.BookProject2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aschey on 3/26/14.
 */
public class TimePickerFragment extends DialogFragment{
    public static final String EXTRA_TIME = "com.android.bookproject2.time";
    private String mTime;

    public static TimePickerFragment newInstance(String time) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TIME, time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mTime = getArguments().getString(EXTRA_TIME);
        int hour = Integer.parseInt(mTime.substring(0,2));
        int minutes = Integer.parseInt(mTime.substring(3, mTime.length()));
        Calendar calendar = Calendar.getInstance();
        //Date date = new SimpleDateFormat("MMM").parse(mDate, new ParsePosition(0));
        //calendar.setTime(date);
        //int month = calendar.get(Calendar.MONTH);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minutes) {
                mTime = hour + ":" + minutes;
                getArguments().putString(EXTRA_TIME, mTime);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
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
            Intent i = new Intent();
            i.putExtra(EXTRA_TIME, mTime);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
