package com.example.todolist.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener listener;
    String date;

    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener listener, String date) {
        this.listener = listener;
        this.date = date;
    }

    /**
     * 日付選択ダイアログの作成
     *
     * @param savedInstanceState 処理中のインスタンスに保持されている値
     * @return DatePickerDialog 作成したダイアログのインスタンス
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = 0;
        int month = 0;
        int dayOfMonth = 0;

        if (null == this.date || this.date.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = Integer.parseInt(date.substring(0, 4));
            // 月は0~11で表されるので、−1する
            month = Integer.parseInt(date.substring(5, 7)) - 1;
            dayOfMonth = Integer.parseInt(date.substring(9, 10));
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, dayOfMonth);

        return datePickerDialog;
    }
}
