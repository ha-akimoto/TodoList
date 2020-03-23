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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.R;
import com.example.todolist.common.Constants;
import com.example.todolist.databinding.ActivityCreateTaskBinding;
import com.example.todolist.fragment.DatePickerDialogFragment;
import com.example.todolist.viewmodel.CreateTodoViewModel;
import com.example.todolist.viewmodel.CreateTodoViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private int position;
    private int id;
    private boolean compStatus;
    private CreateTodoViewModel viewModel;
    private ActivityCreateTaskBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.position = intent.getIntExtra(Constants.KEY_POSITION, -1);
        this.id = intent.getIntExtra(Constants.KEY_ID, 0);
        this.compStatus = intent.getBooleanExtra(Constants.KEY_COMP_STATUS, false);

        // 日付が設定されていなければ現在の日付を設定
        String date = intent.getStringExtra(Constants.KEY_DATE);
        if (null == date || date.isEmpty()) {
            date = DateFormat.format(Constants.DATE_FORMAT,
                    java.util.Calendar.getInstance()).toString();
        }

        // ViewModelの設定
        this.viewModel = new ViewModelProvider(this,
                new CreateTodoViewModelFactory(getApplication(),
                        intent.getStringExtra(Constants.KEY_TITLE),
                        date,
                        intent.getStringExtra(Constants.KEY_DETAIL)))
                .get(CreateTodoViewModel.class);

        // バインディングの設定
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task);
        this.binding.setViewModel(this.viewModel);
        this.binding.setLifecycleOwner(this);

        // 期限日のTextViewのクリックリスナーの設定（日付入力ダイアログ）

        ((TextView) findViewById(R.id.textView_date))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.KEY_DATE, viewModel.getDate().getValue());
                        DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                        datePicker.setArguments(bundle);
                        datePicker.show(getSupportFragmentManager(), "datePicker");
                    }
                });


    }

    public void onClick(View view) {

        Intent intent = new Intent();

        //テキストボックスに入力された値を取得して文字列に変換
        String title = ((EditText) findViewById(R.id.textInputLayout_title)).getText().toString();
        String date = ((TextView) findViewById(R.id.textView_date)).getText().toString();
        String detail = ((EditText) findViewById(R.id.textInputLayout_detail)).getText().toString();

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

        this.viewModel.getDate().setValue(dateFormat.format(date));
        //((TextView) findViewById(R.id.textView_date)).setText(dateFormat.format(date));
    }
}
