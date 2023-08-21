package com.example.bnn.kediri.helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bnn.kediri.R;
import com.example.bnn.kediri.listener.UniversalListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public Button holder;
    public UniversalListener<Calendar> listener;
    public DatePickerDialog dpd;
    final long maxDate;
    final long minDate;

    public DatePickerFragment(long maxDate, long minDate) {
        this.maxDate = maxDate;
        this.minDate = minDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dpd = new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
        dpd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.label_cancel), (dialog, which) -> {
            dialog.dismiss();
        });
        if (minDate != 0) {
            dpd.getDatePicker().setMinDate(minDate);
        }

        if (maxDate != 0)
            dpd.getDatePicker().setMaxDate(maxDate);
        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        String setDate = getTwoDigitNumber(dayOfMonth)+"-"+getTwoDigitNumber(month+1)+"-"+year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String setDate = updateDateInView(calendar);
        if(holder!=null)
            holder.setText(setDate);
        if(listener!=null)
            listener.onSetData(calendar);
    }

    public static String getTwoDigitNumber(int number){
        if(number/10 > 0)return ""+number;
        return "0"+number;
    }

    public String updateDateInView(Calendar calendar) {
        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(calendar.getTime());
    }

}
