package com.example.bnn.kediri.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bnn.kediri.R;
import com.example.bnn.kediri.listener.UniversalListener;

import java.util.Calendar;
import java.util.Locale;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public Button holder;
    public UniversalListener<Calendar> listener;
    public TimePickerDialog timePickerDialog;
    private int hour, minutes;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, hour, minutes, true);
        timePickerDialog.getWindow().setTitleColor(getResources().getColor(R.color.purple_200));
        timePickerDialog.setTitle("Pilih Jam");
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        minutes = minute;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        holder.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));
        if (listener != null) {
            listener.onSetData(calendar);
        }
    }

    public static String getTwoDigitNumber(int number){
        if(number/10 > 0)return ""+number;
        return "0"+number;
    }
}
