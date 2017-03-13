package com.example.alex.avtomanager.layout.setup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.avtomanager.MyApplication;
import com.example.alex.avtomanager.R;

import java.util.Calendar;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutEnter2 extends AppCompatActivity {
    private EditText editTextIntervalKm;
    private Button buttonPhotograph, buttonNext, buttonDate;
    private TextView textViewData;

    private Calendar date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity

        initView();

        setButtonNextBehavoir();

        setButtonDataBehavior();

    }

    private void initView() {
        editTextIntervalKm = (EditText) findViewById(R.id.manual_input_layout_enter);
        buttonPhotograph = (Button) findViewById(R.id.foto_input_layout_enter);
        buttonNext = (Button) findViewById(R.id.next);
        buttonDate = (Button) findViewById(R.id.enter_data_layout_2);
        textViewData = (TextView) findViewById(R.id.text_view_data);
    }

    private void setButtonNextBehavoir() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmp2 = getEditText(editTextIntervalKm);

                checkingKMToZero(tmp2);

                MyApplication.getInstance().setVariableSpeedometer(tmp2);
                startLayoutGo(LayoutEnter3.class);
            }
        });
    }

    private void startLayoutGo(Class activity) {
        startActivity(new Intent(LayoutEnter2.this, activity));
    }

    private int getEditText(EditText editText) {
        //Возвращает значение из EditText в формате int
        int tmp = 0;
        String text = editText.getText().toString();
        if (!(text.length() == 0))
            return Integer.parseInt(text);
        else return tmp;
    }

    //TODO подебажить метод уточнить как работает если последнее значение неинициализированно.
    private int checkingKMToZero(int a) {
        //Если километраж не заполнен, то возвращается его последнее значение
        if (a == 0) return MyApplication.getInstance().getVariableSpeedometer();
        else return a;
    }

    public void setButtonDataBehavior() {
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkingDataToZero();
                setDate(v);
            }
        });
    }

    public void setDate(View v) {
        //отображаем диалоговое окно для выбора даты
        new DatePickerDialog(LayoutEnter2.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDate() {

        MyApplication.getInstance().setVariableLastInspecton(date);

        textViewData.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
        ));
    }

    private void checkingDataToZero() {
        //Если поле Дата не заполнено, то возвращается сегодняшняя дата
        Calendar date1 = MyApplication.getInstance().getVariableLastInspecton();
        if (date1 != null)  date = date1;
        else date = Calendar.getInstance();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };
}
