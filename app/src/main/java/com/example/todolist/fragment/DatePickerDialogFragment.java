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

import com.example.todolist.activity.CreateTaskActivity;
import com.example.todolist.common.Constants;

public class DatePickerDialogFragment extends DialogFragment {

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

        String date = getArguments().getString(Constants.KEY_DATE);

        if (null == date || date.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = Integer.parseInt(date.substring(0, 4));
            // 月は0~11で表されるので、−1する
            month = Integer.parseInt(date.substring(5, 7)) - 1;
            dayOfMonth = Integer.parseInt(date.substring(8, 10));
        }

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(),
                        (CreateTaskActivity) getActivity(), year, month, dayOfMonth);

        return datePickerDialog;
    }
}
