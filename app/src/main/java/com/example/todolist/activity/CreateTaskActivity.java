package com.example.todolist.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.fragment.DatePickerDialogFragment;
import com.example.todolist.R;
import com.example.todolist.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private int position;
    private int id;
    private boolean compStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();
        this.position = intent.getIntExtra(Constants.KEY_POSITION, -1);
        this.id = intent.getIntExtra(Constants.KEY_ID, 0);
        this.compStatus = intent.getBooleanExtra(Constants.KEY_COMP_STATUS, false);

        ((EditText) findViewById(R.id.editText_title))
                .setText(intent.getStringExtra(Constants.KEY_TITLE));

        ((EditText) findViewById(R.id.editText_detail))
                .setText(intent.getStringExtra(Constants.KEY_DETAIL));

        // 日付が設定されていなければ現在の日付を設定
        String date = intent.getStringExtra(Constants.KEY_DATE);
        if (null == date || date.isEmpty()) {
            ((TextView) findViewById(R.id.textView_date)).setText(
                    DateFormat.format(Constants.DATE_FORMAT, java.util.Calendar.getInstance()));

        } else {
            ((TextView) findViewById(R.id.textView_date)).setText(date);

        }
        // 期限日のTextViewのクリックリスナーの設定（日付入力ダイアログ）

        ((TextView) findViewById(R.id.textView_date))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.KEY_DATE,
                                ((TextView) findViewById(R.id.textView_date)).getText().toString());
                        DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                        datePicker.setArguments(bundle);
                        datePicker.show(getSupportFragmentManager(), "datePicker");
                    }
                });


    }

    public void onClick(View view) {

        Intent intent = new Intent();

        //テキストボックスに入力された値を取得して文字列に変換
        String title = ((EditText) findViewById(R.id.editText_title)).getText().toString();
        String date = ((TextView) findViewById(R.id.textView_date)).getText().toString();
        String detail = ((EditText) findViewById(R.id.editText_detail)).getText().toString();

        intent.putExtra(Constants.KEY_POSITION, this.position);
        intent.putExtra(Constants.KEY_ID, this.id);
        intent.putExtra(Constants.KEY_TITLE, title);
        intent.putExtra(Constants.KEY_DATE, date);
        intent.putExtra(Constants.KEY_DETAIL, detail);
        intent.putExtra(Constants.KEY_COMP_STATUS, this.compStatus);

        setResult(Constants.RESULT_CODE, intent);
        finish();
    }

    /**
     * OnDateSetListenerで必須のメソッド
     * 選択された日付をテキストビューに設定する
     *
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@link Calendar#MONTH})
     * @param dayOfMonth the selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();

        ((TextView) findViewById(R.id.textView_date)).setText(dateFormat.format(date));
    }
}
